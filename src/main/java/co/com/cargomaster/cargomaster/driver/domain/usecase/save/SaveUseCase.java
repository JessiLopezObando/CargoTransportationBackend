package co.com.cargomaster.cargomaster.driver.domain.usecase.save;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class SaveUseCase implements Function<Driver, Mono<Driver>> {

    private final DriversGateway gateway;

    @Override
    public Mono<Driver> apply(Driver driver) {
        return gateway.saveDriver(driver);
    }
}
