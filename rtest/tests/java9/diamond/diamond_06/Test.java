//.result=COMPILE_PASS
/**
 * Can use diamond access on interfaces with anonymous generic classes
 */
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

public class Test{
    private final CompletionHandler<Integer, ByteBuffer> packetHandler = new CompletionHandler<>() {
        @Override
        public void completed(Integer result, ByteBuffer buffer) {
        }
        @Override
        public void failed(Throwable t, ByteBuffer buffer) {
        }
    };
}