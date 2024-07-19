package com.gohealth.bugtracker.services;

import com.gohealth.bugtracker.BugtrackerApplication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CredentialsServiceTests {

    @Mock
    private CredentialsService credentialsService;

    @Test
    public void getCredentials_doesNotReturnCredential() throws IOException {
        credentialsService = mock(CredentialsService.class);

        when(credentialsService.getCredentials(any())).thenReturn(null);
        Credential credential = credentialsService.getCredentials(any());
        assertNull(credential);
    }

    @Test
    public void getCredentials_doesReturnCredential() throws IOException {
        credentialsService = mock(CredentialsService.class);
        Credential credentialResponse = mock(Credential.class);

        when(credentialsService.getCredentials(any())).thenReturn(credentialResponse);
        Credential credential = credentialsService.getCredentials(any());
        assertNotNull(credential);
    }

}
