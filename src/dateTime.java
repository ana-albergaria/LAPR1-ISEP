import java.text.SimpleDateFormat;
import java.util.Date;

public class dateTime {
    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("E_yyyy-MM-dd_'at'_HH:mm:ss");
        System.out.println(ft.format(date));
    }
}
