package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketrefused;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class TicketRefusedUseCase implements Function<String, Mono<Ticket>> {

    private final TicketRepository repository;


    @Override
    public Mono<Ticket> apply(String id) {
        return this.repository.updateStatusTicketToRefused(id);
    }
}
