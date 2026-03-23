package com.elearning.demo.Config;

import com.elearning.demo.Model.Users;
import com.elearning.demo.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
@Component
public class UserSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userService;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        HttpSession session = request.getSession();
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        String username = authentication.getName();
        userService.getUserByUsername(username).ifPresent(user -> {
            session.setAttribute("fullName", user.getUsername());
            session.setAttribute("id", user.getId());
        });

        response.sendRedirect("/home-page");
    }

}
