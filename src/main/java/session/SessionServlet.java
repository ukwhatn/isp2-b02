package session;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import util.APIClient;
import util.ResponseData;

import java.io.IOException;
import java.io.Serial;

@WebServlet({"/session"})
public class SessionServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public SessionServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        json.put("name", request.getParameter("name"));
        json.put("password", request.getParameter("password"));

        try {
            backendResponse = APIClient.post("api/session", json.toString(), backendCookieString);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (backendResponse.cookieString != null) {
            response.addCookie(new Cookie("backendCookie", backendResponse.cookieString));
        }

        if(backendResponse.statusCode == 401) {
            response.setStatus(302);
            response.setHeader("Location", "/isp2/signin?error=1");
            return;
        }

        if(backendResponse.statusCode == 200) {
            response.setStatus(302);
            response.setHeader("Location", "/isp2/");
            return;
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        try {
            backendResponse = APIClient.delete("api/session", backendCookieString);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // cookie破棄
        Cookie cookie = new Cookie("backendCookie", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(backendResponse.statusCode);
        response.getWriter().write(backendResponse.body);
    }
}
