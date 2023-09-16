package dnit.sgp.consolidador.domain.estrateg;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dnit.sgp.consolidador.helper.DadosHelper;
import lombok.Data;


@Data
public class QTacost {

    private Double kmInicial;
    private Double kmFinal;
    private String ano;
    private Double custoAcost;


    public QTacost(String linha) {
        var dados = linha.split(",");
        this.ano = dados[0];
        this.kmInicial = DadosHelper.converteDouble(dados[1]);
        this.kmFinal = DadosHelper.converteDouble(dados[2]);
        this.custoAcost = DadosHelper.converteDouble(dados[13]);
    }



    public static List<QTacost> CreateListComDadosDeDentroDoQTacost(Path arquivo)  throws IOException {
        var dadosPar = new ArrayList<QTacost>();
        try (Scanner scanner = new Scanner(arquivo)) {
            scanner.nextLine(); // pula cabecalho do arquivo
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.contains("\",\"(km)\",\"(km)\",")) {
                    continue;
                }
                if (!linha.isEmpty()) {
                    dadosPar.add(new QTacost(linha));
                }
            }
            return dadosPar;
        }
    }

}
