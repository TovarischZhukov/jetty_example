package jetty.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;

@SuppressWarnings("NotNullNullableValidation")
public final class HandlersMy {

  public static void main(String[] args) throws Exception {
    final Server server = new DefaultServer().build();

    final ContextHandler contextHandler = new ContextHandler();
    contextHandler.setContextPath("/");
    contextHandler.setHandler(new MyHandler());
    server.setHandler(contextHandler);

    server.start();
  }
}
