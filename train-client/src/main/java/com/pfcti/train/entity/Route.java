package com.pfcti.train.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ruta")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origin_id")
    private Location origin;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Location destination;

    private int distance;

    public Route(Location origin, Location destination, int distance) {
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
    }
}
