package dnit.sgp.consolidador.helper;

public class DadosHelper {


    public static Double converteDouble(String txt) {
        if (txt == null || txt.isEmpty()) {
            return 0.0;
        }
        txt = txt.replaceAll(",", ".").replaceAll("[^0-9.]", "");
        if (txt.isEmpty())
            return 0.0;
        return Double.parseDouble(txt);
    }

}
