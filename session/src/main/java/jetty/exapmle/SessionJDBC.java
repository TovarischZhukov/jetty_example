package jetty.exapmle;

import jetty.example.DefaultServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.*;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import java.net.URL;

@SuppressWarnings({"Duplicates", "NotNullNullableValidation"})
public final class SessionJDBC {

  public static void main(String[] args) throws Exception {
    final Server server = new DefaultServer().build();

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    final URL resource = SessionJDBC.class.getResource("/static");
    context.setBaseResource(Resource.newResource(resource.toExternalForm()));
    context.setWelcomeFiles(new String[]{"/static/example"});
    context.addServlet(new ServletHolder("default", DefaultServlet.class), "/*");

    final SessionHandler sessionHandler = sqlSessionHandler();
    sessionHandler.setHandler(context);
    server.setHandler(sessionHandler);

    server.start();
  }

  private static SessionHandler sqlSessionHandler() {
    SessionHandler sessionHandler = new SessionHandler();
    SessionCache sessionCache = new DefaultSessionCache(sessionHandler);
    sessionCache.setSessionDataStore(
        jdbcDataStoreFactory().getSessionDataStore(sessionHandler)
    );
    sessionHandler.setSessionCache(sessionCache);
    sessionHandler.setHttpOnly(true);
    return sessionHandler;
  }

  private static JDBCSessionDataStoreFactory jdbcDataStoreFactory() {
    DatabaseAdaptor databaseAdaptor = new DatabaseAdaptor();
    databaseAdaptor.setDriverInfo("org.postgresql.Driver", "jdbc:jetty_session://localhost:5432/mrg_jetty_session?user=postgres&password=postgres");
    JDBCSessionDataStoreFactory jdbcSessionDataStoreFactory = new JDBCSessionDataStoreFactory();
    jdbcSessionDataStoreFactory.setDatabaseAdaptor(databaseAdaptor);
    return jdbcSessionDataStoreFactory;
  }
}
