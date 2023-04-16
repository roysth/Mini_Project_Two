package vttp.backend.repository;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class S3ImageRepo {

    @Autowired
    private AmazonS3 s3Client;

    public String upload (MultipartFile file, String uuid) throws IOException {


        //User Data
        Map<String, String> userData = new HashMap<>();
        userData.put("postid", uuid);
        userData.put("uploadTime", (new Date()).toString());
        userData.put("originalFileName", file.getOriginalFilename());

        //Metadata (When you send back the file, this is impt)
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        metadata.setUserMetadata(userData);

        //Create a put request
        PutObjectRequest putReq = new PutObjectRequest(
            "vttp-bucket", //bucket name
            "myobjects/%s".formatted(uuid), //key
            file.getInputStream(), //inputstream (Remember to throw IOException at the top)
            metadata);

        //Set it to be able to be read publicly
        putReq.withCannedAcl(CannedAccessControlList.PublicRead);

        s3Client.putObject(putReq);

        String imageUrl = "https://vttp-bucket.sgp1.digitaloceanspaces.com/myobjects/%s".formatted(uuid);
        
        
        return imageUrl;
    }

    public void deleteImage (String uuid) {
        s3Client.deleteObject("vttp-bucket", "myobjects/%s".formatted(uuid));
    }
    
}
