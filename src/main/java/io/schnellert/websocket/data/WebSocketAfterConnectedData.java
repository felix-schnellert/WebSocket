package io.schnellert.websocket.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;


@Getter @Setter
public final class WebSocketAfterConnectedData {

    private StompSession session;
    
    private StompHeaders headers;
}