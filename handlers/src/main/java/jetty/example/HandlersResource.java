package jetty.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

import java.net.URL;

@SuppressWarnings("NotNullNullableValidation")
public final class HandlersResource {

  public static void main(String[] args) throws Exception {
    final Server server = new DefaultServer().build();

    final ResourceHandler resourceHandler = new ResourceHandler();
    final URL resource = HandlersResource.class.getResource("/static");
    resourceHandler.setBaseResource(Resource.newResource(resource.toExternalForm()));
    resourceHandler.setDirectoriesListed(false);

    final ContextHandler contextHandler = new ContextHandler();
    contextHandler.setContextPath("/*");
    contextHandler.setHandler(resourceHandler);
    server.setHandler(contextHandler);

    server.start();
  }
}
