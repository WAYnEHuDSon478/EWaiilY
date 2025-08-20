// 代码生成时间: 2025-08-20 20:28:47
package com.example.demo.model;

import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 基础数据模型类，包含共有属性
 */
@Introspected
public abstract class BaseModel {
    private Long id;
    private Date createdAt;
    private Date updatedAt;

    @NotNull
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}

package com.example.demo.model;

import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户数据模型
 */
@Introspected
public class User extends BaseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is not valid")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.demo.exception;

/**
 * 自定义异常类，用于处理数据模型验证错误
 */
public class ModelValidationException extends RuntimeException {
    public ModelValidationException(String message) {
        super(message);
    }
}

package com.example.demo.service;

import com.example.demo.exception.ModelValidationException;
import com.example.demo.model.User;
import io.micronaut.validation.Validateable;
import javax.validation.ConstraintViolationException;

/**
 * 数据模型服务类，提供数据模型验证等功能
 */
public class ModelService {
    /**
     * 验证数据模型是否有效
     *
     * @param model 数据模型对象
     * @throws ModelValidationException 验证失败时抛出异常
     */
    public void validateModel(Object model) throws ModelValidationException {
        if (model instanceof Validateable) {
            try {
                ((Validateable) model).validate();
            } catch (ConstraintViolationException e) {
                throw new ModelValidationException("Model validation failed: " + e.getMessage());
            }
        }
    }
}
