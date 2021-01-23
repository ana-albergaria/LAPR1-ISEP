import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

public class asymptoticAnalysis {
    public static void main(String[] args) {

        double leslieMatrix[][]={{0, 3.00, 3.17, 0.39},{0.11,0, 0, 0}, {0, 0.29, 0, 0}, {0, 0, 0.33, 0}};
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
        System.out.print("\nO VALOR PROPRIO QUE TEM O MÓDULO MÁXIMO, É, EM MÓDULO, APROXIMADAMENTE: ");
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

        System.out.println("\nO VETOR PRÓPRIO ASSOCIADO AO VALOR PRÓPRIO MAXIMO REPRESENTA A DISTRIBUIÇÃO ETÁRIA ESTÁVEL (DEE).");
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
    public static void fillNormalizedDEE(double[] normalizedPopVec, double[] popVec){
        double totalPopulation = generationsData.getTotalPopulation(popVec);

        for (int i = 0; i < popVec.length; i++) {
            normalizedPopVec[i] = (popVec[i] / totalPopulation) * 100;
        }
    }
    public static void printDEE(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("- Classe " + (i+1) + ": ");
            System.out.printf("%.2f", array[i]);
            System.out.println("%");
        }
    }

}