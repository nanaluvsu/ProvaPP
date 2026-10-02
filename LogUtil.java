import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/** Configuração compartilhada de entrada e logs da aplicação. */
public final class LogUtil {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static boolean configurado = false;

    private LogUtil() { }

    public static Scanner scanner() {
        return SCANNER;
    }

    public static synchronized Logger logger(Class<?> classe) {
        Logger logger = Logger.getLogger(classe.getName());
        if (!configurado) {
            try {
                Path diretorio = Path.of("logs");
                Files.createDirectories(diretorio);
                FileHandler arquivo = new FileHandler("logs/aplicacao.log", true);
                arquivo.setEncoding("UTF-8");
                arquivo.setFormatter(new SimpleFormatter());
                Logger raiz = Logger.getLogger("");
                for (java.util.logging.Handler handler : raiz.getHandlers()) {
                    raiz.removeHandler(handler);
                }
                raiz.addHandler(arquivo);
                raiz.setLevel(Level.ALL);
                configurado = true;
            } catch (IOException | SecurityException e) {
                System.err.println("Não foi possível configurar o arquivo de log: " + e.getMessage());
                logger.log(Level.SEVERE, "Falha ao configurar o arquivo de log.", e);
                configurado = true;
            }
        }
        logger.setLevel(Level.ALL);
        return logger;
    }
}
