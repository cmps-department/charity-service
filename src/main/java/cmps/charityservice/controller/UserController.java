package cmps.charityservice.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @PutMapping("/{userId}")
    public UserRepresentation updateUser(JwtAuthenticationToken principal, @PathVariable String userId,
                                         @RequestBody UserRepresentation userRepresentation) {
        if (!Objects.equals(principal.getName(), userId)) {
            throw new AccessDeniedException("You can only update information about yourself");
        }

        UserResource user = usersResource.get(userId);
        user.update(userRepresentation);

        return userRepresentation;
    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(JwtAuthenticationToken principal, @PathVariable String userId,
                               @RequestBody String newPassword) {
        if (!Objects.equals(principal.getName(), userId)) {
            throw new AccessDeniedException("You can only change your own password");
        }

        UserResource user = usersResource.get(userId);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newPassword);
        credential.setTemporary(false);

        user.resetPassword(credential);
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<UserRepresentation> getAllUsers() {
        return usersResource.list();
    }

    @PostMapping("/{userId}/roles/admin")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignAdminRole(@PathVariable String userId) {
        UserResource user = usersResource.get(userId);
        RoleRepresentation adminRole = rolesResource.get("ROLE_ADMIN").toRepresentation();

        user.roles().realmLevel().add(Collections.singletonList(adminRole));
    }

    @DeleteMapping("/{userId}/roles/admin")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAdminRole(@PathVariable String userId) {
        UserResource user = usersResource.get(userId);
        RoleRepresentation adminRole = rolesResource.get("ROLE_ADMIN").toRepresentation();

        user.roles().realmLevel().remove(Collections.singletonList(adminRole));
    }
}
