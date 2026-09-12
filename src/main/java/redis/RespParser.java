package redis;

import java.io.IOException;
import java.io.InputStream;

public class RespParser {
    private final InputStream inputStream;

    public RespParser(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public String readLine() throws IOException {
        StringBuilder result = new StringBuilder();
        while(true){
            int current = inputStream.read();
            if(current == -1){
                throw new IOException("Connection Closed");
            }
            if(current == '\r'){
                int next = inputStream.read();
                if(next != '\n'){
                    throw new IOException("Invalid RESP line ending");
                }
                return result.toString();
            }
            result.append((char) current);
        }
    }

    private int readArrayLength() throws IOException{
        String line = readLine();
        if(!line.startsWith("*")){
            throw new IOException("Invalid RESP Parser");
        }
        return Integer.parseInt(line.substring(1));
    }

    public String readBulkString() throws IOException{
        String line = readLine();
        if(!line.startsWith("$")){
            throw new IOException("Invalid RESP bulk string");
        }
        int length = Integer.parseInt(line.substring(1));
        byte[] data = inputStream.readNBytes(length);
        if(data.length != length){
            throw new IOException("Connection closed while reading bulk string");
        }

        int carrierString = inputStream.read();
        int newLine = inputStream.read();
        if(carrierString != '\r' || newLine != '\n'){
            throw new IOException("Invalid RESP bulk string ending");
        }
        return new String(data);
    }

}


