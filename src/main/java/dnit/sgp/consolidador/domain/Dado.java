package dnit.sgp.consolidador.domain;

public class Dado {

    private final StringBuffer linhas = new StringBuffer();

    private final char separador = ',';



    public synchronized void addString(String linha) {
        linhas.append(linha + separador);
    }


    public synchronized void addDouble(String linha) {
        if (linha == null || linha.isEmpty()) {
            linhas.append("0.0" + separador);
        }
        linha = linha.replaceAll(",", ".").replaceAll("[^0-9.]", "");
        if (linha.isEmpty()) {
            linhas.append("0.0" + separador);
        }
        linhas.append(Double.parseDouble(linha) + separador);
    }


    public String getTodasLinhas() {
        return linhas.toString();
    }

}
