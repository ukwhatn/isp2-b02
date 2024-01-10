package test;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serial;

import util.*;

@WebServlet({"/test"})
public class TestServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public TestServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // フロントエンドcookie確認
        Cookie[] cookies = request.getCookies();

        // バックエンドcookieをフロントエンドcookieから取得
        String backendCookieString = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("backendCookie")) {
                    backendCookieString = cookie.getValue();
                }
            }
        }

        ResponseData backendResponse = null;

        JSONObject json = new JSONObject();
        json.put("name", "test123");
        json.put("password", "owowowow");

        try {
            backendResponse = APIClient.post("api/actor",  json.toString(), backendCookieString);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        response.addCookie(new Cookie("backendCookie", backendResponse.cookieString));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(backendResponse.statusCode);
        response.getWriter().write(backendResponse.body);
    }
}
