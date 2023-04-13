package SocketMessageSender.CustomAdminSender;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetHardwareInfoAdminSide;
import SocketMessageSender.SocketMessageSender;

public class GetHardwareInfoSender extends SocketMessageSender<GetHardwareInfoAdminSide> {

    public GetHardwareInfoSender(Sender server) {
        super(server);
    }

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_HARDWARE_INFO;
    }
}
