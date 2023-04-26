package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketupdate;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.Vehicle;
import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.VehicleType;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import co.com.cargomaster.cargomaster.driver.domain.usecase.update.UpdateUseCase;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TicketUpdateUseCaseTest {

    @Mock
    TicketRepository repository;
    TicketUpdateUseCase useCase;

    @BeforeEach
    void init(){ useCase = new TicketUpdateUseCase(repository);}

    @Test
    @DisplayName("TicketUpdateUseCase")
    void update() {

        Ticket ticket1 = new Ticket();
        ticket1.setId("testId");
        ticket1.setDestination("testDestination");

        Ticket ticketUpdated = new Ticket();
        ticketUpdated.setId("testId");
        ticketUpdated.setDestination("testDestinationUpdated");

        Mockito.when(repository.updateTicket(ticket1.getId(),ticketUpdated)).thenReturn(Mono.just(ticketUpdated));

        var response = useCase.apply(ticket1.getId(),ticketUpdated);

        StepVerifier.create(response)
                .expectNext(ticketUpdated)
                .verifyComplete();

        Mockito.verify(repository).updateTicket(ticket1.getId(), ticketUpdated);

    }


}