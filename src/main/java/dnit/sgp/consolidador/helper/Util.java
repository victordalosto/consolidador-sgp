package dnit.sgp.consolidador.helper;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Util {


    private static final Pattern pattern = Pattern.compile(".*ano (\\d+).*csv.*");



    public static boolean valorEhProximo(double v1, double v2) {
        return Math.abs(v1 - v2) < 0.05;
    }


    public static void print(String msg) {
        System.out.print(msg);
    }


    public static void println(String msg) {
        System.out.println(msg);
    }


    public static String getAnoInString(Path path) {
        Matcher matcher = pattern.matcher(path.toString().toLowerCase());
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return "";
    }


    public static boolean contemNoNome(Path p, String text) {
        return p.getFileName().toString().toLowerCase().contains(text.toLowerCase());
    }


}
