package dnit.sgp.consolidador.domain.estrateg;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import dnit.sgp.consolidador.domain.Titulo;
import dnit.sgp.consolidador.helper.Util;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class EstrategConsolidado {

    private String line;

    public EstrategConsolidado(
                       Titulo titulo,
                       List<EstratDadosPar> dadosPar,
                       List<PPIAno> dadosPPIano,
                       List<QTpista> dadosQTpista,
                       List<QTacost> dadosQTacost
                       ) throws IOException
    {

        StringBuilder linha = new StringBuilder();

        for (int i=0; i<dadosPar.size(); i++) {

            var par = dadosPar.get(i);
            var ano = par.getAno();
            var key = par.getKey();
            var kmInicial = par.getKmInicial();

            dadosPPIano.forEach(p -> System.out.println(p.getKey() + " - " + key + " = " + p.getKey().equals(key)));

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

            linha.append(titulo.getSNV() + ",");
            linha.append(titulo.getVersao() + ",");
            linha.append(titulo.getSentido() + ",");
            linha.append(titulo.getBR() + ",");
            linha.append(titulo.getUF() + ",");
            linha.append(titulo.getRegiao() + ",");

            linha.append(ano + ",");

            linha.append(par.getRodovia() + ",");
            linha.append(par.getKmInicial() + ",");
            linha.append(par.getKmFinal() + ",");
            linha.append(par.getExtensao() + ",");
            linha.append(par.getVDM() + ",");
            linha.append(par.getNanoUSACE() + ",");
            linha.append(par.getNanoAASHTO() + ",");
            linha.append(par.getNanoCCP() + ",");
            linha.append(par.getIRI() + ",");
            linha.append(par.getPSI() + ",");
            linha.append(par.getIGG() + ",");
            linha.append(par.getSCI() + ",");
            linha.append(par.getATR() + ",");
            linha.append(par.getTR() + ",");
            linha.append(par.getICS() + ",");
            linha.append(par.getConceitoICS() + ",");

            linha.append(par.getIdade() + ",");
            linha.append(par.getVR() + ",");
            linha.append(par.getSnCalibracao() + ",");
            linha.append(par.getMrCalibracao() + ",");
            linha.append(par.getQI0() + ",");
            linha.append(par.getE1() + ",");
            linha.append(par.getE2() + ",");
            linha.append(par.getE3() + ",");
            linha.append(par.getEsl() + ",");

            linha.append(par.getD0f() + ",");
            linha.append(par.getSnef() + ",");
            linha.append(par.getRc() + ",");
            linha.append(par.getJdr() + ",");
            linha.append(par.getEccp() + ",");
            linha.append(par.getKef() + ",");

            linha.append(par.getH1() + ",");
            linha.append(par.getH2() + ",");
            linha.append(par.getH3() + ",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getAcostLE());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getHracLE());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getHcLE());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getAcostLE());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getFaixa1());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getHc1());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getHr1());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getFaixa2());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getHc2());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getHr2());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getFaixa3());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getHc3());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getHr3());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getFaixa4());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getHc4());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getHr4());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getAcostLD());
            linha.append(",");

            if (optPPIano != null && optPPIano.isPresent())
                linha.append(optPPIano.get().getHracLD());
            linha.append(",");

            if (optQTpista != null && optQTpista.isPresent())
                linha.append(optQTpista.get().getCustoPista());
            linha.append(",");

            if (optQTacost != null && optQTacost.isPresent())
                linha.append(optQTacost.get().getCustoAcost());
            linha.append(",");

            linha.append(par.getExtensao() * 33420.59);

            linha.append("\n");
        }
        this.line = linha.toString();
    }


}
