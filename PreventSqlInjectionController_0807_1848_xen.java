// 代码生成时间: 2025-08-07 18:48:25
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.core.util.StringUtils;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

/**
 * A controller to demonstrate preventing SQL injection using Micronaut and prepared statements.
 */
@Controller("/api/secure")
public class PreventSqlInjectionController {

    private final DataSource dataSource;

    /**
     * Constructor injecting the data source.
     * @param dataSource The data source to use for database operations.
     */
    @Inject
    public PreventSqlInjectionController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Get("/users")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public String getUsers(@QueryValue("name") String name) {
        if (StringUtils.isEmpty(name)) {
            return "Error: Name is required";
        }

        String query = "SELECT * FROM users WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            StringBuilder builder = new StringBuilder();
            while (resultSet.next()) {
                builder.append("Name: ").append(resultSet.getString("name")).append("
");
            }
            return builder.toString();

        } catch (SQLException e) {
            // Log the exception for debugging purposes
            // e.printStackTrace();
            return "Error: An SQL exception occurred";
        }
    }

    // Additional methods can be added here for other secure operations
}
