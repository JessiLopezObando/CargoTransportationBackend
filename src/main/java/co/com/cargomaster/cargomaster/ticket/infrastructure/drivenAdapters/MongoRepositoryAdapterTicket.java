package co.com.cargomaster.cargomaster.ticket.infrastructure.drivenAdapters;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import co.com.cargomaster.cargomaster.ticket.infrastructure.drivenAdapters.data.TicketData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapterTicket implements TicketRepository {


    private final MongoDBRepositoryTicket repository;

    private final ObjectMapper mapper;

    @Override
    public Flux<Ticket> getAllTickets() {
        return this.repository
                .findAll()
                .switchIfEmpty(Flux.empty())
                .map(itemData -> mapper.map(itemData, Ticket.class));
    }

    @Override
    public Mono<Ticket> getTicketById(String id) {
        return this.repository
                .findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("item with id: " + id + "was not found")))
                .map(itemData -> mapper.map(itemData, Ticket.class));
    }

    @Override
    public Mono<Ticket> saveTicket(Ticket ticket) {
        return this.repository
                .save(mapper.map(ticket, TicketData.class))
                .switchIfEmpty(Mono.empty())
                .map(ticketData -> mapper.map(ticketData, Ticket.class));
    }

    @Override
    public Mono<Ticket> updateTicket(String id, Ticket ticket) {
        return this.repository
                .findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ticket with id: " + id + " was not found")))
                .flatMap(ticketData -> {
                    ticket.setId(ticketData.getId());
                    return repository.save(mapper.map(ticket, TicketData.class));
                })
                .map(itemData -> mapper.map(itemData, Ticket.class));

    }

    @Override
    public Mono<String> deleteTicket(String id) {
        return this.repository
                .findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ticket with id: " + id + " was not found")))
                .flatMap(ticketData -> this.repository.delete(ticketData)).thenReturn(id);
    }

    @Override
    public Flux<Ticket> getTicketByDriverAndStatus(String driverId, String status) {
        return this.repository.findByDriverIdAndStatus(driverId, status)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("tickets with the id: " + driverId + " was not found")))
                .map(itemData -> mapper.map(itemData, Ticket.class));
    }


}
