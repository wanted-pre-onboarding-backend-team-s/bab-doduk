package com.wanted.babdoduk.sigungu.service;

import com.wanted.babdoduk.sigungu.dto.SiGunGuResponseDto;
import com.wanted.babdoduk.sigungu.exception.FailedGetSiGunGuException;
import jakarta.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SiGunGuService {

    private List<SiGunGuResponseDto> siGunGuList;

    @Value("${si-gun-gu-file-path}")
    private String siGunGuFilePath;

    @PostConstruct
    public void init() {

        try {
            Path filePath = Path.of(siGunGuFilePath);
            if (Files.notExists(filePath)) {
                log.error("SiGunGu resource '{}' not found", siGunGuFilePath);
                throw new FailedGetSiGunGuException();
            }
            siGunGuList = Files.lines(filePath)
                    .map(line -> line.split(","))
                    .filter(data -> data.length == 4)
                    .map(this::createSiGunGuResponseDto)
                    .toList();
        } catch (Exception e) {
            log.error("[" + e.getClass().getName() + "] ex", e);
            throw new FailedGetSiGunGuException();
        }
    }

    public List<SiGunGuResponseDto> getSiGunGuList() {
        return siGunGuList;
    }

    private SiGunGuResponseDto createSiGunGuResponseDto(String[] data) {
        return SiGunGuResponseDto.builder()
                .doSi(data[0].trim())
                .siGunGu(data[1].trim())
                .lat(Double.parseDouble(data[2].trim()))
                .lon(Double.parseDouble(data[3].trim()))
                .build();
    }
}
