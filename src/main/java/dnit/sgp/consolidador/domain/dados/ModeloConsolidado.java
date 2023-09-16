package dnit.sgp.consolidador.domain.dados;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import dnit.sgp.consolidador.domain.Consolidador;
import dnit.sgp.consolidador.domain.Titulo;
import dnit.sgp.consolidador.helper.Util;
import dnit.sgp.consolidador.service.DadosService;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ModeloConsolidado implements Consolidador {

    private DadosService dados = new DadosService();

    public ModeloConsolidado(
        Titulo titulo,
        List<DadosPar> dadosPar,
        List<ParamsPar> dadosParamPar,
        List<ProjetoParam> dadosProjeto,
        List<Nec> dadosNec
        ) throws IOException
    {

        for (int i=0; i<dadosPar.size(); i++) {

            var par = dadosPar.get(i);
            var kmInicial = par.getKmInicial();

            var optParamsPar = Optional.ofNullable(dadosParamPar)
                                       .flatMap(lista -> lista.stream()
                                       .filter(d -> Util.valorEhProximo(d.getKmInicial(), kmInicial))
                                       .findFirst());

            var optProjeto = Optional.ofNullable(dadosProjeto)
                                     .flatMap(lista -> lista.stream()
                                     .filter(d -> Util.valorEhProximo(d.getKmInicial(), kmInicial))
                                     .findFirst());


            var optNec = Optional.ofNullable(dadosNec)
                                  .flatMap(lista -> lista.stream()
                                  .filter(d -> Util.valorEhProximo(d.getKmInicial(), kmInicial))
                                  .findFirst());

            dados.add(titulo.getSNV());
            dados.add(titulo.getVersao());
            dados.add(titulo.getSentido());
            dados.add(titulo.getBR());
            dados.add(titulo.getUF());
            dados.add(titulo.getRegiao());

            dados.add(par.getRodovia());
            dados.add(par.getKmInicial());
            dados.add(par.getKmFinal());
            dados.add(par.getExtensao());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getTipoPav()),
                                        () -> dados.isNull());

            dados.add(par.getVDM());
            dados.add(par.getNanoUSACE());
            dados.add(par.getNanoAASHTO());
            dados.add(par.getNanoCCP());
            dados.add(par.getIRI());
            dados.add(par.getPSI());
            dados.add(par.getIGG());
            dados.add(par.getSCI());
            dados.add(par.getATR());
            dados.add(par.getFC2());
            dados.add(par.getFC3());
            dados.add(par.getTR());
            dados.add(par.getAP());
            dados.add(par.getICS());

            dados.add(par.getConceitoICS());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getIdade()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getE1()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getE2()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getE3()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getEsl()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getD0ref()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getSnef()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getRc()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getJdr()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getEccp()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getKef()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getH1()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getH2()),
                                        () -> dados.isNull());

            optParamsPar.ifPresentOrElse(p -> dados.add(p.getH3()),
                                        () -> dados.isNull());

            optProjeto.ifPresentOrElse(p -> dados.add(p.getVR()),
                                      () -> dados.isNull());

            optProjeto.ifPresentOrElse(p -> dados.add(p.getCriterioVR()),
                                      () -> dados.isNull());

            optProjeto.ifPresentOrElse(p -> dados.add(p.getCamadaCritica()),
                                      () -> dados.isNull());

            optProjeto.ifPresentOrElse(p -> dados.add(p.getDiagnostico()),
                                      () -> dados.isNull());

            optProjeto.ifPresentOrElse(p -> dados.add(p.getMedida()),
                                      () -> dados.isNull());

            optProjeto.ifPresentOrElse(p -> dados.add(p.getTipoCP()),
                                      () -> dados.isNull());

            optProjeto.ifPresentOrElse(p -> dados.add(p.getHc()),
                                      () -> dados.isNull());

            optProjeto.ifPresentOrElse(p -> dados.add(p.getHr()),
                                      () -> dados.isNull());

            optProjeto.ifPresentOrElse(p -> dados.add(p.getDp()),
                                      () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getAcostLE()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHracLE()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHracLE()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHcLE()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getFaixa1()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHc1()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHr1()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getFaixa2()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHc2()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHr2()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getFaixa3()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHc3()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHr3()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getFaixa4()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHc4()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHr4()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getAcostLD()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHracLD()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getHracLD()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getVRfx1()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getVRfx2()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getVRfx3()),
                                  () -> dados.isNull());

            optNec.ifPresentOrElse(p -> dados.add(p.getVRfx4()),
                                  () -> dados.isNull());

            dados.pulaLinha();
        }
    }


    @Override
    public String getConsolidacao() {
        return dados.getTodasLinhas();
    }

}
