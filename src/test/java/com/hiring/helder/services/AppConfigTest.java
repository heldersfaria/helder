package com.hiring.helder.services;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.hiring.helder.constants.StringConstants.DUMMY;

@TestPropertySource("classpath:application.properties")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class AppConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void whenDependentBeanNotAvailable_ThrowsNosuchBeanDefinitionException() {
        context.getBean(DUMMY);
    }

    @Test
    public void getSpotifyApiWithSuccess() {

        SpotifyApi spotifyApi = context.getBean(SpotifyApi.class);

        Assert.assertNotNull(spotifyApi);
        Assert.assertEquals(spotifyApi.getClass(), SpotifyApi.class);
        Assert.assertNotNull(spotifyApi.getClientId());
        Assert.assertNotNull(spotifyApi.getClientSecret());
    }

    @Test
    public void getClientCredentialsRequestSuccess() throws IOException, SpotifyWebApiException {
        ClientCredentialsRequest clientCredentialsRequest = context.getBean(ClientCredentialsRequest.class);

        Assert.assertNotNull(clientCredentialsRequest);
        Assert.assertNotNull(clientCredentialsRequest.execute().getAccessToken());
    }
}