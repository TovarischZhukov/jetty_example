package jetty.example;

import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

@SuppressWarnings("NotNullNullableValidation")
public final class FiltersGuice {

  public static void main(String[] args) throws Exception {
    final Server server = new DefaultServer().build(3466);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
    context.setContextPath("/");
    context.addEventListener(new MyGuiceListener());
    context.addFilter(GuiceFilter.class, "/", EnumSet.of(DispatcherType.REQUEST));
    server.setHandler(context);

    server.start();
  }
}
