package com.iqbaal.dogapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sub-breed")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubBreed {

    public SubBreed(String name){
        this.name = name;
    }

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}
