package io.schnellert.websocket.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.messaging.simp.stomp.StompHeaders;


@Getter @Setter
public final class WebSocketReceivedData {

    private StompHeaders headers;
    
    private Object payload;
}