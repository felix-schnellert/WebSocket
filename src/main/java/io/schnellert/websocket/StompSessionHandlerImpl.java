package io.schnellert.websocket;

import io.schnellert.websocket.data.WebSocketAfterConnectedData;
import io.schnellert.websocket.data.WebSocketReceivedData;
import io.schnellert.websocket.data.exception.WebSocketException;
import java.lang.reflect.Type;
import java.util.Objects;
import lombok.NonNull;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;


public final class StompSessionHandlerImpl implements StompSessionHandler {

    private final WebSocketHandlerImpl webSocketHandlerImpl;
    
    //<editor-fold defaultstate="collapsed" desc="StompSessionHandlerImpl">
    public StompSessionHandlerImpl(@NonNull final WebSocketHandlerImpl webSocketHandlerImpl) {
        
        Objects.requireNonNull(webSocketHandlerImpl, "WebSocketHandler cannot be null");
        
        this.webSocketHandlerImpl = webSocketHandlerImpl;
    }
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="afterConnected">
    @Override
    public void afterConnected(StompSession session, StompHeaders headers) {
        
        if (this.webSocketHandlerImpl.getAfterConnectedDataCallback() == null)
            return;
        final WebSocketAfterConnectedData webSocketAfterConnectedData = new WebSocketAfterConnectedData();
        
        if (session != null)
            webSocketAfterConnectedData.setSession(session);
        
        if (headers != null)
            webSocketAfterConnectedData.setHeaders(headers);
        
        this.webSocketHandlerImpl.getAfterConnectedDataCallback().accept(webSocketAfterConnectedData);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="handleException">
    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] bytes, Throwable throwable) {
        
        if (this.webSocketHandlerImpl.getExceptionCallback() == null)
            return;
        final WebSocketException webSocketException = new WebSocketException("EXCEPTION");
        
        if (session != null)
            webSocketException.setSession(session);
        
        if (command != null)
            webSocketException.setCommand(command);
        
        if (headers != null)
            webSocketException.setHeaders(headers);
        
        if (bytes.length > 0)
            webSocketException.setBytes(bytes);
        
        if (throwable != null)
            webSocketException.setThrowable(throwable);
        
        this.webSocketHandlerImpl.getExceptionCallback().accept(webSocketException);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="handleTransportError">
    @Override
    public void handleTransportError(StompSession session, Throwable throwable) {
        
        if (this.webSocketHandlerImpl.getExceptionCallback() == null)
            return;
        final WebSocketException webSocketException = new WebSocketException("TRANSPORT_ERROR");
        
        if (session != null)
            webSocketException.setSession(session);
        
        if (throwable != null)
            webSocketException.setThrowable(throwable);
        
        this.webSocketHandlerImpl.getExceptionCallback().accept(webSocketException);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getPayloadType">
    @Override
    public Type getPayloadType(StompHeaders headers) {
        return new Type(){};
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="handleFrame">
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        
        if (this.webSocketHandlerImpl.getReceivedDataCallback() == null)
            return;
        final WebSocketReceivedData webSocketReceivedData = new WebSocketReceivedData();
        
        if (headers != null)
            webSocketReceivedData.setHeaders(headers);
        
        if (payload != null)
            webSocketReceivedData.setPayload(payload);
        
        this.webSocketHandlerImpl.getReceivedDataCallback().accept(webSocketReceivedData);
    }
    //</editor-fold>
}