package com.karakoc.mezat.auction;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ArchiveEntity {
    @Id
    private String id;
    private String photoPath;
    private String productTitle;
    private String owner;
    private LocalDateTime endDate;
    private LocalDateTime createddatetime;
    private double price;
    private String description;
    private EAuctionStatus auctionStatus;
    public static ArchiveEntityDTO archiveToDTO(ArchiveEntity archiveEntity) {
        ArchiveEntityDTO dto = new ArchiveEntityDTO();
        dto.setId(archiveEntity.getId());
        dto.setPhotoPath(archiveEntity.getPhotoPath());
        dto.setProductTitle(archiveEntity.getProductTitle());
        dto.setOwner(archiveEntity.getOwner());
        dto.setEndDate(archiveEntity.getEndDate());
        dto.setCreateddatetime(archiveEntity.getCreateddatetime());
        dto.setPrice(archiveEntity.getPrice());
        dto.setDescription(archiveEntity.getDescription());
        dto.setAuctionStatus(archiveEntity.getAuctionStatus());
        return dto;
    }

    public static List<ArchiveEntityDTO> archivesToDTO(List<ArchiveEntity> archiveEntities) {
        List<ArchiveEntityDTO> response = new ArrayList<>();
        for (ArchiveEntity archiveEntity : archiveEntities) {
            response.add(archiveToDTO(archiveEntity));
        }
        return response;
    }
}
