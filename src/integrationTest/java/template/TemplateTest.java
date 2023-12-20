package template;

import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;
import org.htmlunit.WebRequest;
//import org.htmlunit.html.HtmlButton;
//import org.htmlunit.html.HtmlInput;
import org.htmlunit.Page;
import org.htmlunit.UnexpectedPage;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TemplateTest {

    @Test
    public void testTemplateResponse() throws Exception {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        String url = this.getBaseUrl() + "/template";
        WebRequest request = new WebRequest((new URI(url)).toURL());
        Page rawResponse = webClient.getPage(request);

        assertTrue(rawResponse instanceof UnexpectedPage);

        JSONObject json = new JSONObject();
        json.put("message", "Hello World!");
        assertEquals(json.toString(), rawResponse.getWebResponse().getContentAsString());
        assertEquals(200, rawResponse.getWebResponse().getStatusCode());

        webClient.close();
    }

    protected String getBaseUrl() {
        return "http://localhost:8080/isp2";
    }

}
