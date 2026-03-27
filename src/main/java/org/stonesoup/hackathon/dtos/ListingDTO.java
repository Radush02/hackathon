package org.stonesoup.hackathon.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.stonesoup.hackathon.models.Listing;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public record ListingDTO(
        String id,
        Integer rooms,
        @JsonProperty("area_sqm") Double areaSqm,
        Double price,
        @JsonProperty("listing_type") String listingType,
        List<String> tags,
        Double lat,
        Double lon,
        Integer floor
) {
    public static ListingDTO fromEntity(Listing listing) {
        return new ListingDTO(
                listing.getId(),
                listing.getRooms(),
                listing.getAreaSqm(),
                listing.getPrice(),
                listing.getListingType(),
                parseTags(listing.getTags()),
                listing.getLat(),
                listing.getLon(),
                listing.getFloor()
        );
    }

    private static List<String> parseTags(String rawTags) {
        if (rawTags == null || rawTags.isBlank()) {
            return Collections.emptyList();
        }
        String clean = rawTags.replace("[", "")
                .replace("]", "")
                .replace("\"", "");

        return Arrays.stream(clean.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}