package com.abdproject.gestionstock.controller.api;

import com.flickr4java.flickr.FlickrException;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


import static com.abdproject.gestionstock.utils.Constants.APP_ROOT;

@Api(APP_ROOT +"/photos")
public interface PhotoApi {

    @PostMapping(APP_ROOT +"/photos/{id}/{titre}/{context}")
    Object savePhoto(String context, Integer id, @RequestPart("file") MultipartFile photo, String titre) throws IOException, FlickrException;

}
