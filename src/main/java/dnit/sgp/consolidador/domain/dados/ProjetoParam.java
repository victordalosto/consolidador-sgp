package dnit.sgp.consolidador.domain.dados;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dnit.sgp.consolidador.helper.Util;
import lombok.Data;


@Data
public class ProjetoParam {

    private Double kmInicial;
    private Double kmFinal;
    private Double VR;
    private String criterioVR;
    private String camadaCritica;
    private String diagnostico;
    private String medida;
    private String tipoCP;
    private Double hc;
    private Double hr;
    private Double dp;



    public ProjetoParam(String linhaArquivoParamsPar) {
        String[] split = linhaArquivoParamsPar.replaceAll(", ", ". ").split(",");
        this.kmInicial = Util.converteDouble(split[2]);
        this.kmFinal = Util.converteDouble(split[3]);
        this.VR = Util.converteDouble(split[7]);
        this.criterioVR = split[8];
        this.camadaCritica = split[9];
        this.diagnostico = split[10];
        this.medida = split[12];
        this.tipoCP = split[13];
        this.hc = Util.converteDouble(split[14]);
        this.hr = Util.converteDouble(split[15]);
        this.dp = Util.converteDouble(split[16]);
    }


    public static List<ProjetoParam> CreateListComDadosDeDentroDoPar(List<Path> arquivosProjetoPar) throws IOException {
        if (arquivosProjetoPar == null)
            return null;
        var arquivo = arquivosProjetoPar.stream()
                                     .filter(p -> p.getFileName().toString().toUpperCase().contains("NEC_"))
                                     .findFirst();
        if (arquivo.isEmpty())
            return null;
        var projetoParam = new ArrayList<ProjetoParam>();
        try (Scanner scanner = new Scanner(arquivo.get())) {
            scanner.nextLine(); // pula cabecalho do arquivo
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (!linha.isEmpty()) {
                    projetoParam.add(new ProjetoParam(linha));
                }
            }
            return projetoParam;
        }
    }
}
