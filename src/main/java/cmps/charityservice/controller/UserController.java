package cmps.charityservice.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final RolesResource rolesResource;
    private final UsersResource usersResource;

    @GetMapping("/{userId}")
    public UserRepresentation getUserById(@PathVariable String userId) {
        return usersResource.get(userId).toRepresentation();
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<UserRepresentation> getUsers() {
        return usersResource.list();
    }

    @PostMapping("/{userId}/roles/admin")
    @Secured("ROLE_ADMIN")
    public void assignRole(@PathVariable String userId) {
        UserResource user = usersResource.get(userId);
        RoleRepresentation adminRole = rolesResource.get("ROLE_ADMIN").toRepresentation();

        user.roles().realmLevel().add(Collections.singletonList(adminRole));
    }

    @DeleteMapping("/{userId}/roles/admin")
    @Secured("ROLE_ADMIN")
    public void removeRole(@PathVariable String userId) {
        UserResource user = usersResource.get(userId);
        RoleRepresentation adminRole = rolesResource.get("ROLE_ADMIN").toRepresentation();

        user.roles().realmLevel().remove(Collections.singletonList(adminRole));
    }

    @PutMapping("/{userId}/password")
    public void changePassword(@PathVariable String userId, @RequestBody String newPassword) {
        UserResource user = usersResource.get(userId);
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newPassword);
        credential.setTemporary(false);
        user.resetPassword(credential);
    }

    @PutMapping("/{userId}/email")
    public void changeEmail(@PathVariable String userId, @RequestBody String newEmail) {
        UserResource user = usersResource.get(userId);
        UserRepresentation userRepresentation = user.toRepresentation();
        userRepresentation.setEmail(newEmail);
        user.update(userRepresentation);
    }
}
