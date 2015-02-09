package local;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timer;
import javax.jms.JMSException;

@Singleton
public class TimerBean {
	int counter = 1;
	
	@EJB
	public MessageSender messageSender;
	
	@Schedule(second="*/10", minute="*",hour="*", persistent=false)
	public void timeout(Timer timer) {
		System.out.println("Producing 1000 messages every 10 seconds.");
		for (int i = 0; i < 1000; i++){
			try {
				messageSender.sendMessage("message nr " + counter);
			} catch (JMSException e) {
				System.out.println("Message nr "+counter+" could not be sent.");
			}
			counter++;
		}
	}
}
