package cmps.charityservice;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/")
public class SampleController {

    @GetMapping
    public Map<String, Object> currentUser(JwtAuthenticationToken principal) {
        return principal.getTokenAttributes();
    }
}
