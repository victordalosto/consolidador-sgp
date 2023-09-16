package dnit.sgp.consolidador.helper;

public class DadosHelper {


    public static Double converteDouble(String txt) {
        if (txt == null || txt.isEmpty()) {
            return 0.0;
        }
        txt = txt.replaceAll(",", ".").replaceAll("A-Za-z", "").replaceAll("\\s+", "");
        if (txt.isEmpty())
            return 0.0;
        return Double.parseDouble(txt);
    }

}
