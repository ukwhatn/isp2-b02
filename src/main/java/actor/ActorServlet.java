package actor;

import jakarta.servlet.ServletException;
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

@WebServlet({"/actor"})
public class ActorServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public ActorServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // フロントエンドcookie確認
        Cookie[] cookies = req.getCookies();

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

        try {
            if (req.getParameter("id") != null) {
                backendResponse = APIClient.get("api/actor/" + req.getParameter("id"), backendCookieString);
            } else {
                backendResponse = APIClient.get("api/actor", backendCookieString);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (backendResponse.cookieString != null) {
            resp.addCookie(new Cookie("backendCookie", backendResponse.cookieString));
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(backendResponse.statusCode);
        resp.getWriter().write(backendResponse.body);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // フロントエンドcookie確認
        Cookie[] cookies = req.getCookies();

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
        json.put("name", req.getParameter("name"));
        json.put("password", req.getParameter("password"));

        try {
            backendResponse = APIClient.post("api/actor", json.toString(), backendCookieString);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (backendResponse.cookieString != null) {
            resp.addCookie(new Cookie("backendCookie", backendResponse.cookieString));
        }

        if (backendResponse.statusCode == 200) {
            resp.setStatus(302);
            resp.setHeader("Location", "/isp2/");
            return;
        }

        resp.setStatus(302);
        resp.setHeader("Location", "/isp2/signup?error=1");
    }
}
