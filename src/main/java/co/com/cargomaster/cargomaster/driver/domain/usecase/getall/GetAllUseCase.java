package co.com.cargomaster.cargomaster.driver.domain.usecase.getall;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllUseCase implements Supplier<Flux<Driver>>{

    private final DriversGateway gateway;


    @Override
    public Flux<Driver> get() {
        return gateway.getAllDrivers();
    }
}
