//exemplo de utilização desta biblioteca

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class callGnuplot {
    public static void main(String[] args) throws IOException, InterruptedException {
        showGnuplotted0(9, 4, 1);
    }
    public static void showGnuplotted0 (int gen, int classes, int nfile) throws IOException, InterruptedException {
        Process process0 = Runtime.getRuntime().exec("gnuplot -c ./gnuplot/show"+ nfile +".gp " + classes + " " + gen);
    }
    public static void showGnuplotted1 (int gen, int classes, int nfile) throws IOException, InterruptedException {
        Process process1 = Runtime.getRuntime().exec("gnuplot -c ./gnuplot/show"+ nfile +".gp " + classes + " " + gen);
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
    public static void printResults(Process process) throws IOException {
       BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}