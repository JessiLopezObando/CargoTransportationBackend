package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketupdate;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class TicketUpdateUseCase implements BiFunction<String, Ticket, Mono<Ticket>> {

    private final TicketRepository repository;

    @Override
    public Mono<Ticket> apply(String id, Ticket ticket) {
        return repository.updateTicket(id, ticket);
    }
}