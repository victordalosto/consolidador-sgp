package dnit.sgp.consolidador.domain;
import java.io.IOException;
import dnit.sgp.consolidador.service.PropertiesService;
import lombok.Data;


@Data
public class Titulo {

    private String SNV;
    private String versao;
    private String sentido;
    private String BR;
    private String UF;
    private String regiao;


    public Titulo(String fileName, PropertiesService props) throws IOException {
        if (fileName == null || fileName.isEmpty() || !fileName.contains("_") || !fileName.toLowerCase().contains("csv"))
            throw new IllegalArgumentException("Erro na formatacao do arquivo: " + fileName);
        fileName = fileName.replaceAll("\\s+", "").replace(".csv", "");

        this.SNV = fileName.split("_")[1];
        this.sentido = fileName.split("_")[2];
        this.BR = SNV.substring(0, 3);
        this.UF = SNV.substring(4, 6);
        this.versao = props.getParam("versao_snv");
        this.regiao = props.getParam("regiao");

    }

}
