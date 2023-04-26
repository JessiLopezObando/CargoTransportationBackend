package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketsave;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.Vehicle;
import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.VehicleType;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import co.com.cargomaster.cargomaster.driver.domain.usecase.save.SaveUseCase;
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
class TicketSaveUseCaseTest {

    @Mock
    TicketRepository repository;
    TicketSaveUseCase useCase;

    @BeforeEach
    void init(){ useCase = new TicketSaveUseCase(repository);}

    @Test
    @DisplayName("TicketSaveUseCase")
    void save() {
        Ticket ticket1 = new Ticket();

        Mockito.when(repository.saveTicket(ticket1)).thenReturn(Mono.just(ticket1));

        var response = useCase.apply(ticket1);
        StepVerifier.create(response)
                .expectNext(ticket1)
                .verifyComplete();

        Mockito.verify(repository).saveTicket(ticket1);


    }

}