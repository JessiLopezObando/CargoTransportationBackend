package co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters.data;

import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.Vehicle;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
