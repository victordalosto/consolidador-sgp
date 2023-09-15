package dnit.sgp.consolidador.domain.estrateg;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import dnit.sgp.consolidador.domain.Titulo;
import dnit.sgp.consolidador.domain.dados.DadosPar;
import dnit.sgp.consolidador.domain.dados.Nec;
import dnit.sgp.consolidador.domain.dados.ParamsPar;
import dnit.sgp.consolidador.domain.dados.ProjetoParam;
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
            var kmInicial = par.getKmInicial();

            var optPPIano = Optional.ofNullable(dadosPPIano)
                                       .flatMap(lista -> lista.stream()
                                       .filter(d -> Util.valorEhProximo(d.getKmInicial(), kmInicial))
                                       .filter(d -> d.getAno().equals(ano))
                                       .findFirst());

            var optProjeto = Optional.ofNullable(dadosProjeto)
                                     .flatMap(lista -> lista.stream()
                                     .filter(d -> Util.valorEhProximo(d.getKmInicial(), kmInicial))
                                     .findFirst());

            linha.append(titulo.getSNV() + ";");
            linha.append(titulo.getVersao() + ";");
            linha.append(titulo.getSentido() + ";");
            linha.append(titulo.getBR() + ";");
            linha.append(titulo.getUF() + ";");
            linha.append(titulo.getRegiao() + ";");

            linha.append(par.getRodovia() + ";");
            linha.append(par.getKmInicial() + ";");
            linha.append(par.getKmFinal() + ";");
            linha.append(par.getExtensao() + ";");

            if (optParamsPar != null && optParamsPar.isPresent()) {
                linha.append(optParamsPar.get().getTipoPav());
            }
            linha.append(";");

            linha.append(par.getVDM() + ";");
            linha.append(par.getNanoUSACE() + ";");
            linha.append(par.getNanoAASHTO() + ";");
            linha.append(par.getNanoCCP() + ";");
            linha.append(par.getIRI() + ";");
            linha.append(5*Math.exp(-(par.getIRI()*13.0)/71.5) + ";"); // PSI
            linha.append(par.getIGG() + ";");
            linha.append((309.22-(0.616*par.getIGG()))/(61.844+par.getIGG()) + ";"); // SCI
            linha.append(par.getATR() + ";");
            linha.append(par.getFC2() + ";");
            linha.append(par.getFC3() + ";");
            linha.append(Math.min(par.getFC2() + par.getFC3(), 100) + ";"); // TR
            linha.append(par.getAP() + ";");
            linha.append(par.getICS() + ";");
            linha.append(par.getConceitoICS() + ";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getIdade());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getE1());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getE2());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getE3());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getEsl());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getD0ref());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getSnef());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getRc());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getJdr());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getEccp());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getKef());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getH1());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getH2());
            linha.append(";");

            if (optParamsPar != null && optParamsPar.isPresent())
                linha.append(optParamsPar.get().getH3());
            linha.append(";");

            if (optProjeto != null && optProjeto.isPresent())
                linha.append(optProjeto.get().getVR());
            linha.append(";");

            if (optProjeto != null && optProjeto.isPresent())
                linha.append(optProjeto.get().getCriterioVR());
            linha.append(";");

            if (optProjeto != null && optProjeto.isPresent())
                linha.append(optProjeto.get().getCamadaCritica());
            linha.append(";");

            if (optProjeto != null && optProjeto.isPresent())
                linha.append(optProjeto.get().getDiagnostico());
            linha.append(";");

            if (optProjeto != null && optProjeto.isPresent())
                linha.append(optProjeto.get().getMedida());
            linha.append(";");

            if (optProjeto != null && optProjeto.isPresent())
                linha.append(optProjeto.get().getTipoCP());
            linha.append(";");

            if (optProjeto != null && optProjeto.isPresent())
                linha.append(optProjeto.get().getHc());
            linha.append(";");

            if (optProjeto != null && optProjeto.isPresent())
                linha.append(optProjeto.get().getHr());
            linha.append(";");

            if (optProjeto != null && optProjeto.isPresent())
                linha.append(optProjeto.get().getDp());
            linha.append(";");

            var optNec = dadosNec.stream().filter(d -> Util.valorEhProximo(d.getKmInicial(), kmInicial)).findFirst();

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getAcostLE());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getHracLE());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getHcLE());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getFaixa1());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getHc1());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getHr1());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getFaixa2());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getHc2());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getHr2());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getFaixa3());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getHc3());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getHr3());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getFaixa4());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getHc4());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getHr4());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getAcostLD());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getHracLD());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getHracLD());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getVRfx1());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getVRfx2());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getVRfx3());
            linha.append(";");

            if (optNec != null && optNec.isPresent())
                linha.append(optNec.get().getVRfx4());

            linha.append("\n");
        }
        this.line = linha.toString();
    }


}
