package cmps.charityservice.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Bean
    public Keycloak keycloak(@Value("${keycloak.auth-server-url}") String serverUrl,
                             @Value("${keycloak.realm}") String realm,
                             @Value("${keycloak.client.id}") String clientId,
                             @Value("${keycloak.client.secret}") String clientSecret) {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope("openid")
                .grantType("client_credentials")
                .build();
    }

    @Bean
    public UsersResource usersResource(final Keycloak keycloak, @Value("${keycloak.realm}") String realm) {
        return keycloak.realm(realm).users();
    }

    @Bean
    public RolesResource rolesResource(final Keycloak keycloak, @Value("${keycloak.realm}") String realm) {
        return keycloak.realm(realm).roles();
    }
}