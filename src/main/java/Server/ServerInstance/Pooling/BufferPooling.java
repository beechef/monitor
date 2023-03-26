package Server.ServerInstance.Pooling;

import java.nio.ByteBuffer;

public class BufferPooling extends QueuePooling<ByteBuffer> {

    public BufferPooling(int buffer) {
        super(() -> ByteBuffer.allocate(buffer));
    }
}
