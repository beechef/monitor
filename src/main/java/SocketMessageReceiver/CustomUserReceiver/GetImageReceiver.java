package SocketMessageReceiver.CustomUserReceiver;

import Client.TCPClient;
import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.EventDispatcher.SocketMessageGeneric;
import Server.ServerInstance.Pooling.BufferPooling;
import Server.ServerInstance.Sender;
import Server.UserController;
import SocketMessageReceiver.DataType.GetImage.GetImageRequestServerSide;
import SocketMessageReceiver.DataType.GetImage.GetImageResultUserSide;
import SocketMessageReceiver.SocketMessageReceiver;
import Utilities.Utilities;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.UUID;

public class GetImageReceiver extends SocketMessageReceiver<GetImageRequestServerSide> {

    @Override
    public byte getHeadByte() {
        return EventHeadByte.USER_DATA;
    }

    @Override
    public byte getSubHeadByte() {
        return EventHeadByte.UserData.GET_IMAGE;
    }

    @Override
    protected void onExecute(Sender server, SocketMessageGeneric<GetImageRequestServerSide> socketMsg) {
        var adminId = socketMsg.msg.adminId;
        var adminUuid = socketMsg.msg.adminUuid;
        var uuid = Utilities.getUUID();

        var image = getImage();
        var byteBuffer = ByteBuffer.wrap(image);
        var chunkSize = 1000 * 1024;
        var pos = 0;

        var sender = new SocketMessageSender.CustomUserSender.GetImageResultSender(server);
        do {
            chunkSize = Math.min(chunkSize, image.length - pos);
            var chunk = new byte[chunkSize];

            byteBuffer.get(pos, chunk, 0, chunkSize);

            sender.send(socketMsg.sender, new GetImageResultUserSide(adminId, adminUuid, chunk, pos < image.length, uuid));

            pos += chunkSize;
        } while (pos < image.length);

        byteBuffer.clear();
        TCPClient.destroyBuffer(byteBuffer);
    }

    private byte[] getImage() {
        try {
            var robot = new Robot();
            var capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            var image = robot.createScreenCapture(capture);
//            () -> Utilities.ImageUtil
//            Utilities.ImageUtil

            var bos = new ByteArrayOutputStream();
            var writers = ImageIO.getImageWritersByFormatName("png");
            var writer = writers.next();
            var ios = ImageIO.createImageOutputStream(bos);

            writer.setOutput(ios);
            var param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.001f);

            writer.write(null, new javax.imageio.IIOImage(image, null, null), param);
            var compressedImg = Utilities.compressImage(bos.toByteArray(), 0.1f);
//            writer.setOutput(ios);

//            javax.imageio.ImageIO.write(image, "png", bos);
//            var data = bos.toByteArray();

            image.flush();
            bos.close();
            ios.close();

            return compressedImg;

        } catch (AWTException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
