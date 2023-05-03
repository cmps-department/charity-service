package cmps.charityservice.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
@Secured("ROLE_ADMIN")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final RolesResource rolesResource;
    private final UsersResource usersResource;

    @GetMapping
    public List<UserRepresentation> getUsers() {
        return usersResource.list();
    }

    @PostMapping("/{userId}/roles/admin")
    public void assignRole(@PathVariable String userId) {
        UserResource user = usersResource.get(userId);
        RoleRepresentation adminRole = rolesResource.get("ROLE_ADMIN").toRepresentation();

        user.roles().realmLevel().add(Collections.singletonList(adminRole));
    }

    @DeleteMapping("/{userId}/roles/admin")
    public void removeRole(@PathVariable String userId) {
        UserResource user = usersResource.get(userId);
        RoleRepresentation adminRole = rolesResource.get("ROLE_ADMIN").toRepresentation();

        user.roles().realmLevel().remove(Collections.singletonList(adminRole));
    }
}
