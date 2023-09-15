package dnit.sgp.consolidador.service;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
                throw new RuntimeException(e.getMessage());
            }
        }
        return filesCache.get(directory);
    }


    public List<Path> getListArquivosWithName(String fileName, String directory) throws IOException {
        var list = cacheSearch(directory);
        if (list == null) {
            return null;
        }
        return list.stream()
                   .filter(path -> path.getFileName().toString().toUpperCase().contains(fileName.toUpperCase()))
                   .toList();
    }


    public Path getArquivoWithName(String fileName, String directory) throws IOException {
        var list = cacheSearch(directory);
        if (list == null) {
            return null;
        }
        return list.stream()
                   .filter(path -> path.getFileName().toString().toUpperCase().contains(fileName.toUpperCase()))
                   .findFirst()
                   .orElse(null);
    }



    public List<String> buscaDadosNoArquivo(String searchKey, Path arquivo) throws IOException {
        if (arquivo == null)
            return null;
        return Files.readAllLines(arquivo)
                    .stream()
                    .filter(p -> p.toUpperCase().contains(searchKey.toUpperCase()))
                    .toList();
    }


    public void salvaArquivo(StringBuffer sb, File file) {
        if (file.exists())
            file.delete();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            System.out.println(" ..Salvando arquivo em: " + file);
            writer.write(sb.toString());
        } catch (IOException e) {
            System.err.println("Não foi possivel salvar o consolidador: " + e.getMessage());
        }
    }





}
