package jetty.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.DoSFilter;
import org.eclipse.jetty.util.resource.Resource;

import javax.servlet.DispatcherType;
import java.net.URL;
import java.util.EnumSet;

@SuppressWarnings({"Duplicates", "NotNullNullableValidation"})
public final class FiltersDoS {

  public static void main(String[] args) throws Exception {
    final Server server = new DefaultServer().build(3466);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
    context.setContextPath("/");
    final URL resource = FiltersQoS.class.getResource("/static");
    context.setBaseResource(Resource.newResource(resource.toExternalForm()));
    context.setWelcomeFiles(new String[]{"/static/example"});
    context.addServlet(new ServletHolder("default", DefaultServlet.class), "/*");

    final DoSFilter filter = new DoSFilter();
    final FilterHolder filterHolder = new FilterHolder(filter);
    filterHolder.setInitParameter("maxRequestsPerSec", "1");
    context.addFilter(filterHolder, "/*", EnumSet.of(DispatcherType.REQUEST));
    server.setHandler(context);

    server.start();
  }
}
