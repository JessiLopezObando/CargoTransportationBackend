package co.com.cargomaster.cargomaster.driver.infrastructure.entryPoints;

import co.com.cargomaster.cargomaster.driver.domain.model.Driver;
import co.com.cargomaster.cargomaster.driver.domain.usecase.delete.DeleteUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.getall.GetAllUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.getbyid.GetByIdUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.save.SaveUseCase;
import co.com.cargomaster.cargomaster.driver.domain.usecase.update.UpdateUseCase;
import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketdelete.TicketDeleteUseCase;
import co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketgetall.TicketGetAllUseCase;
import co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketgetbyid.TicketGetByIdUseCase;
import co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketsave.TicketSaveUseCase;
import co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketupdate.TicketUpdateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .onErrorResume(error -> ServerResponse.badRequest().build()));
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
                        .onErrorResume(error -> ServerResponse.notFound().build())));
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
}
