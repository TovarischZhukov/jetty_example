package jetty.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.InetAccessHandler;

@SuppressWarnings("NotNullNullableValidation")
public final class HandlersIPAccess {

  public static void main(String[] args) throws Exception {
    final Server server = new DefaultServer().build();

    final ContextHandler contextHandler = new ContextHandler();
    contextHandler.setContextPath("/*");
    InetAccessHandler inetAccessHandler = new InetAccessHandler();
    inetAccessHandler.exclude("localhost");
    inetAccessHandler.setHandler(new DefaultHandler());
    contextHandler.setHandler(inetAccessHandler);
    server.setHandler(contextHandler);

    server.start();
  }
}
