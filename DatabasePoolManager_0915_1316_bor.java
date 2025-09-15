// 代码生成时间: 2025-09-15 13:16:00
import io.micronaut.context.annotation.Bean;
    import io.micronaut.context.annotation.Factory;
    import io.micronaut.context.annotation.Requires;
    import io.micronaut.context.env.Environment;
    import io.micronaut.context.exceptions.BeanCreationException;
    import io.micronaut.core.util.StringUtils;
    import javax.sql.DataSource;
    import java.sql.SQLException;
    import java.util.Properties;
    import javax.inject.Singleton;
    import com.zaxxer.hikari.HikariDataSource;

    // Factory class to manage database connection pool
    @Factory
    @Requires(env = Environment.DATABASE)
    public class DatabasePoolManager {

        // Bean to create HikariCP DataSource
        @Bean(preDestroy = 