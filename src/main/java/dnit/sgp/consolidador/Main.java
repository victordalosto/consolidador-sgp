package dnit.sgp.consolidador;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import dnit.sgp.consolidador.domain.DadosPar;
import dnit.sgp.consolidador.domain.ModeloConsolidado;
import dnit.sgp.consolidador.domain.Nec;
import dnit.sgp.consolidador.domain.ParamsPar;
import dnit.sgp.consolidador.domain.ProjetoParam;
import dnit.sgp.consolidador.domain.Titulo;
import dnit.sgp.consolidador.helper.Util;
import dnit.sgp.consolidador.service.ArquivoService;
import dnit.sgp.consolidador.service.PropertiesService;


public class Main {

    private static String diretorioRaiz;
    private static PropertiesService props;
    private static ArquivoService arquivoService;


    public static void main(String[] args) throws Exception {
        bootStrap();

        if ("true".equals(props.getParam("consolidar_dados"))) {
            consolidaPAR();
        }

    }

    private static void consolidaPAR() throws IOException {
        StringBuffer linhas = new StringBuffer();
        linhas.append(Util.titulo + "\n");

        var arquivosPar = arquivoService.getListArquivosWithName("PAR_", "Dados");
        for (var arquivoPar : arquivosPar) {
            var titulo = new Titulo(arquivoPar.getFileName().toString(), props);
            var dadosPar = DadosPar.CreateListComDadosDeDentroDoPar(arquivoPar);

            String key = titulo.getSNV() + "_" + titulo.getSentido();

            var arquivoParamPar = arquivoService.getListArquivosWithName(key, "Calc/Params");
            var dadosParamPar = ParamsPar.CreateListComDadosDeDentroDoParampar(arquivoParamPar);

            var arquivoProjeto = arquivoService.getListArquivosWithName(key, "Calc/Projeto/Params");
            var dadosProjeto = ProjetoParam.CreateListComDadosDeDentroDoPar(arquivoProjeto);

            var arquivoNec = arquivoService.buscaDadosNoArquivo(key, "Nec.csv", "Calc");
            var nec = Nec.createListComDadosDentroDoNec(arquivoNec);

            var modelo = new ModeloConsolidado(
                                            titulo,
                                            dadosPar,
                                            dadosParamPar,
                                            dadosProjeto,
                                            nec
            );

            linhas.append(modelo.getLine());

        }
        File file = Paths.get(diretorioRaiz, "Resultados", "Consolidado_v2.csv").toFile();
        arquivoService.salvaArquivo(linhas, file);
    }

    private static void bootStrap() throws IOException {
        props = new PropertiesService("configuracoes.properties");
        diretorioRaiz = props.getParam("diretorio_raiz");
        arquivoService = new ArquivoService(diretorioRaiz);
    }

}