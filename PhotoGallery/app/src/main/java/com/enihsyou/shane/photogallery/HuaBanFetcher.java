package com.enihsyou.shane.photogallery;

import android.net.Uri;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HuaBanFetcher {
    private static final String TAG = "HuaBanFetcher";

    public List<GalleryItem> fetchItems() {
        List<GalleryItem> items = new ArrayList<>();
        try {
            String url = Uri.parse("http://huaban.com/all/")
                    .buildUpon()
                    // .appendQueryParameter("since", "940074419")
                    .appendQueryParameter("limit", "100")
                    .build()
                    .toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "doInBackground: Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: Failed to fetch items: ", e);
        } catch (JSONException e) {
            Log.e(TAG, "fetchItems: Failed to parse JSON", e);
        }
        return items;
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    private void parseItems(List<GalleryItem> items, JSONObject jsonBody) throws JSONException {
        JSONArray photoJsonArray = jsonBody.getJSONArray("pins");
        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
            GalleryItem item = new GalleryItem();
            String photoId = photoJsonObject.getString("pin_id");
            String caption = photoJsonObject.getString("raw_text");
            String url = photoJsonObject.getJSONObject("file").getString("key");
            item.setId(photoId);
            item.setCaption(caption);
            item.setUrl(url);
            items.add(item);
        }
    }
}
