package io.schnellert.websocket.factory;

import io.schnellert.websocket.IWebSocketHandler;
import java.net.URI;
import lombok.NonNull;
import org.springframework.lang.Nullable;


public interface IWebSocketHandlerFactory {
    
    @Nullable
    IWebSocketHandler newHandler(@NonNull final URI uri);
    
    @Nullable
    IWebSocketHandler newHandler(@NonNull final String endpoint);
}