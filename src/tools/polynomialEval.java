package tools;

import algorithm.Setup;
import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;

import static algorithm.Setup.pairing;

/**
 * @author emilio
 * @date 2023-10-09 00:21
 */
public class polynomialEval {
    public static Element polyEval(Element[] coefficient, int x){
        Element result = Setup.zero;
        Element input_x = pairing.getZr().newElement(x).getImmutable();
        for (int i=0;i<coefficient.length;i++){
            result = result.add(coefficient[i].mul(input_x.pow(BigInteger.valueOf(i))));
        }
        return result;
    }

}
