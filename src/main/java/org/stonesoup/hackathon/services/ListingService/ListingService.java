package org.stonesoup.hackathon.services.ListingService;

import org.stonesoup.hackathon.dtos.ClusterDTO;
import org.stonesoup.hackathon.dtos.FullListingDTO;
import org.stonesoup.hackathon.dtos.ListingDTO;

import java.util.List;

public interface ListingService {
    List<ListingDTO> searchListings(
            Integer minRooms, Integer maxRooms,
            Double minPrice, Double maxPrice,
            String listingType,
            Double minArea, Double maxArea,
            Integer minFloor, Integer maxFloor,
            List<String> tags,
            Double minLat, Double maxLat, Double minLon, Double maxLon,
            int limit
    );

    FullListingDTO getListingById(String id);

    List<ClusterDTO> getClusters(
            Double minLat, Double maxLat, Double minLon, Double maxLon,
            Integer minRooms, Integer maxRooms,
            Double minPrice, Double maxPrice,
            String listingType,
            Double minArea, Double maxArea,
            Integer minFloor, Integer maxFloor,
            List<String> tags,
            int maxClusters);
}