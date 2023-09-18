package dnit.sgp.consolidador;
import static dnit.sgp.consolidador.helper.Util.contemNoNome;
import static dnit.sgp.consolidador.helper.Util.print;
import static dnit.sgp.consolidador.helper.Util.println;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import dnit.sgp.consolidador.domain.Titulo;
import dnit.sgp.consolidador.domain.dados.DadosPar;
import dnit.sgp.consolidador.domain.dados.ModeloConsolidado;
import dnit.sgp.consolidador.domain.dados.Nec;
import dnit.sgp.consolidador.domain.dados.ParamsPar;
import dnit.sgp.consolidador.domain.dados.ProjetoParam;
import dnit.sgp.consolidador.domain.estrateg.EstratDadosPar;
import dnit.sgp.consolidador.domain.estrateg.EstrategConsolidado;
import dnit.sgp.consolidador.domain.estrateg.PPIAno;
import dnit.sgp.consolidador.domain.estrateg.QTacost;
import dnit.sgp.consolidador.domain.estrateg.QTpista;
import dnit.sgp.consolidador.helper.Util;
import dnit.sgp.consolidador.service.ArquivoProxy;
import dnit.sgp.consolidador.service.ArquivoService;
import dnit.sgp.consolidador.service.PropertiesService;


public class Main {

    private static String diretorioRaiz;
    private static PropertiesService props;
    private static ArquivoProxy arquivoProxy;
    private static ArquivoService arquivoService;
    private static ExecutorService executor;


    public static void main(String[] args) throws Exception {
        println("-- CONSOLIDADOR V1.0 --");

        print("\n Bootstrap.. ");
        bootStrap();
        println("ok");

        print("\n Consolidando Dados.. ");
        if (props.getBooleanParam("consolidar_dados")) {
            println("");
            consolidaPAR();
            println("Consolidando Dados.. ok");
        } else {
            println(" ignorado");
        }

        arquivoProxy.clearCache();

        print("\n Consolidando Estrat.. ");
        if (props.getBooleanParam("consolidar_estrat")) {
            println("");
            consolidarEstra();
            println("Consolidando Estrat.. ok");
        } else {
            println(" ignorado");
        }
        println("\n\n -- FIM --");
    }




    private static void bootStrap() throws IOException {
        props = new PropertiesService("configuracoes.properties");
        diretorioRaiz = props.getParam("diretorio_raiz");
        arquivoProxy = new ArquivoProxy(diretorioRaiz);
        arquivoService = new ArquivoService(arquivoProxy);
    }




