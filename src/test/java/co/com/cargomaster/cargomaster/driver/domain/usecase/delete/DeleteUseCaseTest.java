package co.com.cargomaster.cargomaster.driver.domain.usecase.delete;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.Vehicle;
import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.VehicleType;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.repository.TicketRepository;
import co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketdelete.TicketDeleteUseCase;
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
class DeleteUseCaseTest {

    @Mock
    DriversGateway gateway;
    DeleteUseCase useCase;

    @BeforeEach
    void init(){ useCase = new DeleteUseCase(gateway);}

    @Test
    @DisplayName("DeleteUseCase")
    void apply() {
        String id = "testId";
        Driver driver1 = new Driver();
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setType(VehicleType.TRUCK);
        driver1.setId(id);
        driver1.setVehicle(vehicle1);

        Mockito.when(gateway.deleteDriver(driver1.getId())).thenReturn(Mono.just(id));

        var response = useCase.apply(driver1.getId());

        StepVerifier.create(response)
                .expectNext(id)
                .verifyComplete();

        Mockito.verify(gateway).deleteDriver(id);
    }

}