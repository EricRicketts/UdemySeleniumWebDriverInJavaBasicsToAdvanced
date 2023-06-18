package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Animal {

    private String phylum;
    private String animalClass;
    private String order;
    private String family;
    private String species;

    public Animal(
            String phylum, String animalClass, String order, String family, String species
    ) {
        this.phylum = phylum;
        this.animalClass = animalClass;
        this.order = order;
        this.family = family;
        this.species = species;
    }
}
