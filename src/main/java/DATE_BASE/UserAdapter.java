package DATE_BASE;

import com.google.gson.*;
import database.UserTemp;

import java.lang.reflect.Type;

public class UserAdapter implements JsonDeserializer<UserTemp> {
    private static Gson gson = new GsonBuilder().create();

    @Override
    public UserTemp deserialize(JsonElement json, Type type,
                                JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        //     JsonElement typeValue = ;
        // JsonPrimitive prim = (JsonPrimitive) jsonObject.get("CLASSNAME");
        // String className = prim.getAsString();

        //  Class<?> klass = null;
        //       try {
        //    klass = Class.forName(className);
        //       } catch (ClassNotFoundException e) {
        //           e.printStackTrace();
        //         throw new JsonParseException(e.getMessage());
        //}
        //     return context.deserialize(jsonObject.get("INSTANCE"), klass);
        return null;//gson.fromJson(jsonObject, gson.fromJson(jsonObject, User.class).getType());
    }
/*
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        String className = user.getClass().getName();
        retValue.addProperty("CLASSNAME", className);
        JsonElement elem = context.serialize(user);
        retValue.add("INSTANCE", elem);
        return retValue;
    }

 */
}