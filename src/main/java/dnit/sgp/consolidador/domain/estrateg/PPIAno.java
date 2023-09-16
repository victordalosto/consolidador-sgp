package dnit.sgp.consolidador.domain.estrateg;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dnit.sgp.consolidador.helper.Util;
import lombok.Data;


@Data
public class PPIAno {

    private String key;
    private String ano;
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


    public PPIAno(String linha) {
        var dados = linha.split(",");
        this.ano = dados[0];
        this.key = dados[1];
        this.kmInicial = Util.converteDouble(dados[3]);
        this.kmFinal = Util.converteDouble(dados[4]);
        this.acostLE = dados[5];
        this.hracLE = Util.converteDouble(dados[6]);
        this.hcLE = Util.converteDouble(dados[7]);
        this.faixa1 = dados[8];
        this.hc1 = Util.converteDouble(dados[9]);
        this.hr1 = Util.converteDouble(dados[10]);
        this.faixa2 = dados[11];
        this.hc2 = Util.converteDouble(dados[12]);
        this.hr2 = Util.converteDouble(dados[13]);
        this.faixa3 = dados[14];
        this.hc3 = Util.converteDouble(dados[15]);
        this.hr3 = Util.converteDouble(dados[16]);
        this.faixa4 = dados[17];
        this.hc4 = Util.converteDouble(dados[18]);
        this.hr4 = Util.converteDouble(dados[19]);
        this.acostLD = dados[20];
        this.hracLD = Util.converteDouble(dados[21]);
        this.hcLD = Util.converteDouble(dados[22]);
    }


    public static List<PPIAno> CreateListComDadosDeDentroDoPPIano(Path arquivo)  throws IOException {
        var dadosPar = new ArrayList<PPIAno>();
        try (Scanner scanner = new Scanner(arquivo)) {
            scanner.nextLine(); // pula cabecalho do arquivo
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.contains("\"\",\"\",\"\",")) {
                    continue;
                }
                if (!linha.isEmpty()) {
                    dadosPar.add(new PPIAno(linha));
                }
            }
            return dadosPar;
        }
    }

}
