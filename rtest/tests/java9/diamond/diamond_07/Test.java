//.result=COMPILE_FAIL
/**
 * Methods implementing an interface must have types corresponding to the target type 
 */

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

public class Test{
    private final CompletionHandler<Integer, ByteBuffer> packetHandler = new CompletionHandler<>() {
        @Override
        public void completed(Object result, Object buffer) {
        }
        @Override
        public void failed(Throwable t, Object buffer) {
        }
    };
}