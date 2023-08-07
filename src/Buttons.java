import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Buttons {
    List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

    public void create(SendMessage message) {
        InlineKeyboardButton commands = new InlineKeyboardButton();
        InlineKeyboardButton set = new InlineKeyboardButton();
        InlineKeyboardButton add = new InlineKeyboardButton();
        InlineKeyboardButton view = new InlineKeyboardButton();
        InlineKeyboardButton start = new InlineKeyboardButton();

        setButtons(start, commands, set, add, view, message);
    }


    private void setButtons(InlineKeyboardButton start, InlineKeyboardButton commands, InlineKeyboardButton set, InlineKeyboardButton add, InlineKeyboardButton view, SendMessage message) {
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();

        start.setText("Start");
        start.setCallbackData("/info");
        row1.add(start);

        commands.setText("Command List");
        commands.setCallbackData("/commands");
        row2.add(commands);

        add.setText("Add events");
        add.setCallbackData("/add");
        row2.add(add);

        set.setText("Edit an event");
        set.setCallbackData("/edit");
        row3.add(set);

        view.setText("View List");
        view.setCallbackData("/view");
        row3.add(view);

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        display(message);
    }


    private void display(SendMessage message) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        Respond respond = new Respond();
        message.setText("Please, select the command from the list bellow: ");
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);

        respond.sendApi(message);
    }

    public void createChoice(SendMessage message){
        InlineKeyboardButton ID = new InlineKeyboardButton();
        InlineKeyboardButton evenType = new InlineKeyboardButton();
        InlineKeyboardButton date = new InlineKeyboardButton();
        InlineKeyboardButton category = new InlineKeyboardButton();

        keyboard.clear();

        ID.setText("Special number");
        ID.setCallbackData("ID");

        evenType.setText("Event Type");
        evenType.setCallbackData("evenType");

        date.setText("Date when you will be notified");
        date.setCallbackData("time");

        category.setText("Category");
        category.setCallbackData("category");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(date);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(evenType);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(ID);
        row3.add(category);

        keyboard.add(row2);
        keyboard.add(row1);
        keyboard.add(row3);

        Respond respond = new Respond();

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);

        respond.sendApi(message);
    }
}
