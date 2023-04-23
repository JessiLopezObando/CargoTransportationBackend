package co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters;

import co.com.cargomaster.cargomaster.driver.infrastructure.drivenAdapters.data.DriverData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoDBRepository extends ReactiveMongoRepository<DriverData, String> {
}
