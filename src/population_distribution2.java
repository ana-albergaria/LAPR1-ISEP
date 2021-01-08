import java.util.Scanner;
import java.util.Arrays;

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

        do {
            generationNum = read.nextInt();
        } while (generationNum <= 0);

        double[] popVec = new double[leslieMatrix.length];
        double[] normalizedPopVec = new double[popVec.length];
        double[] popDim = new double[popVec.length];
        double[] rateVariation = new double[popVec.length-1];
        double dim, rate;

        while (time < generationNum) {
            System.out.println("YEAR " + time);
            System.out.println("Population Distribution:");

            if(time == 0) {
                printPopDistribution(initialPopVec);
                time++;
                int a=time-1;

                //NORMALIZATION

                System.out.println("Normalized Population Distribution:");
                fillNormalizedPopVec(normalizedPopVec,initialPopVec);

                //DIMENSION

                double initialDim=getTotalPopulation(initialPopVec);
                System.out.println("Population Dimension: " + getTotalPopulation(initialPopVec));
                fillArray(initialDim, time-1, popDim);




            } else {
                fillPopulationDistribution(initialPopVec,popVec,leslieMatrix,time);
                printPopDistribution(popVec);
                time++;


                //NORMALIZATION

                System.out.println("Normalized Population Distribution:");
                fillNormalizedPopVec(normalizedPopVec,popVec);
                printPopDistribution(normalizedPopVec);

                //DIMENSION

                dim=getTotalPopulation(popVec);
                System.out.println("Population Dimension: " + dim);
                System.out.println();
                fillArray(dim, time-1, popDim);

            }

        }
        //APAGAR ANTES DE ENTREGAR
        System.out.println(Arrays.toString(popDim));

        time=0;
        while(time+1<generationNum) {
            rate = getRateOfChangeOverTheYears(time, popDim);
            fillArray(rate, time, rateVariation);
            time++;
        }

        //APAGAR ANTES DE ENTREGAR
        System.out.println(Arrays.toString(rateVariation));


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
    /* -------------------------------- APAGAR ANTES DE ENTREGAR ------------------------------------------------------*/
    public static void printMatrix(double[][] array) {
        for (int line = 0; line < array.length; line++) {
            for (int column = 0; column < array[line].length; column++) {
                System.out.print(array[line][column] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static double printPopDistribution(double[] array) {
        double sm = 0;
        for (int i = 0; i < array.length; i++) {
            System.out.println("- Class " + i + " : " + array[i]);
            sm += array[i];
        }
        System.out.println();
        return sm;
    }

    /*-----------------------object = FILL POP DIMENSION AND RATE VARIATION OVER THE YEARS----------------------------*/
    public static void fillArray(double object, int time, double[] array) {
        array[time] = object;
        System.out.println();
    }


    public static double getRateOfChangeOverTheYears(int time, double [] popDim) {
        double nowGeneration = popDim[time];
        double nextGeneration = popDim[time+1];

        double quocient = nextGeneration/nowGeneration;

        return quocient;
    }
}

