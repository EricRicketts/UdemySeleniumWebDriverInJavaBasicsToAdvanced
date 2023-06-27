package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class RandomNumber {

    private int upperLimit;
    private int lowerLimit;

    public int generateRandomNumber() {
        // in order to match the array indices we number starting from zero
        Random randomNumber = new Random();
        int chosenRandomNumber = randomNumber.nextInt(
                getUpperLimit() - getLowerLimit() + 1
        ) + getLowerLimit();
        return chosenRandomNumber;
    }
    public RandomNumber(int upperLimit, int lowerLimit) {
        setUpperLimit(upperLimit);
        setLowerLimit(lowerLimit);
    }
}
