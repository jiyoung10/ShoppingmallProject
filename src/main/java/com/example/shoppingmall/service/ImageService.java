package com.example.shoppingmall.service;

import com.example.shoppingmall.domain.entity.Image;
import com.example.shoppingmall.domain.entity.Item;
import com.example.shoppingmall.domain.entity.Tag;
import com.example.shoppingmall.domain.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    // local 절대 경로 이미지 저장 test
    public Image registerImage(MultipartFile file, Item item) throws Exception {
        // 오리지날 네임 저장할지 여부
        String fileName = getUniqueFileName(file);
        // 저장할 파일 경로 생성
        Path filePath = Path.of(uploadPath, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        // 데이터베이스에 이미지 정보 저장
        Image image = Image.of(fileName, filePath.toString(), item);
        imageRepository.save(image);
        return image;
    }

    public void deleteImageByCommunity(Item item) throws Exception {

        imageRepository.delete(imageRepository.findByItemId(item.getId()).orElseThrow(
                ()-> new Exception("IMAGE_DOES_NOT_EXIST")));
    }

    // 고유한 파일명 추출
    public String getUniqueFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        return UUID.randomUUID() + "." + extension;
    }

    public Image updateImage(Long itemId, MultipartFile file, Item item) throws Exception {

        Image image = imageRepository.findByItemId(itemId).orElseThrow(
                () -> new Exception("TAG_DOES_NOT_EXIST"));
        imageRepository.delete(image);

        image.updateImage(Image.of(file.getName(), uploadPath, item));
        imageRepository.save(image);

        return image;
    }
}
