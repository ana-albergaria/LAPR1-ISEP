import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class main {
    static final String POP_DIM_FILE_PATH = "gnuplot/populationTotal.dat";
    static final String POP_CLASSES_BASE_FILE_PATH = "gnuplot/class";
    static final String FOLDER_DATA_FILES = "./gnuplot/";
    static final int NUMBER_OF_GRAPHS = 4;

    static Scanner read = new Scanner(System.in);
    public static void main(String[] args) throws IOException, InterruptedException {
        switch (args.length){
            case 0:
               dataInsert();
                break;
            case 2:
                String fileNameInput = args[1];
                fileInitOrganizer(fileNameInput);
                break;
            default:
                analysisOfDatas(args);
                break;
        }
    }
    /*=========================================MENUS==============================================*/
    public static void dataInsert() throws IOException, InterruptedException {
        System.out.println("\nDe qual forma gostaria de inicializar os dados?:");
        System.out.println("\n ==============================");
        System.out.println("|     1 - Manualmente            |");
        System.out.println("|     2 - Ficheiro de texto      |");
        System.out.println("|     0 - Sair                   |");
        System.out.println("=================================\n");
        System.out.print("Opção -> ");
        int init = read.nextInt();
        read.nextLine();
        System.out.print("\n");

        switch (init) {
            case 1:
                manualStartOrganizer();
                break;
            case 2:
                System.out.println("Insira o nome do ficheiro:");
                String fileNameInput = read.nextLine();
                fileInitOrganizer(fileNameInput);
                break;
            case 0:
                break;
            default:
                System.out.println("Opção Inválida!");
                break;
        }
    }
    public static void menu(int numberOfGenerations, double[]initialPopulation, double[]popVec,
                            double[][]distributionMatrix, double[][]leslieMatrix, double[]normalizedPopVec,
                            double[][]normDistMatrix, double [] popDim, double[] rateVariation, String specie) throws IOException, InterruptedException { // menu principal
        int option;
        getGenerationsData(initialPopulation, leslieMatrix, numberOfGenerations, popVec, normalizedPopVec,
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
            System.out.println("|     7 - Alterar numero de gerações a estimar                                                                                       |");
            System.out.println("|     9 - Inserir dados de outra especie                                                                                             |");
            System.out.println("|     0 - Sair                                                                                                                       |");
            System.out.println("======================================================================================================================================\n");

            System.out.print("Opção -> ");
            option = read.nextInt();
            read.nextLine();
            System.out.print("\n");

            switch (option) {
                case 1:
                    printTotalPopDistribution(numberOfGenerations,distributionMatrix,normDistMatrix);
                    break;
                case 2:
                    printPopDim(popDim,numberOfGenerations);
                    break;
                case 3:
                    assintoticAnalysis(leslieMatrix);
                    break;
                case 4:
                    printRateVariation(rateVariation, numberOfGenerations);
                    break;
                case 5:
                    menuGraphs((numberOfGenerations), popVec.length, specie, distributionMatrix, rateVariation, popDim);
                    break;
                case 6:
                    printTotalPopDistribution(numberOfGenerations,distributionMatrix,normDistMatrix);
                    printPopDim(popDim,numberOfGenerations);
                    assintoticAnalysis(leslieMatrix);
                    printRateVariation(rateVariation, numberOfGenerations);
                    int format = graphsFormat();
                    for(int j = 0; j<=NUMBER_OF_GRAPHS;j++){
                        showGnuplotted((numberOfGenerations-1), popVec.length, j);
                        saveGnuplotted((numberOfGenerations-1), popVec.length,j, format, specie);
                    }
                    break;
                case 7:
                    deleteDatFiles();
                    insertGenerationsNum(leslieMatrix, specie, initialPopulation);
                    getGenerationsData(initialPopulation, leslieMatrix, numberOfGenerations, popVec, normalizedPopVec,
                            distributionMatrix, normDistMatrix, popDim, rateVariation);
                    break;
                case 9:
                    deleteDatFiles();
                    dataInsert();
                    break;
                case 0:
                    deleteDatFiles();
                    break;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        } while (option != 0);
        deleteDatFiles();
    }
    public static void menuGraphs(int gens, int classes, String specie, double [][] distributionMatrix,
                                  double[] rateVariation, double [] popDim) throws IOException, InterruptedException {
        int options;
        do {
        System.out.println("\nInsira o numero do graficos que deseja visualizar:");
        System.out.println("\n ======================================================================================================================");
        System.out.println("|     1 - Dimensão da população ao longo do tempo                                                                        |");
        System.out.println("|     2 - Variações da dimensão população entre as gerações                                                              |");
        System.out.println("|     3 - Distribuição da população                                                                                      |");
        System.out.println("|     4 - Distribuição normalizada pelo total da população                                                               |");
        System.out.println("|     0 - Voltar                                                                                                         |");
        System.out.println("=========================================================================================================================\n");

        System.out.print("Opção -> ");
        options = read.nextInt();
        System.out.print("\n");
            switch (options) {
                case 1:
                    printPopDim(popDim,gens,distributionMatrix);
                    showGnuplotted((gens-1), classes, options);
                    break;
                case 2:
                    printRateVariation(rateVariation, gens);
                    showGnuplotted((gens-1), classes, options);
                    break;
                case 3:
                    for (int time = 0; time <= gens; time++) {
                        printPopDistribution(distributionMatrix, time);
                    }
                    showGnuplotted((gens-1), classes, options);
                    break;
                case 4:
                    for (int time = 0; time <= gens; time++) {
                        printNormDistribution(distributionMatrix, time);
                    }
                    showGnuplotted((gens-1), classes, options);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        if(options == 1|options == 2|options == 3|options == 4) {
            System.out.println("Gostaria de guardar o grafico gerado?");
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
                    int format = graphsFormat();
                        saveGnuplotted(gens, classes,options, format, specie);
                    break;
                case 2:
                    break;
            }
        }
        } while (options != 0);
    }
    public static int graphsFormat ()  {
        System.out.println("Qual formato os ficheiros dos graficos devem ser guardados?");
        System.out.println("\n ================");
        System.out.println("|     1 - PNG      |");
        System.out.println("|     2 - EPS      |");
        System.out.println("|     3 - TXT      |");
        System.out.println("===================\n");
        System.out.print("Opção -> ");
        int format = read.nextInt();
        read.nextLine();
        System.out.print("\n");
        return format;
    }
    /*===========================================================MENUS=============================================-===============*/
    /*=======================================DATA STARTS AND FILE READ=============================================================*/
    public static void analysisOfDatas (String[] args) throws IOException, InterruptedException {
        String fileNameinput, fileNomeOutput; String specie; int numberOfGenerations=0, gnuplotFormat = 0, i;
        boolean e = false, v= false, r = false;
        for (i=0; i<args.length; i++){
            switch (args[i]) {
                case "-t":
                    numberOfGenerations = Integer.parseInt(args[i + 1]);
                    break;
                case "-g":
                    gnuplotFormat = Integer.parseInt(args[i + 1]);
                    break;
                case "-e":
                    e = true;
                    break;
                case "-v":
                    v = true;
                    break;
                case "-r":
                    r = true;
                    break;

            }
        }
        fileNameinput = args[args.length-2];
        fileNomeOutput = args[args.length-1];
        specie = speciesName(fileNameinput);
        executeArguments(e, v, r, numberOfGenerations, gnuplotFormat, fileNameinput, fileNomeOutput, specie);
    }
    public static void executeArguments (boolean flag1, boolean flag2, boolean flag3,  int numberOfGenerations,
                                         int gnuplotFormat, String input, String output,String specie) throws IOException, InterruptedException {
        int ageClass = sizeMatrix(input);
        double[] initialPopulation = new double[ageClass];
        double[][] leslieMatrix = new double[ageClass][ageClass];

        readFile(input, initialPopulation, leslieMatrix);

        double[] popVec = new double[leslieMatrix.length];
        double[] normalizedPopVec = new double[popVec.length];

        double[][] distributionMatrix = new double[numberOfGenerations+1][leslieMatrix.length];
        double[][] normDistMatrix = new double[numberOfGenerations+1][leslieMatrix.length];

        double[] popDim = new double[numberOfGenerations+1];
        double[] rateVariation = new double[numberOfGenerations+1];

        getGenerationsData(initialPopulation, leslieMatrix, numberOfGenerations, popVec, normalizedPopVec, distributionMatrix, normDistMatrix, popDim, rateVariation);

        PrintStream out = new PrintStream (new FileOutputStream(output, true), true);
        System.setOut(out);

        System.out.printf("Espécie analisada: %s %n", specie);
<<<<<<< HEAD
        printTotalPopDistribution(numberOfGenerations,distributionMatrix,normDistMatrix);
=======

        printTotalPopDistribution(numberOfGenerations, initialPopulation, popVec, distributionMatrix, leslieMatrix, normalizedPopVec, normDistMatrix);
>>>>>>> 692fb3bcbf001ac3167abbc64fe7b4e8b87caf4e

        if (flag1){
            //chamar os métodos com as funcionalidades de -e
            assintoticAnalysis(leslieMatrix);
        }
        if (flag2){
            //chamar os métodos com as funcionalidades de -v
            printPopDim(popDim,numberOfGenerations);
        }
        if (flag3){
            //chamar os métodos com as funcionalidades de -r
            printRateVariation(rateVariation, numberOfGenerations);
        }
        if (gnuplotFormat!=0){
            for(int j = 0; j<=NUMBER_OF_GRAPHS;j++){
                saveGnuplotted((numberOfGenerations-1), popVec.length,j, gnuplotFormat, specie);
            }
            deleteDatFiles();
        }

    }
    public static void manualStartOrganizer () throws IOException, InterruptedException {
        System.out.println("Insira o nome da espécie a analisar:");
        String speciesName = read.nextLine();

        double[] initialPopulationAux = new double[200];
        System.out.println("Insira o número de indivíduos por classes, para finalizar a inserção das populações digite um número negativo:");
        int sizeArray = fillClasse(initialPopulationAux);
        double[] initialPopulation = new double[sizeArray];
        fillInitialPopulation (initialPopulation, initialPopulationAux);

        System.out.println("Insira a taxa de sobrevivência dos indivíduos reprodutores:");
        double[][] leslieMatrix = new double[sizeArray][sizeArray];
        fillSurviveRate(leslieMatrix);

        System.out.println("Insira o número médio de indivíduos reprodutores gerados por um indivíduo reprodutor:");
        fillFecundityRate(leslieMatrix);

        insertGenerationsNum(leslieMatrix, speciesName, initialPopulation);
    }
    public static void fileInitOrganizer (String fileNameInput) throws IOException, InterruptedException {
        String speciesName1 = speciesName(fileNameInput);
        int ageClass = sizeMatrix(fileNameInput);
        double [] initialPopulation = new double[ageClass];
        double [][] leslieMatrix = new double[ageClass][ageClass];
        readFile(fileNameInput, initialPopulation, leslieMatrix);

        insertGenerationsNum(leslieMatrix, speciesName1, initialPopulation);
    }
    public static void insertGenerationsNum (double [][] leslieMatrix, String speciesName, double [] initialPopulation) throws IOException, InterruptedException {
        System.out.println("Insira o número de gerações a estimar: ");
        int numberOfGenerations = read.nextInt();
        read.nextLine();
        double[] popVec = new double[leslieMatrix.length];
        double[] normalizedPopVec = new double[popVec.length];
        double[][] distributionMatrix = new double[numberOfGenerations + 1][leslieMatrix.length];
        double[][] normDistMatrix = new double[numberOfGenerations + 1][leslieMatrix.length];
        double[] popDim = new double[numberOfGenerations + 1];
        double[] rateVariation = new double[numberOfGenerations + 1];
        menu(numberOfGenerations, initialPopulation, popVec, distributionMatrix,
                leslieMatrix, normalizedPopVec, normDistMatrix, popDim, rateVariation, speciesName);
    }
    public static String speciesName (String path){
        String[] specie = path.split(".txt");
        return specie[0];
    }
    public static void readFile (String path, double[] size, double[][] leslie) throws FileNotFoundException {
        String vector, initialLetter;
        String [] auxVector;
        int i;
        File archive = new File(path);
        Scanner readFile = new Scanner(archive);

        do{
            vector = readFile.nextLine();
            if (!vector.equals("")){
                switch (vector.charAt(0)){
                    case 'x':
                        initialLetter="x";
                        auxVector = caseX(vector, initialLetter);
                        for (i=0; i<auxVector.length; i++){
                            size[i] = Integer.parseInt(auxVector[i]);
                        }
                        break;
                    case 's':
                        initialLetter="s";
                        auxVector = transformVector(vector, initialLetter);
                        for (i=0; i<leslie.length-1; i++) {
                            if (Double.parseDouble(auxVector[i])<=1){
                                leslie[i+1][i] = Double.parseDouble(auxVector[i]);
                            }
                        }
                        break;
                    case 'f':
                        initialLetter = "f";
                        auxVector = transformVector(vector, initialLetter);
                        for (i=0; i<auxVector.length; i++){
                            leslie[0][i] = Double.parseDouble(auxVector[i]);
                        }
                        break;
                }
            }
        }while (readFile.hasNextLine());

        readFile.close();
    }
    public static int sizeMatrix (String path) throws FileNotFoundException {
        int size=0;
        File archive = new File(path);
        Scanner readFile = new Scanner(archive);
        do{
            String vector = readFile.nextLine();
            if(!vector.equals("")) {
                if (vector.charAt(0) == 'x') {
                    size = transformVector(vector, "x").length;
                }
            }
        }while (readFile.hasNextLine());
        readFile.close();
        return size;
    }
    /*=======================================DATA STARTS AND FILE READ=============================================================*/
    /*=======================================ARRAY FILLERS AND CALCULATIONS=============================================================*/
    public static void fillInitialPopulation (double[] array1, double[] array2){
        System.arraycopy(array2, 0, array1, 0, array1.length);
    }
    public static int fillClasse (double[] array){
        int cont=0; double numberOfAnimals;
        System.out.print("Classe " + (cont+1) + ":");
        numberOfAnimals = read.nextDouble();
        while (cont<array.length && numberOfAnimals>=0){
            array[cont] = numberOfAnimals;
            cont++;
            System.out.print("Classe " + (cont+1) + ":");
            numberOfAnimals = read.nextDouble();
        }
        return cont;
    }
    public static void fillSurviveRate (double[][] array){
        double surviveRate;
        for (int i=0; i<array.length-1 ;i++){
            System.out.print("Classe " + (i+1) + ":");
            surviveRate = read.nextDouble();
            while (surviveRate>1){
                System.out.println("Valor inválido! A taxa de sobrevivência deve estar entre 0 e 1");
                System.out.print("Classe " + (i+1) + ":");
                surviveRate = read.nextDouble();
            }
            array[i+1][i] = surviveRate;
        }
    }
    public static void fillFecundityRate (double[][] array){
        for (int i=0; i<array.length ;i++){
            System.out.print("Classe " + (i+1) + ":");
            array[0][i] = read.nextDouble();
        }
    }


    public static String[] transformVector (String vector, String letter){
        String[] auxVector = vector.split(", ");
        for (int i=0; i<auxVector.length; i++){
            if (Integer.parseInt(auxVector[i].substring(auxVector[i].indexOf(letter)+1))==i){
                auxVector[i] = auxVector[i].substring(auxVector[i].indexOf("=")+1);
            }
        }
        return auxVector;
    }
    public static String[] caseX (String vector, String letter){
        String[] auxVector = vector.split(", ");
        for (int i=0; i<auxVector.length; i++){
            if (Integer.parseInt(auxVector[i].substring(auxVector[i].indexOf(letter)+2))==i){
                auxVector[i] = auxVector[i].substring(auxVector[i].indexOf("=")+1);
            }
        }
        return auxVector;
    }



    public static void print (double[] size){
        for (int i=0; i< size.length; i++){
            System.out.printf("%f ", size[i]);
            System.out.println();
        }
    }

    public static void getGenerationsData (double[] initialPopVec, double[][] leslieMatrix, int generationNum,
                                           double[] popVec, double[] normalizedPopVec, double[][] distributionMatrix,
                                           double[][] normDistMatrix, double[] popDim, double[] rateVariation) throws IOException {
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
    public static void fillPopulationDistribution(double[] initialPopVec, double[] popVec, double[][] distributionMatrix, double[][] leslieMatrix, int time) {
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
    public static void fillNormalizedDEE(double[] normalizedPopVec, double[] popVec){
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

        return (nextGeneration/nowGeneration);
    }
    public static void assintoticAnalysis(double[][]leslieMatrix){

        //creation of the maximum Eigen Vector Array
        double[] maxVecM = new double [leslieMatrix.length];
        //creation of the normalized maximum Eigen Vector Array
        double[] normalizedMaxVecM = new double [maxVecM.length];

        //saving the max Eigen Value through the method maxEigenValue and filling the maxVecM through the fillMaxVecM
        double maxEigenValue = findMaxEigenValue(leslieMatrix,maxVecM);

        double percChangePop = (maxEigenValue-1) * 100;

        System.out.println("COMPORTAMENTO ASSINTÓTICO DA POPULAÇÃO ASSOCIADO AO VALOR PRÓPRIO MÁXIMO");
        System.out.println("\nAo longo do tempo, a distribuição normalizada pelo total da população vai estabilizando até a população atingir um estado de equilíbrio.\n");
        System.out.println("Nesse estado, existe um número constante específico associado a um vetor de população específico.");
        System.out.println("Esse número é o valor próprio que tem o módulo máximo da Matriz de Leslie representativa da população da espécie atual.");
        System.out.println("O vetor é o seu respetivo vetor próprio.");
        System.out.print("\nO valor próprio que tem o módulo máximo, é, em módulo, aproximadamente: ");
        System.out.printf("%.4f%n", Math.abs(maxEigenValue));
        System.out.println("Este valor próprio representa a taxa de crescimento a partir do momento que a população atinge esse estado de equilíbrio.");

        if(maxEigenValue > 1) {
            System.out.print("Como o valor próprio é maior do que 1, a população vai crescer e será, sucessiva e aproximadamente, ");
            System.out.printf("%.0f", percChangePop);
            System.out.println("% maior em comparação com ano anterior.");
        } else if(maxEigenValue < 1) {
            System.out.print("Como o valor próprio é menor do que 1, a população vai decrescer e será, sucessiva e aproximadamente, ");
            System.out.printf("%.0f", Math.abs(percChangePop));
            System.out.println("% menor em comparação com o ano anterior.");
        } else {
            System.out.println("Como o valor próprio é igual a 1, a população permanecerá constante ao longo do tempo.");
        }

        System.out.println("\nO vetor próprio associado ao valor próprio máximo representa a Distribuição Etária Estável (DEE).");
        System.out.println("\nDistribuição (Normalizada) Etária Estável: (2 casas decimais)");
        fillNormalizedDEE(normalizedMaxVecM,maxVecM);
        printDEE(normalizedMaxVecM);
    }
    public static double findMaxEigenValue(double[][] leslieMatrix, double[] maxVecM) {

        Matrix leslie = new Basic2DMatrix(leslieMatrix);

        EigenDecompositor eigenD = new EigenDecompositor(leslie);
        Matrix [] decompLeslie = eigenD.decompose();

        double[][] vecM= decompLeslie[0].toDenseMatrix().toArray();
        double[][] valM= decompLeslie[1].toDenseMatrix().toArray();

        double maxEigenVal = Math.abs(valM[0][0]);
        int columnMaxEigenVal = 0;

        for (int line = 0; line < valM.length; line++) {
            for (int column = 0; column < valM.length; column++) {
                if(line == column) {
                    if (Math.abs(valM[line][column]) > maxEigenVal) {
                        maxEigenVal = valM[line][column];
                        columnMaxEigenVal = column;
                    }
                }
            }
        }
        fillMaxVecM(vecM,columnMaxEigenVal,maxVecM);

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
    /*=======================================ARRAY FILLERS AND CALCULATIONS=============================================================*/
    /*=========================================================PRINTERS================================================================*/
    public static void printPopDistribution(double[][] matrix, int time) {
        System.out.println("GERAÇÃO " + time);
        System.out.println("Distribuição da População:");
        for (int line = 0; line < matrix.length; line++) {
            for (int column = 0; column < matrix[line].length; column++) {
                if(line == time) {
                    System.out.print("- Classe " + (column+1) + ": ");
                    System.out.printf("%.2f%n", matrix[line][column]);
                }
            }
        }
        System.out.println();
    }

    public static void printDEE(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("- Classe " + (i+1) + ": ");
            System.out.printf("%.2f", array[i]);
            System.out.println("%");
        }
    }
    public static void printTotalPopDistribution(int numberOfGenerations, double[][]distributionMatrix, double[][]normDistMatrix) {
        for (int time = 0; time <= numberOfGenerations; time++) {
            printPopDistribution(distributionMatrix, time);
            printNormDistribution(normDistMatrix,time);
        }
    }

    public static void printPopDim (double[]popDim, int numberOfGenerations){
        System.out.println("\nDIMENSÕES DAS POPULAÇÕES\n");
        for (int time = 0; time <= numberOfGenerations; time++) {
            System.out.printf("GERAÇÃO " + time + " - %.2f", popDim[time] );
            System.out.println();
        }

    }
    public static void printNormDistribution(double[][] matrix, int time) {
        System.out.println("Distribuição Normalizada pelo Total da População:");
        for (int line = 0; line < matrix.length; line++) {
            for (int column = 0; column < matrix[line].length; column++) {
                if(line == time) {
                    System.out.printf("- Classe " + (column+1) + ": %.2f", matrix[line][column]);
                    System.out.println("%");
                }
            }
        }
        System.out.println();
    }
    public static void printRateVariation (double[]rateVariation, int numberOfGenerations){
        System.out.println("TAXAS DE VARIAÇÃO ENTRE AS GERAÇÕES: \n");
        for (int time = 0; time <= numberOfGenerations; time++) {
            if(time != numberOfGenerations) {
                System.out.printf(time + " E " + (time + 1) + ": %.2f", rateVariation[time]);
                System.out.println();
            } else {
                System.out.println("Para a última geração (" + time + "), não existe Taxa de Variação.\n");
            }
        }
    }
    /*=========================================================PRINTERS================================================================*/
    /*=============================================DATA FORMATING AND GNUPLOT==========================================================*/
    public static void generationsDataFormat (double [] popVec, double [] normalizedPopVec, int gen) throws IOException {
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        int filesNum = popVec.length;
        String data, fn;
        for(int i=0;i<filesNum;i++){
            data =  gen + " " + df.format(popVec[i]) + " " + df.format(normalizedPopVec[i]);
            fn = POP_CLASSES_BASE_FILE_PATH+(i+1)+".dat";
            dataToFile(fn, data);
        }
    }
    public static void dimensionDataFormat (double [] popDim, double [] rateVariation) throws IOException {
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        String data;
        for(int k=0; k < popDim.length; k++){
            data = k + " " + df.format(popDim[k]) + " " + df.format(rateVariation[k]);
            dataToFile(POP_DIM_FILE_PATH, data);
        }
    }
    public static void dataToFile(String fileName, String fileData) throws IOException {
        File file = new File(fileName);
        if(file.exists()){
            //Set true for append mode
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.newLine();
            writer.write(fileData);
            writer.close();
        }else{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(fileData);
            writer.close();
        }
    }
    public static void showGnuplotted (int gen, int classes, int nfile) throws IOException {
        Process process = Runtime.getRuntime().exec("gnuplot -c ./gnuplot/show"+ nfile +".gp " + classes + " " + gen);
    }

    public static void saveGnuplotted (int gen, int classes, int nfile, int option, String specie) throws IOException, InterruptedException {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("E_dd-MM-yyyy_'at'_HH-mm-ss");
        String name = specie + "-" + ft.format(date);
        Process process1 = Runtime.getRuntime().exec("gnuplot -c ./gnuplot/save"
                + nfile +".gp "+ option + " " + classes
                + " " + gen + " " + name);
        process1.waitFor();
    }
    public static void deleteDatFiles(){
        File folder = new File(FOLDER_DATA_FILES);
        File[] fList = folder.listFiles();
        for (int i = 0; i < fList.length; i++) {
            String pes = String.valueOf(fList[i]);
            if (pes.endsWith(".dat")) {
                new File(String.valueOf(fList[i])).delete();
            }
        }
    }
    /*=============================================DATA FORMATING AND GNUPLOT==========================================================*/
}



