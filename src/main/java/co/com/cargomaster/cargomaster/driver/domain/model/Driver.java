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

    public Driver setToUpperCase(){

        this.name = this.name.toUpperCase();
        this.lastName = this.lastName.toUpperCase();
        this.email = this.email.toUpperCase();
        this.vehicle.setPlate(this.vehicle.getPlate().toUpperCase());
        this.vehicle.setBrand(this.vehicle.getBrand().toUpperCase());
        this.vehicle.setModel(this.vehicle.getModel().toUpperCase());
        this.vehicle.setColor(this.vehicle.getColor().toUpperCase());

        return this;
    }


}
