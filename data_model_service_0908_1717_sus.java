// 代码生成时间: 2025-09-08 17:17:37
package com.example.demo;

import io.micronaut.core.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

// Entity class representing a DataModel
public class DataModel {
    // Unique identifier for the data model
    private Long id;

    // Required property to store the name of the data model
    @NotBlank(message = "Name cannot be empty")
    private String name;

    // Optional description of the data model
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Nullable
    private String description;

    // Constructor, getters and setters
    public DataModel() {
    }

    public DataModel(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

// Service interface for the data model
public interface DataModelService {
    // Method to retrieve a data model by its ID
    Optional<DataModel> findById(Long id);

    // Method to retrieve all data models
    List<DataModel> findAll();

    // Method to create a new data model
    DataModel create(DataModel dataModel);

    // Method to update an existing data model
    DataModel update(Long id, DataModel dataModel);

    // Method to delete a data model by its ID
    void delete(Long id);
}

// Implementation of the service interface
public class DataModelServiceImpl implements DataModelService {
    private final DataModelRepository repository;

    // Constructor injection of the repository
    public DataModelServiceImpl(DataModelRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<DataModel> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<DataModel> findAll() {
        return repository.findAll();
    }

    @Override
    public DataModel create(DataModel dataModel) {
        if (dataModel.getName() == null || dataModel.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        return repository.save(dataModel);
    }

    @Override
    public DataModel update(Long id, DataModel dataModel) {
        // Check if the data model exists
        Optional<DataModel> existingModel = findById(id);
        if (existingModel.isPresent()) {
            DataModel existing = existingModel.get();
            existing.setName(dataModel.getName());
            existing.setDescription(dataModel.getDescription());
            return repository.save(existing);
        } else {
            throw new IllegalArgumentException("Data model with ID not found");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<DataModel> existingModel = findById(id);
        if (existingModel.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Data model with ID not found");
        }
    }
}

// Repository interface for database operations
public interface DataModelRepository extends JpaRepository<DataModel, Long> {
}