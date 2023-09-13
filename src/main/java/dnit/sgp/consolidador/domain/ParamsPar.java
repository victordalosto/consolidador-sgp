package dnit.sgp.consolidador.domain;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.Data;


@Data
public class ParamsPar {

    private Double kmInicial;
    private Double kmFinal;
    private String tipoPav;
    private Double PSI;
    private Double SCI;
    private Double TR;
    private Double idade;
    private Double E1;
    private Double E2;
    private Double E3;
    private Double Esl;
    private Double d0ref;
    private Double snef;
    private Double rc;
    private Double jdr;
    private Double eccp;
    private Double kef;
    private Double h1;
    private Double h2;
    private Double h3;


    public ParamsPar(String linhaArquivoParamsPar) {
        String[] split = linhaArquivoParamsPar.split(",");
        this.tipoPav = split[3];
        this.kmInicial = Double.parseDouble(split[1]);
        this.kmFinal = Double.parseDouble(split[2]);
        this.PSI = Double.parseDouble(split[8]);
        this.SCI = Double.parseDouble(split[9]);
        this.TR = Double.parseDouble(split[11]);
        this.idade = Double.parseDouble(split[12]);
        this.E1 = Double.parseDouble(split[14]);
        this.E2 = Double.parseDouble(split[15]);
        this.E3 = Double.parseDouble(split[16]);
        this.Esl = Double.parseDouble(split[17]);
        this.d0ref = Double.parseDouble(split[18]);
        this.snef = Double.parseDouble(split[19]);
        this.rc = Double.parseDouble(split[20]);
        this.jdr = Double.parseDouble(split[21]);
        this.eccp = Double.parseDouble(split[22]);
        this.kef = Double.parseDouble(split[23]);
        this.h1 = Double.parseDouble(split[24]);
        this.h2 = Double.parseDouble(split[25]);
        this.h3 = Double.parseDouble(split[26]);
    }


    public static List<ParamsPar> CreateListComDadosDeDentroDoParampar(List<Path> arquivosParamPar) throws IOException {
        if (arquivosParamPar == null)
            return null;
        var arquivo = arquivosParamPar.stream()
                                     .filter(p -> p.getFileName().toString().toUpperCase().contains("PAR_"))
                                     .findFirst();
        if (arquivo.isEmpty())
            return null;
        var dadosParamPar = new ArrayList<ParamsPar>();
        try (Scanner scanner = new Scanner(arquivo.get())) {
            scanner.nextLine(); // pula cabecalho do arquivo
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (!linha.isEmpty()) {
                    dadosParamPar.add(new ParamsPar(linha));
                }
            }
            return dadosParamPar;
        }
    }

}
