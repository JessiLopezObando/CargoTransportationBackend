package co.com.cargomaster.cargomaster.driver.domain.usecase.getbyid;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetByIdUseCase implements Function<String, Mono<Driver>> {

    private final DriversGateway gateway;

    @Override
    public Mono<Driver> apply(String id) {
        return gateway.getDriverById(id);
    }
}

