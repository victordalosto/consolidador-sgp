package dnit.sgp.consolidador.helper;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static final String titulo = "SNV;Versao do SNV;Sentido;BR;UF;Regiao;Rodovia;Inicio (km);Final (km);Extensao (km);TipoPav;VDM;NanoUSACE;NanoAASHTO;NanoCCP;IRI (m/km);PSI;IGG;SCI;ATR (mm);FC2 (%);FC3 (%);TR (%);AP (%);ICS;Conceito ICS;Idade;E1;E2;E3;Esl;D0ref;SNef;Rc;JDR;Eccp;Kef;H1 (cm);H2 (cm);H3 (cm);VR (anos);Critério_VR;CamadaCrítica;Diagnostico;Medida;Tipo_ConservaPesada;hc (cm);HR (cm);Dp (0.01 mm);AcostLE;HRacLE;hcLE;Faixa1;hc1;HR1;Faixa2;hc2;HR2;Faixa3;hc3;HR3;Faixa4;hc4;HR4;AcostLD;HRacLD;hcLD;VR_Fx1;VR_Fx2;VR_Fx3;VR_Fx4";

    public static Double converteDouble(String txt) {
        if (txt == null || txt.isEmpty()) {
            return 0.0;
        }
        txt = txt.replaceAll(",", ".").replaceAll("\"\"", "").replaceAll("\\s+", "");
        if (txt.isEmpty())
            return 0.0;
        return Double.parseDouble(txt);
    }


    public static boolean valorEhProximo(double v1, double v2) {
        return Math.abs(v1 - v2) < 0.05;
    }


    public static List<Path> removeArquivosComString(String name, List<Path> lista) {
        if (lista == null || lista.size() == 0)
            return lista;
        return lista.stream()
                    .filter(f -> !f.getFileName().toString().toUpperCase().contains("_FX1"))
                    .collect(Collectors.toList());
    }


}
