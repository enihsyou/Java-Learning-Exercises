import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Parser {
    public static void main(String[] args) throws IOException {
        Path file = Paths.get("src/network.json");
        Gson gson  = new Gson();
        NetworkSearchResult result = gson.fromJson(Files.newBufferedReader(file), NetworkSearchResult.class);
        System.out.println(result);
    }
}
