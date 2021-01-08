import java.util.Scanner;

public class population_distribution2 {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);

        double initialPopVec[] = {1000, 300, 330, 100};
        //double[][] leslieMatrix = {{0.50,2.40,1,0},{0.5,0,0,0},{0,0.8,0,0},{0,0,0.5,0}};
        double[][] leslieMatrix = {{0, 3, 3.17, 0.39}, {0.11, 0, 0, 0}, {0, 0.29, 0, 0}, {0, 0, 0.33, 0}};

        printPopDistribution(initialPopVec);
        printMatrix(leslieMatrix);

        int generationNum, time = 0;


        System.out.println("Insert the generations' number to be estimated: ");
        // CONFIRMAR VALIDAÇÃO OU FORMA DE VERIFICAR O NÚMERO DE GERAÇÕES
        do {
            generationNum = read.nextInt();
        } while (generationNum <= 0);

        double[] popVec = new double[leslieMatrix.length];
        double[] normalizedPopVec = new double[popVec.length];

        while (time < generationNum) { //VERIFICAR SE É < OU <=
            System.out.println("YEAR " + time);
            System.out.println("Population Distribution:");
            if(time == 0) {
                printPopDistribution(initialPopVec);
                time++;

                //NORMALIZATION

                System.out.println("Normalized Population Distribution:");
                fillNormalizedPopVec(normalizedPopVec,initialPopVec);
                printPopDistribution(normalizedPopVec);

            } else {
                fillPopulationDistribution(initialPopVec,popVec,leslieMatrix,time);
                printPopDistribution(popVec);
                time++;

                //NORMALIZATION

                System.out.println("Normalized Population Distribution:");
                fillNormalizedPopVec(normalizedPopVec,popVec);
                printPopDistribution(normalizedPopVec);
            }
        }
    }

    public static void fillPopulationDistribution(double initialPopVec[], double[] popVec, double[][] leslieMatrix, int time) {
        double mult = 0;
        double[] previousPopVec = new double[popVec.length];

        if (time == 1) {
            for (int line = 0; line < leslieMatrix.length; line++) {
                for (int column = 0; column < leslieMatrix[line].length; column++) {
                    mult = mult + leslieMatrix[line][column] * initialPopVec[column];
                }
                popVec[line] = mult;
                mult = 0;
            }
        } else {
            fillPreviousPopVec(previousPopVec,popVec);
            for (int line = 0; line < leslieMatrix.length; line++) {
                for (int column = 0; column < leslieMatrix[line].length; column++) {
                    mult = mult + leslieMatrix[line][column] * previousPopVec[column];
                }
                popVec[line] = mult;
                mult = 0;
            }
        }
    }
    public static void fillPreviousPopVec(double[] previousPopVec, double[] popVec) {
        for (int i = 0; i < popVec.length; i++) {
            previousPopVec[i] = popVec[i];
        }
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
    public static void printMatrix(double[][] array) {
        for (int line = 0; line < array.length; line++) {
            for (int column = 0; column < array[line].length; column++) {
                System.out.print(array[line][column] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void printPopDistribution(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("- Class " + i + " : " + array[i]);
        }
        System.out.println();
    }
}

