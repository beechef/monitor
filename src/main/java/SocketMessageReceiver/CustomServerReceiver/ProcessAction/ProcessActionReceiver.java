package SocketMessageReceiver.CustomServerReceiver.ProcessAction;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import SocketMessageReceiver.CustomServerReceiver.AdminUserReceiver;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionRequestAdminSide;
import SocketMessageReceiver.DataType.ProcessAction.ProcessActionRequestServerSide;

public class ProcessActionReceiver extends AdminUserReceiver<ProcessActionRequestAdminSide, ProcessActionRequestServerSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.PROCESS_ACTION;
    }


    @Override
    protected void setData(SocketMessageGeneric<ProcessActionRequestAdminSide> socketMsg, AdminUserReceiver<ProcessActionRequestAdminSide, ProcessActionRequestServerSide>.AdminUserInfo info) {

    }
}
