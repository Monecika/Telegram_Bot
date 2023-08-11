import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Respond extends TelegramLongPollingBot {
    private final Bot bot = new Bot();

    private final Data data = new Data();

    int index = 1;
    String lastCommand = null;

    @Override
    public String getBotUsername() {
        return bot.getUsername();
    }

    @Override
    public String getBotToken() {
        return bot.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            System.out.println(update.getCallbackQuery().getData());
        }
        menu(update);
    }

    public void menu(Update update) {
        if (lastCommand != null && lastCommand.equalsIgnoreCase("/add") && index <= 4) {
            String useResponse = update.getMessage().getText();
            index++;
            data.add(String.valueOf(update.getMessage().getChatId()), index, useResponse);
        } else if (lastCommand != null && lastCommand.equalsIgnoreCase("/edit") && index < 4) {
            String useResponse;
            if (index == 2) {
                useResponse = update.getCallbackQuery().getData();
                index++;
                data.edit(String.valueOf(update.getCallbackQuery().getMessage().getChatId()), index, useResponse);
            } else {
                useResponse = update.getMessage().getText();
                index++;
                data.edit(String.valueOf(update.getMessage().getChatId()), index, useResponse);
            }
        } else if (update.hasCallbackQuery() || update.hasMessage()) {
            if (index == 5 || index == 4) index = 1;

            if (update.hasCallbackQuery() && update.getCallbackQuery().getData().contains("/"))
                lastCommand = update.getCallbackQuery().getData();
            else if (update.hasMessage() && update.getMessage().getText().contains("/"))
                lastCommand = update.getMessage().getText();
            request(update);
        }
    }

    public void request(Update update) {
        String chatID;
        String data;
        String username;

        if (update.hasMessage()) {
            chatID = String.valueOf(update.getMessage().getChatId());
            username = update.getMessage().getFrom().getUserName();
            data = update.getMessage().getText();
            response(update, chatID, username, data);
        } else if (update.hasCallbackQuery()) {
            chatID = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            username = update.getCallbackQuery().getMessage().getFrom().getUserName();
            data = update.getCallbackQuery().getData();
            response(update, chatID, username, data);
        }
    }

    public void response(Update update, String chatID, String username, String data) {
        SendMessage message = new SendMessage();

        Commands commands = new Commands();
        Buttons buttons = new Buttons();

        message.setChatId(chatID);
        switch (data.toLowerCase(Locale.ROOT)) {
            case "/start", "/info" -> {
                message.setText(commands.start(username));
                sendApi(message);

                message.setText(commands.commands());
                sendApi(message);

                buttons.create(message);
            }
            case "/commands" -> {
                message.setText(commands.commands());
                sendApi(message);

                buttons.create(message);
            }
            case "/add" -> {
                commands.add(chatID);
            }
            case "/view" -> {
                commands.view(chatID);
            }
            case "/edit" -> {
                commands.edit(chatID);
            }
        }
    }

    public void sendApi(SendMessage message) {
        try {
            sendApiMethod(message);
        } catch (TelegramApiException e) {
            System.out.println("sth went wrong");
        }
    }
}
