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
    private Double weight;
    private Integer minutes;
    private Double cost;

    private TicketStatus status = TicketStatus.PENDING;

    public Ticket calculateCost (){
        double timeRecharge = Math.ceil(minutes / 5.0) * 0.5;
        double weightRecharge = Math.ceil(weight / 25.0) * 2;
        this.cost = timeRecharge + weightRecharge;
        return this;
    }



}
