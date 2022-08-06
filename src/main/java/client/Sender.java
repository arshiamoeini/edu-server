package client;

import shared.RequestType;

public interface Sender {
    void send(RequestType type, Object... args);
}
