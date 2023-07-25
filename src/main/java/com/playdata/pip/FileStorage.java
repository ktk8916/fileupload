package com.playdata.pip;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class FileStorage {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String uuid;
    private String extension;
    private String savedName;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;

    public static FileStorage from(String fileName){
        FileStorage fileStorage = new FileStorage();
        fileStorage.name = fileName;
        fileStorage.uuid = UUID.randomUUID().toString();
        fileStorage.extension = fileName.substring(fileName.lastIndexOf("."));
        fileStorage.savedName = fileStorage.uuid + fileStorage.extension;
        fileStorage.createdAt = LocalDateTime.now();
        //후에 유저별로 저장기간 다르게 해도 괜찮을듯
        fileStorage.endedAt = fileStorage.createdAt.minusDays(2);

        return fileStorage;
    }
}
