package me.minemis.studentasigmentsapp.filter;

import me.minemis.studentasigmentsapp.domain.User;
import me.minemis.studentasigmentsapp.repository.UserRepository;
import me.minemis.studentasigmentsapp.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    public JwtFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepo = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();

        if (!jwtUtil.validateToken(token, ))
    }
}
