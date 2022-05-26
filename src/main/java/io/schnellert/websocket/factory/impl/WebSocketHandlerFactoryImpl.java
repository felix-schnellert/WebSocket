package io.schnellert.websocket.factory.impl;

import io.schnellert.websocket.IWebSocketHandler;
import io.schnellert.websocket.impl.WebSocketHandlerImpl;
import io.schnellert.websocket.factory.IWebSocketHandlerFactory;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.NonNull;


public final class WebSocketHandlerFactoryImpl implements IWebSocketHandlerFactory {

    //<editor-fold defaultstate="collapsed" desc="newHandler">
    @Override
    public IWebSocketHandler newHandler(@NonNull final URI uri) {
        try {
            return new WebSocketHandlerImpl(uri);
        } catch (Exception ex) {
            Logger.getLogger(WebSocketHandlerFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="newHandler">
    @Override
    public IWebSocketHandler newHandler(@NonNull final String endpoint) {
        try {
            return new WebSocketHandlerImpl(endpoint);
        } catch (Exception ex) {
            Logger.getLogger(WebSocketHandlerFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    //</editor-fold>
}