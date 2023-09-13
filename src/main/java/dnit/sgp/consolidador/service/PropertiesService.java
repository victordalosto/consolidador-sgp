package dnit.sgp.consolidador.service;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class PropertiesService {

    private final String caminhoConfiguracoes;


    public String getParam(String key) throws IOException {
        try (var fis = new FileInputStream(caminhoConfiguracoes)) {
            var properties = new Properties();
            properties.load(fis);
            String value = properties.getProperty(key);
            return value;
        }
    }

}