package messageservices;

import beans.Product;
import beans.User;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Topic;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnectionFactory;
/**
 *
 * @author Vidar
 */
@Stateless
public class EmailProducer {

    @Resource(lookup = "jms/emailConnectionFactory")
    private TopicConnectionFactory connectionFactory;
    @Resource(lookup = "jms/emailTopic")
    private Topic topic;

    public void sendEmail(User winner, Product product) {
        MessageProducer messageProducer;
        TextMessage textMessage;
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(topic);
            textMessage = session.createTextMessage();

            textMessage.setText("---- START EMAIL to customer " + winner.getUsername() + " ----\n"
                    + "\n"
                    + "Dear " + winner.getUsername() + ",\n"
                    + "\n"
                    + "Congratulations! You have won in bidding for product " + product.getName() + ".\n"
                    + "\n"
                    + "You can access the product using the following link:\n"
                    + "\n"
                    + "URL=" + product.getName() + "\n"
                    + "\n"
                    + "---- END EMAIL to customer " + winner.getUsername() + " ----");
            System.out.println(textMessage.getText());
            messageProducer.send(textMessage);

            messageProducer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
