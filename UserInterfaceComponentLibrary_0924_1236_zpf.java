// 代码生成时间: 2025-09-24 12:36:26
import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * UserInterfaceComponentLibrary is a class that represents a library of UI components.
 * It provides functionality to manage and use these components in a user interface.
 * This class is structured to be easily understandable and maintainable.
 */
@Introspected
public class UserInterfaceComponentLibrary {

    // Map to store UI components with their names as keys and component instances as values.
    private Map<String, Object> components = new HashMap<>();

    /**
     * Adds a UI component to the library.
     * @param componentName The name of the component to add.
     * @param component The component instance to add.
     * @throws IllegalArgumentException if the component name is blank or the component is null.
     */
    public void addComponent(@NotBlank String componentName, Object component) {
        if (componentName == null || component == null) {
            throw new IllegalArgumentException("Component name and component cannot be null");
        }
        components.put(componentName, component);
    }

    /**
     * Retrieves a UI component from the library by name.
     * @param componentName The name of the component to retrieve.
     * @return The component instance if found, otherwise null.
     */
    public Object getComponent(String componentName) {
        return components.get(componentName);
    }

    /**
     * Removes a UI component from the library by name.
     * @param componentName The name of the component to remove.
     * @return The removed component instance if found, otherwise null.
     */
    public Object removeComponent(String componentName) {
        return components.remove(componentName);
    }

    /**
     * Provides a string representation of the UI component library.
     * @return A string representation of the components in the library.
     */
    @Override
    public String toString() {
        return "UserInterfaceComponentLibrary{components=" + components + "}";
    }
}
