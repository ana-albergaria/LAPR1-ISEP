import java.io.File;

public class deleteDataFiles {
    public static void main(String[] args) {
        // Lists all files in folder
        File folder = new File("./gnuplot/");
        File fList[] = folder.listFiles();
        // Searchs .lck
        for (int i = 0; i < fList.length; i++) {
            String pes = String.valueOf(fList[i]);
            if (pes.endsWith(".dat")) {
                // and deletes
                boolean success = (new File(String.valueOf(fList[i])).delete());
            }
        }
    }
}
