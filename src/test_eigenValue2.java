import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

public class test_eigenValue2 {
    public static void main(String[] args) {

        //double leslieMatrix[][]={{0.50, 2.40, 1, 0},{0.5,0, 0, 0}, {0, 0.8, 0, 0}, {0, 0, 0.5, 0}};
        double leslieMatrix[][]={{0.50, 2.40, 1, 0},{0.5,0, 0, 0}, {0, 0.8, 0, 0}, {0, 0, 0.5, 0}};

        //creation and filling of the eigen Vector Matrix and the eigen Value Matrix through Eigen Decomposition
        double eigenVecM [][] = new double[leslieMatrix.length][leslieMatrix.length];
        double eigenValM [][] = new double[leslieMatrix.length][leslieMatrix.length];
        eigenValAndVecMatrix(leslieMatrix,eigenVecM,eigenValM);


        //creation of the maximum Eigen Vector Array
        double maxVecM [] = new double [leslieMatrix.length];
        //creation of the normalized maximum Eigen Vector Array
        double normalizedMaxVecM [] = new double [maxVecM.length];

        //saving the max Eigen Value through the method maxEigenValue and filling the maxVecM through the fillMaxVecM
        double maxEigenValue = findMaxEigenValue(leslieMatrix,eigenValM,eigenVecM,maxVecM);


        //TESTES:
        /*
        print2D(eigenVecM);
        System.out.println();
        print2D(eigenValM);
        System.out.println();
        System.out.println("The eigen value which has the maximum module is: " + maxEigenValue);
        System.out.println();
        System.out.println("The maximum eigen vector is: ");
        print1D(maxVecM);
         */


        double percChangePop = (maxEigenValue-1) * 100;

        System.out.println("ASYMPTOTIC BEHAVIOUR OF THE POPULATION ASSOCIATED TO THE MAXIMUM EIGEN VALUE");
        System.out.println();
        System.out.println("At Steady State there is a specific constant number associated with a specific population vector.");
        System.out.println("The constant number is the eigen value which has the maximum module of the Leslie Matrix representative of the current population.");
        System.out.println("The vector is its respective eigenvector.");
        System.out.println();
        System.out.print("The eigen value which has the maximum module is, approximately: ");
        System.out.printf("%.2f%n", maxEigenValue);
        System.out.println("This eigen value represents the growth rate.");

        if(maxEigenValue > 1) {
            System.out.print("As the eigen value is greater than 1, this means the population is growing and will be, approximately, ");
            System.out.printf("%.0f", percChangePop);
            System.out.println("% larger in size.");
        } else if(maxEigenValue < 1) {
            System.out.print("As the eigen value is lesser than 1, this means the population is decreasing and will be, approximately, ");
            System.out.printf("%.0f", Math.abs(percChangePop));
            System.out.println("% smaller in size.");
        } else {
            System.out.println("As the eigen value is equal to 1, the population will remain constant in size over time.");
        }

        System.out.println();
        System.out.println("The eigenvector associated to the maximum eigenvalue represents the constant population proportions.");
        System.out.println();
        System.out.println("Constant population proportions: (2 decimal places)");
        printPopDistribution(maxVecM);
        System.out.println();
        System.out.println("Normalized constant population proportions: (2 decimal places)");
        fillNormalizedPopVec(normalizedMaxVecM,maxVecM);
        printPopDistribution(normalizedMaxVecM);


    }
    public static void eigenValAndVecMatrix(double[][] leslieMatrix, double[][] eigenVecM, double[][] eigenValM) {
        Matrix leslie = new Basic2DMatrix(leslieMatrix);

        EigenDecompositor eigenD = new EigenDecompositor(leslie);
        Matrix [] decompLeslie = eigenD.decompose();

        double vecM [][] = decompLeslie[0].toDenseMatrix().toArray();
        double valM [][] = decompLeslie[1].toDenseMatrix().toArray();

        //APAGAR FOR ANTES DE ENTREGAR
        for (int i = 0; i < 2; i++) {
            System.out.println(decompLeslie[i]);
        }
        //TERMINA AQUI

        fillEmptyMatrix(eigenVecM,vecM);
        fillEmptyMatrix(eigenValM,valM);

    }

    public static double findMaxEigenValue(double[][] leslieMatrix, double[][] eigenValM, double[][] eigenVecM, double[] maxVecM) {

        double maxEigenVal = Math.abs(eigenValM[0][0]);
        int columnMaxEigenVal = 0;

        for (int line = 0; line < eigenValM.length; line++) {
            for (int column = 0; column < eigenValM.length; column++) {
                if (Math.abs(eigenValM[line][column]) > maxEigenVal) {
                    maxEigenVal = eigenValM[line][column];
                    columnMaxEigenVal = column;
                }
            }
        }
        fillMaxVecM(eigenVecM,columnMaxEigenVal,maxVecM);

        return maxEigenVal;

    }
    public static void fillMaxVecM(double[][] eigenVecM, int columnMaxEigenVal, double[] maxVecM) {

        for (int line = 0; line < eigenVecM.length; line++) {
            for (int column = 0; column < eigenVecM[line].length; column++) {
                if(column == columnMaxEigenVal) {
                    maxVecM[line] = eigenVecM[line][column];
                }
            }
        }
    }
    public static void fillEmptyMatrix(double[][] emptyMatrix, double[][] matrix) {
        for (int line = 0; line < matrix.length; line++) {
            for (int column = 0; column < matrix[line].length; column++) {
                emptyMatrix[line][column] = matrix[line][column];
            }
        }
    }
    //MÉTODO QUE SE REPETE NA POPULATION_DISTRIBUTION
    public static void printPopDistribution(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("- Class " + i + ": ");
            System.out.printf("%.2f%n", array[i]);
        }
    }
    //MÉTODO QUE SE REPETE NA POPULATION_DISTRIBUTION
    public static double getTotalPopulation(double[] popVec) {
        double sum = 0;

        for (int i = 0; i < popVec.length; i++) {
            sum += popVec[i];
        }
        return sum;
    }
    //MÉTODO QUE SE REPETE NA POPULATION_DISTRIBUTION
    public static void fillNormalizedPopVec(double[] normalizedPopVec, double[] popVec) {
        double totalPopulation = getTotalPopulation(popVec);

        for (int i = 0; i < popVec.length; i++) {
            normalizedPopVec[i] = popVec[i] / totalPopulation;
        }
    }
    //APAGAR ANTES DE ENTREGAR
    public static void print2D(double[][] array) {
        for (int line = 0; line < array.length; line++) {
            for (int column = 0; column < array[line].length; column++) {
                System.out.print(array[line][column] + " ");
            }
            System.out.println();
        }
    }
    //APAGAR ANTES DE ENTREGAR
    public static void print1D(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
