package tools;

/**
 * @author emilio
 * @date 2023-10-09 01:17
 */
import algorithm.Setup;
import it.unisa.dia.gas.jpbc.Element;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {


    //SHA256
    public static String getSHA256(String str) throws Exception{
        MessageDigest messageDigest;
        messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(str.getBytes("UTF-8"));
        String encodestr = byte2Hex(messageDigest.digest());
        return encodestr;
    }

    private static String byte2Hex(byte[] bytes) throws Exception{
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<bytes.length;i++){
            String temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

//    public static Element HashGToZ(Element a) throws Exception {
//
//        Element Hash_to_Z= Setup.pairing.getG1().newElement().setFromHash(getSHA256(a,Setup.messageDigest), 0, getSHA256(a).length);
//
//        return Hash_to_Z;
//    }
//
//    public static Element HashGToG(Element b) throws Exception {
//        Element Hash_to_G= Setup.pairing.getZr().newElement().setFromHash(getSHA256(b,Setup.messageDigest), 0, getSHA256(b).length);
//
//        return Hash_to_G;
//    }

}
