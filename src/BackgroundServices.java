import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class BackgroundServices {
    public void Timer() {
        Scheduler scheduler;

        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();

            JobDetail jobDetail = JobBuilder.newJob(CheckEventsJob.class).withIdentity("Check event job").build();

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("checkTrigger").withSchedule(SimpleScheduleBuilder.repeatHourlyForever(6)).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            System.out.println(e.getMessage());
        }
    }
}
