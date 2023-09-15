package dnit.sgp.consolidador;
import static dnit.sgp.consolidador.helper.Util.contemNoNome;
import static dnit.sgp.consolidador.helper.Util.print;
import static dnit.sgp.consolidador.helper.Util.println;
import static dnit.sgp.consolidador.helper.Util.removeArquivosComString;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import dnit.sgp.consolidador.domain.Titulo;
import dnit.sgp.consolidador.domain.dados.DadosPar;
import dnit.sgp.consolidador.domain.dados.ModeloConsolidado;
import dnit.sgp.consolidador.domain.dados.Nec;
import dnit.sgp.consolidador.domain.dados.ParamsPar;
import dnit.sgp.consolidador.domain.dados.ProjetoParam;
import dnit.sgp.consolidador.domain.estrateg.EstratDadosPar;
import dnit.sgp.consolidador.helper.Util;
import dnit.sgp.consolidador.service.ArquivoService;
import dnit.sgp.consolidador.service.PropertiesService;


public class Main {

    private static String diretorioRaiz;
    private static PropertiesService props;
    private static ArquivoService arquivoService;


    public static void main(String[] args) throws Exception {
        println("-- CONSOLIDADOR V0.1 --");
        print("\n Bootstrap.. ");
        bootStrap();
        println("ok");

        print("\n Consolidando Dados.. ");
        if ("true".equals(props.getParam("consolidar_dados"))) {
            consolidaPAR();
            println("Consolidando Dados.. ok");
        } else {
            println(" ignorado");
        }


        println("\n Consolidando Estrat.. ");
        if ("true".equals(props.getParam("consolidar_estrat"))) {
            consolidarEstra();
            println("Consolidando Estrat.. ok");
        } else {
            println(" ignorado");
        }

    }




    private static void bootStrap() throws IOException {
        props = new PropertiesService("configuracoes.properties");
        diretorioRaiz = props.getParam("diretorio_raiz");
        arquivoService = new ArquivoService(diretorioRaiz);
    }




    private static void consolidaPAR() throws IOException {
        StringBuffer linhas = new StringBuffer();
        linhas.append("SNV;Versao do SNV;Sentido;BR;UF;Regiao;Rodovia;Inicio (km);Final (km);Extensao (km);TipoPav;VDM;NanoUSACE;NanoAASHTO;NanoCCP;IRI (m/km);PSI;IGG;SCI;ATR (mm);FC2 (%);FC3 (%);TR (%);AP (%);ICS;Conceito ICS;Idade;E1;E2;E3;Esl;D0ref;SNef;Rc;JDR;Eccp;Kef;H1 (cm);H2 (cm);H3 (cm);VR (anos);Critério_VR;CamadaCrítica;Diagnostico;Medida;Tipo_ConservaPesada;hc (cm);HR (cm);Dp (0.01 mm);AcostLE;HRacLE;hcLE;Faixa1;hc1;HR1;Faixa2;hc2;HR2;Faixa3;hc3;HR3;Faixa4;hc4;HR4;AcostLD;HRacLD;hcLD;VR_Fx1;VR_Fx2;VR_Fx3;VR_Fx4" + "\n");

        var arquivoNec = arquivoService.getArquivoWithName("Nec.csv", "Calc");
        var arquivosPar = arquivoService.getListArquivosWithName("PAR_", "Dados");

        for (var arquivoPar : arquivosPar) {
            var titulo = new Titulo(arquivoPar.getFileName().toString(), props);
            var dadosPar = DadosPar.CreateListComDadosDeDentroDoPar(arquivoPar);

            println(" ..Dado: " + titulo.getSNV());
            String key = titulo.getSNV() + "_" + titulo.getSentido();

            var arquivoParamPar = arquivoService.getListArquivosWithName(key, "Calc/Params");
            arquivoParamPar = removeArquivosComString("_FX1", arquivoParamPar);
            var dadosParamPar = ParamsPar.CreateListComDadosDeDentroDoParampar(arquivoParamPar);

            var arquivoProjeto = arquivoService.getListArquivosWithName(key, "Calc/Projeto/Params");
            arquivoProjeto = removeArquivosComString("_FX1", arquivoProjeto);
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
            println(" ..Rodando: " + pastaEstrat);

            var arquivosQTpista = arquivoService.getListArquivosWithName("QTpista_", pastaEstrat);
            var arquivosQTacost = arquivoService.getListArquivosWithName("QTacost_", pastaEstrat);
            var arquivosPPIano = arquivoService.getListArquivosWithName("PPI_Ano", pastaEstrat);

            var arquivosPar = arquivoService.getListArquivosWithName("PAR_", pastaEstrat + "/Params");

            if (arquivosPar == null || arquivosPPIano == null) {
                continue;
            }

            arquivosPar = removeArquivosComString("_FX1", arquivosPar);
            arquivosPar = arquivosPar.stream()
                                     .filter(p -> contemNoNome(p, "ano "))
                                     .collect(Collectors.toList());

            for (var arquivoPar : arquivosPar) {
                String ano = Util.getAnoInString(arquivoPar);
                if (ano.isEmpty()) {
                    continue;
                }
                var titulo = new Titulo(arquivoPar.getFileName().toString(), props);
                System.out.println("\n" + arquivoPar);
                System.out.println(titulo);
                var dadosPar = EstratDadosPar.CreateListComDadosDeDentroDoPar(arquivoPar);
                var dadosPPIAno = arquivosPPIano.stream()
                                   .filter(p -> contemNoNome(p, "ppi_ano " + ano))
                                   .filter(p -> contemNoNome(p, titulo.getSNV().toLowerCase()))
                                   .filter(p -> contemNoNome(p, "_" + titulo.getSentido().toLowerCase() + "_"))
                                   .collect(Collectors.toList());
                System.out.println(dadosPPIAno);

            }


            for (var ppiAno : arquivosPPIano) {
                String ano = ppiAno.getFileName().toString().replaceAll("[^0-9]+", "");
                if (ano.isEmpty()) {
                    continue;
                }



                var dadosPPIano = arquivoService.buscaDadosNoArquivo(ano, ppiAno);

            }

            println("");
            // println("Arquivospar: " + arquivosPar);
            // println("QTpistsa: " + arquivosQTpista);
            // println("QTacost: " + arquivosQTacost);
            arquivosPar.stream()
                       .filter(p -> p.getFileName().toString().toUpperCase().contains("_FX2_"))
                       .collect(Collectors.toList());

            println("" + arquivosPar.size());






        }
    }


}