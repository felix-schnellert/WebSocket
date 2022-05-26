package io.schnellert.websocket;

import io.schnellert.websocket.data.WebSocketAfterConnectedData;
import io.schnellert.websocket.data.WebSocketReceivedData;
import io.schnellert.websocket.data.WebSocketException;
import java.util.function.Consumer;
import lombok.NonNull;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;


public interface IWebSocketHandler {

    void disconnect();

    void disconnect(@NonNull final StompHeaders headers);

    String getSessionId();

    boolean isConnected();


    StompSession.Receiptable acknowledge(@NonNull final StompHeaders headers, final boolean ack);

    StompSession.Receiptable acknowledge(@NonNull final String string, final boolean ack);

    StompSession.Receiptable send(@NonNull final StompHeaders headers, @NonNull final Object payload);

    StompSession.Receiptable send(@NonNull final String string, @NonNull final Object payload);

    StompSession.Subscription subscribe(@NonNull final StompHeaders headers, @NonNull final StompFrameHandler stompFrameHandler);

    StompSession.Subscription subscribe(@NonNull final String string, @NonNull final StompFrameHandler stompFrameHandler);


    void onAfterConnected(@NonNull final Consumer<WebSocketAfterConnectedData> callback);

    void onException(@NonNull final Consumer<WebSocketException> callback);

    void onReceived(@NonNull final Consumer<WebSocketReceivedData> callback);
}