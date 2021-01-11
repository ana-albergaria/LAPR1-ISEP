import java.util.Scanner;
import java.util.Arrays;

public class FINAL_pop_distribution {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);

        double initialPopVec[] = {1000, 300, 330, 100};
        //double[][] leslieMatrix = {{0.50,2.40,1,0},{0.5,0,0,0},{0,0.8,0,0},{0,0,0.5,0}};
        double[][] leslieMatrix = {{0, 3, 3.17, 0.39}, {0.11, 0, 0, 0}, {0, 0.29, 0, 0}, {0, 0, 0.33, 0}};

        print1D(initialPopVec);
        printMatrix(leslieMatrix);

        int generationNum, t = 0;


        System.out.println("Insert the generations' number to be estimated: ");

        do {
            generationNum = read.nextInt();
        } while (generationNum <= 0);

        double[] popVec = new double[leslieMatrix.length];
        double[] normalizedPopVec = new double[popVec.length];

        double[][] distributionMatrix = new double[generationNum][leslieMatrix.length];
        double[][] normDistMatrix = new double[generationNum][leslieMatrix.length];

        double[] popDim = new double[generationNum];
        double[] rateVariation = new double[generationNum];

        double dim, rate;

        for(int time = 0; time < generationNum; time++) {

            //POPULATION DISTRIBUTION
            fillPopulationDistribution(initialPopVec,popVec,distributionMatrix,leslieMatrix,time);

            //NORMALIZATION
            fillNormalizedPopVec(normalizedPopVec,popVec,normDistMatrix,time);

            //DIMENSION
            dim=getTotalPopulation(popVec);
            fillArray(dim, time, popDim);

        }

        //RATE

        while(t+1<generationNum) {
            rate = getRateOfChangeOverTheYears(t, popDim);
            fillArray(rate, t, rateVariation);
            t++;
        }

        for (int i = 0; i < generationNum; i++) {
            printGenerationInfo(i,generationNum,distributionMatrix,normDistMatrix,popDim,rateVariation);
        }


        /*
        //APAGAR ANTES DE ENTREGAR
        System.out.println("Population Dimension Array:");
        System.out.println(Arrays.toString(popDim));


        //APAGAR ANTES DE ENTREGAR
        System.out.println("Rate Variation Array:");
        System.out.println(Arrays.toString(rateVariation));

        System.out.println("Distribution Matrix:");
        print2D(distributionMatrix);
        System.out.println();

        System.out.println("Normalized Distribution Matrix:");
        print2D(normDistMatrix);
         */

    }


    public static void fillPopulationDistribution(double initialPopVec[], double[] popVec, double[][] distributionMatrix, double[][] leslieMatrix, int time) {
        double mult = 0;
        double[] previousPopVec = new double[popVec.length];

        if (time == 0) {
            for (int i = 0; i < initialPopVec.length; i++) {
                popVec[i] = initialPopVec[i];
            }
            fillMatrix(distributionMatrix,time,popVec);
        } else {
            fillPreviousPopVec(previousPopVec,popVec);
            for (int line = 0; line < leslieMatrix.length; line++) {
                for (int column = 0; column < leslieMatrix[line].length; column++) {
                    mult = mult + leslieMatrix[line][column] * previousPopVec[column];
                }
                popVec[line] = mult;
                mult = 0;
            }
            fillMatrix(distributionMatrix,time,popVec);
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
    public static void fillNormalizedPopVec(double[] normalizedPopVec, double[] popVec,double[][] normDistMatrix, int time) {
        double totalPopulation = getTotalPopulation(popVec);

        for (int i = 0; i < popVec.length; i++) {
            normalizedPopVec[i] = popVec[i] / totalPopulation;
        }
        fillMatrix(normDistMatrix,time,normalizedPopVec);
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
    public static void printPopDistribution(double[][] matrix, int time) {
        for (int line = 0; line < matrix.length; line++) {
            for (int column = 0; column < matrix[line].length; column++) {
                if(line == time) {
                    System.out.print("- Class " + column + ": ");
                    System.out.printf("%.3f%n", matrix[line][column]);
                }
            }
        }
        System.out.println();
    }

    /*-----------------------object = FILL POP DIMENSION AND RATE VARIATION OVER THE YEARS----------------------------*/
    public static void fillArray(double object, int time, double[] array) {
        array[time] = object;
    }
    public static void fillMatrix(double[][] distributionMatrix, int time, double[] popVec) {
        for (int line = time; line < (time+1); line++) {
            for (int column = 0; column < popVec.length; column++) {
                distributionMatrix[line][column] = popVec[column];
            }
        }
    }
    public static double getRateOfChangeOverTheYears(int time, double [] popDim) {
        double nowGeneration = popDim[time];
        double nextGeneration = popDim[time+1];

        double quocient = nextGeneration/nowGeneration;

        return quocient;
    }
    public static void printGenerationInfo(int time, int generationNum, double[][] distributionMatrix, double[][] normDistMatrix, double[] popDim, double[] rateVariation) {
        System.out.println("GENERATION " + time);
        System.out.println("Population Distribution:");
        printPopDistribution(distributionMatrix, time);
        System.out.println("Normalized Population Distribution:");
        printPopDistribution(normDistMatrix, time);
        System.out.println("Population Dimension: " + popDim[time]);
        if(time != generationNum-1) {
            System.out.println("Rate Variation between generation " + time + " and generation " + (time + 1) + ": " + rateVariation[time]);
        } else {
            System.out.println("For this generation, there is no Rate Variation.");
        }
        System.out.println();
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


