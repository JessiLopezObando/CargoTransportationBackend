package co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters.data.DriverData;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface MongoDBRepository extends ReactiveMongoRepository<DriverData, String> {

    Mono<Driver> findDriverByEmail(String email);

    @Query("{'vehicle.currentCapacity': {$lt: ?0}}, 'vehicle.totalCapacity': {$gt: ?0 + 'vehicle.currentCapacity'}}")
    Flux<DriverData> findByCurrentCapacityLessThanAndTotalCapacityGreaterThanCurrentCapacityPlusRequestedWeight(Double weightRequested);

}
