package co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters.data.DriverData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapter implements DriversGateway {

    private final MongoDBRepository repository;
    private final ObjectMapper mapper;

    @Override
    public Flux<Driver> getAllDrivers() {
        return repository.findAll()
                .switchIfEmpty(Flux.empty())
                .map(driverData -> mapper.map(driverData, Driver.class));
    }

    @Override
    public Mono<Driver> getDriverById(String id) {
        return repository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .map(driverData -> mapper.map(driverData, Driver.class));
    }

    @Override
    public Mono<Driver> saveDriver(Driver driver) {
        return repository
                .save(mapper.map(driver.generateUsername(), DriverData.class))
                .switchIfEmpty(Mono.empty())
                .map(driverData -> mapper.map(driverData, Driver.class));
    }

    @Override
    public Mono<String> deleteDriver(String id) {
        return repository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(driverData -> repository.deleteById(id).thenReturn(id));
    }

    @Override
    public Mono<Driver> updateDriver(String id, Driver driver) {
        return repository
                .findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(driverData -> {
                    driver.setId(id);
                    return repository.save(mapper.map(driver, DriverData.class));
                })
                .map(driverData -> mapper.map(driverData, Driver.class));
    }

    @Override
    public Mono<Driver> updateVehicleCapacity(String id, Double weightRequested) {
        return repository
                .findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("driver with id: " + id + " was not found")))
                .flatMap(driverData -> {
                        return repository.save(driverData.updateVehicleCapacity(weightRequested));
                })
                .map(driverData -> mapper.map(driverData, Driver.class));


    }


}
