package shared;

import javax.persistence.criteria.CriteriaBuilder;
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

    public RequestType getType() {
        return type;
    }

    public void addData(String element) {
        data.add(element);
    }

    public String getLastData() {
        String result = data.get(data.size() - 1);
        data.remove(data.size() - 1);
        return result;
    }

    public ArrayList<String> getData() {
        return data;
    }
}
