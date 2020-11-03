package jetty.example;


import org.eclipse.jetty.server.AsyncRequestLogWriter;
import org.eclipse.jetty.server.CustomRequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.eclipse.jetty.server.CustomRequestLog.DEFAULT_DATE_FORMAT;
import static org.eclipse.jetty.server.CustomRequestLog.NCSA_FORMAT;

@SuppressWarnings({"FieldCanBeLocal", "NotNullNullableValidation", "FieldMayBeFinal"})
public final class Logging {
  private static String timezone = "Europe/Moscow";

  public static void main(String[] args) throws Exception {
    final Server server = new DefaultServer().build();

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
    context.setContextPath("/");
    context.setWelcomeFiles(new String[]{"/static/example"});
    context.addServlet(new ServletHolder("default", DefaultServlet.class), "/*");
    server.setHandler(context);

    final Path logsPath = Paths.get(Logging.class.getResource("/").toURI());
    final Path jettyPath = logsPath.resolve("jetty");
    if (!Files.exists(jettyPath)) {
      Files.createDirectories(jettyPath);
    }

    final AsyncRequestLogWriter writer = new AsyncRequestLogWriter(
        jettyPath.toString() + '/' + "app.log"
    );
    context.setBaseResource(Resource.newResource(jettyPath));

    writer.setAppend(true);
    writer.setRetainDays(7);
    writer.setTimeZone(timezone);
    final String format = NCSA_FORMAT.replace("%t", "%{" + DEFAULT_DATE_FORMAT + '|' + timezone + "}t");
    final CustomRequestLog customRequestLog = new CustomRequestLog(writer, format);
    server.setRequestLog(customRequestLog);

    server.start();
  }
}
