package com.jusethag.market.web.security.filter;

import com.jusethag.market.domain.service.MarketUserDetailService;
import com.jusethag.market.web.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    private static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    private static final String HEADER_PREFIX_VALUE = "Bearer";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MarketUserDetailService marketUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION_KEY);

        if (this.isAValidAuthorizationHeader(authorizationHeader)) {

            String jwt = this.getJwtFromAuthorizationHeader(authorizationHeader);
            String username = this.jwtUtil.getUsername(jwt);

            if (!this.isUserAuthenticated(username)) {

                UserDetails userDetails = this.marketUserDetailService.loadUserByUsername(username);

                if (this.jwtUtil.validateToken(jwt, userDetails)) {
                    this.authenticateUser(userDetails, request);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAValidAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(HEADER_PREFIX_VALUE);
    }

    private String getJwtFromAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader.substring(HEADER_PREFIX_VALUE.length() + 1);
    }

    private boolean isUserAuthenticated(String username) {
        return username != null && SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private void authenticateUser(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null, userDetails.getAuthorities()
        );

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
