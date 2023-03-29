package Client;

import Server.EventDispatcher.EventHead.EventHeadByte;
import Server.ServerInstance.Message;
import SocketMessageReceiver.DataType.RegisterRequest;
import Utilities.Utilities;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        InetSocketAddress serverAddr = new InetSocketAddress("localhost", 4445);
        client.connect(serverAddr, null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                System.out.println("Connected to server");
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                client.read(buffer, null, new CompletionHandler<Integer, Void>() {
                    @Override
                    public void completed(Integer numBytes, Void attachment) {
                        if (numBytes == -1) {
                            return;
                        }

                        byte[] data = new byte[numBytes];
                        System.arraycopy(buffer.array(), 0, data, 0, numBytes);

                        System.out.println(new String(data));

                        client.read(buffer, null, this);
                    }

                    @Override
                    public void failed(Throwable exc, Void attachment) {

                    }
                });
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.err.println("Failed to connect to server: " + exc.getMessage());
            }
        });

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter message to send to server: ");
            String message = scanner.nextLine();
            buffer.clear();

            Message msg = new Message(EventHeadByte.CONNECTION, EventHeadByte.Connection.REGISTER, new RegisterRequest(message, message, message));

            byte[] yourBytes = Utilities.toBytes(msg);

            buffer.put(yourBytes);
            buffer.flip();

            client.write(buffer);
        }
    }
}
