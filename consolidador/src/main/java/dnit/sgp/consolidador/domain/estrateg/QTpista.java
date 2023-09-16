package dnit.sgp.consolidador.domain.estrateg;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dnit.sgp.consolidador.helper.DadosHelper;
import lombok.Data;


@Data
public class QTpista {

    private Double kmInicial;
    private Double kmFinal;
    private String ano;
    private Double custoPista;


    public QTpista(String linha) {
        var dados = linha.split(",");
        this.ano = dados[0];
        this.kmInicial = DadosHelper.converteDouble(dados[1]);
        this.kmFinal = DadosHelper.converteDouble(dados[2]);
        this.custoPista = DadosHelper.converteDouble(dados[21]);
    }



    public static List<QTpista> CreateListComDadosDeDentroDoQTpista(Path arquivo)  throws IOException {
        var dadosPar = new ArrayList<QTpista>();
        try (Scanner scanner = new Scanner(arquivo)) {
            scanner.nextLine(); // pula cabecalho do arquivo
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.contains("\",\"(km)\",\"(km)\",")) {
                    continue;
                }
                if (!linha.isEmpty()) {
                    dadosPar.add(new QTpista(linha));
                }
            }
            return dadosPar;
        }
    }

}
