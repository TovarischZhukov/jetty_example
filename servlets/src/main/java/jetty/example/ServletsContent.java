package jetty.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

@SuppressWarnings({"Duplicates", "NotNullNullableValidation"})
public final class ServletsContent {

  public static void main(String[] args) throws Exception {
    final Server server = new DefaultServer().build();

    ServletContextHandler context = new ServletContextHandler(
        ServletContextHandler.NO_SESSIONS
    );
    context.setContextPath("/");
    context.addServlet(
        new ServletHolder("default",
            new ContentServlet(new ContentGenerator())
        ),
        "/"
    );
    server.setHandler(context);

    server.start();
  }
}
