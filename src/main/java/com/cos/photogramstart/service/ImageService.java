package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")
    private String uploadFolder;
    public void ImageUpload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
        UUID uuid = UUID.randomUUID();  //UUID란?
        String imageFileName = uuid + "-" + imageUploadDto.getFile().getOriginalFilename();

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        try{
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }

        //image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        Image imageEntity = imageRepository.save(image);

        log.info(">>>>>>>>>> ImageUpload :: " + imageEntity);
    }
}
