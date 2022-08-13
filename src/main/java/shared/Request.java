package shared;

import com.google.gson.Gson;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;

public class Request {
    private RequestType type;
    private ArrayList<Object> data;
    public Request(RequestType type) {
        this.type = type;
    }

    public Request(RequestType type, ArrayList<String> data) {
        this.type = type;
        this.data = new ArrayList<>();
        for (String str: data) {
            this.data.add(str);
        }
    }

    public Request(RequestType type, Gson gson, Object... objects) {
        this.type = type;
        data = new ArrayList<>();
        for (Object obj: objects) {
            data.add(gson.toJson(obj));
        }
    }

    public Request(RequestType type, String... args) {
        this(type, new ArrayList<>(Arrays.asList(args)));
    }
    public Request(Object... objects) {
        data = new ArrayList<>(Arrays.asList(objects));
    }


    public RequestType getType() {
        return type;
    }

    public void addData(String element) {
        data.add(element);
    }
    public void addData(Gson gson, Object obj) {
        data.add(gson.toJson(obj));
    }

    public String getLastData() {
        String result = (String) data.get(data.size() - 1);
        data.remove(data.size() - 1);
        return result;
    }

    public ArrayList<Object> getData() {
        return data;
    }

    public String getData(int index) {
        return (String) data.get(index);
    }

    public Object getLast() {
        Object result = data.get(data.size() - 1);
        data.remove(data.size() - 1);
        return result;
    }

    public boolean getBoolean(int i) {
        return Boolean.valueOf(getData(i));
    }
}
