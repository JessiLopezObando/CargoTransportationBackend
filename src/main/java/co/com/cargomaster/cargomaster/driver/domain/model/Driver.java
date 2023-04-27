package co.com.cargomaster.cargomaster.driver.domain.model;

import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.Vehicle;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Driver {

    private String id;
    private String username;
    private String name;
    private String lastName;
    private String dni;
    private String email;
    private String phone;
    private Integer age;
    private Vehicle vehicle;


    public Driver vehicleWeight(Vehicle vehicle){
        if (vehicle.getTotalCapacity() < 0) {
            throw new IllegalArgumentException("Total capacity can't be negative");
        } else {
            return this;
        }
    }


}
