package co.com.cargomaster.cargomaster.driver.domain.usecase.getbyid;

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
class GetByIdUseCaseTest {

    @Mock
    DriversGateway gateway;
    GetByIdUseCase useCase;

    @BeforeEach
    void init(){ useCase = new GetByIdUseCase(gateway);}

    @Test
    @DisplayName("GetByIdUseCase")
    void get() {
        Driver driver1 = new Driver();
        driver1.setId("testId");
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setType(VehicleType.TRUCK);
        driver1.setVehicle(vehicle1);

        Mockito.when(gateway.getDriverById(driver1.getId())).thenReturn(Mono.just(driver1));

        var response = useCase.apply(driver1.getId());

        StepVerifier.create(response)
                .expectNext(driver1)
                .verifyComplete();

        Mockito.verify(gateway).getDriverById("testId");
    }

}