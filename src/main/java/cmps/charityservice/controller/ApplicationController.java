package cmps.charityservice.controller;

import cmps.charityservice.model.Application;
import cmps.charityservice.model.Category;
import cmps.charityservice.model.Status;
import cmps.charityservice.repository.ApplicationRepository;
import cmps.charityservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Application> getAll(JwtAuthenticationToken principal,
                                    @RequestParam(required = false, defaultValue = "PENDING") Status status,
                                    @RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam(required = false, defaultValue = "50") int size) {

        boolean isAdmin = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> Objects.equals(authority, "ROLE_ADMIN"));

        Status search = isAdmin ? status : Status.APPROVED;

        return applicationRepository.findAllByStatus(search, PageRequest.of(page, size));
    }

    @PostMapping
    public Application createApplication(JwtAuthenticationToken principal,
                                         @RequestBody Application application) {

        application.setAuthorId(principal.getName());
        application.setStatus(Status.PENDING);

        return applicationRepository.save(application);
    }

    @PostMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public Application updateApplication(@PathVariable String id,
                                         @RequestBody Application application) {
        application.setId(id);

        return applicationRepository.save(application);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Transactional
    public void createApplication(@PathVariable String id) {
        applicationRepository.deleteById(id);
    }

    @GetMapping("/{id}/category")
    public Category getCategory(@PathVariable String id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid application ID"));

        return application.getCategory();
    }
}
