// 代码生成时间: 2025-08-13 21:26:25
package com.example.migrations;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.exceptions.BeanCreationException;
import io.micronaut.context.exceptions.DependencyInjectionException;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.StringUtils;
import io.micronaut.runtime.Micronaut;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;

// DatabaseMigrationTool is a utility class that handles database migrations.
@Factory
@Requires(env = Environment.CLI)
public class DatabaseMigrationTool {

    private final DataSource dataSource;

    // Constructor injection of DataSource.
    public DatabaseMigrationTool(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Method to perform database migration.
    public void performMigration(Path migrationScriptPath) {
        try (Connection connection = dataSource.getConnection()) {
            // Read all migration scripts from the provided path.
            List<String> migrationScripts = Files.readAllLines(migrationScriptPath);

            // Execute each migration script.
            for (String script : migrationScripts) {
                if (StringUtils.isNotEmpty(script.trim())) {
                    connection.createStatement().execute(script);
                }
            }
        } catch (SQLException e) {
            throw new BeanCreationException("Error during database migration", e);
        } catch (Exception e) {
            throw new DependencyInjectionException("Error reading migration scripts", e);
        }
    }

    // Main method to run database migration tool.
    public static void main(String[] args) {
        // Check if the migration script path is provided.
        if (args.length != 1) {
            throw new IllegalArgumentException("Migration script path must be provided");
        }

        // Start Micronaut application in CLI environment.
        Micronaut.run(DatabaseMigrationTool.class, args);
    }

    // Bean definition for DatabaseMigrationTool.
    @Bean
    public DatabaseMigrationTool databaseMigrationTool(DataSource dataSource) {
        return new DatabaseMigrationTool(dataSource);
    }
}
