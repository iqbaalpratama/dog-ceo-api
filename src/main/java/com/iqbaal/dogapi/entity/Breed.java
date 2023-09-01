package com.iqbaal.dogapi.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "breeds")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Breed {

    public Breed(String name){
        this.name = name;
    }
    
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "breed_sub_breed", 
             joinColumns = @JoinColumn(name = "breed_id"),
             inverseJoinColumns = @JoinColumn(name = "sub_breed_id"))
    private Set<SubBreed> subBreed = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "breed")
    private Set<Image> images = new HashSet<>();
}
