package dnit.sgp.consolidador;
import static dnit.sgp.consolidador.helper.Util.contemNoNome;
import static dnit.sgp.consolidador.helper.Util.print;
import static dnit.sgp.consolidador.helper.Util.println;
import static dnit.sgp.consolidador.helper.Util.removeArquivosComString;
import java.io.IOException;
import java.util.List;
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


    public static void main(String[] args) throws Exception {
        println("-- CONSOLIDADOR V0.2 --");
        print("\n Bootstrap.. ");
        bootStrap();
        println("ok");

        println("\n Consolidando Dados.. ");
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
        arquivoProxy = new ArquivoProxy(diretorioRaiz);
        arquivoService = new ArquivoService(arquivoProxy);
    }




    private static void consolidaPAR() throws IOException {
        StringBuffer linhas = new StringBuffer();
        linhas.append("SNV,Versao do SNV,Sentido,BR,UF,Regiao,Rodovia,Inicio (km),Final (km),Extensao (km),TipoPav,VDM,NanoUSACE,NanoAASHTO,NanoCCP,IRI (m/km),PSI,IGG,SCI,ATR (mm),FC2 (%),FC3 (%),TR (%),AP (%),ICS,Conceito ICS,Idade,E1,E2,E3,Esl,D0ref,SNef,Rc,JDR,Eccp,Kef,H1 (cm),H2 (cm),H3 (cm),VR (anos),Critério_VR,CamadaCrítica,Diagnostico,Medida,Tipo_ConservaPesada,hc (cm),HR (cm),Dp (0.01 mm),AcostLE,HRacLE,hcLE,Faixa1,hc1,HR1,Faixa2,hc2,HR2,Faixa3,hc3,HR3,Faixa4,hc4,HR4,AcostLD,HRacLD,hcLD,VR_Fx1,VR_Fx2,VR_Fx3,VR_Fx4" + "\n");

        var arquivoNec = arquivoService.getUnicoArquivoPeloNome("Nec.csv", "Calc");
        var arquivosPar = arquivoService.getListArquivosPeloNome("PAR_", "Dados");

        for (var arquivoPar : arquivosPar) {
            var titulo = new Titulo(arquivoPar.getFileName().toString(), props);
            println(" ..Dado: " + titulo.getSNV());

            String key = titulo.getSNV() + "_" + titulo.getSentido();
            var dadosPar = DadosPar.CreateListComDadosDeDentroDoPar(arquivoPar);

            var arquivoParamPar = arquivoService.getListArquivosPeloNome(key, "Calc/Params");
            arquivoParamPar = removeArquivosComString("_FX1", arquivoParamPar);
            var dadosParamPar = ParamsPar.CreateListComDadosDeDentroDoParampar(arquivoParamPar);

            var arquivoProjeto = arquivoService.getListArquivosPeloNome(key, "Calc/Projeto/Params");
            arquivoProjeto = removeArquivosComString("_FX1", arquivoProjeto);
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

            linhas.append(modelo.getLine());

        }
        arquivoService.salvaArquivo("Consolidado-dados", linhas);
    }




    private static void consolidarEstra() throws IOException {

        for (int i = 0; i <= 10; i++) {
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

            arquivosPar = removeArquivosComString("_FX1", arquivosPar);
            arquivosPar = arquivosPar.stream().filter(p -> contemNoNome(p, "ano ")).collect(Collectors.toList());

            for (var arquivoPar : arquivosPar) {
                String ano = Util.getAnoInString(arquivoPar);
                if (ano.isEmpty()) {
                    continue;
                }

                Titulo titulo = new Titulo(arquivoPar.getFileName().toString(), props);
                String key = titulo.getSNV() + "_" + titulo.getSentido();
                println(" ....Dado: " + titulo.getSNV() + ", Ano: " + ano);

                List<EstratDadosPar> dadosPar = null;
                List<PPIAno> dadoPPIano = null;
                List<QTpista> dadoQTpista = null;
                List<QTacost> dadoQTacost = null;

                dadosPar = EstratDadosPar.CreateListComDadosDeDentroDoPar(arquivoPar);
                dadosPar.stream().forEach(par ->  {
                    par.setAno(ano);
                    par.setKey(key);
                });

                var arquivoPPIano = arquivosPPIano.stream()
                                   .filter(p -> contemNoNome(p, "ppi_ano " + ano + ".csv"))
                                   .findFirst();
                if (arquivoPPIano.isPresent()) {
                    dadoPPIano = PPIAno.CreateListComDadosDeDentroDoPPIano(arquivoPPIano.get());
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

                linhas.append(modelo.getLine());
            }
            arquivoService.salvaArquivo("Consolidado-Estrat-" + i, linhas);
        }
    }

}