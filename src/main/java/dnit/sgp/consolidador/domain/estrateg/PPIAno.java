package dnit.sgp.consolidador.domain.estrateg;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dnit.sgp.consolidador.helper.DadosHelper;
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
        this.key = dados[1].replaceAll("\"", "");
        this.kmInicial = DadosHelper.converteDouble(dados[3]);
        this.kmFinal = DadosHelper.converteDouble(dados[4]);
        this.acostLE = dados[5];
        this.hracLE = DadosHelper.converteDouble(dados[6]);
        this.hcLE = DadosHelper.converteDouble(dados[7]);
        this.faixa1 = dados[8];
        this.hc1 = DadosHelper.converteDouble(dados[9]);
        this.hr1 = DadosHelper.converteDouble(dados[10]);
        this.faixa2 = dados[11];
        this.hc2 = DadosHelper.converteDouble(dados[12]);
        this.hr2 = DadosHelper.converteDouble(dados[13]);
        this.faixa3 = dados[14];
        this.hc3 = DadosHelper.converteDouble(dados[15]);
        this.hr3 = DadosHelper.converteDouble(dados[16]);
        this.faixa4 = dados[17];
        this.hc4 = DadosHelper.converteDouble(dados[18]);
        this.hr4 = DadosHelper.converteDouble(dados[19]);
        this.acostLD = dados[20];
        this.hracLD = DadosHelper.converteDouble(dados[21]);
        this.hcLD = DadosHelper.converteDouble(dados[22]);
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
