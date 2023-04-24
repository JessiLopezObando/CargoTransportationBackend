package co.com.cargomaster.cargomaster.driver.infrastructure.entryPoints;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.usecase.delete.DeleteUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.getall.GetAllUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.getbyid.GetByIdUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.save.SaveUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.update.DriverUpdateWeightUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.update.UpdateUseCase;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
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


    @Bean
    @RouterOperation(path = "/drivers/id/{id}/weight/{weight}/accepted", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DriverUpdateWeightUseCase.class, method = RequestMethod.PATCH,
            beanMethod = "apply",
            operation = @Operation(operationId = "DriverUpdateWeight", tags = "Drivers usecases",
                    parameters = {
                            @Parameter(name = "id", description = "Driver ID", required = true, in = ParameterIn.PATH),
                            @Parameter(name = "weight", description = "requested weight", required = true, in = ParameterIn.PATH)
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Ticket.class))),
                            @ApiResponse(responseCode = "204", description = "Nothing to show")
                    }))
        public RouterFunction<ServerResponse> patchDriverWeightAccepted(DriverUpdateWeightUseCase driverUpdateWeightUseCase){
            return route(PATCH("/drivers/id/{id}/weight/{weight}/accepted"),
                    request -> driverUpdateWeightUseCase.apply( request.pathVariable("id"), Double.valueOf(request.pathVariable("weight")))
                            .flatMap(item -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(item))
                            .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
            );
        }
}
