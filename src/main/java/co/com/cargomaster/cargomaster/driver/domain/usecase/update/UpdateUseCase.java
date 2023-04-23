package co.com.cargomaster.cargomaster.driver.domain.usecase.update;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateUseCase implements BiFunction<String, Driver, Mono<Driver>> {

    private final DriversGateway gateway;
    @Override
    public Mono<Driver> apply(String id, Driver driver) {
        return gateway.updateDriver(id, driver);
    }
}
