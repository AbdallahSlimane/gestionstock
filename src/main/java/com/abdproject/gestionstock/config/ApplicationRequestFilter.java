package com.abdproject.gestionstock.config;

import com.abdproject.gestionstock.services.auth.ApplicationUserDetailsService;
import com.abdproject.gestionstock.utils.JwtUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApplicationRequestFilter extends OncePerRequestFilter {

    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    // Cette méthode à pour but d'intercepter les requetes
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // essayer de get le Header
        final  String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        String idEntreprise = null;

        // On verifie si l'authHeader n'est pas null
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // prendre le header
            jwt = authHeader.substring(7);
            // extraire le username
            username = jwtUtils.extractUsername(jwt);
            // extraitre l'idEntreprise
            idEntreprise = jwtUtils.extractIdEntreprise(jwt);
        }

        // Je verifie Si j'ai un utilisateur
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Je le recupere avec UserDetails
            UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(username);
            // je verifie si le token est valide pour cetrte utilisateur
            if (jwtUtils.validateToken(jwt, userDetails)) {
                // Je créer un objet de type usernamepassword etc
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                // Je lui donne les Details avec setDetails
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // je recupere le context et je set l'authentification
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // donne la possibilité de stocké des objet dans ce cas la l'idEntreprise
        MDC.put("idEntreprise", idEntreprise);

        filterChain.doFilter(request, response);


    }
}
