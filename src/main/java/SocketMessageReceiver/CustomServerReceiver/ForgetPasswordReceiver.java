package SocketMessageReceiver.CustomServerReceiver;

import Server.Database.CombineCondition;
import Server.Database.Condition;
import Server.Database.DatabaseConnector;
import Server.Database.Operator;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.RecoveryOTP;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ForgetPasswordRequest;
import SocketMessageReceiver.DataType.ForgetPasswordResult;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.ForgetPasswordResultSender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class ForgetPasswordReceiver extends SocketMessageReceiver<ForgetPasswordRequest> {
    private static final String ADMIN_TABLE = "admin";
    private static final String EMAIL_FIELD = "email";

    private static final int ALIVE_TIME = 60 * 5; // 5 minutes

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.FORGET_PASSWORD;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<ForgetPasswordRequest> socketMsg) {
        var email = socketMsg.msg.email;
        var isExistEmail = isExistEmail(email);
        var sender = new ForgetPasswordResultSender(server);

        if (!isExistEmail) {
            sender.send(socketMsg.sender, new ForgetPasswordResult(email, ForgetPasswordResult.Result.EMAIL_NOT_FOUND));
            return;
        }

        var otpItem = RecoveryOTP.instance.generateOTP(email, getExpireTime());
        sender.send(socketMsg.sender, new ForgetPasswordResult(email, ForgetPasswordResult.Result.SUCCESS));

        sendEmail(email, otpItem.otp);
    }

    private void sendEmail(String email, String otp) {
        final String username = "phamtuc19@gmail.com";
        final String password = "wnndtmhxohalrksp";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject("Testing Gmail SSL");
            message.setText(otp);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Date getExpireTime() {
        var calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, ALIVE_TIME);


        return calendar.getTime();
    }

    private boolean isExistEmail(String email) {
        var existConditions = new Condition[]{new Condition(EMAIL_FIELD, Operator.Equal, email, CombineCondition.NONE)};
        ResultSet existUser = null;

        try {
            existUser = DatabaseConnector.select(ADMIN_TABLE, null, existConditions);
            return existUser.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
