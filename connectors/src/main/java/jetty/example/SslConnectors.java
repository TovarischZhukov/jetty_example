package jetty.example;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;

@SuppressWarnings("NotNullNullableValidation")
public final class SslConnectors {

  public static void main(String[] args) throws Exception {
    final Server server = new Server();

    final HttpConfiguration httpConfig = new HttpConfiguration();

    /*
    httpConfig.setOutputBufferSize(32768);
    httpConfig.setRequestHeaderSize(8192 * 2);
    httpConfig.setResponseHeaderSize(8192 * 2);
    httpConfig.setSendServerVersion(true);
    httpConfig.setSendDateHeader(false);*/

    final SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
    sslContextFactory.setKeyStoreResource(Resource.newClassPathResource("keystore"));
    sslContextFactory.setKeyStorePassword("qwerty");

    final SslConnectionFactory httpConnectionFactory = new SslConnectionFactory(
        sslContextFactory,
        HttpVersion.HTTP_1_1.asString()
    );

    final ServerConnector serverConnector = new ServerConnector(server,
        httpConnectionFactory,
        new HttpConnectionFactory(httpConfig)
    );

    serverConnector.setHost("localhost");
    serverConnector.setPort(3466);

    server.setConnectors(new Connector[]{serverConnector});

    server.start();
  }
}
