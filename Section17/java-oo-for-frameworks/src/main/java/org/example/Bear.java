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
    public Bear(String phylum, String animalClass, String order,
                String family, String species, int numberOfBears) {
        super(phylum, animalClass, order, family, species);
        setNumberOfBears(numberOfBears);
    }

    public void incrementNumberOfBears() {
        setNumberOfBears(numberOfBears + 1);
    }

    public void decrementNumberOfBears() {
        setNumberOfBears(numberOfBears - 1);
    }
}
