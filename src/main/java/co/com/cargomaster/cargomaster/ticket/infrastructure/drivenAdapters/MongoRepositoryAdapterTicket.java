package co.com.cargomaster.cargomaster.ticket.infrastructure.drivenAdapters;

import co.com.cargomaster.cargomaster.application.services.EmailService;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.TicketStatus;
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

    private final EmailService emailService;


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
                .map(ticketData -> mapper.map(ticketData, Ticket.class));
    }

    @Override
    public Mono<Ticket> updateStatusTicketToAccepted(String id) {
        return this.repository
                .findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ticket with id: " + id + " was not found")))
                .flatMap(ticketData -> {
                    if (ticketData.getStatus() != TicketStatus.PENDING) {
                        throw new IllegalArgumentException("ticket status is not PENDING");
                    }

                    // Send email to the customer
                    emailService.send("cargomaster23@gmail.com",
                            ticketData.getCustomerEmail(),
                            "Service Accepted by Driver" ,
                            "Dear " + ticketData.getCustomerName() + ",\n\n" +
                                    "We are pleased to inform you that we have received your request for a delivery service and a driver has accepted to be the carrier for your package.\n\n"
                                    + "The driver's name is [Driver Name] and their contact information is as follows:\n\n"
                                    + "Phone Number: [Driver Phone Number]\n\n"
                                    + "Phone Number: [Driver Phone Number]\n\n"
                                    +
                                    "You can contact the driver directly to arrange for pick-up and delivery of your package. If you have any questions or concerns regarding the delivery, please don't hesitate to reach out to our customer service team at [Phone Number] or [Email Address]. We are always here to help.\n\n" +
                                    "Thank you for choosing our delivery service and we look forward to serving you in the future.\n\n" +
                                    "Best regards,\n" +
                                    "CargoMasters");
                    // Map the saved invoice data to Invoice class
                    return repository.save(ticketData.changeStatusToAccepted());
                })
                .map(ticketData -> mapper.map(ticketData, Ticket.class));

    }

    @Override
    public Mono<Ticket> updateStatusTicketToRefused(String id) {
        return null;
    }


}
