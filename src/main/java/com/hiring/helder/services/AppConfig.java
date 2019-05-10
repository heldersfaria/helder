package com.hiring.helder.services;

import com.hiring.helder.exceptions.SpotifyApiException;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class AppConfig {

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.clientSecret}")
    private String clientSecret;

    @Bean
    @Qualifier("spotifyApi")
    public SpotifyApi spotifyApi() {
        return new SpotifyApi.Builder().setClientId(clientId).setClientSecret(clientSecret).build();
    }

    @Bean
    @DependsOn("spotifyApi")
    @Qualifier("clientCredentialsRequest")
    public ClientCredentialsRequest clientCredentialsRequest() throws SpotifyApiException {
        return spotifyApi().clientCredentials().build();
    }
}
