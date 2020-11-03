package jetty.example;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//http://localhost:3466/?name1=value1
//http://localhost:3466


@SuppressWarnings("NotNullNullableValidation")
public final class ManyHandlers {

  public static void main(String[] args) throws Exception {
    var server = new DefaultServer().build();

    var paramHandler = new ParamHandler();
    var wrapper = new HandlerWrapper() {
      @Override
      public void handle(String target,
                         Request baseRequest,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {
        System.out.println(HandlerWrapper.class.getSimpleName());
        request.setAttribute("welcome", "Hello");
        super.handle(target, baseRequest, request, response);
      }
    };
    var helloHandler = new HelloHandler();
    var defaultHandler = new DefaultHandler();
    var logHandler = new ConsoleLogHandler();

    var handlerCollection = new HandlerCollection();
    var handlerList = new HandlerList();

    wrapper.setHandler(helloHandler);

    handlerList.setHandlers(new Handler[]{paramHandler, wrapper, defaultHandler});

    handlerCollection.setHandlers(new Handler[]{handlerList, logHandler});

    server.setHandler(handlerCollection);
    server.start();
  }

  public static final class ParamHandler extends AbstractHandler {
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
      System.out.println(ParamHandler.class.getSimpleName());
      var params = request.getParameterMap();
      if (params.size() > 0) {
        response.setContentType("text/plain");
        response.getWriter().println(params);
        baseRequest.setHandled(true);
      }
    }
  }

  public static final class HelloHandler extends AbstractHandler {
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
      System.out.println(HelloHandler.class.getSimpleName());
      response.setContentType("text/html;charset=utf-8");
      response.setStatus(HttpServletResponse.SC_OK);
      baseRequest.setHandled(true);
      final var attr = request.getAttribute("welcome");
      response.getWriter().println("<h1>" + attr + " jetty</h1>");
    }
  }

  public static final class ConsoleLogHandler extends AbstractHandler {
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) {
      System.out.println(ConsoleLogHandler.class.getSimpleName());
      System.out.println(request.getParameterMap());
      baseRequest.setHandled(true);
    }
  }
}