package co.com.cargomaster.cargomaster.driver.domain.usecase.update;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.Vehicle;
import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.VehicleType;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
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
class DriverUpdateWeightOnDeliveredTicketUseCaseTest {

    @Mock
    DriversGateway gateway;
    DriverUpdateWeightOnAcceptedTicketUseCase useCase;

    @BeforeEach
    void init(){ useCase = new DriverUpdateWeightOnAcceptedTicketUseCase(gateway);}

    @Test
    @DisplayName("DriverUpdateWeightOnAcceptedTicketUseCase")
    void updateWeightOnAcceptedTicket() {

        double weight = 5.0;

        Driver driver1 = new Driver();
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setType(VehicleType.TRUCK);
        vehicle1.setCurrentCapacity(10.0);
        vehicle1.setTotalCapacity(20.0);
        driver1.setVehicle(vehicle1);

        Driver driverUpdated = new Driver();
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setType(VehicleType.TRUCK);
        vehicle2.setCurrentCapacity(vehicle1.getCurrentCapacity() - weight);
        vehicle2.setTotalCapacity(20.0);
        driverUpdated.setVehicle(vehicle2);

        Mockito.when(gateway.updateVehicleCapacityOnAcceptedTicket(driver1.getId(), weight)).thenReturn(Mono.just(driverUpdated));

        var response = useCase.apply(driver1.getId(), weight);

        StepVerifier.create(response)
                .expectNext(driverUpdated)
                .verifyComplete();

        Mockito.verify(gateway).updateVehicleCapacityOnAcceptedTicket(driver1.getId(), weight);
    }

}