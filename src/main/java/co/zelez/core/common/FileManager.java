package co.zelez.core.common;

import co.zelez.core.shopping.repository.ShopRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.AllArgsConstructor;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;

@AllArgsConstructor
public class FileManager {
    private final File json;

    public FileManager(String filePath) {
        json = new File(filePath);
    }

    public void setShopData(HashMap<Long, ShopRepository> shopData) {
        try {
            Gson gson = new Gson();
            String jsonString = gson.toJson(shopData);
            FileWriter writer = new FileWriter(json);
            writer.write(jsonString);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<Long, ShopRepository> getShopData() {
        if (json.exists()) {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<HashMap<Long, ShopRepository>>() {
                }.getType();
                JsonReader jsonReader = new JsonReader(new FileReader(json));
                HashMap<Long, ShopRepository> jsonMap = gson.fromJson(jsonReader, type);

                return Objects.requireNonNullElseGet(jsonMap, HashMap::new);
            } catch (Exception ignore) {
            }
        }
        return new HashMap<>();
    }
}
