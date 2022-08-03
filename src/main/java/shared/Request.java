package shared;

import java.util.ArrayList;
import java.util.Arrays;

public class Request {
    private RequestType type;
    private ArrayList<String> data;

    public Request(RequestType type) {
        this.type = type;
    }

    public Request(RequestType type, ArrayList<String> data) {
        this.type = type;
        this.data = data;
    }
}
