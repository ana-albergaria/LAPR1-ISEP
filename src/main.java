import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Scanner;

public class main {
    static Scanner read = new Scanner(System.in);
    public static void main(String[] args) throws IOException, InterruptedException {
        //simulação da entrada por paramêtro do nome do ficheiro
        String fileName = read.next();
        String[] speciesName = speciesName(fileName);
        int size = sizeMatrix(fileName);
        double [] sizeSpecies = new double [size];
        double[][] leslieMatrix = new double[size][size];
        readFile(fileName, sizeSpecies, leslieMatrix);
        //print(sizeSpecies);
        //print1(leslieMatrix);
        int t = popDistribution(sizeSpecies, leslieMatrix);
        callGnuplot(t-1, size);
        assintoticAnalysis(leslieMatrix);
    }
    public static String[] speciesName (String path){
        String[] specie = path.split(".txt");
        return specie;
    }
    public static void readFile (String path, double[] size, double[][] leslie) throws FileNotFoundException {
        String vector;
        String [] auxVector;
        int cont=0, i;
        File archive = new File(path);
        Scanner readFile = new Scanner(archive);

        do{
            vector = readFile.nextLine();
            switch (vector.charAt(0)){
                case 'x':
                    auxVector = transformVector(vector);
                    for (i=0; i<auxVector.length; i++){
                        size[i] = Integer.parseInt(auxVector[i]);
                    }
                    break;
                case 's':
                    auxVector = transformVector(vector);
                    for (i=0; i<leslie.length-1; i++) {
                        leslie[i+1][i] = Double.parseDouble(auxVector[i]);
                    }
                    break;
                case 'f':
                    auxVector = transformVector(vector);
                    for (i=0; i<auxVector.length; i++){
                        leslie[0][i] = Double.parseDouble(auxVector[i]);
                    }
                    break;
            }
            cont++;
        }while (readFile.hasNextLine());

        readFile.close();
    }

    public static String[] transformVector (String vector){
        String[] auxVector = vector.split(", ");
        for (int i=0; i<auxVector.length; i++){
            auxVector[i] = auxVector[i].substring(auxVector[i].indexOf("=")+1);
        }
        return auxVector;
    }

    public static int sizeMatrix (String path) throws FileNotFoundException {
        int size=0;
        File archive = new File(path);
        Scanner readFile = new Scanner(archive);
        do{
            String vector = readFile.nextLine();
            int compare = Character.compare(vector.charAt(0), 'f');
            if (compare==0){
               size = transformVector(vector).length;
            }
        }while (readFile.hasNextLine());

        readFile.close();
        return size;
    }

    public static void print (int[] size){
        for (int i=0; i< size.length; i++){
            System.out.printf("%d ", size[i]);
            System.out.println();
        }
    }

    public static void print1 (double[][] size){
        for (int i=0; i< size.length; i++){
            for (int k=0; k< size.length; k++){
                System.out.printf("%f ", size[i][k]);
            }
            System.out.println();
        }
    }

