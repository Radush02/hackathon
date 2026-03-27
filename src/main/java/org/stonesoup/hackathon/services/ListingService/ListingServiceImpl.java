package org.stonesoup.hackathon.services.ListingService;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.stonesoup.hackathon.dtos.ClusterDTO;
import org.stonesoup.hackathon.dtos.FullListingDTO;
import org.stonesoup.hackathon.dtos.ListingDTO;
import org.stonesoup.hackathon.models.Listing;
import org.stonesoup.hackathon.repositories.ListingRepository;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ListingServiceImpl implements ListingService {
    private final ListingRepository repository;

    @Override
    public List<ListingDTO> searchListings(
            Integer minRooms, Integer maxRooms,
            Double minPrice, Double maxPrice,
            String listingType,
            Double minArea, Double maxArea,
            Integer minFloor, Integer maxFloor,
            List<String> tags,
            Double minLat, Double maxLat, Double minLon, Double maxLon,
            int limit) {

        String tagsJson = null;
        if (tags != null && !tags.isEmpty()) {
            tagsJson = new ObjectMapper().writeValueAsString(tags);
        }

        int safeLimit = Math.max(1, Math.min(limit, 500));
        List<Listing> listings = repository.findListingsOptimized(
                minLat, maxLat, minLon, maxLon,
                listingType, minPrice, maxPrice,
                minRooms, maxRooms, minArea, maxArea,
                minFloor, maxFloor,
                tagsJson, safeLimit
        );

        return listings.stream()
                .map(ListingDTO::fromEntity)
                .toList();
    }

    @Override
    public FullListingDTO getListingById(String id) {
        return FullListingDTO.fromEntity(repository.findById(id).orElseThrow(()->new EntityNotFoundException("ID-ul "+id+" nu exista.")));
    }

    @Override
    public List<ClusterDTO> getClusters(
            Double minLat, Double maxLat, Double minLon, Double maxLon,
            Integer minRooms, Integer maxRooms,
            Double minPrice, Double maxPrice,
            String listingType,
            Double minArea, Double maxArea,
            Integer minFloor, Integer maxFloor,
            List<String> tags,
            int maxClusters) {

        int safeMaxClusters = Math.max(1, maxClusters);
        int gridSize = Math.max(1, (int) Math.sqrt(safeMaxClusters));


        List<String> activeTags = (tags == null) ? Collections.emptyList() : tags;
        int tagCount = activeTags.size();
        String tagsJson = null;
        if (tags != null && !tags.isEmpty()) {
            tagsJson = new ObjectMapper().writeValueAsString(tags);
        }

        List<Object[]> results = repository.findClustersOptimized(
                minLat, maxLat, minLon, maxLon,
                listingType, minPrice, maxPrice,
                minRooms, maxRooms, minArea, maxArea,
                minFloor, maxFloor,
                tagsJson,
                gridSize
        );

        return results.stream()
                .map(row -> new ClusterDTO(
                        ((Number) row[0]).doubleValue(),
                        ((Number) row[1]).doubleValue(),
                        ((Number) row[2]).intValue()
                ))
                .toList();
    }
}
