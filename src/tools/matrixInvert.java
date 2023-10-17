package tools;

import algorithm.Setup;
import it.unisa.dia.gas.jpbc.Element;

/**
 * @author emilio
 * @date 2023-10-11 15:03
 */
public class matrixInvert {

    public static Element[][] inverse(Element[][] A) {
//        Element[][] B = new Element[A.length][A[0].length];
//        for (int i = 0; i < A.length; i++) {
//            for (int j = 0; j < A[0].length; j++) {
//                B[i][j] = A[i][j];
//            }
//        }
        Element[][] C = new Element[A.length][A[0].length];
        System.out.println("come to here");
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                int i_add_j = i+j;
                Element ii = Setup.pairing.getZr().newElement(i_add_j).getImmutable();
                System.out.println("Is this correct"+ii);
                Element minus_one = Setup.pairing.getZr().newElement(-1).getImmutable();
                System.out.println(minus_one);
                C[i][j] = determinant(minor(A, j, i),minor(A, j, i).length).mul(minus_one.powZn(ii)); //waste time
                System.out.println("what about here"+C[i][j]);
            }
        }
        System.out.println("come to here twice");
        Element[][] D = new Element[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                D[i][j] = C[i][j].mul(determinant(A,A.length).invert());
            }
        }

        return D;
    }


    public static Element determinant(Element[][] p,int n) {
        if (n == 1) return p[0][0];
        Element zero = Setup.zero;

        Element exChange = Setup.one; // 记录行列式中交换的次数
        boolean isZero = false; // 标记行列式某一行的最右边一个元素是否为零

        for (int i = 0; i < n; i ++) {// i 表示行号
            if (!p[i][n - 1].isEqual(zero)) { // 若第 i 行最右边的元素不为零
                isZero = true;
                if (i != (n - 1)) { // 若第 i 行不是行列式的最后一行
                    for (int j = 0; j < n; j ++) { // 以此交换第 i 行与第 n-1 行各元素
                        Element temp = p[i][j];
                        p[i][j] = p[n - 1][j];
                        p[n - 1][j] = temp;

                        exChange = exChange.mul(Setup.minus_one);
                    }
                }

                break;
            }
        }

        if (!isZero) return zero; // 行列式最右边一列元素都为零，则行列式为零。


        for (int i = 0; i < (n - 1); i ++) {
            // 用第 n-1 行的各元素，将第 i 行最右边元素 p[i][n-1] 变换为 0，
            // 注意：i 从 0 到 n-2，第 n-1 行的最右边元素不用变换
            if (!p[i][n - 1].isEqual(zero)) {
                // 计算第  n-1 行将第 i 行最右边元素 p[i][n-1] 变换为 0的比例
                Element proportion = p[i][n - 1].mul(p[n - 1][n - 1].invert());

                for (int j = 0; j < n; j ++) {
                    p[i][j] = p[i][j].add(p[n - 1][j].mul(proportion.mul(Setup.minus_one)));
                }
            }
        }

        Element mid = exChange.mul(p[n - 1][n - 1]);
        return mid.mul(determinant(p,n-1));
    }

//    public static Element determinant(Element[][] a) {
////        System.out.println("1");
//        if (a.length == 1) {
//            return a[0][0];
//        }
//        Element det = Setup.zero;
//        for (int i = 0; i < a[0].length; i++) {
//            if (i%2==0){
//                det = det.add(a[0][i].mul(determinant(minor(a, 0, i))));
//            }
//            else{
//                Element mid = a[0][i].mul(determinant(minor(a, 0, i)));
//                Element minus_one = Setup.pairing.getZr().newElement(-1).getImmutable();
//                det = det.add(mid.mul(minus_one));
//            }
//        }
////        System.out.println("2");
//        return det;
//    }


    public static Element factorial(int num){
        int mid = 1;
        for (int i=0;i<num;i++){
            mid = mid*(num-i);
        }
        Element num_E = Setup.pairing.getZr().newElement(mid).getImmutable();
        return num_E;
    }


    public static Element[][] minor(Element[][] b, int i, int j) {
        Element[][] a = new Element[b.length - 1][b[0].length - 1];
        for (int x = 0, y = 0; x < b.length; x++) {
            if (x == i) {
                continue;
            }
            for (int m = 0,n = 0; m < b[0].length; m++) {
                if (m == j) {
                    continue;
                }
                a[y][n] = b[x][m];
                n++;
            }
            y++;
        }
        return a;
    }
}
