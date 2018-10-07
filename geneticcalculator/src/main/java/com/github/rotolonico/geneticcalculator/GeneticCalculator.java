package com.github.rotolonico.geneticcalculator;

public class GeneticCalculator {

    private char[] genotype1C;
    private char[] genotype2C;
    private String binary = "-1";
    private String binary2 = "1";
    private String[] genotype1S;
    private String[] genotype2S;
    private String[] binaryComposition;
    private String[] childProbabilitiesRaw;
    private StringBuilder[] childProbabilities;
    private StringBuilder[] childProbabilitiesG;
    private String[] childProbabilitiesS;
    private String[] childProbabilitiesSG;

    public GeneticCalculator(String genotype1, String genotype2) throws InvalidGenotypesException, UncompatibleGenotypesException, InvalidCharactersException, EmptyGenotypesException {
        getGenotypes(genotype1, genotype2);
    }

    public String[] getGenotype1Alleles(){
        return genotype1S;
    }

    public String[] getGenotype2Alleles(){
        return genotype2S;
    }

    public String[] getChildrenGenotypes(){
        getChildProbabilities(genotype1S,genotype2S);
        childProbabilitiesS = new String[childProbabilities.length];
        for (int i = 0; i < childProbabilities.length; i++){
            childProbabilitiesS[i] = childProbabilities[i].toString();
        }
        return childProbabilitiesS;
    }

    public String[] getChildrenPhenotypes(){
        getChildProbabilitiesG(childProbabilities);
        childProbabilitiesSG = new String[childProbabilitiesG.length];
        for (int i = 0; i < childProbabilities.length; i++){
            childProbabilitiesSG[i] = childProbabilitiesG[i].toString();
        }
        return childProbabilitiesSG;
    }

    private void getGenotypes(String genotype1, String genotype2) throws EmptyGenotypesException, InvalidGenotypesException, InvalidCharactersException, UncompatibleGenotypesException {

        genotype1C = genotype1.toCharArray();
        genotype2C = genotype2.toCharArray();

        if (isGenotype(genotype1C) && isGenotype(genotype2C) && areGenotypesCompatible(genotype1C, genotype2C)) {
            genotype1S = new String[(int) Math.pow(2, genotype1C.length / 2)];
            int binaryNumber = Integer.parseInt(binary, 2);
            int binaryAdd1 = Integer.parseInt(binary2, 2);
            binaryComposition = new String[(int) (Math.pow(2, genotype1C.length / 2) + 1)];
            for (int i = 0; i < Math.pow(2, genotype1C.length / 2); i++) {
                binaryComposition[i] = (Integer.toBinaryString(binaryNumber += binaryAdd1));
                for (int k = binaryComposition[i].length(); k < genotype1C.length / 2; k++) {
                    binaryComposition[i] = "0" + binaryComposition[i];
                }
                for (int j = 0; j < binaryComposition[i].length(); j++) {
                    if (binaryComposition[i].charAt(j) == '0' && j * 2 < (int) Math.pow(2, genotype1C.length / 2)) {
                        genotype1S[i] += String.valueOf(genotype1C[j * 2]);
                    } else if (j * 2 + 1 < (int) Math.pow(2, genotype1C.length / 2)) {
                        genotype1S[i] += String.valueOf(genotype1C[j * 2 + 1]);
                    }
                }
                genotype1S[i] = genotype1S[i].substring(4, genotype1S[i].length());
            }


        }

        if (isGenotype(genotype1C) && isGenotype(genotype2C) && areGenotypesCompatible(genotype1C, genotype2C)) {
            genotype2S = new String[(int) Math.pow(2, genotype2C.length / 2)];
            int binaryNumber = Integer.parseInt(binary, 2);
            int binaryAdd1 = Integer.parseInt(binary2, 2);
            binaryComposition = new String[(int) (Math.pow(2, genotype2C.length / 2) + 1)];
            for (int i = 0; i < Math.pow(2, genotype2C.length / 2); i++) {
                binaryComposition[i] = (Integer.toBinaryString(binaryNumber += binaryAdd1));
                for (int k = binaryComposition[i].length(); k < genotype2C.length / 2; k++) {
                    binaryComposition[i] = "0" + binaryComposition[i];
                }
                for (int j = 0; j < binaryComposition[i].length(); j++) {
                    if (binaryComposition[i].charAt(j) == '0' && j * 2 < (int) Math.pow(2, genotype2C.length / 2)) {
                        genotype2S[i] += String.valueOf(genotype2C[j * 2]);
                    } else if (j * 2 + 1 < (int) Math.pow(2, genotype2C.length / 2)) {
                        genotype2S[i] += String.valueOf(genotype2C[j * 2 + 1]);
                    }
                }
                genotype2S[i] = genotype2S[i].substring(4, genotype2S[i].length());
            }
        }
    }

