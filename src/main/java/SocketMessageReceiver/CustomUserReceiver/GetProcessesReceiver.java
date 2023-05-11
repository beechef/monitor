package SocketMessageReceiver.CustomUserReceiver;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Sender;
import SocketMessageReceiver.DataType.GetProcesses.GetProcessesServerSide;
import SocketMessageReceiver.DataType.GetProcessesResult.GetProcessesResultUserSide;
import SocketMessageReceiver.DataType.ProcessInfo;
import SocketMessageReceiver.SocketMessageReceiver;
import SocketMessageSender.CustomUserSender.GetProcessesResultSender;
import Utilities.Utilities;
import oshi.SystemInfo;

public class GetProcessesReceiver extends SocketMessageReceiver<GetProcessesServerSide> {
    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_PROCESSES;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<GetProcessesServerSide> socketMsg) {
        var adminId = socketMsg.msg.adminId;
        var adminUuid = socketMsg.msg.adminUuid;
        var uuid = Utilities.getUUID();

        var operatingSystem = new SystemInfo().getOperatingSystem();
        var processes = operatingSystem.getProcesses();
        var processInfos = new ProcessInfo[processes.size()];
        var size = 0f;

        for (int i = 0; i < processes.size(); i++) {
            var process = processes.get(i);

            processInfos[i] = new ProcessInfo(process.getProcessID(), process.getName(), process.getPath());

            size += processInfos[i].getSize();

        }

        var sender = new GetProcessesResultSender(server);
        var buffer = server.getBuffer();
        var index = 0;

        while (size > 0) {
            var chunkSize = 0;
            var startIndex = index;

            while (index < processInfos.length) {
                var processInfo = processInfos[index];
                var processInfoSize = processInfo.getSize();

                if (chunkSize + processInfoSize > buffer) break;
                chunkSize += processInfoSize;
                index++;
            }

            size -= chunkSize;

            var processInfoChunk = new ProcessInfo[index - startIndex];
            System.arraycopy(processInfos, startIndex, processInfoChunk, 0, index - startIndex);

            sender.send(socketMsg.sender, new GetProcessesResultUserSide(adminId, adminUuid, uuid, processInfoChunk));
        }
    }
}
