import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Manu {
    static Scanner read = new Scanner(System.in);
    public static void main (String[] args) throws FileNotFoundException {
        //simulação da entrada por paramêtro do nome do ficheiro
        String fileName = read.next();
        String[] speciesName = speciesName(fileName);
        int size = sizeMatrix(fileName);
        int[] sizeSpecies = new int[size];
        double[][] leslieMatrix = new double[size][size];
        readFile(fileName, sizeSpecies, leslieMatrix);
        print(sizeSpecies);
        print1(leslieMatrix);

    }
    public static String[] speciesName (String path){
        String[] specie = path.split(".txt");
        return specie;
    }
    public static void readFile (String path, int[] size, double[][] leslie) throws FileNotFoundException {
        String vector;
        String [] auxVector;
        int cont=0, i;
        File archive = new File(path);
        Scanner readFile = new Scanner(archive);

        /*
        String [] auxVector2;
        String [] auxVector3;
        String vetor1 = readFile.nextLine();
        auxVector= transformVector(vetor1);
        String vetor2 = readFile.nextLine();
        auxVector2 = transformVector(vetor2);
        String vetor3 = readFile.nextLine();
        auxVector3 = transformVector(vetor3);
        for (int m=1; m<auxVector.length; m++){
            for (int k=0; k<auxVector.length; k++){
                if (m == k+1){
                    leslie[m][k] = Double.parseDouble(auxVector2[k]);
                }
            }
        }
        for (int j=0; j<auxVector.length; j++){
            leslie[0][j] = Double.parseDouble(auxVector3[j]);
        }
        */

        do{
            vector = readFile.nextLine();
            auxVector = transformVector(vector);
            switch (cont){
                case 0:
                    for (i=0; i<auxVector.length; i++){
                        size[i] = Integer.parseInt(auxVector[i]);
                    }
                    break;
                case 1:
                    for (int m=1; m<leslie.length; m++) {
                        for (int k = 0; k < leslie.length; k++) {
                            if (m == k + 1) {
                                leslie[m][k] = Double.parseDouble(auxVector[k]);
                            }
                        }
                    }
                    break;
                default:
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

    public static int sizeMatrix (String path) throws FileNotFoundException{
        File archive = new File(path);
        Scanner readFile = new Scanner(archive);
        String vector = readFile.nextLine();
        int size = transformVector(vector).length;
        readFile.close();
        return size;
    }

    public static void print (int[] size){
        for (int i=0; i< size.length; i++){
            System.out.printf("%d ", size[i]);
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

}



