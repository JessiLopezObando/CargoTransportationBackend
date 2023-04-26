package co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketgetcost;

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
class TicketGetCostUseCaseTest {

    @Mock
    TicketRepository repository;
    TicketGetCostUseCase useCase;

    @BeforeEach
    void init(){ useCase = new TicketGetCostUseCase(repository);}

    @Test
    @DisplayName("TicketGetCostUseCase")
    void apply() {

        double weight = 5.0;
        int minutes = 6;
        double cost = 5.0;



        Mockito.when(repository.getCostBasedOnMinutesAndWeight(minutes, weight)).thenReturn(Mono.just(cost));

        var response = useCase.apply(minutes, weight);

        StepVerifier.create(response)
                .expectNext(cost)
                .verifyComplete();

        Mockito.verify(repository).getCostBasedOnMinutesAndWeight(minutes, weight);



    }

}