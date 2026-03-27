package org.stonesoup.hackathon.dtos;

import org.stonesoup.hackathon.models.Listing;

public record ClusterDTO(
        Double lat,
        Double lon,
        Integer count
) {
}
