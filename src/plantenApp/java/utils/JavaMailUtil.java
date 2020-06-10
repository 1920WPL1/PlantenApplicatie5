package plantenApp.java.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

//https://mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/

public class JavaMailUtil {

    public static void sendMail(String recipient) throws Exception {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.telenet.be");
        properties.put("mail.smtp.auth", "true");


        String myAccount = "bartms3010@telenet.be";
        String password = "Bmvives28";

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
            message.setSubject("This is a test");
            message.setText("hey there, how are you?");
            Transport.send(message);

        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}