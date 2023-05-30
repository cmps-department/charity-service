package cmps.charityservice.controller;

import cmps.charityservice.model.Application;
import cmps.charityservice.model.Category;
import cmps.charityservice.model.Status;
import cmps.charityservice.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/applications")
@CrossOrigin
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationRepository applicationRepository;

    @GetMapping
    public List<Application> getAll(JwtAuthenticationToken principal,
                                    @RequestParam(required = false) Status status,
                                    @RequestParam(required = false) Category category,
                                    @RequestParam(required = false) String authorId) {

        if (Objects.isNull(principal)) {
            return applicationRepository.findAllByFilters(Status.APPROVED, category, authorId);
        }

        boolean isAdmin = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> Objects.equals(authority, "ROLE_ADMIN"));

        boolean isMyOwn = Objects.equals(principal.getName(), authorId);

        Status searchStatus = isAdmin | isMyOwn ? status : Status.APPROVED;

        return applicationRepository.findAllByFilters(searchStatus, category, authorId);
    }

    @PostMapping
    public Application createApplication(JwtAuthenticationToken principal,
                                         @RequestBody Application application) {

        application.setAuthorId(principal.getName());
        application.setStatus(Status.PENDING);
        application.setCategory(application.getCategory());

        return applicationRepository.save(application);
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public Application updateApplication(@PathVariable String id,
                                         @RequestBody Application application) {
        application.setId(id);

        return applicationRepository.save(application);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Transactional
    public void deleteApplication(@PathVariable String id) {
        applicationRepository.deleteById(id);
    }
}
