package util;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
        HttpClient client = HttpClient.newHttpClient();

        // リクエスト送信
        System.out.print("doGET: " + baseUrl + path);
        HttpResponse<String> response = client.send(HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .header("Cookie", backendCookieString)
                .build(), HttpResponse.BodyHandlers.ofString());
        String cookieString = String.valueOf(response.headers().firstValue("Set-Cookie"));
        System.out.println(" -> " + response.statusCode() + " " + response.body() + " " + cookieString);
        return new ResponseData(response.statusCode(), response.body(), cookieString);
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
        HttpClient client = HttpClient.newHttpClient();

        // リクエスト送信
        System.out.print("doPOST: " + baseUrl + path + " " + body);
        HttpResponse<String> response = client.send(HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build(), HttpResponse.BodyHandlers.ofString());
        String cookieString = String.valueOf(response.headers().firstValue("Set-Cookie"));
        System.out.println(" -> " + response.statusCode() + " " + response.body() + " " + cookieString);
        return new ResponseData(response.statusCode(), response.body(), cookieString);
    }

}
