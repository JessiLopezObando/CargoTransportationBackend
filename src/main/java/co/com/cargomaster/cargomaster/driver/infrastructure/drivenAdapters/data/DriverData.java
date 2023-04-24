package co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters.data;

import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.Vehicle;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@Document(collection = "drivers")
@NoArgsConstructor
public class DriverData {

    @Id
    private String id;
    private String username;
    private String name;
    private String lastName;
    private String dni;
    private String email;
    private String phone;
    private Integer age;
    private Vehicle vehicle;

    public DriverData updateVehicleCapacity(Double requestWeight){
        if (this.vehicle.getIsFull()){
            throw new IllegalArgumentException("The vehicle is full");
        } else if (requestWeight + this.vehicle.getCurrentCapacity() > this.vehicle.getTotalCapacity()) {
            throw new IllegalArgumentException("The weight requested exceeds the total capacity of the vehicle");
        } else {
            this.vehicle.setCurrentCapacity(this.getVehicle().getCurrentCapacity() + requestWeight);
            if (Objects.equals(this.vehicle.getCurrentCapacity(), this.vehicle.getTotalCapacity())) {
                this.vehicle.setIsFull(true);
            }
            return this;
        }
    }

}
