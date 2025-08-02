// 代码生成时间: 2025-08-02 20:01:31
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.server.exceptions.InternalServerException;
import io.micronaut.views.ViewRenderable;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

/**
 * Controller for handling XSS protection.
 * This controller demonstrates how to prevent Cross-Site Scripting (XSS) attacks
 * by sanitizing user input.
 */
@Controller("/xss")
public class XssProtectionService {

    // Define a safelist that allows only certain HTML tags and attributes
    private static final Safelist SAFELIST = Safelist.basic();

    /**
     * Endpoint to demonstrate XSS protection.
     * @param userInput The user input that may contain malicious scripts.
     * @return A view renderable response with sanitized input.
     */
    @Post("/protect")
    public ViewRenderable protectXss(@NotNull HttpRequest<?> request, @NotNull String userInput) {
        try {
            // Sanitize the user input to prevent XSS attacks
            String sanitizedInput = Jsoup.clean(userInput, SAFELIST);
            return ViewRenderable.of("xss_protect.html", "userInput", sanitizedInput);
        } catch (Exception e) {
            // Handle any unexpected errors
            throw new InternalServerException("Error sanitizing user input", e);
        }
    }

    /**
     * Endpoint to display the form for user input.
     * @return A view renderable response with a form.
     */
    @Get("/form")
    public ViewRenderable form() {
        return ViewRenderable.of("xss_form.html");
    }
}
