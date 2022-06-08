package com.abdproject.gestionstock.services.Implementation;

import com.abdproject.gestionstock.dto.ClientDto;
import com.abdproject.gestionstock.exceptions.EntityNotFoundException;
import com.abdproject.gestionstock.exceptions.ErrorCodes;
import com.abdproject.gestionstock.exceptions.InvalidEntityException;

import com.abdproject.gestionstock.exceptions.InvalidOperationException;
import com.abdproject.gestionstock.model.Client;
import com.abdproject.gestionstock.model.CommandeClient;
import com.abdproject.gestionstock.model.LigneCommandeClient;
import com.abdproject.gestionstock.repository.ClientRepository;
import com.abdproject.gestionstock.repository.CommandeClientRepository;
import com.abdproject.gestionstock.services.ClientService;
import com.abdproject.gestionstock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private CommandeClientRepository commandeClientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, CommandeClientRepository commandeClientRepository){ this.clientRepository = clientRepository;
        this.commandeClientRepository = commandeClientRepository;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto);
        if (!errors.isEmpty()){
            log.error("le client n'est pas valide {}", dto);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }
        return ClientDto.fromEntity(
                clientRepository.save(
                        ClientDto.toEntity(dto)
                )
        );    }

    @Override
    public ClientDto findById(Integer id) {
        if (id == null){
            log.error("Client id is null");
            return null;
        }

        Optional<Client> client = clientRepository.findById(id);


        return Optional.of(ClientDto.fromEntity(client.get())).orElseThrow(() ->
                new EntityNotFoundException("Aucun client avec l'ID = " + id + "n'a été trouvé dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND));
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Client id is null");
            return;
        }
        List<CommandeClient> commandeClients = commandeClientRepository.findAllByClientId(id);
        if (!commandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un client deja utilisé dans des commandes client", ErrorCodes.CLIENT_IN_USE);
        }
        clientRepository.deleteById(id);
    }


}
