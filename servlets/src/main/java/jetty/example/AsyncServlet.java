package jetty.example;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@SuppressWarnings({"NotNullNullableValidation", "FieldCanBeLocal"})
public final class AsyncServlet extends HttpServlet {
  private static final String HEAVY__RESOURCE = "This is some heavy resource that will be served in an async way";

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ByteBuffer content = ByteBuffer.wrap(HEAVY__RESOURCE.getBytes(StandardCharsets.UTF_8));

    AsyncContext async = request.startAsync();
    ServletOutputStream out = response.getOutputStream();
    out.setWriteListener(new WriteListener() {
      @Override
      public void onWritePossible() throws IOException {
        while (out.isReady()) {
          if (!content.hasRemaining()) {
            response.setStatus(200);
            async.complete();
            return;
          }
          out.write(content.get());
        }
      }

      @Override
      public void onError(Throwable t) {
        getServletContext().log("Async Error", t);
        async.complete();
      }
    });
  }
}