package jetty.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;

@SuppressWarnings("NotNullNullableValidation")
public final class HandlersDefault {
  public static void main(String[] args) throws Exception {
    final Server server = new DefaultServer().build();

    final ContextHandler contextHandler = new ContextHandler();
    contextHandler.setContextPath("/");
    contextHandler.setHandler(new DefaultHandler());
    server.setHandler(contextHandler);

    server.start();
  }
}
