package dnit.sgp.consolidador;
import java.io.IOException;
import dnit.sgp.consolidador.domain.DadosPar;
import dnit.sgp.consolidador.domain.ModeloFinal;
import dnit.sgp.consolidador.domain.ParamsPar;
import dnit.sgp.consolidador.domain.ProjetoParam;
import dnit.sgp.consolidador.domain.Titulo;
import dnit.sgp.consolidador.helper.Util;
import dnit.sgp.consolidador.service.ArquivoService;
import dnit.sgp.consolidador.service.PropertiesService;


public class Main {

    private static PropertiesService propertiesService;
    private static ArquivoService arquivoService;


    public static void main(String[] args) throws Exception {
        bootStrap();

        StringBuffer linhas = new StringBuffer();
        linhas.append(Util.titulo);

        var arquivosPar = arquivoService.getListArquivosWithName("PAR_", "Dados");
        for (var arquivoPar : arquivosPar) {
            if (arquivoPar.getFileName().toString().contains("101BSE0970_CD")) {
                var titulo = new Titulo(arquivoPar.getFileName().toString());
                var dadosPar = DadosPar.CreateListComDadosDeDentroDoPar(arquivoPar);

                var arquivoParamPar = arquivoService.getListArquivosWithName(titulo.getSNV(), "Calc/Params");
                var dadosParamPar = ParamsPar.CreateListComDadosDeDentroDoParampar(arquivoParamPar);

                var arquivoProjeto = arquivoService.getListArquivosWithName(titulo.getSNV(), "Calc/Projeto/Params");
                var dadosProjeto = ProjetoParam.CreateListComDadosDeDentroDoPar(arquivoProjeto);

                var arquivoNec = arquivoService.getArquivoWithName("Nec.csv", "Calc");

                var modelo = new ModeloFinal(titulo,
                                             dadosPar,
                                             dadosParamPar,
                                             dadosProjeto,
                                             arquivoNec);

            }


        }
        System.out.println(linhas);

    }

    private static void bootStrap() throws IOException {
        propertiesService = new PropertiesService("configuracoes.properties");
        var diretorioRaiz = propertiesService.getProperty("diretorio_raiz");
        arquivoService = new ArquivoService(diretorioRaiz);
    }

}