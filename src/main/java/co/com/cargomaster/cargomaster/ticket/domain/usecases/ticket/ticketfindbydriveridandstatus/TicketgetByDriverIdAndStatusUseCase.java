package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketfindbydriveridandstatus;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class TicketgetByDriverIdAndStatusUseCase implements BiFunction<String, String, Flux<Ticket>> {

    private final TicketRepository repository;

    @Override
    public Flux<Ticket> apply(String driverId, String status) {
        return this.repository.getTicketByDriverAndStatus(driverId, status);
    }
}
