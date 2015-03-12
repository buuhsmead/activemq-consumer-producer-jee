package local;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@Stateless
public class MessageSender {

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/queue/HELLOWORLDMDBQueue")
    private Queue queue;

    public void sendMessage(final String message) throws JMSException {
        System.out.println("Sending message: " + message);

        final Connection connection = this.connectionFactory.createConnection();
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final MessageProducer messageProducer = session.createProducer(this.queue);

        final TextMessage jsmTextmessage = session.createTextMessage();
        jsmTextmessage.setText(message);
        messageProducer.send(jsmTextmessage);
        session.close();
        connection.close();
    }
}
