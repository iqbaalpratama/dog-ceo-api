package com.iqbaal.dogapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iqbaal.dogapi.entity.Breed;


public interface BreedRepository extends JpaRepository<Breed, Long> {
    boolean existsByName(String name);
    Optional<Breed> findByName(String name);
}
