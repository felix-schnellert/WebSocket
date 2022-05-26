package io.schnellert.websocket.data.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;


@RequiredArgsConstructor
@Getter @Setter
public final class WebSocketException {
    
    private final String type;
    
    private StompSession session;
    
    private StompCommand command;
    
    private StompHeaders headers;
    
    private byte[] bytes;
    
    private Throwable throwable;
}