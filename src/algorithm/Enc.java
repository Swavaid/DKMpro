package algorithm;

import it.unisa.dia.gas.jpbc.Element;
import tools.AES;
import tools.Hash;
import entity.*;
import tools.HexToBytes;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author emilio
 * @date 2023-10-08 14:16
 */
public class Enc {

    public static long[] Encryption(String plaintext, storageServer storageServer, Client client) throws Exception {

        long[] sender_computation = new long[4];
        Object[] cipher = new Object[2];
        Element ID_message = Setup.pairing.getZr().newRandomElement().getImmutable();
        client.ID_c = ID_message;
        Element random_value = Setup.pairing.getZr().newRandomElement().getImmutable();
//        System.out.println("lll::"+Setup.public_key.powZn(random_value).toString());
        long sender_exG_0 = System.nanoTime();
        Element mek_preimage = Setup.public_key.powZn(random_value);
        long sender_exG_1 = System.nanoTime();
        System.out.println("Sender's Exponentiation::"+(sender_exG_1-sender_exG_0));

        long sender_hash_0 = System.nanoTime();
        byte[] mek = HexToBytes.hexStringToByteArray(Hash.getSHA256(mek_preimage.toString()));
        long sender_hash_1 = System.nanoTime();
        System.out.println("sender's hash :"+(sender_hash_1-sender_hash_0));
//        System.out.println("mek:"+mek);

        String test = AES.encryptAES("test",mek);


        long sender_AES_0 = System.nanoTime();
        String cipher_e = AES.encryptAES(plaintext,mek);
        long sender_AES_1 = System.nanoTime();

        System.out.println("Sender AES ::"+(sender_AES_1-sender_AES_0));

//        System.out.println("length:"+mek.length);
        cipher[0] = Setup.G_generator.powZn(random_value);
        cipher[1] = cipher_e;
//        System.out.println("a:"+Setup.G_generator.powZn(random_value).powZn(Setup.secret_key));
//        System.out.println("b:"+Setup.public_key.powZn(random_value));
        storageServer.ciphertext_store.put(ID_message,cipher);
        System.out.println("ID:"+ID_message);
//        String s = (String) cipher[1];
//        System.out.println("cipher_e:" +s);
        sender_computation[0] = sender_hash_1-sender_hash_0;
        sender_computation[1] = sender_exG_1-sender_exG_0;
        sender_computation[2] = sender_AES_1 -sender_AES_0;
        sender_computation[3] = sender_computation[0] + sender_computation[1] +sender_computation[2];
        return sender_computation;
    }
}
