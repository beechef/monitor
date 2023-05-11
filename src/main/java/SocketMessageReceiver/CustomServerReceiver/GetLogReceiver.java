package SocketMessageReceiver.CustomServerReceiver;

import Key.JWTKey;
import Server.Database.CombineCondition;
import Server.Database.Condition;
import Server.Database.DatabaseConnector;
import Server.Database.Operator;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.GetLogRequest;
import SocketMessageReceiver.DataType.GetLogResult;
import SocketMessageReceiver.DataType.KeyLogRequest;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomServerSender.GetLogResultSender;
import io.jsonwebtoken.Jwts;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetLogReceiver extends SocketMessageReceiver<GetLogRequest> {
    private static final String USER_LOG_TABLE = "user_log";
    private static final String ID = "id_user";
    private static final String LOG = "log";
    private static final String DATE = "date";

    private static final int LIMIT_LOG_SIZE = 1024; //Chars


    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_LOG;
    }


    private String getLog(String uuid, Date from, Date to) {
        var log = new StringBuilder();
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        var conditions = new Condition[]{
                new Condition(ID, Operator.Equal, uuid, CombineCondition.AND),
                new Condition(DATE, Operator.GreaterEqual, dateFormat.format(from), CombineCondition.AND),
                new Condition(DATE, Operator.LesserEqual, dateFormat.format(to), CombineCondition.NONE)};
        try {
            var results = DatabaseConnector.select(USER_LOG_TABLE, new String[]{LOG}, conditions);

            while (!results.isClosed() && results.next()) log.append(results.getString(LOG));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return log.toString();
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<GetLogRequest> socketMsg) {
        var jwt = Jwts.parserBuilder().setSigningKey(JWTKey.getKey()).build().parseClaimsJws(socketMsg.msg.getToken());
        var adminId = jwt.getBody().get(LoginReceiver.ID_FIELD, Integer.class);
        var adminUuid = jwt.getBody().get(LoginReceiver.UUID_FIELD, String.class);

        var admin = UserController.getAdmin(adminId, adminUuid);
        if (admin == null) return;

        var sender = new GetLogResultSender(server);
        var log = getLog(socketMsg.msg.userUuid, socketMsg.msg.from, socketMsg.msg.to);

        var logSize = log.length();
        var splitSize = Math.round((logSize * 1.0f / LIMIT_LOG_SIZE) + .5f);

        for (var i = 0; i < splitSize; i++) {
            var start = i * LIMIT_LOG_SIZE;
            var end = Math.min(start + LIMIT_LOG_SIZE, logSize);

            var chunk = log.substring(start, end);
            sender.send(admin.tcpSocket, new GetLogResult(chunk, i == splitSize - 1));
        }
    }
}
