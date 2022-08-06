package shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Response {
    private Object[] data;
    private Integer readPoint;
    public Response(Object[] data) {
        this.data = data;
    }

    public ArrayList<String> getData() {
        return (ArrayList<String>) Arrays.stream(data).map(s -> (String) s).collect(Collectors.toList());
    }
}
