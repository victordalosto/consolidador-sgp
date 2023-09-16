package dnit.sgp.consolidador.domain.dados;
import java.util.ArrayList;
import java.util.List;
import dnit.sgp.consolidador.helper.DadosHelper;
import lombok.Data;


@Data
public class Nec {

    private Double kmInicial;
    private Double kmFinal;
    private String acostLE;
    private Double hracLE;
    private Double hcLE;
    private String faixa1;
    private Double hc1;
    private Double hr1;
    private String faixa2;
    private Double hc2;
    private Double hr2;
    private String faixa3;
    private Double hc3;
    private Double hr3;
    private String faixa4;
    private Double hc4;
    private Double hr4;
    private String acostLD;
    private Double hracLD;
    private Double hcLD;
    private Double VRfx1;
    private Double VRfx2;
    private Double VRfx3;
    private Double VRfx4;


    public Nec(String linha) {
        String[] split = linha.split(",");
        this.kmInicial = DadosHelper.converteDouble(split[2]);
        this.kmFinal = DadosHelper.converteDouble(split[3]);
        this.acostLE = split[4];
        this.hracLE = DadosHelper.converteDouble(split[5]);
        this.hcLE = DadosHelper.converteDouble(split[6]);
        this.faixa1 = split[7];
        this.hc1 = DadosHelper.converteDouble(split[8]);
        this.hr1 = DadosHelper.converteDouble(split[9]);
        this.faixa2 = split[10];
        this.hc2 = DadosHelper.converteDouble(split[11]);
        this.hr2 = DadosHelper.converteDouble(split[12]);
        this.faixa3 = split[13];
        this.hc3 = DadosHelper.converteDouble(split[14]);
        this.hr3 = DadosHelper.converteDouble(split[15]);
        this.faixa4 = split[16];
        this.hc4 = DadosHelper.converteDouble(split[17]);
        this.hr4 = DadosHelper.converteDouble(split[18]);
        this.acostLD = split[19];
        this.hracLD = DadosHelper.converteDouble(split[20]);
        this.hcLD = DadosHelper.converteDouble(split[21]);
        this.VRfx1 = DadosHelper.converteDouble(split[22]);
        this.VRfx2 = DadosHelper.converteDouble(split[23]);
        this.VRfx3 = DadosHelper.converteDouble(split[24]);
        this.VRfx4 = DadosHelper.converteDouble(split[25]);
    }


    public static List<Nec> createListComDadosDentroDoNec(List<String> linhaArquivos) {
        if (linhaArquivos == null)
            return null;
        var necs = new ArrayList<Nec>();
        for (String linha : linhaArquivos) {
            if (!linha.isEmpty()) {
                necs.add(new Nec(linha));
            }
        }
        return necs;

    }

}
