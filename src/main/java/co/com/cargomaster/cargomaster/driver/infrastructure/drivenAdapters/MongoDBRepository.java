package co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters.data.DriverData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MongoDBRepository extends ReactiveMongoRepository<DriverData, String> {

    Mono<Driver> findDriverByEmail(String email);

    Mono<Driver> findDriverByDni(String dni);
    Flux<Driver> findDriverByUsername(String username);

    Mono<Driver> findDriverByVehiclePlate(String vehiclePlate);


}
