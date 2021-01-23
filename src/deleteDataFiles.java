import java.io.File;

public class deleteDataFiles {
    public static void main(String[] args) {
        File folder = new File("./gnuplot/");
        File fList[] = folder.listFiles();
        for (int i = 0; i < fList.length; i++) {
            String pes = String.valueOf(fList[i]);
            if (pes.endsWith(".dat")) {
                boolean success = (new File(String.valueOf(fList[i])).delete());
            }
        }
    }
}
