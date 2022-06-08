package com.abdproject.gestionstock.services.strategy;

import com.abdproject.gestionstock.dto.FournisseurDto;
import com.abdproject.gestionstock.exceptions.ErrorCodes;
import com.abdproject.gestionstock.exceptions.InvalidOperationException;
import com.abdproject.gestionstock.services.FlickrService;
import com.abdproject.gestionstock.services.FournisseurService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
@Service("fournisseurStrategy")
@Slf4j
public class SaveFournisseurPhoto implements Strategy<FournisseurDto>{

    private FlickrService flickrService;
    private FournisseurService fournisseurService;

    @Autowired
    public SaveFournisseurPhoto(FlickrService flickrService, FournisseurService fournisseurService) {
        this.flickrService = flickrService;
        this.fournisseurService = fournisseurService;
    }

    @Override
    public FournisseurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        FournisseurDto fournisseurDto = fournisseurService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if (!StringUtils.hasLength(urlPhoto)){
            throw  new InvalidOperationException("Erreur lors de l'enregistrement de la photo du fournisseur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        fournisseurDto.setPhoto(urlPhoto);
        return fournisseurService.save(fournisseurDto);
    }
}
