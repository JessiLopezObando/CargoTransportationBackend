package co.com.cargomaster.cargomaster.driver.infrastructure.entryPoints;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.usecase.delete.DeleteUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.getall.GetAllUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.getbyemail.GetDriverByEmailUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.getbasedonrequestedweight.DriverGetBasedOnRequestedWeightUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.getbyid.GetByIdUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.save.SaveUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.update.DriverUpdateWeightOnAcceptedTicketUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.update.DriverUpdateWeightOnDeliveredTicketUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.update.UpdateUseCase;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    @RouterOperation(path = "/drivers", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllUseCase.class, method = RequestMethod.GET,
            beanMethod = "get",
            operation = @Operation(operationId = "getAllDriverss", tags = "Driver usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Ticket.class))),
                            @ApiResponse(responseCode = "204", description = "Nothing to show")
                    }))
    public RouterFunction<ServerResponse> getAllDrivers(GetAllUseCase useCase){
        return route(GET("/drivers"),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.get(), Driver.class))
                        .onErrorResume(error -> ServerResponse.noContent().build()));
    }

    @Bean
    @RouterOperation(path = "/drivers/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getDriverById", tags = "Driver usecases",
                    parameters = {
                            @Parameter(name = "id", description = "Driver ID", required = true, in = ParameterIn.PATH)
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Driver.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getDriverById(GetByIdUseCase useCase){
        return route(GET("/drivers/{id}"),
                request -> useCase.apply(request.pathVariable("id"))
                        .flatMap(driver -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(driver))
                        .onErrorResume(error -> ServerResponse.notFound().build()));
    }

    @Bean
    @RouterOperation(path = "/drivers", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = SaveUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveDriver", tags = "Driver usecases",
                    parameters = {
                            @Parameter(name = "driver", in = ParameterIn.PATH, schema = @Schema(implementation = Driver.class))
                    },
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success", content = @Content(schema = @Schema(implementation = Driver.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable. Try again")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Driver.class)))))
    public RouterFunction<ServerResponse> saveDriver(SaveUseCase useCase){
        return route(POST("/drivers").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Driver.class)
                        .flatMap(driver -> useCase.apply(driver)
                                .flatMap(driverSaved -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(driverSaved)))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage())));
    }

    @Bean
    @RouterOperation(path = "/drivers/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteDriverById", tags = "Driver usecases",
                    parameters = {
                            @Parameter(name = "id", description = "Driver ID", required = true, in = ParameterIn.PATH)
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Driver.class))),
                            @ApiResponse(responseCode = "404", description = "Driver Not Found")
                    }))
    public RouterFunction<ServerResponse> deleteDriver(DeleteUseCase useCase){
        return route(DELETE("/drivers/{id}"),
                request -> useCase.apply(request.pathVariable("id"))
                        .flatMap(msg -> ServerResponse.ok()
                                .bodyValue(msg + " deleted")
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))));
    }

    @Bean
    @RouterOperation(path = "/drivers/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateUseCase.class, method = RequestMethod.PUT, beanMethod = "apply",
            operation = @Operation(operationId = "updateDriver", tags = "Driver usecases",
                    parameters = {
                            @Parameter(name = "id", description = "Driver ID", required = true, in = ParameterIn.PATH),
                            @Parameter(name = "driver", in = ParameterIn.PATH, schema = @Schema(implementation = Driver.class))
                    },
                    responses = {
                            @ApiResponse(responseCode = "202", description = "Accepted", content = @Content(schema = @Schema(implementation = Driver.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable. Try again")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Driver.class)))))
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
    @RouterOperation(path = "/drivers/email/{email}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetDriverByEmailUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getDriverByEmail", tags = "Driver usecases",
                    parameters = {
                            @Parameter(name = "email", description = "Driver Email", required = true, in = ParameterIn.PATH)
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Driver.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getDriverByEmail(GetDriverByEmailUseCase useCase){
        return route(GET("/drivers/email/{email}"),
                request -> useCase.apply(request.pathVariable("email"))
                        .flatMap(driver -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(driver))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage())));
    }


    @Bean
    @RouterOperation(path = "/drivers/request/weight/{requestedWeight}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DriverGetBasedOnRequestedWeightUseCase.class, method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "DriverGetBasedOnRequestedWeight", tags = "Drivers usecases",
                    parameters = {
                            @Parameter(name = "requestedWeight", description = "Weight requested by Customer for deliver service", required = true, in = ParameterIn.PATH),
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Driver.class))),
                            @ApiResponse(responseCode = "204", description = "Nothing to show")
                    }))
    public RouterFunction<ServerResponse> getDriversBasedOnRequestedWeight (DriverGetBasedOnRequestedWeightUseCase driverGetBasedOnRequestedWeight) {
        return route(GET("/drivers/request/weight/{requestedWeight}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(driverGetBasedOnRequestedWeight.apply(Double.valueOf(request.pathVariable("requestedWeight"))), Driver.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    @RouterOperation(path = "/drivers/id/{id}/weight/{weight}/accepted", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DriverUpdateWeightOnAcceptedTicketUseCase.class, method = RequestMethod.PATCH,
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
        public RouterFunction<ServerResponse> patchDriverWeightAccepted(DriverUpdateWeightOnAcceptedTicketUseCase driverUpdateWeightUseCase){
            return route(PATCH("/drivers/id/{id}/weight/{weight}/accepted"),
                    request -> driverUpdateWeightUseCase.apply( request.pathVariable("id"), Double.valueOf(request.pathVariable("weight")))
                            .flatMap(item -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(item))
                            .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
            );
        }


    @Bean
    @RouterOperation(path = "/drivers/id/{id}/weight/{weight}/delivered", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = DriverUpdateWeightOnDeliveredTicketUseCase.class, method = RequestMethod.PATCH,
            beanMethod = "apply",
            operation = @Operation(operationId = "DriverUpdateWeightOnDeliveredTicket", tags = "Drivers usecases",
                    parameters = {
                            @Parameter(name = "id", description = "Driver ID", required = true, in = ParameterIn.PATH),
                            @Parameter(name = "weight", description = " weight delivered", required = true, in = ParameterIn.PATH)
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Ticket.class))),
                            @ApiResponse(responseCode = "204", description = "Nothing to show")
                    }))
    public RouterFunction<ServerResponse> patchDriverWeightDelivered(DriverUpdateWeightOnDeliveredTicketUseCase driverUpdateWeightOnDeliveredTicketUseCase){
        return route(PATCH("/drivers/id/{id}/weight/{weight}/delivered"),
                request -> driverUpdateWeightOnDeliveredTicketUseCase.apply( request.pathVariable("id"), Double.valueOf(request.pathVariable("weight")))
                        .flatMap(item -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(item))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

}
