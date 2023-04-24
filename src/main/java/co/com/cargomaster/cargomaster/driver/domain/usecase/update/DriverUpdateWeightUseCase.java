package co.com.cargomaster.cargomaster.driver.domain.usecase.update;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class DriverUpdateWeightUseCase implements BiFunction<String, Double, Mono<Driver>> {

    private final DriversGateway gateway;


    @Override
    public Mono<Driver> apply(String id, Double requestedWeight) {
        return this.gateway.updateVehicleCapacity(id, requestedWeight);
    }
}
