package co.com.cargomaster.cargomaster.driver.infrastructure.entryPoints;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.usecase.delete.DeleteUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.getall.GetAllUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.getbyid.GetByIdUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.save.SaveUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.update.UpdateUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@CrossOrigin(origins = "*")
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> getAllDrivers(GetAllUseCase useCase){
        return route(GET("/drivers"),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), Driver.class))
                        .onErrorResume(error -> ServerResponse.noContent().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> getDriverById(GetByIdUseCase useCase){
        return route(GET("/drivers/{id}"),
                request -> useCase.apply(request.pathVariable("id"))
                        .flatMap(driver -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(driver))
                        .onErrorResume(error -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> saveDriver(SaveUseCase useCase){
        return route(POST("/drivers").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Driver.class)
                        .flatMap(driver -> useCase.apply(driver)
                                .flatMap(driverSaved -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(driverSaved)))
                        .onErrorResume(error -> ServerResponse.badRequest().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteDriver(DeleteUseCase useCase){
        return route(DELETE("/drivers/{id}"),
                request -> useCase.apply(request.pathVariable("id"))
                        .flatMap(msg -> ServerResponse.ok()
                                .bodyValue(msg + " deleted")
                        .onErrorResume(error -> ServerResponse.notFound().build())));
    }

    @Bean
    public RouterFunction<ServerResponse> updateDriver(UpdateUseCase useCase){
        return route(PUT("/drivers/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Driver.class)
                        .flatMap(driver -> useCase.apply(request.pathVariable("id"), driver)
                                .flatMap(driverUpdated -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(driverUpdated)))
                        .onErrorResume(error -> ServerResponse.badRequest().build()));
    }
}
