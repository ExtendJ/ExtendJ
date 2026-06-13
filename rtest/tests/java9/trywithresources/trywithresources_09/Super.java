import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Super {
    protected final BufferedReader br1;

    Super() throws Exception{
        Reader inputString = new StringReader("Hello World");
        br1 = new BufferedReader(inputString);
    }
}
