package co.com.cargomaster.cargomaster.ticket.infrastructure.entryPoints;

import co.com.cargomaster.cargomaster.ticket.domain.model.ticket.Ticket;
import co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketdelete.TicketDeleteUseCase;
import co.com.cargomaster.cargomaster.ticket.domain.usecases.ticket.ticketfindbydriveridandstatus.TicketgetByDriverIdAndStatusUseCase;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterTicket {

    @Bean
    @RouterOperation(path = "/tickets", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = TicketGetAllUseCase.class, method = RequestMethod.GET,
            beanMethod = "get",
            operation = @Operation(operationId = "getAllTickets", tags = "Tickets usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Ticket.class))),
                            @ApiResponse(responseCode = "204", description = "Nothing to show")
                    }))
    public RouterFunction<ServerResponse> getAllTickets (TicketGetAllUseCase ticketGetAllUseCase){
        return route(GET("/tickets"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(ticketGetAllUseCase.get(), Ticket.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    @RouterOperation(path = "/tickets/{driverId}/{status}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = TicketgetByDriverIdAndStatusUseCase.class, method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getTicketByDriverIdAndStatus", tags = "Tickets usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Ticket.class))),
                            @ApiResponse(responseCode = "204", description = "Nothing to show")
                    }))
    public RouterFunction<ServerResponse> getTicketsByDriverIdAndStatus (TicketgetByDriverIdAndStatusUseCase ticketgetByDriverIdAndStatus) {
        return route(GET("/tickets/driverId/{driverId}/status/{status}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(ticketgetByDriverIdAndStatus.apply(request.pathVariable("driverId"), request.pathVariable("status")), Ticket.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    @RouterOperation(path = "/tickets/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = TicketGetByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getTicketById", tags = "Tickets usecases",
                    parameters = {
                            @Parameter(name = "id", description = "Ticket ID", required = true, in = ParameterIn.PATH)
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Ticket.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getTicketById(TicketGetByIdUseCase ticketGetByIdUseCase){
        return route(GET("tickets/{id}"),
                request -> ticketGetByIdUseCase.apply( request.pathVariable("id"))
                        .flatMap(item -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(item))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

    @Bean
    @RouterOperation(path = "/tickets", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = TicketSaveUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "saveTicket", tags = "Tickets usecases",
                    parameters = {
                            @Parameter(name = "item", in = ParameterIn.PATH, schema = @Schema(implementation = Ticket.class))
                    },
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success", content = @Content(schema = @Schema(implementation = Ticket.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable. Try again")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Ticket.class)))))

    public RouterFunction<ServerResponse> saveTicket (TicketSaveUseCase ticketSaveUseCase){
        return route(POST("/tickets").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Ticket.class)
                        .flatMap(ticket -> ticketSaveUseCase.apply(ticket)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(throwable.getMessage())))
        );
    }

    @Bean
    @RouterOperation(path = "/tickets/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = TicketUpdateUseCase.class, method = RequestMethod.PUT, beanMethod = "apply",
            operation = @Operation(operationId = "updateTicket", tags = "Tickets usecases",
                    parameters = {
                            @Parameter(name = "id", description = "Ticket ID", required = true, in = ParameterIn.PATH),
                            @Parameter(name = "item", in = ParameterIn.PATH, schema = @Schema(implementation = Ticket.class))
                    },
                    responses = {
                            @ApiResponse(responseCode = "202", description = "Accepted", content = @Content(schema = @Schema(implementation = Ticket.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable. Try again")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Ticket.class)))))
    public RouterFunction<ServerResponse> updateTicket(TicketUpdateUseCase ticketUpdateUseCase){
        return route(PUT("/tickets/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Ticket.class)
                        .flatMap(ticket -> ticketUpdateUseCase.apply(request.pathVariable("id"),
                                        ticket)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                        .bodyValue(throwable.getMessage()))
                        )
        );
    }

    @Bean
    @RouterOperation(path = "/tickets/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = TicketDeleteUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteTicketById", tags = "Tickets usecases",
                    parameters = {
                            @Parameter(name = "id", description = "Ticket ID", required = true, in = ParameterIn.PATH)
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content (schema = @Schema(implementation = Ticket.class))),
                            @ApiResponse(responseCode = "404", description = "Ticket Not Found")
                    }))
    public RouterFunction<ServerResponse> deleteTicket (TicketDeleteUseCase ticketDeleteUseCase){
        return route(DELETE("/tickets/{id}"),
                request -> ticketDeleteUseCase.apply(request.pathVariable("id"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(""))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(throwable.getMessage()))
        );
    }

}
