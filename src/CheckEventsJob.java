import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckEventsJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        List<String> chatID = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        Database database = new Database();

        chatID.addAll(database.getChatID());

        Thread backgroungThread = new Thread(() -> {
            List<Event> eventsToNotify = new ArrayList<>();

            String date = currentDate.toString();
            System.out.println(date);
            for(String ID : chatID) {
                eventsToNotify.addAll(database.getEventsToNotify(date, ID));
            }
            SendMessage message = new SendMessage();
            Respond respond = new Respond();

            for (Event event : eventsToNotify) {
                message.setChatId(event.getChatID());
                message.setText("You have set an event for today!! \n Event Type: " + event.getName() + "\nEvent Date: " + event.getDate());
                respond.sendApi(message);
            }
        });
        backgroungThread.start();
    }
}
