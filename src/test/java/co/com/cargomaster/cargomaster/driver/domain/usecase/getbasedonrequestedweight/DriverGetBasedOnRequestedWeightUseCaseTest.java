package co.com.cargomaster.cargomaster.driver.domain.usecase.getbasedonrequestedweight;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.Vehicle;
import co.com.cargomaster.cargomaster.driver.domain.model.ValueObjects.VehicleType;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import co.com.cargomaster.cargomaster.driver.domain.usecase.delete.DeleteUseCase;
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
class DriverGetBasedOnRequestedWeightUseCaseTest {

    @Mock
    DriversGateway gateway;
    DriverGetBasedOnRequestedWeightUseCase useCase;

    @BeforeEach
    void init(){ useCase = new DriverGetBasedOnRequestedWeightUseCase(gateway);}

    @Test
    @DisplayName("DriverGetBasedOnRequestedWeightUseCase")
    void apply() {
        String id = "testId";
        Double requestedWeight = 20.0;
        Driver driver1 = new Driver();
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setType(VehicleType.TRUCK);
        driver1.setVehicle(vehicle1);
        Driver driver2 = new Driver();
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setType(VehicleType.TRUCK);
        driver2.setVehicle(vehicle2);

        Mockito.when(gateway.getDriversBasedOnRequestedWeight(requestedWeight)).thenReturn(Flux.just(driver1, driver2));

        var response = useCase.apply(requestedWeight);

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(gateway).getDriversBasedOnRequestedWeight(requestedWeight);
    }

}