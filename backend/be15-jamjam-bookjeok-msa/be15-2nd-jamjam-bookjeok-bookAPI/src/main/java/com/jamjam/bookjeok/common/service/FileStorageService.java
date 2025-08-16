package com.jamjam.bookjeok.common.service;

import com.jamjam.bookjeok.exception.book.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {

    private final Path uploadDir;

    public FileStorageService(@Value("${image.image-dir}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir);
        try {
            Files.createDirectories(this.uploadDir);
        } catch (IOException e) {
            log.error("업로드 디렉터리 생성 실패: {}", e.getMessage());
            throw new FileStorageException("업로드 디렉터리 생성 실패");
        }
    }

    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("파일이 존재하지 않습니다.");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        String fileName = UUID.randomUUID()
                + (extension != null && !extension.isEmpty() ? "." + extension : "");
        Path targetLocation = uploadDir.resolve(fileName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            log.error("파일 저장 실패 [{}]: {}", fileName, ex.getMessage());
            throw new FileStorageException("파일 저장을 실패했습니다.");
        }

        return fileName;
    }

    public String getFileName(String image) {

        String extension = FilenameUtils.getExtension(image);
        String fileName = UUID.randomUUID()
                + (extension != null && !extension.isEmpty() ? "." + extension : "");
        return fileName;
    }

    public void deleteFile(String fileName) {
        Path filePath = this.uploadDir.resolve(fileName);
        try {
            if (!Files.deleteIfExists(filePath)) {
                log.warn("삭제할 파일이 존재하지 않음: {}", filePath);
            }
        } catch (IOException ex) {
            log.error("파일 삭제 실패 [{}]: {}", fileName, ex.getMessage());
            throw new FileStorageException("파일 삭제를 실패했습니다.");
        }
    }
}
