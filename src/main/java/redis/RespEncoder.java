package redis;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class RespEncoder {
    private final OutputStream outputStream;

    public RespEncoder(OutputStream outputStream){
        this.outputStream = outputStream;
    }

    public void writeSimpleString(String value) throws IOException {
        String response = "+" + value + "\r\n";
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
