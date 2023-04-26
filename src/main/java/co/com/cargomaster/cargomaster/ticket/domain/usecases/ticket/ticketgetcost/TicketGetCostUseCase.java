package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketgetcost;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class TicketGetCostUseCase implements BiFunction <Integer, Double, Mono<Double>> {

    private final TicketRepository repository;

    @Override
    public Mono<Double> apply(Integer minutes, Double weight) {
        return repository.getCostBasedOnMinutesAndWeight(minutes, weight);
    }
}
