package dnit.sgp.consolidador.domain;
import lombok.Data;


@Data
public class Titulo {

    private String SNV;
    private String sentido;
    private String BR;
    private String UF;


    public Titulo(String fileName) {
        if (fileName == null || fileName.isEmpty() || !fileName.contains("_") || !fileName.toLowerCase().contains("csv"))
            throw new IllegalArgumentException("Erro na formatacao do arquivo: " + fileName);
        fileName = fileName.replaceAll("\\s+", "").replace(".csv", "");

        this.SNV = fileName.split("_")[1];
        this.sentido = fileName.split("_")[2];
        this.BR = SNV.substring(0, 3);
        this.UF = SNV.substring(4, 6);
    }

}
