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
    private static final String FETCH_METHOD = "all";
    private static final String SEARCH_METHOD = "search";
    private static final Uri ENDPOINT = Uri.parse("http://huaban.com/");

    public List<GalleryItem> downloadGalleryItems(String url) {
        List<GalleryItem> items = new ArrayList<>();
        try {

            String jsonString = getUrlString(url);
            Log.i(TAG, "doInBackground: Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: Failed to fetch items: ", e);
        } catch (JSONException e) {
            Log.e(TAG, "downloadGalleryItems: Failed to parse JSON", e);
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

    private String buildUrl(String method, String query) {
        Uri.Builder builder = ENDPOINT.buildUpon();
        if (method.equals(SEARCH_METHOD)){
            builder.appendPath(SEARCH_METHOD).appendQueryParameter("q", query);
        } else if (method.equals(FETCH_METHOD)) {
            builder.appendPath(FETCH_METHOD);
        }
        return builder.build().toString();
    }
    public List<GalleryItem> fetchRecentPhotos() {
        String url = buildUrl(FETCH_METHOD, null);
        return downloadGalleryItems(url);
    }
    public List<GalleryItem> searchPhotos(String query){
        String url = buildUrl(SEARCH_METHOD, query);
        return downloadGalleryItems(url);
    }
}
