package plantenApp.java.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

//server van VIVES kan hieronder rechtstreeks ingesteld worden
//server zelf moet misschien wel nog ingesteld worden via bv. XAMPP - Mercury (wij hebben dit getest met Gmail en dit werkte):
//https://levinwayne.wordpress.com/2012/01/05/xampp-for-windows-configure-mercury-mail-transport-system-for-external-mail/
public class JavaMailUtil {
    public static void sendMail(String recipient, String subject, String body) throws Exception {
        Properties properties = new Properties();
        //Settings van de server van VIVES kunnen hieronder aangepast worden (host & account + password)
        //Wij werken nu rechstreeks met gmail om te testen
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        String myAccount = "plantenvives@gmail.com";
        String password = "planten123";

        //de settings hieronder worden hier dan overbodig (= instellingen server)
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}