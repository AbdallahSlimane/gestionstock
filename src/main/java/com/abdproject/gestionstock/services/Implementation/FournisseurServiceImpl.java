package com.abdproject.gestionstock.services.Implementation;


import com.abdproject.gestionstock.dto.FournisseurDto;
import com.abdproject.gestionstock.exceptions.EntityNotFoundException;
import com.abdproject.gestionstock.exceptions.ErrorCodes;
import com.abdproject.gestionstock.exceptions.InvalidEntityException;

import com.abdproject.gestionstock.exceptions.InvalidOperationException;
import com.abdproject.gestionstock.model.CommandeClient;
import com.abdproject.gestionstock.model.CommandeFournisseur;
import com.abdproject.gestionstock.model.Fournisseur;
import com.abdproject.gestionstock.repository.CommandeFournisseurRepository;
import com.abdproject.gestionstock.repository.FournisseurRepository;
import com.abdproject.gestionstock.services.FournisseurService;

import com.abdproject.gestionstock.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

    private FournisseurRepository fournisseurRepository;
    private CommandeFournisseurRepository commandeFournisseurRepository;

    @Autowired
    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository, CommandeFournisseurRepository commandeFournisseurRepository){
        this.fournisseurRepository = fournisseurRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
    }

    @Override
    public FournisseurDto save(FournisseurDto dto) {
        List<String> errors = FournisseurValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("le fournisseur n'est pas valide {}", dto);
            throw new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.FOURNISSEUR_NOT_VALID, errors);
        }
        return FournisseurDto.fromEntity(
                fournisseurRepository.save(
                        FournisseurDto.toEntity(dto)
                )
        );        }

    @Override
    public FournisseurDto findById(Integer id) {
        if (id == null){
            log.error("Fournisseur id is null");
            return null;
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);


        return Optional.of(FournisseurDto.fromEntity(fournisseur.get())).orElseThrow(() ->
                new EntityNotFoundException("Aucun fournisseur avec l'ID = " + id + "n'a été trouvé dans la BDD",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND));      }


    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Fournisseur id is null");
            return;
        }
        List<CommandeFournisseur> commandeFournisseurs = commandeFournisseurRepository.findAllByFournisseurId(id);
        if (!commandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un fournisseur deja utilisé dans des commandes fournisseur", ErrorCodes.FOURNISSEUR_IN_USE);
        }
        fournisseurRepository.deleteById(id);
    }
}
