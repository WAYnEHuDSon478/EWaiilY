// 代码生成时间: 2025-09-01 11:11:05
package com.example.validation;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Body;
import io.micronaut.validation.validator.Validator;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

// DTO class for form data
class FormData {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Size(min = 5, max = 10, message = "Name must be between 5 and 10 characters")
    private String username;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

// FormValidator class
@Requires(property = "app.validator.enabled")
@Controller("/form")
public class FormValidator {

    private final ValidatorFactory validatorFactory;

    // Constructor injection of ValidatorFactory
    public FormValidator(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    // Method to validate form data
    @Post("/validate")
    public String validateFormData(@Body FormData formData) {
        try {
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<FormData>> violations = validator.validate(formData);
            if (!violations.isEmpty()) {
                StringBuilder errorBuilder = new StringBuilder("Validation failed: ");
                for (ConstraintViolation<FormData> violation : violations) {
                    errorBuilder.append(violation.getMessage()).append("
");
                }
                return errorBuilder.toString();
            } else {
                return "Validation succeeded";
            }
        } catch (Exception e) {
            return "An error occurred during validation: " + e.getMessage();
        }
    }
}
