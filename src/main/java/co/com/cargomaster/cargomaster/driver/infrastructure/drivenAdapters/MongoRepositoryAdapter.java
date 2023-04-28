package co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters.data.DriverData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Driver with id " + id + " was not found")))
                .map(driverData -> mapper.map(driverData, Driver.class));
    }

    @Override
    public Mono<Driver> saveDriver(Driver driver) {
        generateUsername(driver);
        return repository.findDriverByEmail(driver.getEmail())
                .flatMap(existingUser -> Mono.<Driver>error(new RuntimeException("Driver with email " + driver.getEmail() + " already exists")))
                .switchIfEmpty(repository.findDriverByDni(driver.getDni())
                        .flatMap(existingUser -> Mono.<Driver>error(new RuntimeException("Driver with dni " + driver.getDni() + " already exists")))
                        .switchIfEmpty(repository.findDriverByVehiclePlate(driver.getVehicle().getPlate()))
                            .flatMap(existingUser -> Mono.<Driver>error(new RuntimeException("Vehicle with plate " + driver.getVehicle().getPlate() + " already exists")))
                            .switchIfEmpty(repository.save(mapper.map(driver.vehicleWeight(driver.getVehicle()), DriverData.class))
                                    .map(driverData -> mapper.map(driverData, Driver.class)))
                );
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
        return repository.findDriverByVehiclePlate(driver.plateToUpperCase())
                            .flatMap(existingUser -> Mono.<Driver>error(new RuntimeException("Vehicle with plate " + driver.getVehicle().getPlate() + " already exists")))
                .switchIfEmpty(repository.save(mapper.map(driver.setId(id).vehicleWeight(driver.getVehicle()), DriverData.class))
                        .map(driverData -> mapper.map(driverData, Driver.class)));
    }


    @Override
    public Mono<Driver> getDriverByEmail(String email) {
        return repository
                .findDriverByEmail(email)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Driver with email " + email + " was not found")))
                .map(driverData -> mapper.map(driverData, Driver.class));
    }

    public Mono<Driver> updateVehicleCapacityOnAcceptedTicket(String id, Double weightRequested) {
        return repository
                .findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("driver with id: " + id + " was not found")))
                .flatMap(driverData -> repository.save(driverData.vehicleCapacityOnAcceptedTicket(weightRequested)))
                .map(driverData -> mapper.map(driverData, Driver.class));
    }

    @Override
    public Mono<Driver> updateVehicleCapacityOnDeliveredTicket(String id, Double weightDelivered) {
        return repository
                .findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("driver with id: " + id + " was not found")))
                .flatMap(driverData -> repository.save(driverData.vehicleCapacityOnDeliveredTicket(weightDelivered)))
                .map(driverData -> mapper.map(driverData, Driver.class));
    }

    public void generateUsername(Driver driver) {

        String username = driver.getName().charAt(0) + driver.getLastName();
        int random = (int) (Math.random() * 100);
        username = username + "_" +random;


        while (repository.findDriverByUsername(username) == null) {
            random = (int) (Math.random() * 100);
            username = username + random;
        }

        driver.setUsername(username);
        driver.plateToUpperCase();

    }
    
     @Override
    public Flux<Driver> getDriversBasedOnRequestedWeight(Double weightRequested) {
        return repository.findAll()
                .filter(driverData -> driverData.getVehicle().getCurrentCapacity() + weightRequested <= driverData.getVehicle().getTotalCapacity())
                .switchIfEmpty(Flux.empty())
                .map(driverData -> mapper.map(driverData, Driver.class));
    }


}
