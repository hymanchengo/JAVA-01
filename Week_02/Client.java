package com.hymanting.geekjavatrain.socket;

import okhttp3.*;
import java.io.IOException;

/**
 * @Author hxchen
 * @Date 2021/1/20
 */
public class Client {
    public static void main(String[] args) {
        String url = "http://localhost:8801";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
