import java.util.Arrays;
import java.util.Scanner;

public class tratamentOfDatasTerminal {
    static Scanner read = new Scanner(System.in);
    public static void main(String[] args) {
        switch (args.length){
            case 0:
                System.out.println("Insira o nome da espécie a analisar:");
                String speciesName = read.nextLine();
                System.out.println("Insira quantidade de faixas etárias:");
                int ageClass = read.nextInt();
                double initialPopulation[] = new double[ageClass];
                fillClasse(initialPopulation);
                print(initialPopulation);
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
    }
    public static void fillClasse (double[] array){
        for (int i=0;i < array.length;i++){
            System.out.print("Classe " + (i+1) + ":");
            array[i] = read.nextDouble();
        }
    }
    public static void print (double[] size){
        for (int i=0; i< size.length; i++){
            System.out.printf("%f ", size[i]);
            System.out.println();
        }
    }

    public static String fileName (String[] array){
        int index = Arrays.toString(array).indexOf("-n");
        System.out.println(Arrays.toString(array));
        String file = array[index];
        return file;
    }
}
