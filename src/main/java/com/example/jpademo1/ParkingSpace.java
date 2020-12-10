package com.example.jpademo1;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ParkingSpace {
    @Id
    //@Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    //@Column(name="lot")
    private Integer lot;
    //@Column(name="location")
    private String location;
}
