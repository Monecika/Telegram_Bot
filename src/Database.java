import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public Statement connection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "mona");
            System.out.println("Connection succeed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return statement;
    }

    public void insert(int ID, String evenType, String date, String category, String chatID) {
        Statement statement = connection();
        String sql = "INSERT INTO users (ID, chatID, evenType, time, category) VALUES ('" + ID + "', '" + chatID + "', '" + evenType + "', '" + date + "', '" + category + "')";
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Insertion succeed");
        send(chatID);
    }

    public void select(String chatID) {
        Statement statement = connection();
        String sql = "SELECT * FROM users WHERE chatID = '" + chatID + "';";
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            String result = null;
            while (resultSet.next()) {
                String ID = resultSet.getString("ID");
                String evenType = resultSet.getString("evenType");
                String date = resultSet.getString("time");
                String category = resultSet.getString("category");

                if (result != null) result = result + ID + ". " + evenType + ", " + date + ", " + category + "\n";
                else result = ID + ". " + evenType + ", " + date + ", " + category + "\n";
            }

            resultSet.close();
            statement.close();

            if (result != null) {
                sendResult(result, chatID);
            } else {
                SendMessage message = new SendMessage();
                message.setChatId(chatID);
                message.setText("No data found");

                Respond respond = new Respond();
                respond.sendApi(message);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Select succeed");
    }

    public void edit(int ID, String chatID, String field, String result) {
        Statement statement = connection();

        String sql = "UPDATE users SET " + field + " = '" + result + "' WHERE ID = " + ID + " AND chatID = '" + chatID + "';";
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        send(chatID);
    }

    public void send(String chatID) {
        SendMessage message = new SendMessage();

        message.setChatId(chatID);
        message.setText("Completed with success");

        Respond respond = new Respond();
        respond.sendApi(message);
    }

    public void sendResult(String result, String chatID) {
        SendMessage message = new SendMessage();
        message.setChatId(chatID);
        message.setText(result);

        Respond respond = new Respond();
        respond.sendApi(message);
    }

    public List<Event> getEventsToNotify(String date, String chatID) {
        List<Event> list = new ArrayList<>();
        Statement statement = connection();

        String sql = "SELECT evenType, time, chatID FROM users WHERE time = '" + date + "' and chatID = '" + chatID + "';";

        try {
            ResultSet set = statement.executeQuery(sql);
            while (set.next()) {
                list.add(new Event(set.getString("evenType"), set.getString("time"),set.getString("chatID")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<String> getChatID() {
        List<String> list = new ArrayList<>();
        Statement statement = connection();

        String sql = "SELECT DISTINCT chatID FROM users";
        try {
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                list.add(resultSet.getString("chatID"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
