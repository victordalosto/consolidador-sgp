package dnit.sgp.consolidador.domain;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.Data;


@Data
public class DadosPar {

    private String rodovia;
    private Double kmInicial;
    private Double kmFinal;
    private Double extensao;
    private Double VDM;
    private Double nanoUSACE;
    private Double nanoAASHTO;
    private Double nanoCCP;
    private Double IRI;
    private Double IGG;
    private Double ATR;
    private Double FC2;
    private Double FC3;
    private Double AP;


    public DadosPar(String linhaArquivoPar) {
        var dados = linhaArquivoPar.split(",");
        this.rodovia = dados[0];
        this.kmInicial = Double.parseDouble(dados[1]);
        this.kmFinal = Double.parseDouble(dados[2]);
        this.extensao = Math.abs(kmFinal - kmInicial);
        this.VDM = Double.parseDouble(dados[3]);
        this.nanoUSACE = Double.parseDouble(dados[4]);
        this.nanoAASHTO = Double.parseDouble(dados[5]);
        this.nanoCCP = Double.parseDouble(dados[6]);
        this.IRI = Double.parseDouble(dados[7]);
        this.IGG = Double.parseDouble(dados[8]);
        this.ATR = Double.parseDouble(dados[9]);
        this.FC2 = Double.parseDouble(dados[10]);
        this.FC3 = Double.parseDouble(dados[11]);
        this.AP = Double.parseDouble(dados[12]);
    }


    public static List<DadosPar> CreateListComDadosDeDentroDoPar(Path arquivo) throws IOException {
        var dadosPar = new ArrayList<DadosPar>();
        try (Scanner scanner = new Scanner(arquivo)) {
            scanner.nextLine(); // pula cabecalho do arquivo
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (!linha.isEmpty()) {
                    dadosPar.add(new DadosPar(linha));
                }
            }
            return dadosPar;
        }
    }


}
