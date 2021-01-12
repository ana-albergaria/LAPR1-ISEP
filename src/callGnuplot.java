//exemplo de utilização desta biblioteca

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class callGnuplot {
    public static void main(String[] args) {

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