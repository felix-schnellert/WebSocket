package io.schnellert.websocket.test;

import io.schnellert.websocket.IWebSocketHandler;
import io.schnellert.websocket.factory.IWebSocketHandlerFactory;
import io.schnellert.websocket.factory.impl.WebSocketHandlerFactoryImpl;


public final class Main {

    public static void main(String[] args) {
        final IWebSocketHandlerFactory webSocketHandlerFactory = 
                new WebSocketHandlerFactoryImpl();
        
        final IWebSocketHandler webSocketHandler = 
                webSocketHandlerFactory.newHandler("ws://yourUrl.de");
   
        webSocketHandler.send("a", 0); // string = queue like mqtt broker ??
        
        webSocketHandler.onReceived((data) -> {
            
        });
        
        webSocketHandler.onException((exception) -> {
            
        });
    }
    

}