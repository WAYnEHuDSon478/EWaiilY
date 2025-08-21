// 代码生成时间: 2025-08-21 12:13:44
import io.micronaut.context.annotation.Bean;
    import io.micronaut.context.annotation.Factory;
    import io.micronaut.context.annotation.Requires;
    import javax.sql.DataSource;
    import io.micronaut.sql.DataSourceResolver;
    import io.micronaut.transaction.SynchronousTransactionManager;
    import javax.transaction.TransactionManager;
    import javax.inject.Singleton;
    import java.sql.Connection;
    import java.sql.SQLException;

    /**
     * A factory class to manage database connection pool.
     */
    @Factory
    public class DatabaseConnectionPoolManager {

        /**
         * Creates a DataSource bean for database connection pooling.
         * @param dataSourceResolver The datasource resolver for resolving the data source.
         * @return The configured DataSource bean.
         */
        @Bean
        @Singleton
        @Requires(bean = DataSourceResolver.class)
        public DataSource dataSource(DataSourceResolver dataSourceResolver) {
            return dataSourceResolver.resolveDataSource(