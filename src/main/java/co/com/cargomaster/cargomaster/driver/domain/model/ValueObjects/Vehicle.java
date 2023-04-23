package co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    private String plate;
    private String brand;
    private String model;
    private String color;
    private String type;
    private Double totalCapacity;
    private Double availableCapacity;
    private Boolean isFull;
}
