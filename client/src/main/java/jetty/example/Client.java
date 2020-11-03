package jetty.example;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;

@SuppressWarnings("NotNullNullableValidation")
public final class Client {

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = new HttpClient();

        httpClient.setFollowRedirects(false);

        httpClient.start();

        final ContentResponse get = httpClient.GET("http://localhost:3466/");
        System.out.println( new String(get.getContent()));
    }
}
