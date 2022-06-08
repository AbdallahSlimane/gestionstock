package com.abdproject.gestionstock.services.Implementation;

import com.abdproject.gestionstock.dto.EntrepriseDto;
import com.abdproject.gestionstock.dto.RolesDto;
import com.abdproject.gestionstock.dto.UtilisateurDto;
import com.abdproject.gestionstock.exceptions.EntityNotFoundException;
import com.abdproject.gestionstock.exceptions.ErrorCodes;
import com.abdproject.gestionstock.exceptions.InvalidEntityException;
import com.abdproject.gestionstock.model.Entreprise;
import com.abdproject.gestionstock.repository.EntrepriseRepository;
import com.abdproject.gestionstock.repository.RolesRepository;
import com.abdproject.gestionstock.services.EntrepriseService;
import com.abdproject.gestionstock.services.UtilisateurService;
import com.abdproject.gestionstock.validator.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {


    private EntrepriseRepository entrepriseRepository;
    private UtilisateurService utilisateurService;
    private RolesRepository rolesRepository;

    @Autowired
    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository, UtilisateurService utilisateurService, RolesRepository rolesRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurService = utilisateurService;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        List<String> errors = EntrepriseValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("l'entreprise n'est pas valide {}", dto);
            throw new InvalidEntityException("L'entreprise n'est pas valide", ErrorCodes.ENTREPRISE_NOT_VALID, errors);
        }
        EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(
                entrepriseRepository.save(EntrepriseDto.toEntity(dto))
        );

        UtilisateurDto utilisateurDto = fromEntreprise(savedEntreprise);

        UtilisateurDto savedUser = utilisateurService.save(utilisateurDto);

        RolesDto rolesDto = RolesDto.builder()
                .roleName("ADMIN").utilisateur(savedUser)
                .build();

        rolesRepository.save(RolesDto.toEntity(rolesDto));

        return savedEntreprise;
    }

    private UtilisateurDto fromEntreprise(EntrepriseDto dto) {

        return UtilisateurDto.builder()
                .adresse(dto.getAdresse())
                .nom(dto.getNom())
                .prenom(dto.getCodeFiscal())
                .mail(dto.getMail())
                .motDePasse(generateRandomPassword())
                .entreprise(dto)
                .dateDeNaissance(Instant.now())
                .photo(dto.getPhoto())
                .build();

    }

    private String generateRandomPassword() {
        return "hajar77176";
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        if (id == null){
            log.error("Entreprise id is null");
            return null;
        }

        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);


        return Optional.of(EntrepriseDto.fromEntity(entreprise.get())).orElseThrow(() ->
                new EntityNotFoundException("Aucune entreprise avec l'ID = " + id + "n'a été trouvé dans la BDD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND));
    }


    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Entreprise id is null");
            return;
        }
        entrepriseRepository.deleteById(id);
    }
}
