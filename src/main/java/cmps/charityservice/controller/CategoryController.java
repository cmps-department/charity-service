package cmps.charityservice.controller;

import cmps.charityservice.model.Application;
import cmps.charityservice.model.Category;
import cmps.charityservice.model.Status;
import cmps.charityservice.repository.ApplicationRepository;
import cmps.charityservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/categories")
@CrossOrigin
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ApplicationRepository applicationRepository;

    @PostMapping
    public Category createCategory(JwtAuthenticationToken principal,
                                   @RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PostMapping("/{id}")
    public Category updateCategory(@PathVariable Long id,
                                   @RequestBody Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    @GetMapping("/{id}/applications")
    public List<Application> getApplicationsByCategoryId(@PathVariable Long id,
                                                         @RequestParam(required = false, defaultValue = "PENDING") Status status,
                                                         @RequestParam(required = false, defaultValue = "0") int page,
                                                         @RequestParam(required = false, defaultValue = "50") int size) {

        Status search = Status.APPROVED;

        return categoryRepository.findAllByCategoryName(id, search, PageRequest.of(page, size));
    }
}
