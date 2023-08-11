import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi bot = new TelegramBotsApi(DefaultBotSession.class);
            bot.registerBot(new Respond());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        ExecutorService backgroundExecutor = Executors.newCachedThreadPool();
        backgroundExecutor.execute(() -> {
            BackgroundServices back =  new BackgroundServices();
            back.Timer();
        });
    }
}