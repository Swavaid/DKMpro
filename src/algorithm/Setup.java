package algorithm;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
import entity.*;
import tools.*;
import javafx.beans.binding.Bindings;

import java.lang.instrument.Instrumentation;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author emilio
 * @date 2023-10-08 14:16
 */

public class Setup {
    public static TypeACurveGenerator pg=new TypeACurveGenerator(256,512);
    public static PairingParameters typeAParams = pg.generate();
    public static Pairing pairing = PairingFactory.getPairing(typeAParams);

    //initial n key server
    public static int n = 20;
    public static int threshold = 6;
    public static Element G_generator = pairing.getG1().newRandomElement().getImmutable();
    public static Element G_1_generator = pairing.getGT().newRandomElement().getImmutable();
    public static BigInteger prime_group = pairing.getG1().getOrder();
    public static BigInteger prime_z = pairing.getZr().getOrder();
//    public static serviceNode[] serviceNodes = new serviceNode[n];


    public static Element zero = pairing.getZr().newElement(0).getImmutable();
    public static Element one = pairing.getZr().newElement(1).getImmutable();
    public static Element one_point = pairing.getG1().newElement(1).getImmutable();
    public static Element zero_point=pairing.getG1().newElement(0).getImmutable();
    public static Element minus_one = pairing.getZr().newElement(-1).getImmutable();


    public static Element public_key = zero;


    //each service node jointly generate a secret
    public static long shareJointly(Client client,serviceNode[] serviceNodes){

        long server_keyGen = 0;

        client.secret_key = zero;
        //each service node randomly selected a_i_0
        long server_a = System.nanoTime();
        for (int i=0;i<n;i++) {
            serviceNodes[i] = new serviceNode();
            //randomly choose a polynomial


            serviceNodes[i].ss = pairing.getZr().newRandomElement().getImmutable();
            serviceNodes[i].coefficient[0] = serviceNodes[i].ss;
            serviceNodes[i].pub_coefficient[0] = G_generator.powZn(serviceNodes[i].coefficient[0]);
            for (int j = 1; j < threshold; j++) {
                serviceNodes[i].coefficient[j] = pairing.getZr().newRandomElement().getImmutable();
                serviceNodes[i].pub_coefficient[j] = G_generator.powZn(serviceNodes[i].coefficient[j]);
            }
            //compute the value of polynomial f_i(k)
            for (int k = 0; k < n; k++) {
                serviceNodes[i].polyEval[k] = polynomialEval.polyEval(serviceNodes[i].coefficient, k);
//                System.out.println("f_" + i + "(" + k + ")" + serviceNodes[i].polyEval[k]);
            }
        }
        long server_b = System.nanoTime();
        long server_1 = server_b-server_a;

        for (int i=0;i<n;i++) {
            //each service node sends evaluation to others
            for (int j = 0; j < n; j++) {
                Element timesLine = one_point;
                for (int k=0;k<threshold;k++){
                    timesLine = timesLine.mul(serviceNodes[j].pub_coefficient[k].pow(BigInteger.valueOf(i).pow(k)));
                }
                //verify
                if (G_generator.powZn(serviceNodes[j].polyEval[i]) .isEqual(timesLine)){
                    serviceNodes[i].received_coefficient[j] = serviceNodes[j].polyEval[i];
                    System.out.println("pass");
                }
                System.out.println("received" + "f_" + j + "(" + i + ")" + serviceNodes[i].received_coefficient[j]);
            }
        }


        long server_c = System.nanoTime();
        for (int i=0;i<n;i++) {
            //after receiving all evaluation, compute secret share
            serviceNodes[i].secret_share = zero;
           for (int j=0;j<n;j++){
               serviceNodes[i].secret_share = serviceNodes[i].secret_share.add(serviceNodes[i].received_coefficient[j]);
           }
           serviceNodes[i].public_share = G_generator.powZn(serviceNodes[i].secret_share);
//           System.out.println("secret share:"+serviceNodes[i].secret_share);
        }
        long server_d = System.nanoTime();
        long server_2 = (server_d-server_c);



        for (int i=0;i<n;i++){
            client.secret_key =client.secret_key.add(serviceNodes[i].ss);
        }

        public_key = G_generator.powZn(client.secret_key);

        System.out.println(public_key);

        return server_1+server_2;
    }
}
