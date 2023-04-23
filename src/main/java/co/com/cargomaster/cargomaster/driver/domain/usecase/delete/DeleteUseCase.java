package co.com.cargomaster.cargomaster.driver.domain.usecase.delete;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.model.gateway.DriversGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteUseCase implements Function<String, Mono<String>> {

    private final DriversGateway gateway;

    @Override
    public Mono<String> apply(String id) {
        return gateway.deleteDriver(id);
    }
}
