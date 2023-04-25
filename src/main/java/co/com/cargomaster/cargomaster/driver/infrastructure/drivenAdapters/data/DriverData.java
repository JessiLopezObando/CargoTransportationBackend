package co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters.data;

import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.Vehicle;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "drivers")
@NoArgsConstructor
public class DriverData {

    @Id
    private String id = UUID.randomUUID().toString().substring(0,10);
    private String username;
    @NotNull(message = "Name can't be null")
    @NotBlank(message = "Name can't be empty")
    private String name;
    @NotNull(message = "Last Name can't be null")
    @NotBlank(message = "Last Name can't be empty")
    private String lastName;
    @NotNull(message = "Dni can't be null")
    @NotBlank(message = "Dni can't be empty")
    private String dni;
    @Email(message = "Enter a valid email")
    @NotNull(message = "Email can't be null")
    @NotBlank(message = "Email can't be empty")
    private String email;
    @NotNull(message = "Phone can't be null")
    @NotBlank(message = "Phone can't be empty")
    private String phone;
    @NotNull(message = "Age can't be null")
    @NotBlank(message = "Age can't be empty")
    @Min(value = 18, message = "You must be 18 or older")
    private Integer age;
    @NotNull(message = "Vehicle Data can't be null")
    @NotBlank(message = "Vehicle Data can't be empty")
    private Vehicle vehicle;
}
