package vttp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vttp.backend.model.Journal;
import vttp.backend.repository.S3ImageRepo;

import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private S3ImageRepo imageRepo;


    //Upload the image and creating the uuid for the Journal
    public Optional<Journal> uploadImage (MultipartFile image) {

        String uuidForJournal = UUID.randomUUID().toString().substring(0,8);

        Journal journal = new Journal();
        journal.setUuid(uuidForJournal);

        try {
            journal.setImageUrl(imageRepo.upload(image, uuidForJournal));
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(journal);

    }

    public void deleteImage (String uuid) {

        imageRepo.deleteImage(uuid);
    }
    
}
