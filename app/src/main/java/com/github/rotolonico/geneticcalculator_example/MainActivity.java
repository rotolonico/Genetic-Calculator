package com.github.rotolonico.geneticcalculator_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.rotolonico.geneticcalculator.GeneticCalculator;
import com.github.rotolonico.geneticcalculator.GeneticCalculatorException;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText genotype1;
    EditText genotype2;
    TextView result;

    String genotype1Text;
    String genotype2Text;
    String[] genotype1Alleles;
    String[] genotype2Alleles;
    String[] childrenGenotypes;
    String[] childrenPhenotypes;

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
                result.setText("");
                genotype1Text = genotype1.getText().toString();
                genotype2Text = genotype2.getText().toString();

                try {
                    getGeneticInfo(genotype1Text, genotype2Text);
                } catch (GeneticCalculatorException e) {
                    result.setText(e.getMessage());
                }
            }
        });
    }

    private void getGeneticInfo(String genotype1, String genotype2) throws GeneticCalculatorException {

        GeneticCalculator gc = new GeneticCalculator(genotype1, genotype2);

        //Print First Parent Alleles on a Textview
        genotype1Alleles = gc.getGenotype1Alleles();
        result.setText(result.getText() + "First Parent's Alleles: ");
        for (int i = 0; i < genotype1Alleles.length; i++) {
            result.setText(result.getText() + genotype1Alleles[i] + " ");
        }

        //Print Second Parent Alleles on a Textview
        genotype2Alleles = gc.getGenotype2Alleles();
        result.setText(result.getText() + "\nSecond Parent's Alleles: ");
        for (int i = 0; i < genotype2Alleles.length; i++) {
            result.setText(result.getText() + genotype2Alleles[i] + " ");
        }

        //Print Children Genotypes on a Textview
        childrenGenotypes = gc.getChildrenGenotypes();
        result.setText(result.getText() + "\n\nPossible Children Genotypes: \n");
        for (int i = 0; i < childrenGenotypes.length; i++) {
            result.setText(result.getText() + childrenGenotypes[i] + " ");
        }

        //Print Children Phenotypes on a Textview
        childrenPhenotypes = gc.getChildrenPhenotypes();
        result.setText(result.getText() + "\n\nPossible Children Phenotypes: \n");
        for (int i = 0; i < childrenPhenotypes.length; i++) {
            result.setText(result.getText() + childrenPhenotypes[i] + " ");
        }
    }
}
