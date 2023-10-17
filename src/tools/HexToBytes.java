package tools;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;

/**
 * @author emilio
 * @date 2023-10-09 15:19
 */
public class HexToBytes {

    public static byte[] hexStringToByteArray(String hex) throws DecoderException {
//        // use DatatypeConverter: parseHexBinary
//        byte[] bytes = Hex.decodeHex(hexString.toCharArray());
//        return bytes;
        if (hex.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hex.length() / 2];
            int j = 0;
            for(int i = 0; i < hex.length(); i+=2) {
                result[j++] = (byte)Integer.parseInt(hex.substring(i,i+2), 16);
            }
            return result;
        }
    }

}
