package Client;

import Server.ServerInstance.Message;
import Utilities.Utilities;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        InetSocketAddress serverAddr = new InetSocketAddress("localhost", 4445);
        client.connect(serverAddr, null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                System.out.println("Connected to server");
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

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = null;
            try {
                Message msg = new Message((byte) 10, (byte) 10, message);

                byte[] yourBytes = Utilities.toBytes(msg);

                buffer.put(yourBytes);
                buffer.flip();

                client.write(buffer, null, new CompletionHandler<Integer, Void>() {
                    @Override
                    public void completed(Integer result, Void attachment) {
                        System.out.println("Message sent to server");

                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        client.read(buffer, null, new CompletionHandler<Integer, Void>() {

                            @Override
                            public void completed(Integer result, Void attachment) {
                                byte[] data = new byte[result];
                                System.arraycopy(buffer.array(), 0, data, 0, result);

                                try {
                                    Message msg = Utilities.castBytes(data);

                                    System.out.println((String) msg.data);
                                } catch (IOException | ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            @Override
                            public void failed(Throwable exc, Void attachment) {

                            }
                        });
                    }

                    @Override
                    public void failed(Throwable exc, Void attachment) {
                        System.err.println("Failed to send message to server: " + exc.getMessage());
                    }
                });
            } finally {
                try {
                    bos.close();
                } catch (IOException ex) {
                    // ignore close exception
                }
            }

        }
    }
}
