package co.com.cargomaster.cargomaster.driver.domain.usecase.getbasedonrequestedweight;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@RequiredArgsConstructor
public class DriverGetBasedOnRequestedWeightUseCase implements Function<Double, Flux<Driver>> {

    private final DriversGateway gateway;

    @Override
    public Flux<Driver> apply(Double requestedWeight) {
        return gateway.getDriversBasedOnRequestedWeight(requestedWeight);
    }
}
