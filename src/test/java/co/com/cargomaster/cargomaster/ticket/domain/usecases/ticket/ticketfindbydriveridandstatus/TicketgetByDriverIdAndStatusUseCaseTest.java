package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketfindbydriveridandstatus;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketdelivered.TicketDeliveredUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TicketgetByDriverIdAndStatusUseCaseTest {

    @Mock
    TicketRepository repository;
    TicketgetByDriverIdAndStatusUseCase useCase;

    @BeforeEach
    void init(){ useCase = new TicketgetByDriverIdAndStatusUseCase(repository);}

    @Test
    @DisplayName("TicketgetByDriverIdAndStatusUseCase")
    void apply() {
        String id = "testId";
        String status = "status";
        Ticket ticket1 = new Ticket();
        ticket1.setId(id);
        Ticket ticket2 = new Ticket();
        ticket1.setId(id);

        Mockito.when(repository.getTicketByDriverAndStatus(ticket1.getId(), status)).thenReturn(Flux.just(ticket1, ticket2));

        var response = useCase.apply(id, status);

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repository).getTicketByDriverAndStatus(id, status);
    }


}