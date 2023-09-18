package dnit.sgp.consolidador.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
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



    public List<Path> removeArquivosComString(String name, List<Path> lista) {
        if (lista == null || lista.size() == 0) {
            return lista;
        }
            return lista.stream()
                        .filter(f -> !f.getFileName().toString().toUpperCase().contains(name.toUpperCase()))
                        .collect(Collectors.toList());
    }



    public void salvaArquivo(String nomeArquivo, StringBuffer sb) {
        if (sb == null || sb.length() == 0)
            return;
        proxy.salvaArquivo(nomeArquivo, sb);
    }

}
