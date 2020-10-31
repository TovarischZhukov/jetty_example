package jetty.example;


import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("NotNullNullableValidation")
public final class MyServlet extends HttpServlet {
  private final ContentGenerator contentGenerator;
  private ServletConfig servletConfig;

  @Inject
  public MyServlet(ContentGenerator contentGenerator) {
    this.contentGenerator = contentGenerator;
  }

  @Override
  public void init(ServletConfig config) {
    this.servletConfig = config;
  }

  @Override
  public ServletConfig getServletConfig() {
    return servletConfig;
  }

  @Override
  public void service(ServletRequest req, ServletResponse res) throws IOException {
    try (ServletOutputStream outputStream = res.getOutputStream()) {
      outputStream.write(contentGenerator.content().getBytes(StandardCharsets.UTF_8));
      outputStream.flush();
    }
  }

  @Override
  public String getServletInfo() {
    return "my Servlet";
  }

  @Override
  public void destroy() {
  }
}
