package tools;

import algorithm.Setup;
import it.unisa.dia.gas.jpbc.Element;

/**
 * @author emilio
 * @date 2023-10-11 14:13
 */
public class matrixConstruct {

    public static Element[][] matrixConstruct(int n){
        Element[][] A_matrix = new Element[n][n];
        for (int k = 0;k<n;k++){
            for (int m=0;m<n;m++){
                Element kk = Setup.pairing.getZr().newElement(k+1).getImmutable();
                Element mm = Setup.pairing.getZr().newElement(m).getImmutable();
                A_matrix[k][m] = kk.powZn(mm);
//                System.out.println("Matrix A:::"+A_matrix[k][m]);
            }
        }
    return A_matrix;
    }
}
