package io.schnellert.websocket.data.exception;


public final class WebSocketSyntaxException extends Exception {

    public WebSocketSyntaxException() {

    }

    public WebSocketSyntaxException(final String message) {
        super(message);
    }
}