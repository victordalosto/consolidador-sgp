package dnit.sgp.consolidador.domain.dados;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dnit.sgp.consolidador.helper.Util;
import lombok.Data;


@Data
public class DadosPar {

    private String key;
    private Double kmInicial;
    private Double kmFinal;
    private String rodovia;
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
        this.kmInicial = Util.converteDouble(dados[1]);
        this.kmFinal = Util.converteDouble(dados[2]);
        this.extensao = Math.abs(kmFinal - kmInicial);
        this.VDM = Util.converteDouble(dados[3]);
        this.nanoUSACE = Util.converteDouble(dados[4]);
        this.nanoAASHTO = Util.converteDouble(dados[5]);
        this.nanoCCP = Util.converteDouble(dados[6]);
        this.IRI = Util.converteDouble(dados[7]);
        this.IGG = Util.converteDouble(dados[8]);
        this.ATR = Util.converteDouble(dados[9]);
        this.FC2 = Util.converteDouble(dados[10]);
        this.FC3 = Util.converteDouble(dados[11]);
        this.AP = Util.converteDouble(dados[12]);
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


    public Double getICS() {
        return Math.min(getICS_IRI(), getICS_IGG());
    }


    public String getConceitoICS() {
        Double ics = getICS();
        if (Math.abs(3.0 - ics) <= 0.1)
            return "Regular";
        if (ics < 3.0)
            return "Ruim";
        return "Bom";
    }


    private Double getICS_IRI() {
        if (IRI > 6)
            return 1.0;
        if (IRI > 4.5)
            return 2.0;
        if (IRI > 3.5)
            return 3.0;
        if (IRI > 2.5)
            return 4.0;
        return 5.0;
    }


    private Double getICS_IGG() {
        if (IGG > 160)
            return 1.0;
        if (IGG > 80)
            return 2.0;
        if (IGG > 40)
            return 3.0;
        if (IGG > 20)
            return 4.0;
        return 5.0;
    }


}