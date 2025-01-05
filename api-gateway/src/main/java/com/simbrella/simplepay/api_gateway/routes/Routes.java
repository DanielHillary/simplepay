package com.simbrella.simplepay.api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> userManagementServiceRoute(){
        return GatewayRouterFunctions.route("user_management")
                .route(RequestPredicates.path("/api/v1/user"), HandlerFunctions.http("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userManagementServiceSwaggerRoute(){
        return GatewayRouterFunctions.route("user_management_swagger")
                .route(RequestPredicates.path("/aggregate/user-management/v3/api-docs"), HandlerFunctions.http("http://localhost:8081"))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> transactionManagementServiceRoute(){
        return GatewayRouterFunctions.route("transaction_management")
                .route(RequestPredicates.path("/api/v1/transaction"), HandlerFunctions.http("http://localhost:8082"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> transactionManagementServiceSwaggerRoute(){
        return GatewayRouterFunctions.route("transaction_management_swagger")
                .route(RequestPredicates.path("/aggregate/transaction-management/v3/api-docs"), HandlerFunctions.http("http://localhost:8082"))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> loanManagementServiceRoute(){
        return GatewayRouterFunctions.route("loan_management")
                .route(RequestPredicates.path("/api/v1/loan"), HandlerFunctions.http("http://localhost:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> loanManagementServiceSwaggerRoute(){
        return GatewayRouterFunctions.route("loan_management_swagger")
                .route(RequestPredicates.path("/aggregate/loan-management/v3/api-docs"), HandlerFunctions.http("http://localhost:8080"))
                .filter(setPath("/api-docs"))
                .build();
    }
}
