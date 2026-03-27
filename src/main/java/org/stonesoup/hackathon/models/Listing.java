package org.stonesoup.hackathon.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "listings")
@Getter
@Setter
@NoArgsConstructor
public class Listing{
    @Id
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String description;

    @Column(name = "rooms", nullable = false)
    private Integer rooms;

    @Column(name = "area_sqm", nullable = false)
    private Double areaSqm;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "listing_type", nullable = false, length = 16)
    private String listingType;

    @Column(name = "tags", nullable = false, columnDefinition = "TEXT")
    private String tags;

    @Column(name = "lat", nullable = false)
    private Double lat;

    @Column(name = "lon", nullable = false)
    private Double lon;

    @Column(name = "floor", nullable = false)
    private Integer floor;
}
