package co.com.cargomaster.cargomaster.ticket.infrastructure.drivenAdapters.data;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "item")
@NoArgsConstructor
public class TicketData {

    @Id
    private String id = UUID.randomUUID().toString().substring(0,10);
    @NotNull(message = "driverID  can't be null")
    @NotBlank(message = "driverID can't be empty")
    private String driverID;
    @NotNull(message = "origin  can't be null")
    @NotBlank(message = "origin can't be empty")
    private String origin;
    @NotNull(message = "destination  can't be null")
    @NotBlank(message = "destination can't be empty")
    private String destination;
    @NotNull(message = "Description  can't be null")
    @NotBlank(message = "Description can't be empty")
    private String Description;
    @NotNull(message = "customerName  can't be null")
    @NotBlank(message = "customerName can't be empty")
    private String customerName;
    @NotNull(message = "customerEmail  can't be null")
    @NotBlank(message = "customerEmail can't be empty")
    private String customerEmail;
    @NotNull(message = "packageReceiver  can't be null")
    @NotBlank(message = "packageReceiver can't be empty")
    private String packageReceiver;
    @NotNull(message = "weigth  can't be null")
    private Integer weigth;
    @NotNull(message = "minutes  can't be null")
    private Integer minutes;
    private Integer cost = 0;

    private TicketStatus status = TicketStatus.PENDING;

}
