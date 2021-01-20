import java.io.IOException;

public class testesUnitarios {
    static final int GENERATIONS_TO_BE_ESTIMATED = 2;

    public static void main(String[] args) throws IOException {
        double[][] leslieMatrix = {{0,3,3.17,0.39},{0.11,0,0,0},{0,0.29,0,0},{0,0,0.33,0}};
        double maxVecMAux [] = new double [leslieMatrix.length];
        double normMaxVecMAux [] = new double [leslieMatrix.length];

        System.out.print("TESTE DA DISTRIBUIÇÃO DA POPULAÇÃO ATÉ À GERAÇÃO SOLICITADA: ");
        verifyBoolean(test_fillPopulationDistribution(GENERATIONS_TO_BE_ESTIMATED,leslieMatrix));
        System.out.print("TESTE DA DISTRIBUIÇÃO NORMALIZADA DA POPULAÇÃO ATÉ À GERAÇÃO SOLICITADA: ");
        verifyBoolean(test_fillNormalizedPopVec(GENERATIONS_TO_BE_ESTIMATED,leslieMatrix));
        System.out.print("TESTE DA DIMENSÃO DA POPULAÇÃO ATÉ À GERAÇÃO SOLICITADA: ");
        verifyBoolean(test_getTotalPopulation();
        System.out.print("TESTE DAS TAXAS DE VARIAÇÃO DA POPULAÇÃO ATÉ À GERAÇÃO SOLICITADA: ");
        verifyBoolean(test_getRateOfChangeOverTheYears());
        //COMPORTAMENTO ASSINTÓTICO
        System.out.print("TESTE DO MÓDULO DO VALOR PRÓPRIO MÁXIMO: ");
        verifyBoolean(test_findMaxEigenValue(leslieMatrix,0.6956005054754468,maxVecMAux));
        System.out.print("TESTE DO PREENCHIMENTO DO VETOR PRÓPRIO MÁXIMO: ");
        verifyBoolean(test_fillMaxVecM(maxVecMAux));
        System.out.print("TESTE DO PREENCHIMENTO DA DISTRIBUIÇÃO NORMALIZADA DO VETOR PRÓPRIO MÁXIMO: ");
        verifyBoolean(test_fillNormalizedDEE(maxVecMAux));
        System.out.print("TESTE DA PROPORÇÃO DE CRESCIMENTO DA POPULAÇÃO APÓS ATINGIR A DEE: ");
        verifyBoolean(test_percChangePop(-30.439949452455316, 0.6956005054754468));



    }
    public static Boolean test_fillPopulationDistribution(int generationsToBeTested, double[][]leslieMatrix) {
        double[] initialPopVecAux = {1000, 300, 330, 100};
        double[] popVecAux = new double[initialPopVecAux.length];
        double[][] distribMatrixAux = new double[generationsToBeTested + 1][leslieMatrix.length];
        double[] expectedPopVec0 = {1000, 300, 330, 100};
        double[] expectedPopVec1 = {1985.1, 110.0, 87.0, 108.9};
        double[] expectedPopVec2 = {648.261, 218.361, 31.9, 28.71};

        for (int time = 0; time <= generationsToBeTested; time++) {
            main.fillPopulationDistribution(initialPopVecAux, popVecAux, distribMatrixAux, leslieMatrix, time);
        }

        if(test_ComparePopVecWithMatrix(expectedPopVec0,distribMatrixAux,0) && test_ComparePopVecWithMatrix(expectedPopVec1,distribMatrixAux,1)
                && test_ComparePopVecWithMatrix(expectedPopVec2,distribMatrixAux,2)) {
            return true;
        }
        return false;
    }
    public static Boolean test_fillNormalizedPopVec(int generationsToBeTested, double[][]leslieMatrix) throws IOException {
        double[] initialPopVecAux = {1000, 300, 330, 100};
        double[] popVecAux = new double[initialPopVecAux.length];
        double[] normalizedPopVecAux = new double[popVecAux.length];
        double[][] distribMatrixAux = new double[generationsToBeTested + 1][leslieMatrix.length];
        double[][] normDistMatrixAux = new double[generationsToBeTested + 1][leslieMatrix.length];
        double[] expectedNormVec0 = {57.80346820809249, 17.341040462427745, 19.07514450867052, 5.780346820809249};
        double[] expectedNormVec1 = {86.64775207333042, 4.801396769969446, 3.79746835443038, 4.753382802269752};
        double[] expectedNormVec2 = {69.9135707136941, 23.54976963694092, 3.440347183876311, 3.0963124654886807};

        for (int time = 0; time <= generationsToBeTested; time++) {
            main.fillPopulationDistribution(initialPopVecAux, popVecAux, distribMatrixAux, leslieMatrix, time);
            main.fillNormalizedPopVec(normalizedPopVecAux,popVecAux,normDistMatrixAux,time);
        }

        if(test_ComparePopVecWithMatrix(expectedNormVec0,normDistMatrixAux,0) && test_ComparePopVecWithMatrix(expectedNormVec1,normDistMatrixAux,1)
                && test_ComparePopVecWithMatrix(expectedNormVec2,normDistMatrixAux,2)) {
            return true;
        }
        return false;
    }
    public static Boolean test_CompareArrays(double[]array1, double[]array2){
        for (int i = 0; i < array1.length ; i++) {
            if(array1[i]!=array2[i]){
                return false;
            }
        }
        return true;
    }
    public static Boolean test_ComparePopVecWithMatrix(double[] expectedPopVec, double[][] distributionMatrixAux, int time) {
        for (int line = 0; line < distributionMatrixAux.length; line++) {
            for (int column = 0; column < distributionMatrixAux[line].length; column++) {
                if(line == time) {
                    if(expectedPopVec[column] != distributionMatrixAux[line][column]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static void verifyBoolean(boolean flag){
        if (flag){
            System.out.println("Aprovado");
        } else{
            System.out.println("Reprovado");
        }
    }
    public static Boolean test_getTotalPopulation() {
        double expectedDim0 = 1730;
        double expectedDim1 = 2291;
        double expectedDim2 = 927.232;
        double[] popVec0 = {1000, 300, 330, 100};
        double[] popVec1 = {1985.1, 110.0, 87.0, 108.9};
        double[] popVec2 = {648.261, 218.361, 31.9, 28.71};

        if(main.getTotalPopulation(popVec0) == expectedDim0 && main.getTotalPopulation(popVec1) == expectedDim1
                && main.getTotalPopulation(popVec2) == expectedDim2) {
            return true;
        }
        return false;
    }
    public static Boolean test_fillNormalizedDEE(double[]maxVecMAux) {
        double [] expectedNormMaxVecM = {79.65957397392735, 12.597105764238572, 5.251808535032954, 2.4915117268011366};
        double normMaxVecMAux [] = new double [maxVecMAux.length];
        main.fillNormalizedDEE(normMaxVecMAux,maxVecMAux);

        return test_CompareArrays(expectedNormMaxVecM,normMaxVecMAux);
    }
    public static Boolean test_getRateOfChangeOverTheYears() {
        double expectedRate0 = 1.324277456647399;
        double expectedRate1 = 0.40472806634657355;
        double[] popDimAux = {1730, 2291, 927.232};

        if(main.getRateOfChangeOverTheYears(1, popDimAux) == expectedRate0 && main.getRateOfChangeOverTheYears(2, popDimAux) == expectedRate1) {
            return true;
        }
        return false;
    }
    public static Boolean test_findMaxEigenValue(double[][]leslieMatrix,double expectedMaxEigenValue, double[] maxVecMAux){
        double maxEigenvalue = main.findMaxEigenValue(leslieMatrix,maxVecMAux);

        if(expectedMaxEigenValue==maxEigenvalue) {
            return true;
        } else{
            return false;
        }
    }
    public static Boolean test_fillMaxVecM(double[] maxVecM) {
        double [] expectedMaxVecM = {0.9851705151197174, 0.15579165887623725, 0.06495047188504893, 0.030813168698628407};
        return test_CompareArrays(expectedMaxVecM,maxVecM);
    }
    public static Boolean test_percChangePop(double expectedPercChangePop, double maxEigenValue){
        double percChangePop = (maxEigenValue-1) * 100;

        if(expectedPercChangePop==percChangePop){
            return true;
        }
        return false;
    }


}
