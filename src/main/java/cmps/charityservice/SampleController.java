package cmps.charityservice;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/")
public class SampleController {
    @Autowired
    private Keycloak keycloak;
    private UsersResource usersResource;

//    @GetMapping
    private String usersRealm = "usersRealm";
    public Map<String, Object> currentUser(JwtAuthenticationToken principal) {
        return principal.getTokenAttributes();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/makeUserAdmin")
    public void makeUserAdmin(@RequestParam String userId) {
        UserRepresentation user = usersResource.get(userId).toRepresentation();

        RealmResource realmResource = keycloak.realm(usersRealm);
        RoleRepresentation adminRole = realmResource.roles().get("admin").toRepresentation();
        usersResource.get(userId).roles().realmLevel().add(Collections.singletonList(adminRole));
    }
}
