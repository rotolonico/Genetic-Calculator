package com.github.rotolonico.geneticcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText genotype1;
    EditText genotype2;
    TextView result;
    char[] genotype1C;
    char[] genotype2C;
    String binary = "-1";
    String binary2 = "1";
    String[] genotype1S;
    String[] genotype2S;
    String[] binaryComposition;
    String[] childProbabilitiesRaw;
    StringBuilder[] childProbabilities;
    StringBuilder[] childProbabilitiesG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.calculate);
        result = findViewById(R.id.result);
        result.setMovementMethod(new ScrollingMovementMethod());
        genotype1 = findViewById(R.id.genotype1);
        genotype2 = findViewById(R.id.genotype2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genotype1C = genotype1.getText().toString().toCharArray();
                genotype2C = genotype2.getText().toString().toCharArray();
                result.setText("");

                if (isGenotype(genotype1C) && isGenotype(genotype2C) && areGenotypesCompatible(genotype1C, genotype2C)) {
                    result.setText(result.getText() + "First Parent's Genes: ");
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
                        result.setText(result.getText() + " " + genotype1S[i]);
                    }
                    result.setText(result.getText() + "\n");


                }

                if (isGenotype(genotype1C) && isGenotype(genotype2C) && areGenotypesCompatible(genotype1C, genotype2C)) {
                    result.setText(result.getText() + "Second Parent's Genes: ");
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
                        result.setText(result.getText() + " " + genotype2S[i]);
                    }
                    result.setText(result.getText() + "\n\n");

                    getChildProbabilities(genotype1S,genotype2S);
                    result.setText(result.getText() + "Possible Children Genotypes: \n");
                    for (int i = 0; i < childProbabilities.length; i++){
                        result.setText(result.getText() + childProbabilities[i].toString() + " ");
                    }

                    getChildProbabilitiesG(childProbabilities);

                    result.setText(result.getText() + "\n\nPossible Children Phenotypes: \n");
                    for (int i = 0; i < childProbabilities.length; i++){
                        result.setText(result.getText() + childProbabilitiesG[i].toString() + " ");
                    }

                }

            }
        });
    }

    boolean isGenotype(char[] genotype) {
        if ((genotype.length % 2) == 0) {
            for (int i = 0; i < genotype.length; i++) {
                if ((i % 2) != 0) {
                    if ((genotype[i - 1] > 64 && genotype[i - 1] < 91) || (genotype[i - 1] > 96 && genotype[i - 1] < 123)) {
                        if (genotype[i - 1] == genotype[i] - 32 || genotype[i - 1] == genotype[i] + 32 || genotype[i - 1] == genotype[i]) {
                            return true;
                        } else {
                            result.setText("Error: Invalid Genotype/s");
                            return false;
                        }
                    } else {
                        result.setText("Error: Invalid Character/s");
                        return false;
                    }
                }
            }
        } else {
            result.setText("Error: Invalid Genotypes");
            return false;
        }
        if (genotype.length == 0) {
            result.setText("Error: Empty Genotype field/s");
            return false;
        }
        return true;
    }

    boolean areGenotypesCompatible(char[] genotype1, char[] genotype2){
        boolean match;
        if (genotype1.length != genotype2.length){
            result.setText("Error: Genotypes not compatible");
            return false;
        }
        for (int i = 0; i < genotype1.length; i++){
            match = false;
            for (int j = 0; j < genotype2.length; j++){
                if (genotype1[i] == genotype2[j] || genotype1[i] == genotype2[j]-32 || genotype1[i] == genotype2[j]+32){
                    match = true;
                }
            }
            if (!match){
                result.setText("Error: Genotypes not compatible");
                return false;
            }
        }
        return true;
    }

    void getChildProbabilities(String[] gene1, String[] gene2) {
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

    void getChildProbabilitiesG(StringBuilder[] childProbabilities){
        for (int i = 0; i < childProbabilities.length; i++){
            childProbabilitiesG[i] = new StringBuilder(childProbabilities[i]);
            childProbabilitiesG[i].setLength(childProbabilities[i].length()/2);
            for (int j = 0; j < childProbabilities[i].length()/2; j++){
                childProbabilitiesG[i].setCharAt(j, childProbabilities[i].charAt(j*2));
            }

        }
    }

}
