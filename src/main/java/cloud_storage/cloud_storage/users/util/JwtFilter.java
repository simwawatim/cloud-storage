package cloud_storage.cloud_storage.users.util;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cloud_storage.cloud_storage.users.model.AppUser;
import cloud_storage.cloud_storage.users.repository.AppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AppUserRepository userRepo;

    public JwtFilter(JwtUtil jwtUtil, AppUserRepository userRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        System.out.println("=== JwtFilter ===");
        System.out.println("Authorization header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
                System.out.println("Username extracted from token: " + username);
            } catch (Exception e) {
                System.out.println("Invalid JWT token: " + e.getMessage());
            }
        } else {
            System.out.println("No Bearer token found in header.");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            AppUser user = userRepo.findByUsername(username);

            if (user == null) {
                System.out.println("No user found in DB for username: '" + username + "'");
            } else {
                System.out.println("User found in DB: " + user.getUsername());
            }

            if (user != null && jwtUtil.validateToken(token, user.getUsername())) {
                System.out.println("Token is valid. Setting authentication.");
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else if (user != null) {
                System.out.println("Token validation failed for user: " + user.getUsername());
            }
        }

        filterChain.doFilter(request, response);
    }
}
