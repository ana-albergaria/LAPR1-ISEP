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
        int ageClass, gnuplotFormat, i;
        String fileNameOutput, fileNameInput, path=null, speciesName1;
        String[] speciesName;
        analysisOfDatas(args);
        /*switch (args.length){
            case 0:
                System.out.println("Insira o nome da espécie a analisar:");
                speciesName1 = read.nextLine();
                System.out.println("Insira quantidade de faixas etárias:");
                ageClass = read.nextInt();
                initialPopulation = new double[ageClass];
                fillClasse(initialPopulation);
                System.out.println("Insira a taxa de sobrevivência dos indivíduos reprodutores");
                leslieMatrix = new double[ageClass][ageClass];
                fillSurviveRate(leslieMatrix);
                System.out.println("Insira o número médio de indivíduos reprodutores gerados por um indivíduo reprodutor:");
                fillFecundityRate(leslieMatrix);
                System.out.println("Insira o número de gerações a estimar: ");
                int numberOfGenerations = read.nextInt();
                read.nextLine();

                double[] popVec = new double[leslieMatrix.length];
                double[] normalizedPopVec = new double[popVec.length];
                double[][] distributionMatrix = new double[numberOfGenerations+1][leslieMatrix.length];
                double[][] normDistMatrix = new double[numberOfGenerations+1][leslieMatrix.length];
                double[] popDim = new double[numberOfGenerations+1];
                double[] rateVariation = new double[numberOfGenerations+1];

                System.out.println("POPULATION DISTRIBUTIONS\n");
                printTotalPopDistribution(numberOfGenerations, initialPopulation, popVec, distributionMatrix, leslieMatrix, normalizedPopVec, normDistMatrix);

                //TRANSFORMEI O CASE DO -V -R EM UM MÉTODO E NO CASE SÓ PRINTO, POIS OS DADOS SÃO NECESSARIOS PARA PLOTAGEM DOS GRAFICOS
                fillDimArr(numberOfGenerations, distributionMatrix, popDim);
                fillRatesArr(numberOfGenerations, popDim, rateVariation);
                dimensionDataFormat(popDim, rateVariation);

                System.out.println("Para aceder as funcionalidades do programa, escreva devidamente espaçado as funcionalidades pretendidas:");
                System.out.println("Digite -e para obter os valores e vetores próprios associados a matriz de Leslie");
                System.out.println("Digite -v para obter a dimensão da população a cada geração");
                System.out.println("Digite -r para obter a variação da população entre as gerações");
                String answers = read.nextLine();
                String[] formattedAnswers = answers.split(" ");
                for (i=0; i<formattedAnswers.length; i++){
                    switch (formattedAnswers[i]){
                        case "-e":
                            assintoticAnalysis(leslieMatrix);
                            break;
                        case "-v":
                            System.out.println("\nPOPULATION DIMENSIONS\n");
                            printPopDim(popDim,numberOfGenerations);

                            //perguntar se pretende apenas visualizar ou salvar o gráfico
                            break;
                        case "-r":
                            System.out.println("\nRATE VARIATIONS BETWEEN GENERATIONS: \n");
                            printRateVariation(rateVariation, numberOfGenerations);
                            break;
                    }
                }
                //ask the user the necessary questions for iteractive modes
                //isert the necessary method
                //case 2 split the array after -n to take the arquiche name
                break;
            case 2:
                for (i = 0; i < args.length - 1; i++) {
                    if (args[i].equalsIgnoreCase("-n")) {
                        path = args[i + 1];
                    }
                }
                readFile(path, initialPopulation, leslieMatrix);
                speciesName = speciesName(path);
                break;
            case 9:
                fileNameInput = args[args.length-2];
                fileNameOutput = args[args.length-1];
                speciesName = speciesName(fileNameInput);
                ageClass = sizeMatrix(fileNameInput);
                initialPopulation = new double [ageClass];
                leslieMatrix = new double[ageClass][ageClass];
                readFile(fileNameInput, initialPopulation, leslieMatrix);
                popDistribution(initialPopulation, leslieMatrix, numberOfGenerations);
                callGnuplot(numberOfGenerations-1, ageClass, gnuplotFormat);
                assintoticAnalysis(leslieMatrix);
                break;
            case 6:
                fileNameInput = args[args.length-2];
                fileNameOutput= args[args.length-1];
                break;
            case 7: case 8:
                //check how features the user want
                // -e augevalues and augevectors
                // -v dimension of population
                // -r variation of population in each generation
        }*/
    }
    public static void analysisOfDatas (String[] args) throws IOException, InterruptedException {
        String fileNameinput, fileNomeOutput; String[] nameOfSpecie; int numberOfGenerations=0, gnuplotFormat = 0, i;
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
       executeArguments(e, v, r, numberOfGenerations, gnuplotFormat, fileNameinput, fileNomeOutput);

    }
    public static void executeArguments (boolean flag1, boolean flag2, boolean flag3, int numberOfGenerations, int gnuplotFormat, String input, String output) throws IOException {
        int ageClass = sizeMatrix(input);
        double[] initialPopulation = new double[ageClass];
        double[][] leslieMatrix = new double[ageClass][ageClass];
        readFile(input, initialPopulation, leslieMatrix);
        popDistribution(initialPopulation, leslieMatrix, numberOfGenerations);
            if (flag1){
                //chamar os métodos com as funcionalidades de -e
                assintoticAnalysis(leslieMatrix);
            }
            if (flag2){
                //chamar os métodos com as funcionalidades de -v


            }
            if (flag3){
                //chamar os métodos com as funcionalidades de -r
            }

    }


    /*public static void writeFile (String path, double[][] leslieMatrix, double[] initialPop, int generations){
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
    }*/
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

    public static void print (double[] size){
        for (int i=0; i< size.length; i++){
            System.out.printf("%f ", size[i]);
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

    public static void popDistribution (double initialPopVec[], double[][] leslieMatrix, int generationNum) throws IOException {

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

    }
    public static void generationsDataFormat (double [] popVec, double [] normalizedPopVec, int gen) throws IOException {
        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        int filesNum = popVec.length;
        String data = "", fn= "";
        for(int i=0;i<filesNum;i++){
            data =  gen + " " + df.format(popVec[i]) + " " + df.format(normalizedPopVec[i]);
            fn = "classe"+(i+1)+".dat";
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

    public static void printPopDistribution2(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("- Classe " + i + ": ");
            System.out.printf("%.2f%n", array[i]);
        }
    }

    public static void callGnuplot (int gen, int classes, int formatGaphics) throws IOException, InterruptedException {
        Process process1 = Runtime.getRuntime().exec("gnuplot -c ./testeGnuplot.gp " + formatGaphics + " " + classes + " " + gen);
    }
    public static void callGnuplot (int gen, int classes) throws IOException, InterruptedException {
        Process process1 = Runtime.getRuntime().exec("gnuplot -c ./saveGnuplot.gp 3 " + classes + " " + gen);
        process1.waitFor();
        printResults(process1);
        deleteDatFiles();
    }
    public static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
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

    public static double getDimension(double[][] distributionMatrix, int time) {
        double dim = 0;

        for (int line = time; line < (time+1); line++) {
            for (int column = 0; column < distributionMatrix[line].length; column++) {
                dim += distributionMatrix[line][column];
            }
        }
        return dim;
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
    public static void printTotalPopDistribution(int numberOfGenerations, double[]initialPopulation, double[]popVec, double[][]distributionMatrix,double[][]leslieMatrix, double[]normalizedPopVec, double[][]normDistMatrix) throws IOException {
        for (int time = 0; time <= numberOfGenerations; time++) {
            fillPopulationDistribution(initialPopulation, popVec, distributionMatrix, leslieMatrix, time);
            printPopDistribution(distributionMatrix, time);
            fillNormalizedPopVec(normalizedPopVec,popVec,normDistMatrix,time);
            printNormDistribution(normDistMatrix,time);
        }
    }
    public static void printPopDim (double[]popDim, int numberOfGenerations){
        for (int time = 0; time <= numberOfGenerations; time++) {
            System.out.printf("GENERATION " + time + " - %.2f", popDim[time] );
            System.out.println();
        }
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
    public static void printRateVariation (double[]rateVariation, int numberOfGenerations){
        for (int time = 0; time <= numberOfGenerations; time++) {
            if(time != numberOfGenerations) {
                System.out.printf(time + " AND " + (time + 1) + ": %.2f", rateVariation[time]);
                System.out.println();
            } else {
                System.out.println("For the last generation (" + time + "), there is no Rate Variation.\n");
            }
        }
    }
    public static void printGenerationInfo(int time, int generationNum, double[][] distributionMatrix, double[][] normDistMatrix, double[] popDim, double[] rateVariation) {
        System.out.println("GERAÇÃO " + time);
        System.out.println("Distribuição da População:");
        printPopDistribution(distributionMatrix, time);
        System.out.println("Distribuição da População Normalizada:");
        printPopDistribution(normDistMatrix, time);
        System.out.printf("Dimensão da População: %.2f", popDim[time] );
        System.out.println();
        if(time != generationNum) {
            System.out.print("Taxa de Variação entre a geração " + time + " e a geração " + (time + 1) + ": ");
            System.out.printf("%.2f%n",rateVariation[time]);
            System.out.println();
        } else {
            System.out.println("Para esta geração, não há Taxa de Variação.");
        }
        System.out.println();
    }
    /*public static void printDistribution (int time, double[][] distributionMatrix) {
        System.out.println("GENERATION " + time);
        System.out.println("Population Distribution:");
        printPopDistribution(distributionMatrix, time);
        System.out.println();
    }*/

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

        System.out.println("COMPORTAMENTO ASSINTÓTICO DA POPULAÇÃO ASSOCIADO AO VALOR PRÓPRIO MÁXIMO");
        System.out.println();
        System.out.println("No estado estacionário, existe, um número constante específico, associado a um vetor de população específico.");
        System.out.println("Esse número é o valor próprio que tem o módulo máximo da Matriz de Leslie, representando a população atual.");
        System.out.println("O vetor é o seu respetivo vetor próprio.");
        System.out.println();
        System.out.print("O valor próprio, que tem o módulo máximo, é, aproximadamente: ");
        System.out.printf("%.4f%n", maxEigenValue);
        System.out.println("Este valor próprio, representa a taxa de crescimento.");

        if(maxEigenValue > 1) {
            System.out.print("Enquanto o valor próprio for maior do que 1, a população está a crescer e será, aproximadamente, ");
            System.out.printf("%.0f", percChangePop);
            System.out.println("% maior em tamanho, em comparação ao ano anterior.");
        } else if(maxEigenValue < 1) {
            System.out.print("Enquanto o valor próprio for menor do que 1, a população está a decrescer e será, aproximadamente, ");
            System.out.printf("%.0f", Math.abs(percChangePop));
            System.out.println("% menor em tamanho, em comparação ao ano anterior.");
        } else {
            System.out.println("Enquanto o valor próprio for igual a 1, a população permanecerá constante em tamanho, ao longo do tempo.");
        }

        System.out.println();
        System.out.println("O vetor próprio associado ao valor próprio máximo representa representa as constantes proporções da população.");
        System.out.println();
        System.out.println("Proporções populacionais constantes: (2 casas decimais)");
        printPopDistribution2(maxVecM);
        System.out.println();
        System.out.println("Proporções populacionais constantes normalizadas: (2 casas decimais)");
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



