package template;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serial;

@WebServlet({"/template"})
public class TemplateServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public TemplateServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName());
            }
        }

        response.addCookie(new Cookie("test", "test"));

        JSONObject json = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        json.put("message", "Hello World!");
        response.getWriter().write(json.toString());
    }
}
