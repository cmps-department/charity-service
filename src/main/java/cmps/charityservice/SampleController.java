package cmps.charityservice;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")

public class SampleController {
    @GetMapping
    public Map<String, Object> currentUser(OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal){
        // after authorization through Google should show a list of properties, received from Map
        return oAuth2AuthenticatedPrincipal.getAttributes();
    }
}
