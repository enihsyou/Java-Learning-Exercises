import com.google.gson.Gson;
import okhttp3.*;
import okio.BufferedSink;

import java.io.IOException;

public class Main {
    private class Gist{
        String url;
    }
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MediaType.parse("txt/plain");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Your name\n");
                for (int i = 0; i < 100; i++) {
                    sink.writeUtf8(String.format(" * %s = %s\n", i, Math.exp(i)));
                }
            }
        };
        Request request = new Request.Builder()
                .url("https://httpbin.org/get")
                // .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            Gist gist = gson.fromJson(response.body().charStream(), Gist.class);
            System.out.println(gist.url);
            // Headers responseHeaders = response.headers();
            // for (int i = 0; i < responseHeaders.size(); i++) {
            //     String name = responseHeaders.name(i);
            //     String value = responseHeaders.value(i);
            //     System.out.printf("%s : %s\n", name, value);
            // }
            // System.out.println();
            // System.out.println(response.body().string());
        }
    }
}

