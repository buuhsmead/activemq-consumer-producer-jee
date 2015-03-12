package local;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.ejb3.annotation.ResourceAdapter;

@MessageDriven(activationConfig = {
                                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                                @ActivationConfigProperty(propertyName = "destination", propertyValue = "HELLOWORLDMDBQueue") })
@ResourceAdapter("activemq-rar.rar")
public class MessageReceiver implements MessageListener {

    @Resource
    private MessageDrivenContext mdc;

    @Override
    public void onMessage(final Message message) {
        try {
            System.out.println("Received message: " + ((TextMessage) message).getText());
        } catch (final JMSException e) {
            System.out.println("Wrong message type:" + message);
            this.mdc.setRollbackOnly();
            e.printStackTrace();
        }
    }
}
