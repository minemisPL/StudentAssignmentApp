package me.minemis.studentasigmentsapp.filter;

import me.minemis.studentasigmentsapp.repository.UserRepository;
import me.minemis.studentasigmentsapp.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    public JwtFilter(UserRepository userRepo, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        String username = jwtUtil.getUsernameFromToken(token);

        UserDetails userDetails = userRepo
                .findByUsername(username)
                .orElse(null);

        if (!jwtUtil.validateToken(token, userDetails)) {
            chain.doFilter(request, response);
            return;
        }

        Collection userAuthorities = userDetails == null
                ? Collections.emptyList()
                : userDetails.getAuthorities();


        UsernamePasswordAuthenticationToken passwordAuth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userAuthorities
        );

        passwordAuth.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        //Here the user is actually being logged in
        SecurityContextHolder.getContext().setAuthentication(passwordAuth);

        chain.doFilter(request, response);
    }
}
