package hr.tvz.zavrsni.service.implementation;



import hr.tvz.zavrsni.service.OAuth2Service;
import hr.tvz.zavrsni.model.AppUser;
import hr.tvz.zavrsni.model.enumeration.Role;
import hr.tvz.zavrsni.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;



@Service
public class OAuth2ServiceImpl implements OAuth2Service, OAuth2UserService<OidcUserRequest, OidcUser>{

    private final UserRepository userRepository;


    public OAuth2ServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Transactional
    public AppUser processOAuthPostLogin(String email, String name) {

        AppUser existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            return existingUser;
        }

            AppUser newUser = new AppUser();
            newUser.setUsername(name);
            newUser.setEmail(email);
            newUser.setRole(Role.ROLE_UNDECIDED);
        return userRepository.save(newUser);

    }

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser oidcUser = new DefaultOidcUser(
                Collections.emptyList(),
                userRequest.getIdToken()
        );


        String email = (String) oidcUser.getAttribute("email");
        String name = (String) oidcUser.getAttribute("name");

        AppUser appUser = processOAuthPostLogin(email, name);


        GrantedAuthority authority = new SimpleGrantedAuthority(appUser.getRole().name());


        return new DefaultOidcUser((Collection<? extends GrantedAuthority>) Collections.singletonList(authority), userRequest.getIdToken(), "email");

    }


}