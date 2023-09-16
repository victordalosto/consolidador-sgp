package dnit.sgp.consolidador.domain.dados;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dnit.sgp.consolidador.helper.DadosHelper;
import lombok.Data;


@Data
public class ParamsPar {

    private Double kmInicial;
    private Double kmFinal;
    private String tipoPav;
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
        this.kmInicial = DadosHelper.converteDouble(split[1]);
        this.kmFinal = DadosHelper.converteDouble(split[2]);
        this.idade = DadosHelper.converteDouble(split[12]);
        this.E1 = DadosHelper.converteDouble(split[14]);
        this.E2 = DadosHelper.converteDouble(split[15]);
        this.E3 = DadosHelper.converteDouble(split[16]);
        this.Esl = DadosHelper.converteDouble(split[17]);
        this.d0ref = DadosHelper.converteDouble(split[18]);
        this.snef = DadosHelper.converteDouble(split[19]);
        this.rc = DadosHelper.converteDouble(split[20]);
        this.jdr = DadosHelper.converteDouble(split[21]);
        this.eccp = DadosHelper.converteDouble(split[22]);
        this.kef = DadosHelper.converteDouble(split[23]);
        this.h1 = DadosHelper.converteDouble(split[24]);
        this.h2 = DadosHelper.converteDouble(split[25]);
        this.h3 = DadosHelper.converteDouble(split[26]);
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
