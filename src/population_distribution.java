import java.util.Scanner;

public class population_distribution {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);

        double initialPopVec[] = {20,10,40,30};
        double[][] leslieMatrix = {{0.50,2.40,1,0},{0.5,0,0,0},{0,0.8,0,0},{0,0,0.5,0}};

        print1D(initialPopVec);
        print2D(leslieMatrix);

        int generationNum, time = 0;

        System.out.println("Insert the generation number to be estimated: ");
        // CONFIRMAR VALIDAÇÃO OU FORMA DE VERIFICAR O NÚMERO DE GERAÇÕES
        do {
            generationNum = read.nextInt();
        } while(generationNum <= 0);

        double[][] multipliedLeslie = new double[leslieMatrix.length][leslieMatrix.length];
        double[][] twiceLeslie = new double[leslieMatrix.length][leslieMatrix.length];
        double[] popVec = new double[leslieMatrix.length];
        double[] normalizedPopVec = new double[popVec.length];

        while(time < generationNum) { //VERIFICAR SE É < OU <=
            System.out.println("YEAR " + time);
            System.out.println("Population Distribution:");
            if(time == 0) {
                print1D(initialPopVec);

                //NORMALIZATION

                System.out.println("Normalized Population Distribution:");
                fillNormalizedPopVec(normalizedPopVec,initialPopVec);
                print1D(normalizedPopVec);

                time++;

            } else {
                fillMultiplicatedLeslieMatrix(twiceLeslie,multipliedLeslie,leslieMatrix,time);
                System.out.println("Multipled Leslie Matrix at time: " + time);
                print2D(multipliedLeslie);

                fillPopulationDistribution(initialPopVec,popVec,leslieMatrix,multipliedLeslie,time);
                print1D(popVec);

                //NORMALIZATION

                System.out.println("Normalized Population Distribution:");
                fillNormalizedPopVec(normalizedPopVec,popVec);
                print1D(normalizedPopVec);
                }
                time++;
            }
    }
    public static void fillPopulationDistribution (double[] initialPopVec, double[] popVec, double[][] leslieMatrix, double[][] multipliedLeslie, int time) {
        double mult = 0;

        if(time == 1) {
            for (int line = 0; line < leslieMatrix.length; line++) {
                for (int column = 0; column < leslieMatrix[line].length; column++) {
                    mult = mult + leslieMatrix[line][column] * initialPopVec[column];
                }
                popVec[line] = mult;
                mult = 0;
            }
        } else {
            for (int line = 0; line < leslieMatrix.length; line++) {
                for (int column = 0; column < leslieMatrix[line].length; column++) {
                    mult = mult + multipliedLeslie[line][column] * initialPopVec[column];
                }
                popVec[line] = mult;
                mult = 0;
            }
        }
    }
    public static void fillMultiplicatedLeslieMatrix (double[][] twiceLeslie, double[][] multipliedLeslie, double[][] leslieMatrix, int time) {
        double sum = 0;

        if (time == 2) {
            for (int line = 0; line < leslieMatrix.length; line++) {
                for (int column = 0; column < leslieMatrix[line].length; column++) {
                    for (int k = 0; k < leslieMatrix.length; k++) {
                        sum += leslieMatrix[line][k] * leslieMatrix[k][column];
                    }
                    twiceLeslie[line][column] = sum;
                    sum = 0;
                }
            }
        } else {
            for (int line = 0; line < leslieMatrix.length; line++) {
                for (int column = 0; column < leslieMatrix[line].length; column++) {
                    for (int k = 0; k < leslieMatrix.length; k++) {
                        sum += twiceLeslie[line][k] * leslieMatrix[k][column];
                    }
                    multipliedLeslie[line][column] = sum;
                    sum = 0;
                }
            }
        }
        twiceLeslie = multipliedLeslie;
    }
    public static double getTotalPopulation(double[] popVec) {
        double sum = 0;

        for (int i = 0; i < popVec.length; i++) {
            sum += popVec[i];
        }
        return sum;
    }
    public static void fillNormalizedPopVec(double[] normalizedPopVec, double[] popVec) {
        double totalPopulation = getTotalPopulation(popVec);

        for (int i = 0; i < popVec.length; i++) {
            normalizedPopVec[i] = popVec[i] / totalPopulation;
        }
    }

    public static void print2D(double[][] array) {
        for (int line = 0; line < array.length; line++) {
            for (int column = 0; column < array[line].length; column++) {
                System.out.print(array[line][column] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void print1D(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("- Class " + i + " : " + array[i]);
        }
        System.out.println();
    }
}
