package dnit.sgp.consolidador.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ArquivoService {

    private final ArquivoProxy proxy;


    public List<Path> getListArquivosPeloNome(String fileName, String directory) throws IOException {
        var list = proxy.getFilesInDirectory(directory);
        if (list == null) {
            return null;
        }
        return list.stream()
                   .filter(path -> path.getFileName().toString().toUpperCase().contains(fileName.toUpperCase()))
                   .toList();
    }



    public Path getUnicoArquivoPeloNome(String fileName, String directory) throws IOException {
        var list = proxy.getFilesInDirectory(directory);
        if (list == null) {
            return null;
        }
        return list.stream()
                   .filter(path -> path.getFileName().toString().toUpperCase().contains(fileName.toUpperCase()))
                   .findFirst()
                   .orElse(null);
    }



    public List<String> getDadosDentroDoArquivoPeloIndex(String index, Path arquivo) throws IOException {
        if (arquivo == null)
            return null;
        return Files.readAllLines(arquivo)
                    .stream()
                    .filter(p -> p.toUpperCase().contains(index.toUpperCase()))
                    .toList();
    }



    public void salvaArquivo(String nomeArquivo, StringBuffer sb) {
        if (sb == null || sb.length() == 0)
            return;
        proxy.salvaArquivo(nomeArquivo, sb);
    }

}
