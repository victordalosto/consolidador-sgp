package dnit.sgp.consolidador.domain.estrateg;
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
public class EstrategConsolidado implements Consolidador {

    private DadosService dados = new DadosService();

    public EstrategConsolidado(
                       Titulo titulo,
                       List<EstratDadosPar> dadosPar,
                       List<PPIAno> dadosPPIano,
                       List<QTpista> dadosQTpista,
                       List<QTacost> dadosQTacost
                       ) throws IOException
    {

        for (int i=0; i<dadosPar.size(); i++) {
            var par = dadosPar.get(i);
            var ano = par.getAno();
            var key = par.getKey();
            var kmInicial = par.getKmInicial();

            var optPPIano = Optional.ofNullable(dadosPPIano)
                                    .flatMap(lista -> lista.stream()
                                    .filter(d -> d.getKey().equals(key))
                                    .filter(d -> Util.valorEhProximo(d.getKmInicial(), kmInicial))
                                    .findFirst());

            var optQTpista = Optional.ofNullable(dadosQTpista)
                                     .flatMap(lista -> lista.stream()
                                     .filter(d -> d.getAno().equals(ano))
                                     .filter(d -> Util.valorEhProximo(d.getKmInicial(), kmInicial))
                                     .findFirst());

            var optQTacost = Optional.ofNullable(dadosQTacost)
                                     .flatMap(lista -> lista.stream()
                                     .filter(d -> d.getAno().equals(ano))
                                     .filter(d -> Util.valorEhProximo(d.getKmInicial(), kmInicial))
                                     .findFirst());

            dados.add(titulo.getSNV());
            dados.add(titulo.getVersao());
            dados.add(titulo.getSentido());
            dados.add(titulo.getBR());
            dados.add(titulo.getUF());
            dados.add(titulo.getRegiao());

            dados.add(ano);

            dados.add(par.getRodovia());
            dados.add(par.getKmInicial());
            dados.add(par.getKmFinal());
            dados.add(par.getExtensao());
            dados.add(par.getVDM());
            dados.add(par.getNanoUSACE());
            dados.add(par.getNanoAASHTO());
            dados.add(par.getNanoCCP());
            dados.add(par.getIRI());
            dados.add(par.getPSI());
            dados.add(par.getIGG());
            dados.add(par.getSCI());
            dados.add(par.getATR());
            dados.add(par.getTR());
            dados.add(par.getICS());
            dados.add(par.getConceitoICS());

            dados.add(par.getIdade());
            dados.add(par.getVR());
            dados.add(par.getSnCalibracao());
            dados.add(par.getMrCalibracao());
            dados.add(par.getQI0());
            dados.add(par.getE1());
            dados.add(par.getE2());
            dados.add(par.getE3());
            dados.add(par.getEsl());

            dados.add(par.getD0f());
            dados.add(par.getSnef());
            dados.add(par.getRc());
            dados.add(par.getJdr());
            dados.add(par.getEccp());
            dados.add(par.getKef());

            dados.add(par.getH1());
            dados.add(par.getH2());
            dados.add(par.getH3());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getAcostLE()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getHracLE()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getHcLE()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getFaixa1()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getHc1()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getHr1()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getFaixa2()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getHc2()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getHr2()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getFaixa3()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getHc3()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getHr3()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getFaixa4()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getHc4()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getHr4()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getAcostLD()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getHracLD()),
                                     () -> dados.isNull());

            optPPIano.ifPresentOrElse(p -> dados.add(p.getHcLD()),
                                     () -> dados.isNull());

            optQTpista.ifPresentOrElse(p -> dados.add(p.getCustoPista()),
                                     () -> dados.isNull());

            optQTacost.ifPresentOrElse(p -> dados.add(p.getCustoAcost()),
                                     () -> dados.isNull());

            dados.add(par.getCustoTotal());

            dados.pulaLinha();
        }
    }


    @Override
    public String getConsolidacao() {
        return dados.getTodasLinhas();
    }


}
