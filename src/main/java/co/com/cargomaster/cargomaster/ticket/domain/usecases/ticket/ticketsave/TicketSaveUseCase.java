package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketsave;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class TicketSaveUseCase implements Function<Ticket, Mono<Ticket>> {

    private final TicketRepository repository;


    @Override
    public Mono<Ticket> apply(Ticket ticket) {
        return repository.saveTicket(ticket);
    }


}
