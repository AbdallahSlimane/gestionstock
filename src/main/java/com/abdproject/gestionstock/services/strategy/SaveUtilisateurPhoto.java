package com.abdproject.gestionstock.services.strategy;

import com.abdproject.gestionstock.dto.UtilisateurDto;
import com.abdproject.gestionstock.exceptions.ErrorCodes;
import com.abdproject.gestionstock.exceptions.InvalidOperationException;
import com.abdproject.gestionstock.services.FlickrService;
import com.abdproject.gestionstock.services.UtilisateurService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
@Service("utilisateurStrategy")
@Slf4j
public class SaveUtilisateurPhoto implements Strategy<UtilisateurDto> {

    private FlickrService flickrService;
    private UtilisateurService utilisateurService;

    @Autowired
    public SaveUtilisateurPhoto(FlickrService flickrService, UtilisateurService utilisateurService) {
        this.flickrService = flickrService;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UtilisateurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        UtilisateurDto utilisateurDto = utilisateurService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if (!StringUtils.hasLength(urlPhoto)){
            throw  new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'utilisateur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        utilisateurDto.setPhoto(urlPhoto);
        return utilisateurService.save(utilisateurDto);    }
}
