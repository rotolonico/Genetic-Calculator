package com.github.rotolonico.geneticcalculator;

public class EmptyGenotypesException extends GeneticCalculatorException {
    EmptyGenotypesException(){
        super("There is an empty Genotype");
    }
}
