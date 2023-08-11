public class Event {
    private String name;
    private String date;
    private String chatID;

    public Event() {
    }

    public Event(String name, String date, String chatID) {
        this.name = name;
        this.date = date;
        this.chatID = chatID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }
}
