package com.albanj.capitalize.capitalizeback.security.filter;

import com.albanj.capitalize.capitalizeback.log.LogMessageBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.albanj.capitalize.capitalizeback.security.SecurityConstants.*;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        long start = System.currentTimeMillis();
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("REQUEST START {} - {}", LogMessageBuilder.buildHeader(authentication), req.getRequestURL());
        chain.doFilter(req, res);
        long end = System.currentTimeMillis();
        log.info("REQUEST END {} - {} - HTTP={} - {}ms", LogMessageBuilder.buildHeader(authentication),
                req.getRequestURI(), res.getStatus(), end - start);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String token = req.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
                    .verify(token.replace(TOKEN_PREFIX, "")).getSubject();

            if (username != null) {
                UserDetails user = userDetailsService.loadUserByUsername(username);
                return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                        user.getAuthorities());
            }
            return null;
        }
        return null;
    }

}
