package com.github.rotolonico.geneticcalculator;

public class UncompatibleGenotypesException extends GeneticCalculatorException {
    public UncompatibleGenotypesException() {
        super("The Genotypes are not compatible");
    }
}
