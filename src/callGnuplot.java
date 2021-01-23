//exemplo de utilização desta biblioteca

import java.io.IOException;

public class callGnuplot {
    public static void main(String[] args) throws IOException, InterruptedException {
    }
    public static void showGnuplotted (int gen, int classes) throws IOException, InterruptedException {
        Process process1 = Runtime.getRuntime().exec("gnuplot -c ./showGnuplot.gp " + classes + " " + gen);
        process1.waitFor();
        //deleteDatFiles();
    }
    public static void saveGnuplotted (int gen, int classes) throws IOException, InterruptedException {
        Process process1 = Runtime.getRuntime().exec("gnuplot -c ./saveGnuplot.gp 1 " + classes + " " + gen);
        process1.waitFor();
        //deleteDatFiles();
    }

}