public class Commands {
    private static final Data data = new Data();

    public String start(String username) {
        if (!username.equalsIgnoreCase(null))
            return "Hello, " + username + ". I'm a new added telegram bot which will be serve you to remind about important events or daily tasks you have to do.";
        return "Hello, dear user. I'm a new added telegram bot which will be serve you to remind about important events or daily tasks you have to do.";

    }

    public String commands() {
        return "By using our service, you receive an access for the following functionality: \n" +
                "1. commands -> Get the list of the commands you have access to \n" +
                "2. add -> Add a person in your contact list \n" +
                "3. view -> View the event list \n" +
                "4. edit -> Edit an event from your list";
    }
    public void add(String chatID){
        data.add(chatID,1,"");

    }
    public void view(String chatID){
        Database database = new Database();
        database.select(chatID);
    }
    public void edit(String chatID){
        view(chatID);
        data.edit(chatID,1, "");
    }
}
