package hr.tvz.zavrsni.service;

import hr.tvz.zavrsni.model.AppUser;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public interface OAuth2Service  {

    public AppUser processOAuthPostLogin(String email, String name);
    public OidcUser loadUser(OidcUserRequest userRequest);
}