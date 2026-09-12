package redis;

import java.util.List;

public class CommandHandler {

    public String handle(List<String> command){
        String commandName  = command.get(0).toUpperCase();
        if(commandName.equals("PING")){
            return "PONG";
        }
        return "ERR unknown command";
    }

}
