package com.playdata.pip;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Transactional
public class PipService {

    private final FIleStorageRepository fIleStorageRepository;

    @Value("${file.dir}")
    private String SAVE_DIR;
    public ResponseEntity<Resource> findVideo() {
        String videoPath = SAVE_DIR +"testvideo.mp4";
        Resource resource = getResource(videoPath);
        return getResourceResponseEntity(resource);
    }
    public ResponseEntity<Resource> searchFileById(Long id){
        FileStorage fileStorage = findById(id);
        String filePath = SAVE_DIR + fileStorage.getSavedName();
        Resource resource = getResource(filePath);
        return getResourceResponseEntity(resource);
    }

    private Resource getResource(String filePath) {
        Path path = Paths.get(filePath);
        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e){
            throw new RuntimeException("나중에 바꿔줄 예욈");
        }
    }

    private FileStorage findById(Long id){
        return fIleStorageRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("디비에파일없다"));
    }

    public ResponseEntity<Resource> findAll() {
        String imagePath = SAVE_DIR +"740929d2-d87f-4526-8fba-b00be12f1e8b.png";

        Resource resource = getResource(imagePath);

        return getResourceResponseEntity(resource);
    }

    private ResponseEntity<Resource> getResourceResponseEntity(Resource resource) {
        if (resource.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    public void upload(MultipartFile file){

        FileStorage fileStorage = FileStorage.from(file.getOriginalFilename());
        String savedPath = SAVE_DIR + fileStorage.getSavedName();

        try {
            file.transferTo(new File(savedPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileStorage save = fIleStorageRepository.save(fileStorage);
    }


}
