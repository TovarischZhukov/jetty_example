package jetty.example;

import org.eclipse.jetty.server.*;

@SuppressWarnings("NotNullNullableValidation")
public final class Connectors {

  public static void main(String[] args) throws Exception {
    final Server server = new Server();

    final HttpConfiguration httpConfig = new HttpConfiguration();

    final HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);

    final ServerConnector serverConnector = new ServerConnector(server, httpConnectionFactory);

    serverConnector.setHost("localhost");
    serverConnector.setPort(3466);

    server.setConnectors(new Connector[]{serverConnector});

    server.start();
  }
}
