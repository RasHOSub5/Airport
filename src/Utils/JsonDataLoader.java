package Utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonDataLoader {

    public static JSONArray loadJsonArray(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return new JSONArray(new JSONTokener(reader));
        }
    }

    // Método para parsear fechas desde JSON
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}