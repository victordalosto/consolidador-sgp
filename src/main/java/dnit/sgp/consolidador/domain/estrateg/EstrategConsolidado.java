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


            linha.append("\n");
        }
        this.line = linha.toString();
    }


}
