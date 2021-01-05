//exemplo de utilização desta biblioteca

import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class teste{
    public static void main(String[] args) throws IOException, InterruptedException {

        double matComparacao[][]={{0, 3, 3.17, 0.39},{0.11,0, 0, 0}, {0, 0.29, 0, 0}, {0, 0, 0.33, 0}};

        // Criar objeto do tipo Matriz

        Matrix a = new Basic2DMatrix(matComparacao);

        //Obtem valores e vetores próprios fazendo "Eigen Decomposition"

        EigenDecompositor eigenD=new EigenDecompositor(a);
        Matrix [] mattD= eigenD.decompose();

        for(int i=0; i<2;i++)

        {
            System.out.println(mattD[i]);
        }
        // converte objeto Matrix (duas matrizes)  para array Java

        double matA [][]= mattD[0].toDenseMatrix().toArray();

        double matB [][]= mattD[1].toDenseMatrix().toArray();

        for(int i=0; i<2;i++)

        {
            for (int j=0; j<4; j++)

                System.out.println(matA[i][j]);

        }

        Process process = Runtime.getRuntime().exec("gnuplot src/exemploGnuplot.gp");
        printResults(process);
    }
    public static void printResults(Process process) throws IOException {
       BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}