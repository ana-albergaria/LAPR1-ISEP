public class testeValorMaxProprio {
    public static void main(String[] args) {

        double[][] teste = {{1, 0, 0}, {0, 4, 6}, {0, 0, 2}};

        print2D(teste);
        System.out.println();
        System.out.println(findMaxEigenValue(teste));
    }

    public static double findMaxEigenValue(double[][] eigenValM) {

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
        System.out.println("Coluna do valor máximo próprio: " + columnMaxEigenVal);

        return maxEigenVal;
    }
    public static void print2D(double[][] array) {
        for (int line = 0; line < array.length; line++) {
            for (int column = 0; column < array[line].length; column++) {
                System.out.print(array[line][column] + " ");
            }
            System.out.println();
        }
    }
}