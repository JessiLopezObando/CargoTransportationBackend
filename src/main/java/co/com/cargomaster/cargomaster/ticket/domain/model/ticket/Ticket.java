package co.com.cargomaster.cargomaster.ticket.domain.model.ticket;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Ticket {

    private String id;
    private String driverID;
    private String origin;
    private String destination;
    private String Description;
    private String customerName;
    private String customerEmail;
    private String packageReceiver;
    private Double weigth;
    private Integer minutes;
    private Double cost = 0.0;

    private TicketStatus status = TicketStatus.PENDING;



}
