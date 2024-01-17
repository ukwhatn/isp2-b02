package note;

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

@WebServlet({"/notes"})
public class NotesServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public NotesServlet() {
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

        try {
            backendResponse = APIClient.get("api/note", backendCookieString);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        if (backendResponse.cookieString != null) {
            response.addCookie(new Cookie("backendCookie", backendResponse.cookieString));
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(backendResponse.statusCode);
        response.getWriter().write(backendResponse.body);
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
        json.put("title", req.getParameter("title"));
        json.put("content", req.getParameter("content"));

        try {
            backendResponse = APIClient.post("api/note", json.toString(), backendCookieString);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (backendResponse.cookieString != null) {
            resp.addCookie(new Cookie("backendCookie", backendResponse.cookieString));
        }

        resp.setStatus(302);
        resp.setHeader("Location", "/isp2");
    }
}
