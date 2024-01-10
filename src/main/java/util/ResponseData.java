package util;

public class ResponseData {
    public int statusCode;
    public String body;
    public String cookieString;

    public ResponseData(int statusCode, String body, String cookieString) {
        this.statusCode = statusCode;
        this.body = body;
        this.cookieString = cookieString;
    }
}
