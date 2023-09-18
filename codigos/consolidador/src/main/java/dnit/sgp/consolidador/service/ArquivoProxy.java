package dnit.sgp.consolidador.service;
import static dnit.sgp.consolidador.helper.Util.println;
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
public class ArquivoProxy {

    private final String diretorioRaiz;
    private HashMap<String, List<Path>> filesCache = new HashMap<>();

    public synchronized List<Path> getFilesInDirectory(String directory) {
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

    public void clearCache() {
        filesCache.clear();
    }



    public void salvaArquivo(String nome, StringBuffer sb) {
        File file = Paths.get(diretorioRaiz, "Resultados", nome).toFile();
        String originalFileName = file.getName();
        if (!originalFileName.endsWith(".csv")) {
            originalFileName += ".csv";
            file = new File(file.getParentFile(), originalFileName);
        }

        int attempts = 0;
        while (attempts < 3) {
            try {
                if (file.exists()) {
                    file.delete();
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    println("..Salvando arquivo: " + file.getAbsolutePath());
                    writer.write(sb.toString());
                }
                break; // Successfully saved the file, exit the loop
            } catch (IOException e) {
                attempts++;
                println("Nao foi possivel salvar o consolidador (" + attempts + "ª tentativa): " + e.getMessage());
                // Modify the file name for the next attempt
                String fileNameWithoutExtension = originalFileName.replace(".csv", "");
                originalFileName = fileNameWithoutExtension + "_" + attempts + ".csv";
                file = new File(file.getParentFile(), originalFileName);
            }
        }
    }
}
