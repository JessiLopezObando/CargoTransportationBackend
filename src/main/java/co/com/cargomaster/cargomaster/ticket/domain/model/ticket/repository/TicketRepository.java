package co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TicketRepository {

    Flux<Ticket> getAllTickets();
    Mono<Ticket> getTicketById(String id);
    Mono<Ticket> saveTicket(Ticket ticket);
    Mono<Ticket> updateTicket(String id, Ticket ticket);
    Mono<String> deleteTicket(String id);
    Flux<Ticket> getTicketByDriverAndStatus (String driverId, String status);
    Mono<Ticket> updateStatusTicketToAccepted (String id);
    Mono<Ticket> updateStatusTicketToRefused (String id);
    Mono<Ticket> updateStatusTicketToDelivered (String id);
    Mono<Double> getCostBasedOnMinutesAndWeight (Integer minutes, Double weight);

}
