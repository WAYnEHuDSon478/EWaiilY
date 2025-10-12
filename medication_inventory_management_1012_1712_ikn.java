// 代码生成时间: 2025-10-12 17:12:52
package com.example.demo.inventory;

import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Introspected
public class MedicationInventoryManagement {

    // List to simulate a database of medications
    private List<Medication> medications = new ArrayList<>();

    // Adds a new medication to the inventory
    public void addMedication(@NotNull Medication medication) {
        if (medication == null) {
            throw new IllegalArgumentException("Medication cannot be null");
        }
        medications.add(medication);
    }

    // Removes a medication from the inventory by name
    public boolean removeMedicationByName(String name) {
        return medications.removeIf(medication -> medication.getName().equals(name));
    }

    // Updates the stock of a medication
    public void updateStock(String name, int newStock) {
        Optional<Medication> medicationOptional = medications.stream()
                .filter(medication -> medication.getName().equals(name))
                .findFirst();

        if (medicationOptional.isPresent()) {
            medicationOptional.get().setStock(newStock);
        } else {
            throw new IllegalArgumentException("Medication not found");
        }
    }

    // Gets a medication by name
    public Optional<Medication> getMedicationByName(String name) {
        return medications.stream()
                .filter(medication -> medication.getName().equals(name))
                .findFirst();
    }

    // Prints the current inventory
    public void printInventory() {
        medications.forEach(System.out::println);
    }
}

@Introspected
class Medication {
    private String name;
    private int stock;

    public Medication(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Medication{"name":"" + name + "", "stock": " + stock + "}";
    }
}