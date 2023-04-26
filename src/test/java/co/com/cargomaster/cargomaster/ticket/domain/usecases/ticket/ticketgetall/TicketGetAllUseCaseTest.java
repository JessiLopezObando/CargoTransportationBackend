package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketgetall;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TicketGetAllUseCaseTest {

    @Mock
    TicketRepository repository;
    TicketGetAllUseCase useCase;

    @BeforeEach
    void init(){ useCase = new TicketGetAllUseCase(repository);}

    @Test
    @DisplayName("TicketGetAllUseCase")
    void get() {

        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();

        Mockito.when(repository.getAllTickets()).thenReturn(Flux.just(ticket2, ticket1));

        var response = useCase.get();
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repository).getAllTickets();

    }


}