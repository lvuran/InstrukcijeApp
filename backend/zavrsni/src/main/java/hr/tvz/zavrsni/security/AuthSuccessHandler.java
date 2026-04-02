package hr.tvz.zavrsni.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthSuccessHandler  implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException, IOException {

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("http://localhost:3000/homeAdmin");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEACHER"))) {
            response.sendRedirect("http://localhost:3000/teacherHome");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
            response.sendRedirect("http://localhost:3000/home");
        } else {
            response.sendRedirect("http://localhost:3000/role");
        }
    }
}
