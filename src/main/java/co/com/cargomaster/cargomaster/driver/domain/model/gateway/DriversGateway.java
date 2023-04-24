package co.com.cargomaster.cargomaster.driver.domain.model.gateway;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DriversGateway {

    Flux<Driver> getAllDrivers();
    Mono<Driver> getDriverById(String id);
    Mono<Driver> saveDriver(Driver driver);
    Mono<String> deleteDriver(String id);
    Mono<Driver> updateDriver(String id, Driver driver);

    Mono<Driver> updateVehicleCapacity(String id, Double weightRequested);
}