    private static void consolidaPAR() throws IOException, InterruptedException {
        StringBuffer linhas = new StringBuffer();
        linhas.append("SNV,Versao do SNV,Sentido,BR,UF,Regiao,Rodovia,Inicio (km),Final (km),Extensao (km),TipoPav,VDM,NanoUSACE,NanoAASHTO,NanoCCP,IRI (m/km),PSI,IGG,SCI,ATR (mm),FC2 (%),FC3 (%),TR (%),AP (%),ICS,Conceito ICS,Idade,E1,E2,E3,Esl,D0ref,SNef,Rc,JDR,Eccp,Kef,H1 (cm),H2 (cm),H3 (cm),VR (anos),Criterio_VR,CamadaCritica,Diagnostico,Medida,Tipo_ConservaPesada,hc (cm),HR (cm),Dp (0.01 mm),AcostLE,HRacLE,hcLE,Faixa1,hc1,HR1,Faixa2,hc2,HR2,Faixa3,hc3,HR3,Faixa4,hc4,HR4,AcostLD,HRacLD,hcLD,VR_Fx1,VR_Fx2,VR_Fx3,VR_Fx4" + "\n");

        var arquivoNec = arquivoService.getUnicoArquivoPeloNome("Nec.csv", "Calc");
        var arquivosPar = arquivoService.getListArquivosPeloNome("PAR_", "Dados");

        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (var arquivoPar : arquivosPar) {
            Runnable task = () -> {
                try {
                    taskDados(linhas, arquivoNec, arquivoPar);
                } catch (IOException e) {
                    println("Erro no arquivo: " + arquivoPar.toAbsolutePath().toString());
                    e.printStackTrace();
                }
            };
            executor.submit(task);
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        arquivoService.salvaArquivo("Consolidado-dados", linhas);
    }




    private static void taskDados(StringBuffer linhas, Path arquivoNec, Path arquivoPar) throws IOException {
        var titulo = new Titulo(arquivoPar.getFileName().toString(), props);
        println(" ..Dado: " + titulo.getSNV());

        String key = titulo.getSNV() + "_" + titulo.getSentido();
        var dadosPar = DadosPar.CreateListComDadosDeDentroDoPar(arquivoPar);

        var arquivoParamPar = arquivoService.getListArquivosPeloNome(key, "Calc/Params");
        arquivoParamPar = arquivoService.removeArquivosComString("_FX1", arquivoParamPar);
        var dadosParamPar = ParamsPar.CreateListComDadosDeDentroDoParampar(arquivoParamPar);

        var arquivoProjeto = arquivoService.getListArquivosPeloNome(key, "Calc/Projeto/Params");
        arquivoProjeto = arquivoService.removeArquivosComString("_FX1", arquivoProjeto);
        arquivoProjeto = arquivoService.removeArquivosComString("CALIB", arquivoProjeto);
        var dadosProjeto = ProjetoParam.CreateListComDadosDeDentroDoPar(arquivoProjeto);

        var dadosNec = arquivoService.getDadosDentroDoArquivoPeloIndex(key, arquivoNec);
        var nec = Nec.createListComDadosDentroDoNec(dadosNec);

        var modelo = new ModeloConsolidado(
                                        titulo,
                                        dadosPar,
                                        dadosParamPar,
                                        dadosProjeto,
                                        nec
        );

        linhas.append(modelo.getConsolidacao());
    }




    private static void consolidarEstra() throws IOException, InterruptedException {

        for (int i = 0; i <= 10; i++) {
            executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            var linhas = new StringBuffer();

            var pastaEstrat = "Calc/Estrat_" + i;
            var arquivosPar = arquivoService.getListArquivosPeloNome("PAR_", pastaEstrat + "/Params");
            var arquivosPPIano = arquivoService.getListArquivosPeloNome("PPI_Ano", pastaEstrat);
            var arquivosQTpista = arquivoService.getListArquivosPeloNome("QTpista_", pastaEstrat);
            var arquivosQTacost = arquivoService.getListArquivosPeloNome("QTacost_", pastaEstrat);

            if (arquivosPar == null || arquivosPar.isEmpty() || arquivosPPIano == null || arquivosPPIano.isEmpty()) {
                println(" .. Ignorando a Estrategia: " + i);
                continue;
            }

            println(" ..Rodando Estrategia: " + i);
            linhas.append("SNV,Versao do SNV,Sentido,BR,UF,Regiao,Ano,Rodovia,Inicio (km),Final (km),Extensao (km),VDM,NanoUSACE,NanoAASHTO,NanoCCP,IRI (m/km),PSI,IGG,SCI,ATR (mm),TR (%),ICS,Conceito ICS,Idade,VR(anos),SN calibracao, MR calibracao, QI0,E1,E2,E3,Esl,D0ref,SNef,Rc,JDR,Eccp,Kef,H1 (cm),H2 (cm),H3 (cm),AcostLE,HRacLE,hcLE,Faixa1,hc1,HR1,Faixa2,hc2,HR2,Faixa3,hc3,HR3,Faixa4,hc4,HR4,AcostLD,HRacLD,hcLD,Custo_Pista,Custo_Acost.,Custo_CR" + "\n");

            arquivosPar = arquivoService.removeArquivosComString("_FX1", arquivosPar);
            arquivosPar = arquivosPar.stream().filter(p -> contemNoNome(p, "ano ")).collect(Collectors.toList());


            var mapAnoPPIano = new HashMap<String, List<PPIAno>>();

            if (arquivosPPIano != null) {
                arquivosPPIano.forEach(ppiAno -> {
                    String ano = Util.getAnoInString(ppiAno);
                    List<PPIAno> ppiAnos;
                    try {
                        ppiAnos = PPIAno.CreateListComDadosDeDentroDoPPIano(ppiAno);
                        mapAnoPPIano.put(ano, ppiAnos);
                    } catch (IOException e) {
                        println("Erro no arquivo ppiAno: " + ppiAno.toAbsolutePath().toString());
                        e.printStackTrace();
                    }
                });
            }

            for (var arquivoPar : arquivosPar) {
                final int finalI = i;
                Runnable task = () -> {
                    try {
                        taskEstrateg(finalI, linhas, mapAnoPPIano, arquivosQTpista, arquivosQTacost, arquivoPar);
                    } catch (IOException e) {
                        println("Erro no arquivo: " + arquivoPar.toAbsolutePath().toString());
                        e.printStackTrace();
                    }
                };
                executor.submit(task);
            }
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            arquivoService.salvaArquivo("Consolidado-Estrat-" + i, linhas);
            System.out.println(" ..Estrategia: " + i + " ok");
        }
    }




    private static void taskEstrateg(
            int i,
            StringBuffer linhas,
            HashMap<String, List<PPIAno>> mapAnoPPIano,
            List<Path> arquivosQTpista,
            List<Path> arquivosQTacost,
            Path arquivoPar)
    throws IOException
    {

        String ano = Util.getAnoInString(arquivoPar);

        if (!ano.isEmpty()) {
            Titulo titulo = new Titulo(arquivoPar.getFileName().toString(), props);
            String key = titulo.getSNV() + "_" + titulo.getSentido();
            println(" ....Estrategia: " + i + ", Dado: " + titulo.getSNV() + ", Ano: " + ano);

            List<EstratDadosPar> dadosPar = null;
            List<PPIAno> dadoPPIano = null;
            List<QTpista> dadoQTpista = null;
            List<QTacost> dadoQTacost = null;

            dadosPar = EstratDadosPar.CreateListComDadosDeDentroDoPar(arquivoPar);
            dadosPar.stream().forEach(par ->  {
                par.setAno(ano);
                par.setKey(key);
            });

            if (mapAnoPPIano.containsKey(ano)) {
                dadoPPIano = mapAnoPPIano.get(ano);
            }

            if (arquivosQTpista != null) {
                var arquivoQTpista = arquivosQTpista.stream()
                                                    .filter(p -> contemNoNome(p, titulo.getSNV() + "_" + titulo.getSentido()))
                                                    .findFirst();
                if (arquivoQTpista.isPresent()) {
                    dadoQTpista = QTpista.CreateListComDadosDeDentroDoQTpista(arquivoQTpista.get());
                }
            }

            if (arquivosQTacost != null) {
                var arquivoQTacost = arquivosQTacost.stream()
                                                    .filter(p -> contemNoNome(p, titulo.getSNV() + "_" + titulo.getSentido()))
                                                    .findFirst();
                if (arquivoQTacost.isPresent()) {
                    dadoQTacost = QTacost.CreateListComDadosDeDentroDoQTacost(arquivoQTacost.get());
                }
            }

            var modelo = new EstrategConsolidado(
                titulo,
                dadosPar,
                dadoPPIano,
                dadoQTpista,
                dadoQTacost
            );

            linhas.append(modelo.getConsolidacao());
        }
    }

}