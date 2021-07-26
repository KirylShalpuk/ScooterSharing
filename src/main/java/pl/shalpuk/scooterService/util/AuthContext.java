package pl.shalpuk.scooterService.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.shalpuk.scooterService.model.User;

public class AuthContext {

    public static void setAuthContext(Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static User getAuthContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
