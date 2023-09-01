package com.iqbaal.dogapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iqbaal.dogapi.entity.SubBreed;


public interface SubBreedRepository extends JpaRepository<SubBreed, Long>{
    boolean existsByName(String name);
    Optional<SubBreed> findByName(String name);
}
