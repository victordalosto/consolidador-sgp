package dnit.sgp.consolidador.service;

public class DadosService {

    private final StringBuffer linhas = new StringBuffer();

    private final String separador = ",";


    public synchronized void add(String linha) {
        linha.replaceAll(separador, ".");
        linhas.append(linha + separador);
    }


    public synchronized void add(Double numero) {
        linhas.append(numero + separador);
    }


    public synchronized void isNull() {
        linhas.append(separador);
    }


    public String getTodasLinhas() {
        return linhas.toString();
    }


    public void pulaLinha() {
        linhas.append("\n");
    }

}
