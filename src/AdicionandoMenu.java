import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Scanner;

public class AdicionandoMenu {
    static Scanner read = new Scanner(System.in);
    public static void main(String[] args) throws IOException, InterruptedException {
        switch (args.length){
            case 0:
                System.out.println("Insira o nome da espécie a analisar:");
                String speciesName = read.nextLine();
                System.out.println("Insira quantidade de faixas etárias:");
                int ageClass = read.nextInt();
                double[] initialPopulation = new double[ageClass];
                fillClasse(initialPopulation);
                System.out.println("Insira a taxa de sobrevivência dos indivíduos reprodutores");
                double[][] leslieMatrix = new double[ageClass][ageClass];
                fillSurviveRate(leslieMatrix);
                System.out.println("Insira o número médio de indivíduos reprodutores gerados por um indivíduo reprodutor:");
                fillFecundityRate(leslieMatrix);
                System.out.println("Insira o número de gerações a estimar: ");
                int numberOfGenerations = read.nextInt();
                read.nextLine();

                //POPULATION DISTRIBUTIONS
                double[] popVec = new double[leslieMatrix.length];
                double[] normalizedPopVec = new double[popVec.length];
                double[][] distributionMatrix = new double[numberOfGenerations+1][leslieMatrix.length];
                double[][] normDistMatrix = new double[numberOfGenerations+1][leslieMatrix.length];
                double[] popDim = new double[numberOfGenerations+1];
                double[] rateVariation = new double[numberOfGenerations+1];

                menu(numberOfGenerations, initialPopulation, popVec, distributionMatrix,
                        leslieMatrix, normalizedPopVec, normDistMatrix, popDim, rateVariation);
                //TRANSFORMEI O CASE DO -V -R EM UM MÉTODO E NO CASE SÓ PRINTO, POIS OS DADOS SÃO NECESSARIOS PARA PLOTAGEM DOS GRAFICOS
                /*fillDimArr(numberOfGenerations, distributionMatrix, popDim);
                fillRatesArr(numberOfGenerations, popDim, rateVariation);
                dimensionDataFormat(popDim, rateVariation);*/

                /*System.out.println("Para aceder as funcionalidades do programa, escreva devidamente espaçado as funcionalidades pretendidas:");
                System.out.println("Digite -e para obter os valores e vetores próprios associados a matriz de Leslie");
                System.out.println("Digite -v para obter a dimensão da população a cada geração");
                System.out.println("Digite -r para obter a variação da população entre as gerações");
                String answers = read.nextLine();
                String[] formattedAnswers = answers.split(" ");
                for (int i=0; i<formattedAnswers.length; i++){
                    switch (formattedAnswers[i]){
                        case "-e":
                            assintoticAnalysis(leslieMatrix);
                            break;
                        case "-v":
                            System.out.println("\nPOPULATION DIMENSIONS\n");
                            for (int time = 0; time <= numberOfGenerations; time++) {
                                System.out.printf("GENERATION " + time + " - %.2f", popDim[time] );
                                System.out.println();
                            }
                            //perguntar se pretende apenas visualizar ou salvar o gráfico
                            break;
                        case "-r":
                            System.out.println("\nRATE VARIATIONS BETWEEN GENERATIONS: \n");
                            for (int time = 0; time <= numberOfGenerations; time++) {
                                if(time != numberOfGenerations) {
                                    System.out.printf(time + " AND " + (time + 1) + ": %.2f", rateVariation[time]);
                                    System.out.println();
                                } else {
                                    System.out.println("For the last generation (" + time + "), there is no Rate Variation.\n");
                                }
                            }
                            break;
                    }
                }*/
                //ask the user the necessary questions for iteractive modes
                //isert the necessary method
                //case 2 split the array after -n to take the arquiche name
                break;
            case 2:
                break;
            case 9:
                //no interactive mode with all the features
                break;
            case 4:
                //no interactive mode without any features
                break;
            case 7: case 8:
                //check how features the user want
                // -e augevalues and augevectors
                // -v dimension of population
                // -r variation of population in each generation

        }
        //simulação da entrada por paramêtro do nome do ficheiro
        /*String fileName = read.next();
        String[] speciesName = speciesName(fileName);
        int size = sizeMatrix(fileName);
        double [] sizeSpecies = new double [size];
        double[][] leslieMatrix = new double[size][size];
        readFile(fileName, sizeSpecies, leslieMatrix);
        //print(sizeSpecies);
        //print1(leslieMatrix);
        // ver o último paramêtro
        //ver como usar dependendo do método
        popDistribution(sizeSpecies, leslieMatrix, 10);
        callGnuplot(9, size);
        assintoticAnalysis(leslieMatrix);*/
    }
    public static void menu(int numberOfGenerations, double[]initialPopulation, double[]popVec,
                            double[][]distributionMatrix, double[][]leslieMatrix, double[]normalizedPopVec,
                            double[][]normDistMatrix, double [] popDim, double[] rateVariation) throws IOException, InterruptedException { // menu principal
        int option = 0;
        popDistribution(initialPopulation, leslieMatrix, numberOfGenerations, popVec, normalizedPopVec,
                distributionMatrix, normDistMatrix, popDim, rateVariation);
        do {
            System.out
                    .println("\nDigite o numero da funcionalidade desejada:");
            System.out.println("\n ===================================================================================================================================");
            System.out.println("|     1 - Gerar a distribuição da população e distribuição normalizada pelo total da população para cada instante de tempo           |");
            System.out.println("|     2 - Obter a dimensão da população a cada geração                                                                               |");
            System.out.println("|     3 - Obter os valores e vetores próprios associados a matriz de Leslie                                                          |");
            System.out.println("|     4 - Obter a variação da população entre as gerações                                                                            |");
            System.out.println("|     5 - Plotagem de graficos                                                                                                       |");
            System.out.println("|     6 - Executar todas funcionalidades                                                                                             |");
            System.out.println("|     9 - Inserir dados de outra especie                                                                                             |");
            System.out.println("|     0 - Sair                                                                                                                       |");
            System.out.println("============================================================================================================ =========================\n");

            System.out.println("Opção -> ");
            option = read.nextInt();
            read.nextLine();
            System.out.print("\n");

            switch (option) {
                case 1:
                    System.out.println("POPULATION DISTRIBUTIONS\n");
                    for (int time = 0; time <= numberOfGenerations; time++) {
                        printPopDistribution(distributionMatrix, time);
                        printNormDistribution(normDistMatrix,time);
                    }
                    break;
                case 2:
                    System.out.println("\nPOPULATION DIMENSIONS\n");
                    for (int time = 0; time <= numberOfGenerations; time++) {
                        System.out.printf("GENERATION " + time + " - %.2f", popDim[time] );
                        System.out.println();
                    }
                    break;
                case 3:
                    assintoticAnalysis(leslieMatrix);
                    break;
                case 4:
                    System.out.println("\nRATE VARIATIONS BETWEEN GENERATIONS: \n");
                    for (int time = 0; time <= numberOfGenerations; time++) {
                        if(time != numberOfGenerations) {
                            System.out.printf(time + " AND " + (time + 1) + ": %.2f", rateVariation[time]);
                            System.out.println();
                        } else {
                            System.out.println("For the last generation (" + time + "), there is no Rate Variation.\n");
                        }
                    }
                    break;
                case 5:
                    menuGraphs((numberOfGenerations-1), popVec.length);
                    break;
                case 0:
                    deleteDatFiles();
                    break;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        } while (option != 0);
    }
    public static void menuGraphs(int gens, int classes) throws IOException, InterruptedException {
        int option = 0;
        //do {
            System.out.println("\nInsira separando por espaços os graficos que deseja visualizar:");
            System.out.println("\n ===================================================================================================================================");
            System.out.println("|     1 - Distribuição da população                                                                                                   |");
            System.out.println("|     2 - Distribuição normalizada pelo total da população                                                                            |");
            System.out.println("|     3 - Dimensão da população ao longo do tempo                                                                                     |");
            System.out.println("|     4 - Variações da dimensão população entre as gerações                                                                           |");
            System.out.println("|     0 - Voltar                                                                                                                      |");
            System.out.println("============================================================================================================ =========================\n");

            System.out.print("Opção -> ");
            String optionString = read.nextLine();
            String [] options = optionString.split(" ");
            System.out.print("\n");
            for(int g = 0; g< options.length; g++){
                System.out.println(options[g]);
                switch (Integer.parseInt(options[g])) {
                    case 1:
                        showGnuplotted(gens, classes, Integer.parseInt(options[g]));
                        option = Integer.parseInt(options[g]);
                        break;
                    case 2:
                        showGnuplotted(gens, classes, Integer.parseInt(options[g]));
                        option = Integer.parseInt(options[g]);
                        break;
                    case 3:
                        showGnuplotted(gens, classes, Integer.parseInt(options[g]));
                        option = Integer.parseInt(options[g]);
                        break;
                    case 4:
                        showGnuplotted(gens, classes, Integer.parseInt(options[g]));
                        option = Integer.parseInt(options[g]);
                        break;
                    case 0:
                        option = Integer.parseInt(options[g]);
                        break;
                    default:
                        System.out.println("Opção Inválida!");
                        break;
                }
            }
            if(option !=0) {
                System.out.println("Gostaria de guardar os graficos gerados?");
                System.out.println("\n ================");
                System.out.println("|     1 - Sim      |");
                System.out.println("|     2 - Não      |");
                System.out.println("===================\n");
                System.out.print("Opção -> ");
                int save = read.nextInt();
                read.nextLine();
                System.out.print("\n");
                switch (save) {
                    case 1:
                        System.out.println("Qual formato o ficheiro deve ser guardado?");
                        System.out.println("\n ================");
                        System.out.println("|     1 - PNG      |");
                        System.out.println("|     2 - EPS      |");
                        System.out.println("|     3 - TXT      |");
                        System.out.println("===================\n");
                        System.out.print("Opção -> ");
                        int format = read.nextInt();
                        read.nextLine();
                        System.out.print("\n");
                        for (int k = 0; k < options.length; k++) {
                            saveGnuplotted(gens, classes,Integer.parseInt(options[k]) , format);
                        }
                        break;
                    case 2:
                        break;
                }
            }
    //    } while (option != 0);
    }
    public static void fillClasse (double[] array){
        for (int i=0;i < array.length;i++){
            System.out.print("Classe " + (i+1) + ":");
            array[i] = read.nextDouble();
        }
    }
    public static void fillSurviveRate (double[][] array){
        for (int i=0; i<array.length-1 ;i++){
            System.out.print("Classe " + (i+1) + ":");
            array[i+1][i] = read.nextDouble();
        }
    }
    public static void fillFecundityRate (double[][] array){
        for (int i=0; i<array.length ;i++){
            System.out.print("Classe " + (i+1) + ":");
            array[0][i] = read.nextDouble();
        }
    }
    public static void fillDimArr (int numberOfGenerations, double[][] distributionMatrix, double[] popDim){
        for (int time = 0; time <= numberOfGenerations; time++) {
            double dim=getDimension(distributionMatrix,time);
            fillArray(dim, time, popDim);
        }
    }
    public static void fillRatesArr (int numberOfGenerations, double[] popDim, double[] rateVariation){
        for (int time = 1; time <= numberOfGenerations; time++) {
            double rate = getRateOfChangeOverTheYears(time, popDim);
            fillArray(rate, time - 1, rateVariation);
        }
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
    public static void popDistribution (double initialPopVec[], double[][] leslieMatrix, int generationNum,  double[] popVec, double[] normalizedPopVec,
                                        double[][] distributionMatrix, double[][] normDistMatrix, double[] popDim, double[] rateVariation) throws IOException {
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


    }
    public static void generationsDataFormat (double [] popVec, double [] normalizedPopVec, int gen) throws IOException {
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        int filesNum = popVec.length;
        String data = "", fn= "";
        for(int i=0;i<filesNum;i++){
            data =  gen + " " + df.format(popVec[i]) + " " + df.format(normalizedPopVec[i]);
            fn = "gnuplot/class"+(i+1)+".dat";
            dataToFile(fn, data);
        }
    }
    public static void dimensionDataFormat (double [] popDim, double [] rateVariation) throws IOException {
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        String data = "";
        for(int k=0; k < popDim.length; k++){
            data = k + " " + df.format(popDim[k]) + " " + df.format(rateVariation[k]);
            dataToFile("gnuplot/populationTotal.dat", data);
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
    public static double getDimension(double[][] distributionMatrix, int time) {
        double dim = 0;

        for (int line = time; line < (time+1); line++) {
            for (int column = 0; column < distributionMatrix[line].length; column++) {
                dim += distributionMatrix[line][column];
            }
        }
        return dim;
    }
    public static void fillNormalizedPopVec2(double[] normalizedPopVec, double[] popVec){
        double totalPopulation = getTotalPopulation(popVec);

        for (int i = 0; i < popVec.length; i++) {
            normalizedPopVec[i] = (popVec[i] / totalPopulation) * 100;
        }
    }

    public static void fillNormalizedPopVec(double[] normalizedPopVec, double[] popVec,double[][] normDistMatrix, int time) throws IOException {
        double totalPopulation = getTotalPopulation(popVec);

        for (int i = 0; i < popVec.length; i++) {
            normalizedPopVec[i] = (popVec[i] / totalPopulation) * 100;
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
        System.out.println("GENERATION " + time);
        System.out.println("Population Distribution:");
        for (int line = 0; line < matrix.length; line++) {
            for (int column = 0; column < matrix[line].length; column++) {
                if(line == time) {
                    System.out.print("- Class " + column + ": ");
                    System.out.printf("%.2f%n", matrix[line][column]);
                }
            }
        }
        System.out.println();
    }
    public static void printNormDistribution(double[][] matrix, int time) {
        System.out.println("Normalized Population Distribution:");
        for (int line = 0; line < matrix.length; line++) {
            for (int column = 0; column < matrix[line].length; column++) {
                if(line == time) {
                    System.out.printf("- Class " + column + ": %.2f", matrix[line][column]);
                    System.out.println("%");
                }
            }
        }
        System.out.println();
    }
    public static void printPopDistribution2(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.printf("- Class " + i + ": %.2f", array[i]);
            System.out.println("%");
        }
    }
    public static void showGnuplotted (int gen, int classes, int nfile) throws IOException, InterruptedException {
        Process process1 = Runtime.getRuntime().exec("gnuplot -c ./gnuplot/show"+ nfile +".gp " + classes + " " + gen);
        //process1.waitFor();
        //deleteDatFiles();
    }
    public static void saveGnuplotted (int gen, int classes, int nfile, int option) throws IOException, InterruptedException {
        Process process1 = Runtime.getRuntime().exec("gnuplot -c ./gnuplot/save"+ nfile +".gp "+ option + " " + classes + " " + gen);
        process1.waitFor();
        //deleteDatFiles();
    }
    public static void deleteDatFiles(){
        // Lists all files in folder
        File folder = new File("./gnuplot/");
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
        System.out.printf("Population Dimension: %.2f", popDim[time] );
        System.out.println();
        if(time != generationNum) {
            System.out.print("Rate Variation between generation " + time + " and generation " + (time + 1) + ": ");
            System.out.printf("%.2f%n",rateVariation[time]);
            System.out.println();
        } else {
            System.out.println("For this generation, there is no Rate Variation.");
        }
        System.out.println();
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
        //System.out.println("Constant population proportions: (2 decimal places)");
        //printPopDistribution2(maxVecM);
        //System.out.println();
        System.out.println("Normalized constant population proportions: (2 decimal places)");
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
