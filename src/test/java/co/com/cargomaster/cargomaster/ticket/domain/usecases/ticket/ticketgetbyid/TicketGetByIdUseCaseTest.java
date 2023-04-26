package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketgetbyid;

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


@ExtendWith(MockitoExtension.class)
class TicketGetByIdUseCaseTest {

    @Mock
    TicketRepository repository;
    TicketGetByIdUseCase useCase;

    @BeforeEach
    void init(){ useCase = new TicketGetByIdUseCase(repository);}

    @Test
    @DisplayName("TicketGetByIdUseCase")
    void apply() {
        Ticket ticket1 = new Ticket();
        ticket1.setId("testId");

        Mockito.when(repository.getTicketById(ticket1.getId())).thenReturn(Mono.just(ticket1));

        var response = useCase.apply(ticket1.getId());

        StepVerifier.create(response)
                .expectNext(ticket1)
                .verifyComplete();

        Mockito.verify(repository).getTicketById("testId");
    }

}