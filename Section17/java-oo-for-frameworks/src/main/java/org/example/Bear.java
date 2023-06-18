package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Bear extends Animal {

    private Boolean hibernating;
    private String name;
    private int numberOfBears;
    public Bear(String species, int numberOfBears) {
        setPhylum("Chordates");
        setAnimalClass("Mammals");
        setOrder("Omnivores");
        setFamily("Bears");
        setSpecies(species);
        setNumberOfBears(numberOfBears);
    }

    public void incrementNumberOfBears() {
        setNumberOfBears(numberOfBears + 1);
    }

    public void decrementNumberOfBears() {
        setNumberOfBears(numberOfBears - 1);
    }
}
