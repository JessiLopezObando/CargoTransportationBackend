package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketdelete;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketgetbyid.TicketGetByIdUseCase;
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
class TicketDeleteUseCaseTest {

    @Mock
    TicketRepository repository;
    TicketDeleteUseCase useCase;

    @BeforeEach
    void init(){ useCase = new TicketDeleteUseCase(repository);}

    @Test
    @DisplayName("DeleteTicketByIdUseCase")
    void apply() {
        String id = "testId";
        Ticket ticket1 = new Ticket();
        ticket1.setId(id);

        Mockito.when(repository.deleteTicket(ticket1.getId())).thenReturn(Mono.just(id));

        var response = useCase.apply(ticket1.getId());

        StepVerifier.create(response)
                .expectNext(id)
                .verifyComplete();

        Mockito.verify(repository).deleteTicket(id);
    }


}