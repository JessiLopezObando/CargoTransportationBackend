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
class UpdateUseCaseTest {

    @Mock
    DriversGateway gateway;
    UpdateUseCase useCase;

    @BeforeEach
    void init(){ useCase = new UpdateUseCase(gateway);}

    @Test
    @DisplayName("UpdateUseCase")
    void update() {

        Driver driver1 = new Driver();
        driver1.setId("testId");
        driver1.setEmail("testEmail");
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setType(VehicleType.TRUCK);
        driver1.setVehicle(vehicle1);

        Driver driverUpdated = new Driver();
        driver1.setId("testId");
        driver1.setEmail("testEmailUpdated");
        Vehicle vehicle2 = new Vehicle();
        vehicle1.setType(VehicleType.VAN);
        driver1.setVehicle(vehicle2);

        Mockito.when(gateway.updateDriver(driver1.getId(),driverUpdated)).thenReturn(Mono.just(driverUpdated));

        var response = useCase.apply(driver1.getId(),driverUpdated);

        StepVerifier.create(response)
                .expectNext(driverUpdated)
                .verifyComplete();

        Mockito.verify(gateway).updateDriver(driver1.getId(), driverUpdated);

    }

}