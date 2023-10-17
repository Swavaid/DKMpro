package algorithm;

/**
 * @author emilio
 * @date 2023-10-08 14:16
 */
import entity.*;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import tools.AES;
import tools.Hash;
import tools.HexToBytes;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Dec {

    public static long Dec(storageServer storageServer, Client client, serviceNode[] serviceNodes) throws Exception {
        //request for the ciphertext with ID_M
        long server_DEC = 0;
        Element cipher_w = (Element)storageServer.ciphertext_store.get(client.ID_c)[0];
        String cipher_e = (String) storageServer.ciphertext_store.get(client.ID_c)[1];
        Element random_blind = Setup.pairing.getZr().newRandomElement().getImmutable();
        long[] evaluation_client = new long[6];

        long client_exG_0 = System.nanoTime();
        Element blind_input = cipher_w.powZn(random_blind);
        long client_exG_1 = System.nanoTime();

        // request for the secret key, send g^r to all key servers, and each service
        // node sends evaluation share back to client
        long total_BP = 0;
        long total_BP_divided = 0;
        int valid_num = 0;

        long server_dec_0 = System.nanoTime();
        for (int i=0;i<Setup.n;i++) {
            serviceNodes[i].blind_input = blind_input;
            serviceNodes[i].evaluation_share = serviceNodes[i].blind_input.powZn(serviceNodes[i].secret_share);
        }
        long server_dec_1 = System.nanoTime();
        server_DEC = server_dec_1 - server_dec_0;



        for (int i=0;i<Setup.n;i++){
            long client_BP_0 = System.nanoTime();
            Element left = Setup.pairing.pairing(serviceNodes[i].evaluation_share,Setup.G_generator);
            Element right = Setup.pairing.pairing(blind_input,serviceNodes[i].public_share);
            long client_BP_1 = System.nanoTime();
            total_BP = total_BP + (client_BP_1-client_BP_0);
            if (left.isEqual(right)){
                client.received_eval_share[i] = serviceNodes[i].evaluation_share;
                valid_num++;
                if(valid_num == Setup.threshold){
                    break;
                }
//                System.out.println("Dec_Test_pass");
            }

        }
        server_DEC = server_DEC/Setup.n;
        total_BP_divided = total_BP / Setup.threshold;
        System.out.println("client BP::"+total_BP);

        //The client reconstruct evaluation, and un-blind it
        //lagrange
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

        client.blind_evaluation = Setup.one_point;
        long total_of_t = 0;
        long total_of_t_divided = 0;
        long total_mul = 0;
        long total_mul_divided = 0;
        for (int i=0;i<Setup.threshold;i++){
            long client_exG_b = System.nanoTime();
            Element block_Lan = client.received_eval_share[i].powZn(Lagrange_w[i]);
            long client_exG_a = System.nanoTime();
            total_of_t = total_of_t+(client_exG_a-client_exG_b);

            long client_mul_b = System.nanoTime();
            client.blind_evaluation = client.blind_evaluation.mul(block_Lan);
            long client_mul_a = System.nanoTime();
            total_mul = total_mul + (client_mul_a-client_mul_b);
        }
        total_of_t_divided = total_of_t /Setup.threshold;
        total_mul_divided = total_mul / Setup.threshold;

        long client_exG_2 = System.nanoTime();
        client.evaluation = client.blind_evaluation.powZn(random_blind.invert());
        long client_exG_3 = System.nanoTime();

        long client_ex = client_exG_1-client_exG_0+client_exG_3-client_exG_2+total_of_t;
        System.out.println("client Exponentiation ::"+client_ex);
        System.out.println("client multiplication::"+total_mul);
//        System.out.println("lll::"+client.evaluation.toString());
//        System.out.println("lll::"+cipher_w.powZn(client.secret_key).toString());
        //generate mek

        long client_hash_0 = System.nanoTime();
        client.mek = HexToBytes.hexStringToByteArray(Hash.getSHA256(client.evaluation.toString()));
        long client_hash_1 = System.nanoTime();
        System.out.println("client to hash:"+(client_hash_1-client_hash_0));

//        System.out.println("mek:"+client.mek);
        String test = AES.decryptAES(AES.encryptAES("test",client.mek), client.mek);

        long client_AES_0 = System.nanoTime();
        client.plaintext = AES.decryptAES(cipher_e, client.mek);
        long client_AES_1 = System.nanoTime();
        long AES_client = client_AES_1-client_AES_0;
        System.out.println("Client AES::"+(client_AES_1-client_AES_0));
        System.out.println(client.plaintext);


//        long total_average = 0;
        long total_computation = AES_client+total_BP+total_mul+client_ex+client_hash_1-client_hash_0;
//        for (int i=0;i<100;i++){
//            total_average = total_average = total_computation;
//        }

        System.out.println("total computation:"+total_computation);
        evaluation_client[0] = client_hash_1 - client_hash_0;
        evaluation_client[1] = client_exG_1-client_exG_0+client_exG_3-client_exG_2+total_of_t_divided;
        evaluation_client[2] = total_mul_divided;
        evaluation_client[3] = total_BP_divided;
        evaluation_client[4] = AES_client;
        evaluation_client[5] = total_computation;
        return server_DEC;
    }



}
