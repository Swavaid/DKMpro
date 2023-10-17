package algorithm;

/**
 * @author emilio
 * @date 2023-10-08 14:16
 */
import entity.*;
import it.unisa.dia.gas.jpbc.Element;
import tools.matrixConstruct;
import tools.matrixInvert;
import tools.polynomialEval;

import java.math.BigInteger;

public class KeyRotation {

    public static long rotation(serviceNode[] serviceNodes,storageServer storageServer){
        //each service node randomly selected a_i_0
        long server_rotation = 0;

        long serverRot_1 = 0;
        long serverRot_a = System.nanoTime();
        for (int i=0;i<2*Setup.threshold+1;i++) {
            //randomly choose a polynomial
            serviceNodes[i].rot_ss = Setup.pairing.getZr().newRandomElement().getImmutable();
            serviceNodes[i].rot_coefficient[0] = serviceNodes[i].rot_ss;
            serviceNodes[i].rot_pub_coefficient[0] = Setup.G_generator.powZn(serviceNodes[i].rot_coefficient[0]);
            for (int j = 1; j < Setup.threshold; j++) {
                serviceNodes[i].rot_coefficient[j] = Setup.pairing.getZr().newRandomElement().getImmutable();
                serviceNodes[i].rot_pub_coefficient[j] = Setup.G_generator.powZn(serviceNodes[i].rot_coefficient[j]);
            }
            //compete the value of polynomial f_i(k)

            for (int k = 0; k < 2*Setup.threshold+1; k++) {
                serviceNodes[i].rot_polyEval[k] = polynomialEval.polyEval(serviceNodes[i].rot_coefficient, k);
//                System.out.println("f_" + i + "(" + k + ")" + serviceNodes[i].polyEval[k]);
            }

        }
        long serverRot_b = System.nanoTime();
        serverRot_1 = serverRot_b-serverRot_a;



        long serverRot_2 = 0;
        long serverRot_c = System.nanoTime();
        for (int i=0;i<2*Setup.threshold+1;i++) {
            //each service node sends evaluation to others


            for (int j = 0; j < 2*Setup.threshold+1; j++) {
                Element timesLine = Setup.one_point;
                for (int k=0;k<Setup.threshold;k++){
                    timesLine = timesLine.mul(serviceNodes[j].rot_pub_coefficient[k].pow(BigInteger.valueOf(i).pow(k)));
                }
                //verify
                if (Setup.G_generator.powZn(serviceNodes[j].rot_polyEval[i]) .isEqual(timesLine)){
                    serviceNodes[i].rot_received_coefficient[j] = serviceNodes[j].rot_polyEval[i];
//                    System.out.println("rotation pass");
                }
//                System.out.println("rotation received" + "f_" + j + "(" + i + ")" + serviceNodes[i].rot_received_coefficient[j]);
            }

        }
        long serverRot_d = System.nanoTime();
        serverRot_2 = serverRot_d - serverRot_c;

        long serverRot_3 = 0;
        long serverRot_e = System.nanoTime();
        for (int i=0;i<2*Setup.threshold+1;i++) {

            //after receiving all evaluation, compute secret share
            serviceNodes[i].rot_secret_share = Setup.zero;
            for (int j=0;j<2*Setup.threshold+1;j++){
                serviceNodes[i].rot_secret_share = serviceNodes[i].rot_secret_share.add(serviceNodes[i].rot_received_coefficient[j]);
            }
            serviceNodes[i].rot_public_share = Setup.G_generator.powZn(serviceNodes[i].rot_secret_share);

        }
        long serverRot_f = System.nanoTime();
//            System.out.println("rotation secret share:"+serviceNodes[i].rot_secret_share);
        serverRot_3 = serverRot_f-serverRot_e;



        long serverRot_4 = 0;
        long serverRot_g = System.nanoTime();
        for (int i=0;i<2*Setup.threshold+1;i++){


            //chooses a random
            //polynomial h_i(x) of degree t, such that h_i(0) = ρ_i s_i
            serviceNodes[i].rot_coefficient_later[0] = serviceNodes[i].secret_share.mul(serviceNodes[i].rot_secret_share);
            for (int j=1;j<Setup.threshold;j++){
                serviceNodes[i].rot_coefficient_later[j] = Setup.pairing.getZr().newRandomElement().getImmutable();
            }

            //compete the value of polynomial h_i(j)
            for (int j = 0; j < 2*Setup.threshold+1; j++) {
                serviceNodes[i].rot_polyEval_later[j] = polynomialEval.polyEval(serviceNodes[i].rot_coefficient_later, j);
//                System.out.println("computed: h"+i+"("+j+")"+serviceNodes[i].rot_polyEval_later[j]);
            }


        }
        long serverRot_h = System.nanoTime();

        serverRot_4 =serverRot_h -serverRot_g;

        Element[][] matrix_A = matrixConstruct.matrixConstruct( 2*Setup.threshold+1);
        for (int i=0;i<2*Setup.threshold+1;i++) {
            //each service node sends evaluation_later to others
            for (int j = 0; j < 2*Setup.threshold+1; j++) {
                serviceNodes[i].received_later[j] = serviceNodes[j].rot_polyEval_later[i];
//                System.out.println("received:h"+j+"("+i+")"+serviceNodes[i].received_later[j]);
//                System.out.println("matrix"+(i+1)+(j+1)+":::"+matrix_A[i][j]);
            }
            //reconstruct H(i) and compute new secret key
        }

//        System.out.println("here I am");
        long server_ll = System.nanoTime();
        Element[][] matrix_A_inverse = matrixInvert.inverse(matrix_A);
        long server_kk = System.nanoTime();

        long serverRot_6 = 2*Setup.threshold+1*(server_kk - server_ll);
//        System.out.println("where are you");
//        System.out.println("行列式是多少：："+matrixInvert.determinant(matrix_A));
//        System.out.println("逆的行列式是多少：："+matrixInvert.determinant(matrix_A_inverse));
//        System.out.println("逆的行列式是多少：："+matrixInvert.determinant(matrix_A).invert());


        long serverRot_5 = 0;
        long serverRot_i = System.nanoTime();
        for (int i=0;i<2*Setup.threshold+1;i++){


            serviceNodes[i].new_secret_share = Setup.zero;
            for (int j=0;j<2*Setup.threshold+1;j++){
//                System.out.println("inverse"+(i+1)+(j+1)+":::"+matrix_A_inverse[i][j]);
                serviceNodes[i].new_secret_share =
                        serviceNodes[i].new_secret_share.add(matrix_A_inverse[0][j].mul(serviceNodes[i].received_later[j]));
            }


        }
        long serverRot_j = System.nanoTime();

        serverRot_5 =serverRot_j-serverRot_i;

        //storage server
        for (int i=0;i<2*Setup.threshold+1;i++){
            storageServer.token_shares[i] = serviceNodes[i].rot_secret_share;
            storageServer.public_shares[i] = Setup.G_generator.powZn(serviceNodes[i].new_secret_share);
            System.out.println("finish");
        }

        //reconstruct
        Element[] Lagrange_w = new Element[Setup.threshold];
        for (int j=0;j<Setup.threshold;j++){
            //compute w_j
            Lagrange_w[j] = Setup.one;
            for (int k=0;k<Setup.threshold;k++){
                if (k!=j){
                    Lagrange_w[j] = Lagrange_w[j].mul(BigInteger.valueOf(k).multiply(BigInteger.valueOf(k-j).modInverse(Setup.prime_z)));
//                            (BigInteger.valueOf(k).add(BigInteger.valueOf(-j))));
                }
            }
        }

        storageServer.pub_new_key = Setup.one_point;
        storageServer.update_token = Setup.zero;
        Element test = Setup.zero;
        for (int i=0;i<Setup.threshold;i++){
            storageServer.pub_new_key = storageServer.pub_new_key.mul(storageServer.public_shares[i].powZn(Lagrange_w[i]));
            storageServer.update_token = storageServer.update_token.add(storageServer.token_shares[i].mul(Lagrange_w[i]));
        }
        System.out.println("pub_new_key::"+storageServer.pub_new_key);
        System.out.println("update_token::"+storageServer.update_token);

        server_rotation = serverRot_1+serverRot_2+serverRot_3+serverRot_4+serverRot_5+serverRot_6;
        return server_rotation;
    }

}
