package com.simbrella.simplepay.user_management.config;


import com.simbrella.simplepay.user_management.client.GatewayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Value("${gatewayclient-url}")
    private String userClientUrl;

    @Bean
    public GatewayClient gatewayClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(userClientUrl)
                .build();

        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(GatewayClient.class);
    }

}
