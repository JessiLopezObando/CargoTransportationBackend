package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketgetall;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class TicketGetAllUseCase implements Supplier<Flux<Ticket>> {

    private final TicketRepository repository;


    @Override
    public Flux<Ticket> get() {
        return repository.getAllTickets();
    }
}
