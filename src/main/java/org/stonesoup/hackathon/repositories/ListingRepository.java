package org.stonesoup.hackathon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.stonesoup.hackathon.models.Listing;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, String>, JpaSpecificationExecutor<Listing> {

    @Query(value = """
    SELECT 
        AVG(lat) as lat, 
        AVG(lon) as lon, 
        COUNT(*) as count
    FROM listings
    WHERE (lat BETWEEN :minLat AND :maxLat)
      AND (lon BETWEEN :minLon AND :maxLon)
      AND (:listingType IS NULL OR listing_type = :listingType)
      AND (:tagsJson IS NULL OR JSON_CONTAINS(tags, :tagsJson))
      
    GROUP BY 
        FLOOR((lat - :minLat) / NULLIF((:maxLat - :minLat) / :gridSize, 0)), 
        FLOOR((lon - :minLon) / NULLIF((:maxLon - :minLon) / :gridSize, 0))
    """, nativeQuery = true)
    List<Object[]> findClustersOptimized(
            @Param("minLat") Double minLat, @Param("maxLat") Double maxLat,
            @Param("minLon") Double minLon, @Param("maxLon") Double maxLon,
            @Param("listingType") String listingType,
            @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
            @Param("minRooms") Integer minRooms, @Param("maxRooms") Integer maxRooms,
            @Param("minArea") Double minArea, @Param("maxArea") Double maxArea,
            @Param("minFloor") Integer minFloor, @Param("maxFloor") Integer maxFloor,
            @Param("tagsJson") String tagsJson,
            @Param("gridSize") int gridSize
    );

    @Query(value = """
    SELECT * FROM listings
    WHERE (:minLat IS NULL OR lat >= :minLat)
      AND (:maxLat IS NULL OR lat <= :maxLat)
      AND (:minLon IS NULL OR lon >= :minLon)
      AND (:maxLon IS NULL OR lon <= :maxLon)
      AND (:listingType IS NULL OR listing_type = :listingType)
      AND (:minPrice IS NULL OR price >= :minPrice)
      AND (:maxPrice IS NULL OR price <= :maxPrice)
      AND (:minRooms IS NULL OR rooms >= :minRooms)
      AND (:maxRooms IS NULL OR rooms <= :maxRooms)
      AND (:minArea IS NULL OR area_sqm >= :minArea)
      AND (:maxArea IS NULL OR area_sqm <= :maxArea)
      AND (:minFloor IS NULL OR floor >= :minFloor)
      AND (:maxFloor IS NULL OR floor <= :maxFloor)
      -- Index-friendly tag search
      AND (:tagsJson IS NULL OR JSON_CONTAINS(tags, :tagsJson))
    ORDER BY id ASC
    LIMIT :limit
    """, nativeQuery = true)
    List<Listing> findListingsOptimized(
            @Param("minLat") Double minLat, @Param("maxLat") Double maxLat,
            @Param("minLon") Double minLon, @Param("maxLon") Double maxLon,
            @Param("listingType") String listingType,
            @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
            @Param("minRooms") Integer minRooms, @Param("maxRooms") Integer maxRooms,
            @Param("minArea") Double minArea, @Param("maxArea") Double maxArea,
            @Param("minFloor") Integer minFloor, @Param("maxFloor") Integer maxFloor,
            @Param("tagsJson") String tagsJson,
            @Param("limit") int limit
    );
}