// 代码生成时间: 2025-10-06 18:47:46
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.server.netty.NettyHttpServer;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.views.View;
import io.micronaut.views.ViewsErrorPageCustomizer;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.model.ViewRenderException;
import io.micronaut.views.model.ViewResolutionContext;
import io.micronaut.views.model.Viewable;
import jakarta.inject.Singleton;
import java.util.Optional;

@Controller("/")
@Singleton
public class Http2Server implements ViewsErrorPageCustomizer {
    private final ViewsRenderer viewsRenderer;

    public Http2Server(ViewsRenderer viewsRenderer) {
        this.viewsRenderer = viewsRenderer;
    }

    @Get
    public Viewable home() {
        return Views.render("index", "message", "Hello HTTP/2");
    }

    @Error
    public HttpResponse handleException(Throwable e, HttpRequest request) {
        try {
            return viewsRenderer.render(
                ViewResolutionContext.of(request).templateName("error"));
        } catch (ViewRenderException ex) {
            return HttpResponse.serverError();
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = ApplicationContext.run(EmbeddedServer.class, args);
        Optional<EmbeddedServer> server = context.findBean(EmbeddedServer.class);
        if (server.isPresent()) {
            NettyHttpServer nettyServer = ((NettyHttpServer) server.get().getNativeServer());
            System.out.println("Server is running on port: " + nettyServer.getPort());
        }
    }
}