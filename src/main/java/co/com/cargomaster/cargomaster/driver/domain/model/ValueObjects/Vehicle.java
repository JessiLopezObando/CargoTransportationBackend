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
    private VehicleType type;
    private Double totalCapacity;
    private Double currentCapacity = 0.0;
    private Boolean isFull = false;
}
