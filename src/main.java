import algorithm.Dec;
import algorithm.Enc;
import algorithm.KeyRotation;
import algorithm.Setup;
import entity.*;
import tools.Hash;
import tools.HexToBytes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author emilio
 * @date 2023-10-07 14:39
 */


public class main {

    public static storageServer storageServer;
    public static Client client;
    public static serviceNode[] serviceNodes;
    public static serviceNode[] serviceNodes_ALL;
    public static long average_computation_hash = 0;
    public static long average_computation_ex = 0;
    public static long average_computation_mul = 0;
    public static long average_computation_BP = 0;
    public static long average_computation_AES = 0;
    public static long average_computation_client = 0;
    public static int universe_num = 100;

    public static long sender_hash = 0;
    public static long sender_ex = 0;
    public static long sender_AES = 0;
    public static long sender_computation = 0;

    public static long server_dec = 0;

    public static long server_rot = 0;

    public static void main(String[] args) throws Exception {
        System.out.println("start");
        System.out.println("surprise:::"+Setup.minus_one.mul(Setup.minus_one));
//        System.out.println(Setup.one.mul(Setup.minus_one));
//        System.out.println(Setup.minus_one);
        storageServer = new storageServer();
        client = new Client();
        serviceNodes = new serviceNode[Setup.n];
        serviceNodes_ALL = new serviceNode[universe_num];
        for (int i=0;i<Setup.n;i++){
            serviceNodes[i] = new serviceNode();
        }

        //initialize
        System.out.println("server Setup::::::"+Setup.shareJointly(client,serviceNodes));
//        Setup.shareJointly(client,serviceNodes);

        Enc.Encryption("hello",storageServer,client);
//        for (int i=0;i<100;i++){
//            sender_hash = sender_hash + Enc.Encryption("hello",storageServer,client)[0];
//            sender_ex = sender_ex + Enc.Encryption("hello",storageServer,client)[1];
//            sender_AES = sender_AES + Enc.Encryption("hello",storageServer,client)[2];
//            sender_computation = sender_computation + Enc.Encryption("hello",storageServer,client)[3];
//        }
//        System.out.println("sender_hash:::"+sender_hash/100);
//        System.out.println("sender_ex:::"+sender_ex/100);
//        System.out.println("sender_AES:::"+sender_AES/100);
//        System.out.println("sender:::"+sender_computation/100);
//
        for (int i=0;i<100;i++){
            server_dec = server_dec + Dec.Dec(storageServer,client,serviceNodes);
//            average_computation_ex = average_computation_ex + Dec.Dec(storageServer,client,serviceNodes)[1];
//            average_computation_mul = average_computation_mul + Dec.Dec(storageServer,client,serviceNodes)[2];
//            average_computation_BP = average_computation_BP + Dec.Dec(storageServer,client,serviceNodes)[3];
//            average_computation_AES = average_computation_AES + Dec.Dec(storageServer,client,serviceNodes)[4];
//            average_computation_client = average_computation_client + Dec.Dec(storageServer,client,serviceNodes)[5];
        }
        System.out.println("server_dec:::"+server_dec /100);
//        System.out.println("average_computation_hash::"+average_computation_hash/100);
//        System.out.println("average_computation_ex::"+average_computation_ex/100);
//        System.out.println("average_computation_mul::"+average_computation_mul/100);
//        System.out.println("average_computation_BP::"+average_computation_BP/100);
//        System.out.println("average_computation_AES::"+average_computation_AES/100);
//        System.out.println("average_computation_client::"+average_computation_client/100);


        for (int i=0;i<100;i++){
            server_rot = server_rot + KeyRotation.rotation(serviceNodes,storageServer);
        }
        System.out.println("server rotation:::"+server_rot/100);



//        KeyRotation.rotation(serviceNodes,storageServer);



    }


}
