package org.stonesoup.hackathon.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.stonesoup.hackathon.dtos.ClusterDTO;
import org.stonesoup.hackathon.dtos.FullListingDTO;
import org.stonesoup.hackathon.dtos.ListingDTO;
import org.stonesoup.hackathon.services.ListingService.ListingService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    @GetMapping("/health")
    public Map<String, String> health() {
        return Collections.singletonMap("status", "ok");
    }

    @GetMapping
    public ResponseEntity<List<ListingDTO>> getListings(
            @RequestParam(required = false) Integer min_rooms,
            @RequestParam(required = false) Integer max_rooms,
            @RequestParam(required = false) Double min_price,
            @RequestParam(required = false) Double max_price,
            @RequestParam(required = false) String listing_type,
            @RequestParam(required = false) Double min_area,
            @RequestParam(required = false) Double max_area,
            @RequestParam(required = false) Integer min_floor,
            @RequestParam(required = false) Integer max_floor,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) Double min_lat,
            @RequestParam(required = false) Double max_lat,
            @RequestParam(required = false) Double min_lon,
            @RequestParam(required = false) Double max_lon,
            @RequestParam(defaultValue = "100") int limit
    ) {
        return ResponseEntity.ok(listingService.searchListings(
                min_rooms, max_rooms,
                min_price, max_price,
                listing_type,
                min_area, max_area,
                min_floor, max_floor,
                tags,
                min_lat, max_lat, min_lon, max_lon,
                limit
        ));
    }

    @GetMapping("/{listing_id}")
    public ResponseEntity<FullListingDTO> getListingById(@PathVariable String listing_id) {
        return ResponseEntity.ok(listingService.getListingById(listing_id));
    }

    @GetMapping("/clusters")
    public List<ClusterDTO> getClusters(
            @RequestParam Double min_lat,
            @RequestParam Double max_lat,
            @RequestParam Double min_lon,
            @RequestParam Double max_lon,
            @RequestParam(required = false) Integer min_rooms,
            @RequestParam(required = false) Integer max_rooms,
            @RequestParam(required = false) Double min_price,
            @RequestParam(required = false) Double max_price,
            @RequestParam(required = false) String listing_type,
            @RequestParam(required = false) Double min_area,
            @RequestParam(required = false) Double max_area,
            @RequestParam(required = false) Integer min_floor,
            @RequestParam(required = false) Integer max_floor,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(defaultValue = "10") int max_clusters
    ) {
        int effectiveMax = Math.min(max_clusters, 10);

        return listingService.getClusters(
                min_lat, max_lat, min_lon, max_lon,
                min_rooms, max_rooms, min_price, max_price,
                listing_type, min_area, max_area, min_floor, max_floor,
                tags, effectiveMax
        );
    }
}