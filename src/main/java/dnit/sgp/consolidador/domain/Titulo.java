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
        this.regiao = converteRegiao(UF);
    }


    private String converteRegiao(String uf) {
        switch (uf.toUpperCase()) {
            case "AC":
            case "AM":
            case "AP":
            case "PA":
            case "RO":
            case "RR":
            case "TO":
                return "Norte";

            case "AL":
            case "BA":
            case "CE":
            case "MA":
            case "PB":
            case "PE":
            case "PI":
            case "RN":
            case "SE":
                return "Nordeste";

            case "ES":
            case "MG":
            case "RJ":
            case "SP":
                return "Sudeste";

            case "PR":
            case "RS":
            case "SC":
                return "Sul";

            case "DF":
            case "GO":
            case "MT":
            case "MS":
                return "Centro-Oeste";

            default:
                return "UF desconhecida";
        }
    }

}
