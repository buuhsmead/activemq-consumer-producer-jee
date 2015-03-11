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
	
	@Schedule(second="*/20", minute="*",hour="*", persistent=false)
	public void timeout(Timer timer) {
		System.out.println("Producing 100 messages every 20 seconds.");
		for (int i = 0; i < 100; i++){
			try {
				messageSender.sendMessage("message nr " + counter);
			} catch (JMSException e) {
				System.out.println("Message nr "+counter+" could not be sent.");
			}
			counter++;
		}
	}
}
