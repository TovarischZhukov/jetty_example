package jetty.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

@SuppressWarnings({"Duplicates", "NotNullNullableValidation"})
public final class ServletsMy {

  public static void main(String[] args) throws Exception {
    final Server server = new DefaultServer().build(3466);

    ServletContextHandler context = new ServletContextHandler(
        ServletContextHandler.NO_SESSIONS
    );
    context.setContextPath("/*");
    context.addServlet(
        new ServletHolder("default",
            new MyServlet(new ContentGenerator())
        ),
        "/*"
    );
    server.setHandler(context);

    server.start();
  }
}
