package dnit.sgp.consolidador.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ArquivoService {

    private final String diretorioRaiz;

    private HashMap<String, List<Path>> filesCache = new HashMap<>();


    private synchronized List<Path> cacheSearch(String directory) {
        if (!filesCache.containsKey(directory)) {
            try {
                List<Path> arquivos = new ArrayList<>();
                Files.walk(Paths.get(diretorioRaiz, directory))
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().toLowerCase().endsWith(".csv"))
                        .forEach(arquivos::add);
                filesCache.put(directory, arquivos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filesCache.get(directory);
    }


    public List<Path> getListArquivosWithName(String fileName, String directory) throws IOException {
        var list = cacheSearch(directory)
                .stream()
                .filter(path -> path.getFileName().toString().toUpperCase().contains(fileName.toUpperCase()))
                .toList();
        if (list == null || list.size() <= 1) {
            return list;
        }
        var opt = list.stream().filter(f -> f.getFileName().toString().toUpperCase().contains("FX2")).findFirst();
        if (opt.isPresent()) {
            return List.of(opt.get());
        }
        return list;
    }


    public Path getArquivoWithName(String fileName, String directory) throws IOException {
        var list = cacheSearch(directory);
        return list.stream()
                .filter(path -> path.getFileName().toString().toUpperCase().contains(fileName.toUpperCase()))
                .findFirst()
                .orElse(null);
    }



    public List<String> buscaDadosNoArquivo(String searchKey, String fileName, String directory) throws IOException {
        var arquivo = getArquivoWithName(fileName, directory);
        if (arquivo == null)
            return null;
        return Files.readAllLines(arquivo)
                    .stream()
                    .filter(p -> p.toUpperCase().startsWith(searchKey.toUpperCase()))
                    .toList();
    }





}
