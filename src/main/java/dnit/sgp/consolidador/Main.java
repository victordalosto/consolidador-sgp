package dnit.sgp.consolidador;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import dnit.sgp.consolidador.domain.dados.DadosPar;
import dnit.sgp.consolidador.domain.dados.ModeloConsolidado;
import dnit.sgp.consolidador.domain.dados.Nec;
import dnit.sgp.consolidador.domain.dados.ParamsPar;
import dnit.sgp.consolidador.domain.dados.ProjetoParam;
import dnit.sgp.consolidador.domain.dados.Titulo;
import dnit.sgp.consolidador.helper.Util;
import dnit.sgp.consolidador.service.ArquivoService;
import dnit.sgp.consolidador.service.PropertiesService;


public class Main {

    private static String diretorioRaiz;
    private static PropertiesService props;
    private static ArquivoService arquivoService;


    public static void main(String[] args) throws Exception {
        System.out.println("-- CONSOLIDADOR V0.1 --");
        System.out.print(" Bootstrap.. ");
        bootStrap();
        System.out.println("ok");

        System.out.println("\n Consolidando Dados.. ");
        if ("true".equals(props.getParam("consolidar_dados"))) {
            consolidaPAR();
            System.out.println("\n Consolidando Dados.. ok");
        } else {
            System.out.println(" Consolidando Dados.. ignorado");
        }


        System.out.println("\n Consolidando Estrat.. ");
        if ("true".equals(props.getParam("consolidar_estrat"))) {
            consolidarEstra();
            System.out.println("\n Consolidando Estrat.. ok");
        } else {
            System.out.println(" Consolidando Estrat.. ignorado");
        }

    }




    private static void bootStrap() throws IOException {
        props = new PropertiesService("configuracoes.properties");
        diretorioRaiz = props.getParam("diretorio_raiz");
        arquivoService = new ArquivoService(diretorioRaiz);
    }




    private static void consolidaPAR() throws IOException {
        StringBuffer linhas = new StringBuffer();
        linhas.append(Util.titulo + "\n");

        var arquivoNec = arquivoService.getArquivoWithName("Nec.csv", "Calc");
        var arquivosPar = arquivoService.getListArquivosWithName("PAR_", "Dados");

        for (var arquivoPar : arquivosPar) {
            var titulo = new Titulo(arquivoPar.getFileName().toString(), props);
            var dadosPar = DadosPar.CreateListComDadosDeDentroDoPar(arquivoPar);

            System.out.println("Inserindo: " + titulo.getSNV());
            String key = titulo.getSNV() + "_" + titulo.getSentido();

            var arquivoParamPar = arquivoService.getListArquivosWithName(key, "Calc/Params");
            Util.removeArquivosComString("_FX1", arquivoParamPar);
            var dadosParamPar = ParamsPar.CreateListComDadosDeDentroDoParampar(arquivoParamPar);

            var arquivoProjeto = arquivoService.getListArquivosWithName(key, "Calc/Projeto/Params");
            Util.removeArquivosComString("_FX1", arquivoParamPar);
            var dadosProjeto = ProjetoParam.CreateListComDadosDeDentroDoPar(arquivoProjeto);

            var dadosNec = arquivoService.buscaDadosNoArquivo(key, arquivoNec);
            var nec = Nec.createListComDadosDentroDoNec(dadosNec);

            var modelo = new ModeloConsolidado(
                                            titulo,
                                            dadosPar,
                                            dadosParamPar,
                                            dadosProjeto,
                                            nec
            );

            linhas.append(modelo.getLine());

        }
        File file = Paths.get(diretorioRaiz, "Resultados", "Consolidado-dados.csv").toFile();
        arquivoService.salvaArquivo(linhas, file);
    }




    private static void consolidarEstra() throws IOException {

        for (int i = 0; i <= 10; i++) {
            var pastaEstrat = "Calc/Estrat_" + i;

            var arquivosQTpista = arquivoService.getListArquivosWithName("QTpista_", pastaEstrat);
            var arquivosQTacost = arquivoService.getListArquivosWithName("QTacost_", pastaEstrat);
            var arquivosPPIano = arquivoService.getListArquivosWithName("PPI_Ano", pastaEstrat);

            var arquivosPar = arquivoService.getListArquivosWithName("PAR_", pastaEstrat + "/Params");

            if (arquivosPar == null || arquivosPPIano == null) {
                continue;
            }
            Util.removeArquivosComString("_FX1", arquivosPar);

            for (var ppiAno : arquivosPPIano) {
                String ano = ppiAno.getFileName().toString().replaceAll("[^0-9]+", "");
                if (ano.isEmpty()) {
                    continue;
                }

                for (var arquivoPar : arquivosPar) {
                    var titulo = new Titulo(arquivoPar.getFileName().toString(), props);
                    var dadosPar = DadosPar.CreateListComDadosDeDentroDoPar(arquivoPar);
                }

                var dadosPPIano = arquivoService.buscaDadosNoArquivo(ano, ppiAno);

            }

            System.out.println();
            // System.out.println("Arquivospar: " + arquivosPar);
            // System.out.println("QTpistsa: " + arquivosQTpista);
            // System.out.println("QTacost: " + arquivosQTacost);
            arquivosPar.stream()
                       .filter(p -> p.getFileName().toString().toUpperCase().contains("_FX2_"))
                       .collect(Collectors.toList());

            System.out.println(arquivosPar.size());






        }
    }


}