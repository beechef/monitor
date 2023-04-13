package SocketMessageSender.CustomUserSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetHardwareInfoResultUserSide;
import SocketMessageSender.SocketMessageSender;

public class GetHardwareInfoResultSender extends SocketMessageSender<GetHardwareInfoResultUserSide> {

    public GetHardwareInfoResultSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_HARDWARE_INFO_RESULT;
    }

}
