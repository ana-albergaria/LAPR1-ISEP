import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

public class test_eigenValue {
    public static void main(String[] args) {

        double leslieMatrix[][]={{2, 1, 1},{2,3,4}, {-1,-1,-2}};


        double maxVecM [] = new double[leslieMatrix.length];

        double maxEigenValue = maxEigenValue(leslieMatrix,maxVecM);

        System.out.println();
        System.out.println("The eigen value which has the maximum module is: " + maxEigenValue);
        System.out.println("The maximum eigen vector is: ");
        print1D(maxVecM);



    }
    public static double maxEigenValue(double[][] leslieMatrix, double[] maxVecM) {

        Matrix leslie = new Basic2DMatrix(leslieMatrix);

        EigenDecompositor eigenD = new EigenDecompositor(leslie);
        Matrix [] decompLeslie = eigenD.decompose();

        for (int i = 0; i < 2; i++) {
            System.out.println(decompLeslie[i]);
        }

        double eigenVecM [][] = decompLeslie[0].toDenseMatrix().toArray();

        double eigenValM [][] = decompLeslie[1].toDenseMatrix().toArray();

        print2D(eigenVecM);
        System.out.println();
        print2D(eigenValM);



        double maxEigenVal = Math.abs(eigenValM[0][0]);
        int columnMaxEigenVal = 0;

        for (int line = 0; line < eigenValM.length; line++) {
            for (int column = 0; column < eigenValM[line].length; column++) {
                if (Math.abs(eigenValM[line][column]) > maxEigenVal) {
                    maxEigenVal = eigenValM[line][column];
                    columnMaxEigenVal = column;
                }
            }

        }

        fillmaxVecM(maxVecM,eigenVecM,columnMaxEigenVal);
        
        return maxEigenVal;

    }
    public static void fillmaxVecM(double[] maxVecM, double[][] eigenVecM, int columnMaxEigenVal) {

        for (int line = 0; line < eigenVecM.length; line++) {
            for (int column = 0; column < eigenVecM[line].length; column++) {
                if(column == columnMaxEigenVal) {
                    maxVecM[line] = eigenVecM[line][column];
                }
            }
        }
    }
    public static void print2D(double[][] array) {
        for (int linha = 0; linha < array.length; linha++) {
            for (int coluna = 0; coluna < array[linha].length; coluna++) {
                System.out.print(array[linha][coluna] + " ");
            }
            System.out.println();
        }
    }
    public static void print1D(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
