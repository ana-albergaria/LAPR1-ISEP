//exemplo de utilização desta biblioteca

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class callGnuplot {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        Process process1 = Runtime.getRuntime().exec("gnuplot -c ./testeGnuplot.gp 1 4");
        printResults(process1);
    }
    public static void printResults(Process process) throws IOException {
       BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}