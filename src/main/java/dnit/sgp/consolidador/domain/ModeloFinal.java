package dnit.sgp.consolidador.domain;
import java.nio.file.Path;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ModeloFinal {

    public ModeloFinal(Titulo titulo,
                       List<DadosPar> dadosPar,
                       List<ParamsPar> dadosParamPar,
                       List<ProjetoParam> dadosProjeto,
                       Path arquivoNec
                       )
    {
        StringBuilder linha = new StringBuilder();
        linha.append(titulo.getSNV() + ";");
        linha.append("versaoDoSNV" + ";");
        linha.append(titulo.getSentido() + ";");
        linha.append(titulo.getBR() + ";");
        linha.append(titulo.getUF() + ";");
        linha.append("regiao" + ";");


    }


}
