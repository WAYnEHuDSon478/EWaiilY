// 代码生成时间: 2025-08-04 00:39:16
import io.micronaut.context.annotation.Requires;
    import io.micronaut.http.HttpRequest;
    import io.micronaut.http.annotation.Controller;
    import io.micronaut.http.annotation.Get;
    import io.micronaut.http.annotation.QueryValue;
    import io.micronaut.http.exceptions.HttpStatusException;
    import io.micronaut.security.annotation.Secured;
    import io.micronaut.security.rules.SecurityRule;
    import io.micronaut.web.router.Router;
    import javax.inject.Singleton;
    import javax.sql.DataSource;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;

    /**
     * A Micronaut controller to demonstrate prevention of SQL injection attacks.
     */
    @Requires(env = 