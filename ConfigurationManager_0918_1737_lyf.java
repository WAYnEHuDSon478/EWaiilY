// 代码生成时间: 2025-09-18 17:37:20
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.context.env.PropertiesPropertySourceLoader;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.core.util.CollectionUtils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import javax.inject.Singleton;

/**
 * ConfigurationManager is responsible for managing the application's configuration files.
 * It loads properties from the specified configuration file and provides a way to access them.
 */
@Singleton
public class ConfigurationManager {

    private final Properties properties;

    /**
     * Constructor that initializes the ConfigurationManager with properties from the configuration file.
     * @param environment The environment to use for loading configuration files.
     * @param resourceLoader The resource loader to use for loading files.
     */
    public ConfigurationManager(Environment environment, ResourceLoader resourceLoader) {
        // Define the configuration file name
        final String configFileName = 