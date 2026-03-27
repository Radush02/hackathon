package org.stonesoup.hackathon.services.ListingService;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.stonesoup.hackathon.models.Listing;

import java.util.ArrayList;
import java.util.List;

public final class ListingSpecifications {

    private ListingSpecifications() {
    }

    public static Specification<Listing> build(
            Integer minRooms, Integer maxRooms,
            Double minPrice, Double maxPrice,
            String listingType,
            Double minArea, Double maxArea,
            Integer minFloor, Integer maxFloor,
            List<String> tags,
            Double minLat, Double maxLat,
            Double minLon, Double maxLon) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (minRooms != null) {
                predicates.add(cb.ge(root.get("rooms"), minRooms));
            }
            if (maxRooms != null) {
                predicates.add(cb.le(root.get("rooms"), maxRooms));
            }

            if (minPrice != null) {
                predicates.add(cb.ge(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.le(root.get("price"), maxPrice));
            }

            if (listingType != null && !listingType.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("listingType")), listingType.trim().toLowerCase()));
            }

            if (minArea != null) {
                predicates.add(cb.ge(root.get("areaSqm"), minArea));
            }
            if (maxArea != null) {
                predicates.add(cb.le(root.get("areaSqm"), maxArea));
            }

            if (minFloor != null) {
                predicates.add(cb.ge(root.get("floor"), minFloor));
            }
            if (maxFloor != null) {
                predicates.add(cb.le(root.get("floor"), maxFloor));
            }

            if (minLat != null) {
                predicates.add(cb.ge(root.get("lat"), minLat));
            }
            if (maxLat != null) {
                predicates.add(cb.le(root.get("lat"), maxLat));
            }
            if (minLon != null) {
                predicates.add(cb.ge(root.get("lon"), minLon));
            }
            if (maxLon != null) {
                predicates.add(cb.le(root.get("lon"), maxLon));
            }

            if (tags != null && !tags.isEmpty()) {
                for (String tag : tags) {
                    if (tag != null && !tag.isBlank()) {
                        predicates.add(cb.like(cb.lower(root.get("tags")), "%" + tag.trim().toLowerCase() + "%"));
                    }
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}