    public static int popDistribution (double initialPopVec[], double[][] leslieMatrix) throws IOException {
        Scanner read = new Scanner(System.in);

        //ISTO É PARA APAGAR?
        print1D(initialPopVec);
        printMatrix(leslieMatrix);
        //TERMINA AQUI

        int generationNum, t = 0;

        System.out.println("Insert the generations' number to be estimated: ");

        do {
            generationNum = read.nextInt();
        } while (generationNum <= 0);

        double[] popVec = new double[leslieMatrix.length];
        double[] normalizedPopVec = new double[popVec.length];

        double[][] distributionMatrix = new double[generationNum+1][leslieMatrix.length];
        double[][] normDistMatrix = new double[generationNum+1][leslieMatrix.length];

        double[] popDim = new double[generationNum+1];
        double[] rateVariation = new double[generationNum+1];

        double dim, rate;

        for(int time = 0; time <= generationNum; time++) {

            //POPULATION DISTRIBUTION
            fillPopulationDistribution(initialPopVec,popVec,distributionMatrix,leslieMatrix,time);

            //NORMALIZATION
            fillNormalizedPopVec(normalizedPopVec,popVec,normDistMatrix,time);

            //DIMENSION
            dim=getTotalPopulation(popVec);
            fillArray(dim, time, popDim);

            //RATE
            if(time >= 1) {
                rate = getRateOfChangeOverTheYears(time, popDim);
                fillArray(rate, time-1, rateVariation);
            }
        }

        dimensionDataFormat(popDim, rateVariation);

        for (int i = 0; i <= generationNum; i++) {
            printGenerationInfo(i,generationNum,distributionMatrix,normDistMatrix,popDim,rateVariation);
        }

        //CONFIRMAR COM JOÃO QUE ISTO FAZ
        return t;
    }
    public static void generationsDataFormat (double [] popVec, double [] normalizedPopVec, int gen) throws IOException {
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        int filesNum = popVec.length;
        String data = "", fn= "";
        for(int i=0;i<filesNum;i++){
            data =  gen + " " + df.format(popVec[i]) + " " + df.format(normalizedPopVec[i]);
            fn = "class"+(i+1)+".dat";
            dataToFile(fn, data);
        }
    }
    public static void dimensionDataFormat (double [] popDim, double [] rateVariation) throws IOException {
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        String data = "";
        for(int k=0; k < popDim.length; k++){
            data = k + " " + df.format(popDim[k]) + " " + df.format(rateVariation[k]);
            dataToFile("populationTotal.dat", data);
        }
    }
    public static void dataToFile(String fileName, String fileData) throws IOException {
        String textToAppend = fileData;
        File file = new File(fileName);
        if(file.exists()){
            //Set true for append mode
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.newLine();
            writer.write(textToAppend);
            writer.close();
        }else{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(textToAppend);
            writer.close();
        }
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
    public static void fillNormalizedPopVec2(double[] normalizedPopVec, double[] popVec){
        double totalPopulation = getTotalPopulation(popVec);

        for (int i = 0; i < popVec.length; i++) {
            normalizedPopVec[i] = popVec[i] / totalPopulation;
        }
    }

    public static void fillNormalizedPopVec(double[] normalizedPopVec, double[] popVec,double[][] normDistMatrix, int time) throws IOException {
        double totalPopulation = getTotalPopulation(popVec);

        for (int i = 0; i < popVec.length; i++) {
            normalizedPopVec[i] = popVec[i] / totalPopulation;
        }
        generationsDataFormat(popVec, normalizedPopVec, time);
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
    public static void printPopDistribution2(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("- Class " + i + ": ");
            System.out.printf("%.3f%n", array[i]);
        }
    }
    public static void callGnuplot (int gen, int classes) throws IOException, InterruptedException {
        Process process1 = Runtime.getRuntime().exec("gnuplot -c ./testeGnuplot.gp 1 " + classes + " " + gen);
        process1.waitFor();
        deleteDatFiles();
    }
    public static void deleteDatFiles(){
        // Lists all files in folder
        File folder = new File("./");
        File fList[] = folder.listFiles();
        // Searchs .lck
        for (int i = 0; i < fList.length; i++) {
            String pes = String.valueOf(fList[i]);
            if (pes.endsWith(".dat")) {
                // and deletes
                boolean success = (new File(String.valueOf(fList[i])).delete());
            }
        }
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
        double nowGeneration = popDim[time-1];
        double nextGeneration = popDim[time];

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
        if(time != generationNum) {
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

    public static void assintoticAnalysis(double leslieMatrix[][]){

        //creation and filling of the eigen Vector Matrix and the eigen Value Matrix through Eigen Decomposition
        double eigenVecM [][] = new double[leslieMatrix.length][leslieMatrix.length];
        double eigenValM [][] = new double[leslieMatrix.length][leslieMatrix.length];
        eigenValAndVecMatrix(leslieMatrix,eigenVecM,eigenValM);

        //creation of the maximum Eigen Vector Array
        double maxVecM [] = new double [leslieMatrix.length];
        //creation of the normalized maximum Eigen Vector Array
        double normalizedMaxVecM [] = new double [maxVecM.length];

        //saving the max Eigen Value through the method maxEigenValue and filling the maxVecM through the fillMaxVecM
        double maxEigenValue = findMaxEigenValue(eigenValM,eigenVecM,maxVecM);

        double percChangePop = (maxEigenValue-1) * 100;

        System.out.println("ASYMPTOTIC BEHAVIOUR OF THE POPULATION ASSOCIATED TO THE MAXIMUM EIGEN VALUE");
        System.out.println();
        System.out.println("At Steady State there is a specific constant number associated with a specific population vector.");
        System.out.println("The constant number is the eigen value which has the maximum module of the Leslie Matrix representative of the current population.");
        System.out.println("The vector is its respective eigenvector.");
        System.out.println();
        System.out.print("The eigen value which has the maximum module is, approximately: ");
        System.out.printf("%.4f%n", maxEigenValue);
        System.out.println("This eigen value represents the growth rate.");

        if(maxEigenValue > 1) {
            System.out.print("As the eigen value is greater than 1, this means the population is growing and will be, approximately, ");
            System.out.printf("%.0f", percChangePop);
            System.out.println("% larger in size that it was last year.");
        } else if(maxEigenValue < 1) {
            System.out.print("As the eigen value is lesser than 1, this means the population is decreasing and will be, approximately, ");
            System.out.printf("%.0f", Math.abs(percChangePop));
            System.out.println("% smaller in size that it was last year.");
        } else {
            System.out.println("As the eigen value is equal to 1, the population will remain constant in size over time.");
        }

        System.out.println();
        System.out.println("The eigenvector associated to the maximum eigenvalue represents the constant population proportions.");
        System.out.println();
        System.out.println("Constant population proportions: (3 decimal places)");
        printPopDistribution2(maxVecM);
        System.out.println();
        System.out.println("Normalized constant population proportions: (3 decimal places)");
        fillNormalizedPopVec2(normalizedMaxVecM,maxVecM);
        printPopDistribution2(normalizedMaxVecM);
    }
    public static void eigenValAndVecMatrix(double[][] leslieMatrix, double[][] eigenVecM, double[][] eigenValM) {
        Matrix leslie = new Basic2DMatrix(leslieMatrix);

        EigenDecompositor eigenD = new EigenDecompositor(leslie);
        Matrix [] decompLeslie = eigenD.decompose();

        double vecM [][] = decompLeslie[0].toDenseMatrix().toArray();
        double valM [][] = decompLeslie[1].toDenseMatrix().toArray();

        fillEmptyMatrix(eigenVecM,vecM);
        fillEmptyMatrix(eigenValM,valM);

    }
    public static double findMaxEigenValue(double[][] eigenValM, double[][] eigenVecM, double[] maxVecM) {

        double maxEigenVal = Math.abs(eigenValM[0][0]);
        int columnMaxEigenVal = 0;

        for (int line = 0; line < eigenValM.length; line++) {
            for (int column = 0; column < eigenValM.length; column++) {
                if(line == column) {
                    if (Math.abs(eigenValM[line][column]) > maxEigenVal) {
                        maxEigenVal = eigenValM[line][column];
                        columnMaxEigenVal = column;
                    }
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
}


