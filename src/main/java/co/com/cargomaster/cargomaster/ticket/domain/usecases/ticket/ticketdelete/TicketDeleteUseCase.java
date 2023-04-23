package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketdelete;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class TicketDeleteUseCase implements Function<String, Mono<String>> {

    private final TicketRepository repository;


    @Override
    public Mono<String> apply(String id) {
        return repository.deleteTicket(id);
    }

}
