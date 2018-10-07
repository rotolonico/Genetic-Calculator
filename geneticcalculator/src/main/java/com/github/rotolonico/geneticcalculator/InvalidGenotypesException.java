package com.github.rotolonico.geneticcalculator;

public class InvalidGenotypesException extends GeneticCalculatorException {
    public InvalidGenotypesException() {
        super("The Genotypes are invalid");
    }
}
