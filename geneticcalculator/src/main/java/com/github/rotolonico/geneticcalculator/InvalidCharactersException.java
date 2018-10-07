package com.github.rotolonico.geneticcalculator;

public class InvalidCharactersException extends GeneticCalculatorException {
    InvalidCharactersException() {
        super("There are invalid characters in the Genotypes");
    }
}
