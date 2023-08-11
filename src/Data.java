import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Data {
    private int ID;
    private String event;
    private String date;
    private String category;

    private String field;
    private String result;

    public void add(String chatID, int index, String response) {
        SendMessage message = new SendMessage();
        Respond respond = new Respond();
        Database database = new Database();

        message.setChatId(chatID);

        switch (index) {
            case 1 -> {
                message.setText("Special ID: ");
                respond.sendApi(message);
            }
            case 2 -> {
                ID = Integer.parseInt(response);
                message.setText("Event type: ");
                respond.sendApi(message);
            }
            case 3 -> {
                event = response;
                message.setText("Set the date when you will be notified (yyyy-dd-mm): ");
                respond.sendApi(message);
            }
            case 4 -> {
                date = response;
                message.setText("Set the category for the current event: ");
                respond.sendApi(message);
            }
            case 5 -> {
                category = response;
                database.insert(ID, event, date, category, chatID);
            }
        }
    }

    public void edit(String chatID, int index, String response) {
        Respond respond = new Respond();

        SendMessage message = new SendMessage();
        message.setChatId(chatID);

        Database database = new Database();

        switch (index) {
            case 1 -> {
                message.setText("Chose the special id of the event you want to edit");
                respond.sendApi(message);
            }
            case 2 -> {
                ID = Integer.parseInt(response);

                message.setText("Chose the field you want to edit");
                Buttons buttons = new Buttons();
                buttons.createChoice(message);
            }
            case 3 -> {
                field = response;
                message.setText("Enter the new data: ");
                respond.sendApi(message);
            }
            case 4 -> {
                result = response;

                database.edit(ID, chatID, field, result);
            }
        }

    }
}
