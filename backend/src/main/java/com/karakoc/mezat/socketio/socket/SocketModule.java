package com.karakoc.mezat.socketio.socket;


import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.karakoc.mezat.socketio.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SocketModule
{

    private final SocketIOServer socketIOServer;


    public SocketModule(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        //socketIOServer.addEventListener(); buraya new offer event tetikleyecegiz.
        socketIOServer.addEventListener("send_message", Message.class, onMessageReceived());
    }

    private ConnectListener onConnected(){
        return client -> {
           log.info("Socket ID Connected: " + client.getSessionId().toString());
        };
    }
    private DisconnectListener onDisconnected(){
        return client -> {
            log.info("Socket ID Disconnected: " + client.getSessionId().toString());
        };
    }

    private DataListener<Message> onMessageReceived(){
        return  (senderClient,data,sender)->{
        log.info(data.getMessage().toString() + " tarafindan " + senderClient.getSessionId());
        senderClient.getNamespace().getBroadcastOperations().sendEvent("get_message",data);
        };

    }




}

