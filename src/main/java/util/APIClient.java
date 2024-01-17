package util;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.*;

public class APIClient {
    //private static final String baseUrl = "https://b02.isp2.ukwhatn.com/";
    private static final String baseUrl = "http://localhost:58080/";

    public static ResponseData get(String path, String backendCookieString) throws IOException, InterruptedException {
        // pathの最初にスラッシュがあったら削除
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        // pathの最後にスラッシュがなければ追加
        if (!path.endsWith("/")) {
            path = path + "/";
        }

        // クライアント準備
        OkHttpClient client = new OkHttpClient();

        // リクエスト送信
        System.out.print("doGET: " + baseUrl + path);
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .addHeader("Cookie", backendCookieString)
                .build();
        Response response = client.newCall(request).execute();
        String cookieString = response.header("Set-Cookie");
        if (cookieString != null) {
            cookieString = cookieString.split(";")[0].strip();
        }

        Integer statusCode = response.code();
        ResponseBody body = response.body();

        String bodyString = "<NULL>";
        if (body != null) {
            bodyString = body.string();
        }

        System.out.println(" -> " + statusCode + " " + bodyString + " " + cookieString);
        return new ResponseData(statusCode, bodyString, cookieString);
    }

    public static ResponseData post(String path, String body, String backendCookieString) throws IOException, InterruptedException {
        // pathの最初にスラッシュがあったら削除
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        // pathの最後にスラッシュがなければ追加
        if (!path.endsWith("/")) {
            path = path + "/";
        }

        // クライアント準備
        OkHttpClient client = new OkHttpClient();

        // リクエスト送信
        System.out.print("doPOST: " + baseUrl + path + " " + body);
        RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .addHeader("Cookie", backendCookieString)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        String cookieString = response.header("Set-Cookie");
        if (cookieString != null) {
            cookieString = cookieString.split(";")[0].strip();
        }

        Integer statusCode = response.code();
        ResponseBody responseBody = response.body();

        String responseBodyString = "<NULL>";
        if (responseBody != null) {
            responseBodyString = responseBody.string();
        }

        System.out.println(" -> " + statusCode + " " + responseBodyString + " " + cookieString);
        return new ResponseData(statusCode, responseBodyString, cookieString);
    }

    public static ResponseData delete(String path, String backendCookieString) throws IOException, InterruptedException {
        // pathの最初にスラッシュがあったら削除
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        // pathの最後にスラッシュがなければ追加
        if (!path.endsWith("/")) {
            path = path + "/";
        }

        // クライアント準備
        OkHttpClient client = new OkHttpClient();

        // リクエスト送信
        System.out.print("doDELETE: " + baseUrl + path);
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .addHeader("Cookie", backendCookieString)
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        String cookieString = response.header("Set-Cookie");
        if (cookieString != null) {
            cookieString = cookieString.split(";")[0].strip();
        }

        Integer statusCode = response.code();
        ResponseBody body = response.body();

        String bodyString = "<NULL>";
        if (body != null) {
            bodyString = body.string();
        }

        System.out.println(" -> " + statusCode + " " + bodyString + " " + cookieString);
        return new ResponseData(statusCode, bodyString, cookieString);
    }
}