    private boolean isGenotype(char[] genotype) throws EmptyGenotypesException, InvalidGenotypesException, InvalidCharactersException {
        if ((genotype.length % 2) == 0) {
            for (int i = 0; i < genotype.length; i++) {
                if ((i % 2) != 0) {
                    if ((genotype[i - 1] > 64 && genotype[i - 1] < 91) || (genotype[i - 1] > 96 && genotype[i - 1] < 123)) {
                        if (genotype[i - 1] == genotype[i] - 32 || genotype[i - 1] == genotype[i] + 32 || genotype[i - 1] == genotype[i]) {
                        } else {
                            throw new InvalidGenotypesException();
                        }
                    } else {
                        throw new InvalidCharactersException();
                    }
                }
            }
        } else {
            throw new InvalidGenotypesException();
        }
        if (genotype.length == 0) {
            throw new EmptyGenotypesException();
        }
        return true;
    }

    private boolean areGenotypesCompatible(char[] genotype1, char[] genotype2) throws UncompatibleGenotypesException {
        boolean match;
        if (genotype1.length != genotype2.length){
            throw new UncompatibleGenotypesException();
        }
        for (int i = 0; i < genotype1.length; i++){
            match = false;
            for (int j = 0; j < genotype2.length; j++){
                if (genotype1[i] == genotype2[j] || genotype1[i] == genotype2[j]-32 || genotype1[i] == genotype2[j]+32){
                    match = true;
                }
            }
            if (!match){
                throw new UncompatibleGenotypesException();
            }
        }
        return true;
    }

     private void getChildProbabilities(String[] gene1, String[] gene2) {
        childProbabilitiesRaw = new String[gene1.length*gene2.length];
        childProbabilities = new StringBuilder[gene1.length*gene2.length];
        childProbabilitiesG = new StringBuilder[gene1.length*gene2.length];
        for (int i = 0; i < gene1.length; i++) {
            for (int j = 0; j < gene2.length; j++) {
                childProbabilitiesRaw[gene2.length*i+j] = gene1[i] + gene2[j];
            }
        }
        for (int i = 0; i < childProbabilitiesRaw.length; i++){
            childProbabilities[i] = new StringBuilder(childProbabilitiesRaw[i]);
            for (int j = 0; j < childProbabilitiesRaw[i].length()/2; j++){
                if (childProbabilitiesRaw[i].charAt(j+(childProbabilitiesRaw[i].length()/2)) > 96){
                    childProbabilities[i].setCharAt(j*2+1, childProbabilitiesRaw[i].charAt(j+(childProbabilitiesRaw[i].length()/2)));
                    childProbabilities[i].setCharAt(j*2, childProbabilitiesRaw[i].charAt(j));
                } else {
                    childProbabilities[i].setCharAt(j*2, childProbabilitiesRaw[i].charAt(j+(childProbabilitiesRaw[i].length()/2)));
                    childProbabilities[i].setCharAt(j*2+1, childProbabilitiesRaw[i].charAt(j));
                }
            }
        }
    }

    private void getChildProbabilitiesG(StringBuilder[] childProbabilities){
        for (int i = 0; i < childProbabilities.length; i++){
            childProbabilitiesG[i] = new StringBuilder(childProbabilities[i]);
            childProbabilitiesG[i].setLength(childProbabilities[i].length()/2);
            for (int j = 0; j < childProbabilities[i].length()/2; j++){
                childProbabilitiesG[i].setCharAt(j, childProbabilities[i].charAt(j*2));
            }

        }
    }

}
