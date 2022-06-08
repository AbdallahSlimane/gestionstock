package com.abdproject.gestionstock.services.auth;


import com.abdproject.gestionstock.dto.UtilisateurDto;
import com.abdproject.gestionstock.model.auth.ExtendedUser;
import com.abdproject.gestionstock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        UtilisateurDto utilisateurDto = utilisateurService.findByMail(mail);

        List<SimpleGrantedAuthority> authorities =  new ArrayList<>();
        utilisateurDto.getRoles().forEach(rolesDto -> authorities.add(new SimpleGrantedAuthority(rolesDto.getRoleName())));

        return new ExtendedUser(utilisateurDto.getMail(), utilisateurDto.getMotDePasse(), utilisateurDto.getEntreprise().getId(), authorities);
    }
}
