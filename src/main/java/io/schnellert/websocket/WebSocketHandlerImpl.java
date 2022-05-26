package io.schnellert.websocket;

import io.schnellert.websocket.data.WebSocketAfterConnectedData;
import io.schnellert.websocket.data.WebSocketReceivedData;
import io.schnellert.websocket.data.WebSocketException;
import io.schnellert.websocket.data.exception.WebSocketSyntaxException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;


public final class WebSocketHandlerImpl implements IWebSocketHandler {
    
    private final URI uri;
    
    private WebSocketClient webSocketClient;
    private WebSocketStompClient webSocketStompClient;
    
    private StompSession stompSession;
    
    @Getter
    protected Consumer<WebSocketAfterConnectedData> afterConnectedDataCallback;
    
    @Getter
    protected Consumer<WebSocketException> exceptionCallback;
    
    @Getter
    protected Consumer<WebSocketReceivedData> receivedDataCallback;
    
    
    //<editor-fold defaultstate="collapsed" desc="WebSocketHandlerImpl">
    public WebSocketHandlerImpl(@NonNull final URI uri)
            throws WebSocketSyntaxException {
        
        Objects.requireNonNull(uri, "URI cannot be null");
        
        if (!(uri.getHost().startsWith("ws://"))) {
            throw new WebSocketSyntaxException("URI does not start with ws://");
        }
        
        this.uri = uri;
        
        init();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="WebSocketHandlerImpl">
    public WebSocketHandlerImpl(@NonNull final String endpoint)
            throws URISyntaxException, WebSocketSyntaxException {
        
        Objects.requireNonNull(endpoint, "Endpoint cannot be null");
        
        if (!(endpoint.startsWith("ws://"))) {
            throw new WebSocketSyntaxException("URI does not start with ws://");
        }
        
        this.uri = new URI(endpoint);
        
        init();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="init">
    private void init() {
        this.webSocketClient = new StandardWebSocketClient();
        
        this.webSocketStompClient = new WebSocketStompClient(this.webSocketClient);
        
        this.webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        
        final ListenableFuture<StompSession> listenableFuture = 
                this.webSocketStompClient.connect(uri.toString(), new StompSessionHandlerImpl(this));
        
        try {
            this.stompSession = Objects.requireNonNull(listenableFuture.get(), "Stomp Session cannot be null");
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(WebSocketHandlerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="disconnect">
    @Override
    public void disconnect() {
        this.stompSession.disconnect();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="disconnect">
    @Override
    public void disconnect(StompHeaders headers) {
        
        Objects.requireNonNull(headers, "Headers cannot be null");
        
        this.stompSession.disconnect(headers);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getSessionId">
    @Override
    public String getSessionId() {
        return this.stompSession.getSessionId();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="isConnected">
    @Override
    public boolean isConnected() {
        return this.stompSession.isConnected();
    }
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="acknowledge">
    @Override
    public StompSession.Receiptable acknowledge(@NonNull final StompHeaders headers, final boolean ack) {
        
        Objects.requireNonNull(headers, "Headers cannot be null");
        
        return this.stompSession.acknowledge(headers, ack);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="acknowledge">
    @Override
    public StompSession.Receiptable acknowledge(@NonNull final String string, final boolean ack) {
        
        Objects.requireNonNull(string, "String cannot be null");
        
        return this.stompSession.acknowledge(string, ack);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="send">
    @Override
    public StompSession.Receiptable send(@NonNull final StompHeaders headers, @NonNull final Object payload) {
        
        Objects.requireNonNull(headers, "Headers cannot be null");
        
        Objects.requireNonNull(payload, "Payload cannot be null");
        
        return this.stompSession.send(headers, payload);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="send">
    @Override
    public StompSession.Receiptable send(@NonNull final String string, @NonNull final Object payload) {
        
        Objects.requireNonNull(string, "String cannot be null");
        
        Objects.requireNonNull(payload, "Payload cannot be null");
        
        return this.stompSession.send(string, payload);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="subscribe">
    @Override
    public StompSession.Subscription subscribe(@NonNull final StompHeaders headers, @NonNull final StompFrameHandler stompFrameHandler) {
        
        Objects.requireNonNull(headers, "Headers cannot be null");
        
        Objects.requireNonNull(stompFrameHandler, "Framehandler cannot be null");
        
        return this.stompSession.subscribe(headers, stompFrameHandler);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="subscribe">
    @Override
    public StompSession.Subscription subscribe(String string, StompFrameHandler stompFrameHandler) {
        
        Objects.requireNonNull(string, "String cannot be null");
        
        Objects.requireNonNull(stompFrameHandler, "Framehandler cannot be null");
        
        return this.stompSession.subscribe(string, stompFrameHandler);
    }
    //</editor-fold>
    

    //<editor-fold defaultstate="collapsed" desc="onAfterConnected">
    @Override
    public void onAfterConnected(@NonNull final Consumer<WebSocketAfterConnectedData> callback) {
        
        Objects.requireNonNull(callback, "After Connected Callback cannot be null");
        
        this.afterConnectedDataCallback = callback;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="onException">
    @Override
    public void onException(@NonNull final Consumer<WebSocketException> callback) {
        
        Objects.requireNonNull(callback, "Exception Callback cannot be null");
        
        this.exceptionCallback = callback;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="onReceived">
    @Override
    public void onReceived(@NonNull final Consumer<WebSocketReceivedData> callback) {
        
        Objects.requireNonNull(callback, "Messaging Callback cannot be null");
        
        this.receivedDataCallback = callback;
    }
    //</editor-fold> 
}