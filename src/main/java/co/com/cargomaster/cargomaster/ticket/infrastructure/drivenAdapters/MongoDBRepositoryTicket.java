package co.com.cargomaster.cargomaster.ticket.infrastructure.drivenAdapters;

import co.com.cargomaster.cargomaster.ticket.infrastructure.drivenAdapters.data.TicketData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepositoryTicket extends ReactiveMongoRepository<TicketData, String> {
}
