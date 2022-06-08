package com.abdproject.gestionstock.services.Implementation;


import com.abdproject.gestionstock.dto.ChangerMdpUtilisateurDto;
import com.abdproject.gestionstock.dto.UtilisateurDto;
import com.abdproject.gestionstock.exceptions.EntityNotFoundException;
import com.abdproject.gestionstock.exceptions.ErrorCodes;
import com.abdproject.gestionstock.exceptions.InvalidEntityException;

import com.abdproject.gestionstock.exceptions.InvalidOperationException;
import com.abdproject.gestionstock.model.Utilisateur;
import com.abdproject.gestionstock.repository.UtilisateurRepository;
import com.abdproject.gestionstock.services.UtilisateurService;

import com.abdproject.gestionstock.validator.UtilsateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        List<String> errors = UtilsateurValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("l'utilisateur n'est pas valide {}", dto);
            throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.UTILISATEUR_NOT_VALID, errors);
        }

        if (userAlreadyExists(dto.getMail())){
            throw  new InvalidEntityException("Un autre utilisateur avec le meme mail existe déjà", ErrorCodes.UTILISATEUR_ALREADY_EXIST,
                    Collections.singletonList("Un autre utilisateur avec le même mail existe déjà dans la BDD"));
        }

        dto.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));

        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(
                        UtilisateurDto.toEntity(dto)
                )
        );
    }

    private boolean userAlreadyExists(String mail){
        Optional<Utilisateur> user = utilisateurRepository.findUtilisateurByMail(mail);
        return user.isPresent();
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        if (id == null){
            log.error("Utilisateur id is null");
            return null;
        }

        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);


        return Optional.of(UtilisateurDto.fromEntity(utilisateur.get())).orElseThrow(() ->
                new EntityNotFoundException("Aucun utilsateur avec l'ID = " + id + "n'a été trouvé dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND));
    }

    @Override
    public UtilisateurDto findByMail(String mail) {
        return utilisateurRepository.findUtilisateurByMail(mail)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilsateur avec le mail = " + mail + "n'a ete trouvé dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND));
    }



    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Utilisateur id is null");
            return;
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDto changerMotDePasse(ChangerMdpUtilisateurDto dto) {
        validate(dto);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(dto.getId());
        if (utilisateurOptional.isEmpty()){
            log.warn("Aucun utilisateur a été trouvé avec l'ID" + dto.getId());
            throw new EntityNotFoundException("Aucun utilisateur a été trouvé avec l'ID" + dto.getId(), ErrorCodes.UTILISATEUR_NOT_FOUND);
        }

        Utilisateur utilisateur = utilisateurOptional.get();
        utilisateur.setMotDePasse(dto.getMotDePasse());
        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(utilisateur)
        );
    }

    private void validate(ChangerMdpUtilisateurDto dto){
        if(dto == null){
            log.warn("impossible de modifier le mot de passe avec un objet null");
            throw  new InvalidOperationException("Auncue information n'a ete fourni pour pouvoir changer le mot de passe", ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_NOT_VALID);
        }
        if (dto.getId() == null){
            log.warn("impossible de modifier le mot de passe avec un objet null");
            throw  new InvalidOperationException("ID utilisateur null impossible de modifier le mot de passe", ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_NOT_VALID);
        }

        if (!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())){
            log.warn("impossible de modifier le mot de passe avec un mot de passe vide");
            throw  new InvalidOperationException("mot de passe utilisateur vide impossible de modifier", ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_NOT_VALID);
        }

        if (!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())){
            log.warn("impossible de modifier le mot de passe avec un deux mot de passe différent null");
            throw  new InvalidOperationException("Mots de passe utilisateur différent, impossible de modifier le mot de passe", ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_NOT_VALID);
        }
    }
}
