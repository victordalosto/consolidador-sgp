package dnit.sgp.consolidador.domain.estrateg;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dnit.sgp.consolidador.helper.Util;
import lombok.Data;


@Data
public class EstratDadosPar {

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
        this.kmInicial = Util.converteDouble(dados[1]);
        this.kmFinal = Util.converteDouble(dados[2]);
        this.extensao = Math.abs(kmFinal - kmInicial);
        this.VDM = Util.converteDouble(dados[4]);
        this.nanoUSACE = Util.converteDouble(dados[5]);
        this.nanoAASHTO = Util.converteDouble(dados[6]);
        this.nanoCCP = Util.converteDouble(dados[7]);
        this.PSI = Util.converteDouble(dados[8]);
        this.SCI = Util.converteDouble(dados[9]);
        this.IRI = -71.5/13*Math.log(PSI/5);
        this.IGG = (309.22-61.844*SCI)/(0.616+SCI);
        this.ATR = Util.converteDouble(dados[10]);
        this.TR = Util.converteDouble(dados[11]);
        this.idade = Util.converteDouble(dados[12]);
        this.VR = Util.converteDouble(dados[13]);
        this.snCalibracao = Util.converteDouble(dados[21]);
        this.mrCalibracao = Util.converteDouble(dados[22]);
        this.QI0 = Util.converteDouble(dados[23]);
        this.e1 = Util.converteDouble(dados[24]);
        this.e2 = Util.converteDouble(dados[25]);
        this.e3 = Util.converteDouble(dados[26]);
        this.esl = Util.converteDouble(dados[27]);
        this.d0f = Util.converteDouble(dados[28]);
        this.snef = Util.converteDouble(dados[29]);
        this.rc = Util.converteDouble(dados[30]);
        this.jdr = Util.converteDouble(dados[31]);
        this.eccp = Util.converteDouble(dados[32]);
        this.kef = Util.converteDouble(dados[33]);
        this.h1 = Util.converteDouble(dados[34]);
        this.h2 = Util.converteDouble(dados[35]);
        this.h3 = Util.converteDouble(dados[36]);
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