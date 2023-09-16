package dnit.sgp.consolidador.domain.estrateg;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dnit.sgp.consolidador.helper.DadosHelper;
import lombok.Data;


@Data
public class EstratDadosPar {

    private String key;
    private Double kmInicial;
    private Double kmFinal;
    private String rodovia;
    private Double extensao;
    private Double VDM;
    private Double nanoUSACE;
    private Double nanoAASHTO;
    private Double nanoCCP;
    private Double PSI;
    private Double IRI;
    private Double IGG;
    private Double SCI;
    private Double ATR;
    private Double TR;
    private Double idade;
    private Double VR;
    private Double snCalibracao;
    private Double mrCalibracao;
    private Double QI0;
    private Double e1;
    private Double e2;
    private Double e3;
    private Double esl;
    private Double d0f;
    private Double snef;
    private Double rc;
    private Double jdr;
    private Double eccp;
    private Double kef;
    private Double h1;
    private Double h2;
    private Double h3;
    private String ano;




    public EstratDadosPar(String linhaArquivoPar) {
        var dados = linhaArquivoPar.split(",");
        this.rodovia = dados[0];
        this.kmInicial = DadosHelper.converteDouble(dados[1]);
        this.kmFinal = DadosHelper.converteDouble(dados[2]);
        this.extensao = Math.abs(kmFinal - kmInicial);
        this.VDM = DadosHelper.converteDouble(dados[4]);
        this.nanoUSACE = DadosHelper.converteDouble(dados[5]);
        this.nanoAASHTO = DadosHelper.converteDouble(dados[6]);
        this.nanoCCP = DadosHelper.converteDouble(dados[7]);
        this.PSI = DadosHelper.converteDouble(dados[8]);
        this.SCI = DadosHelper.converteDouble(dados[9]);
        this.IRI = -71.5/13*Math.log(PSI/5);
        this.IGG = (309.22-61.844*SCI)/(0.616+SCI);
        this.ATR = DadosHelper.converteDouble(dados[10]);
        this.TR = DadosHelper.converteDouble(dados[11]);
        this.idade = DadosHelper.converteDouble(dados[12]);
        this.VR = DadosHelper.converteDouble(dados[13]);
        this.snCalibracao = DadosHelper.converteDouble(dados[21]);
        this.mrCalibracao = DadosHelper.converteDouble(dados[22]);
        this.QI0 = DadosHelper.converteDouble(dados[23]);
        this.e1 = DadosHelper.converteDouble(dados[24]);
        this.e2 = DadosHelper.converteDouble(dados[25]);
        this.e3 = DadosHelper.converteDouble(dados[26]);
        this.esl = DadosHelper.converteDouble(dados[27]);
        this.d0f = DadosHelper.converteDouble(dados[28]);
        this.snef = DadosHelper.converteDouble(dados[29]);
        this.rc = DadosHelper.converteDouble(dados[30]);
        this.jdr = DadosHelper.converteDouble(dados[31]);
        this.eccp = DadosHelper.converteDouble(dados[32]);
        this.kef = DadosHelper.converteDouble(dados[33]);
        this.h1 = DadosHelper.converteDouble(dados[34]);
        this.h2 = DadosHelper.converteDouble(dados[35]);
        this.h3 = DadosHelper.converteDouble(dados[36]);
    }


    public static List<EstratDadosPar> CreateListComDadosDeDentroDoPar(Path arquivo) throws IOException {
        var dadosPar = new ArrayList<EstratDadosPar>();
        try (Scanner scanner = new Scanner(arquivo)) {
            scanner.nextLine(); // pula cabecalho do arquivo
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (!linha.isEmpty()) {
                    dadosPar.add(new EstratDadosPar(linha));
                }
            }
            return dadosPar;
        }
    }

    public Double getCustoTotal() {
        return extensao * 33420.59;
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