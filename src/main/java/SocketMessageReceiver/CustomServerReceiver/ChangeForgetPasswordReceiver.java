package SocketMessageReceiver.CustomServerReceiver;

import Server.Database.*;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.MiddleWare.EncryptPassword;
import Server.RecoveryOTP;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.ChangeForgetPasswordRequest;
import SocketMessageReceiver.DataType.ChangeForgetPasswordResult;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.ChangeForgetPasswordResultSender;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChangeForgetPasswordReceiver extends SocketMessageReceiver<ChangeForgetPasswordRequest> {
    private static final String ADMIN_TABLE = "admin";
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";

    @Override
    public byte getHeadByte() {
        return EventHeadByte.ADMIN_CONNECTION;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.AdminConnection.CHANGE_FORGET_PASSWORD;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<ChangeForgetPasswordRequest> socketMsg) {
        var email = socketMsg.msg.email;
        var newPassword = socketMsg.msg.newPassword;
        var otp = socketMsg.msg.otp;

        var sender = new ChangeForgetPasswordResultSender(server);
        var isVerified = RecoveryOTP.instance.verifyOTP(email, otp);

        if (isVerified) {
            changePassword(email, newPassword);
            RecoveryOTP.instance.removeOTP(email);
            sender.send(socketMsg.sender, new ChangeForgetPasswordResult(ChangeForgetPasswordResult.Result.SUCCESS));
        } else {
            sender.send(socketMsg.sender, new ChangeForgetPasswordResult(ChangeForgetPasswordResult.Result.WRONG_OTP));
        }
    }

    private void changePassword(String email, String newPassword) {
        EncryptPassword encryptedPassword = null;
        try {
            encryptedPassword = new EncryptPassword(EncryptPassword.SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        newPassword = encryptedPassword.encrypt(newPassword);

        var values = new ArrayList<KeyPair<String, String>>();
        values.add(new KeyPair<>(PASSWORD_FIELD, newPassword));

        var conditions = new Condition[]{new Condition(EMAIL_FIELD, Operator.Equal, email, CombineCondition.NONE),};

        try {
            DatabaseConnector.update(ADMIN_TABLE, values, conditions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
