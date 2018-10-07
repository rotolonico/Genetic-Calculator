# Genetic Calculator

An Android library that gets children genotypes and phenotypes from two parent's genotypes and more! 

## Getting Started

Add JitPack in your **root *build.gradle*** at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```
Add the dependency in your **app *build.gradle***:
 ```gradle
 	dependencies {
	        implementation 'com.github.rotolonico:Genetic-Calculator:v0.1'
	}
 ```

### Features

The library contains four different public methods that, given two parents genotypes in the class constructor, will return different info about the possible children or the parent's alleles:

* **getGenotype1Alleles()** Returns a string array with all the possible alleles combinations of the first parent
* **getGenotype2Alleles()** Returns a string array with all the possible alleles combinations of the second parent
* **getChildrenGenotypes()** Returns a string array with all the possible genotypes the parent's children can have
* **getChildrenPhenotypes()** Returns a string array with all the possible phenotypes the parent's children can have

### How to Use

```java

String genotype1 = "AaBb";
String genotype2 = "aabb";

// Create a new GeneticCalculator object and feed it your two parents genotypes
GeneticCalculator gc = new GeneticCalculator(genotype1, genotype2);

// Returns a string array with all the possible alleles combinations of the first parent
String[] genotype1Alleles = gc.getGenotype1Alleles();
// genotype1Alleles = [AB, Ab, aB, ab]

// Returns a string array with all the possible alleles combinations of the second parent
String[] genotype2Alleles = gc.getGenotype2Alleles();
// genotype2Alleles = [ab, ab, ab, ab]

// Returns a string array with all the possible genotypes the parent's children can have
String[] childrenGenotypes = gc.getChildrenGenotypes();
// childrenGenotypes = [AaBb, AaBb, AaBb, AaBb, Aabb, Aabb, Aabb, Aabb, aaBb, aaBb, aaBb, aaBb, aabb, aabb, aabb, aabb]

// Returns a string array with the phenotypes of all the possible genotypes the parent's children can have
String[] childrenPhenotypes = gc.getChildrenPhenotypes();
// childrenPhenotypes = [AB, AB, AB, AB, Ab, Ab, Ab, Ab, aB, aB, aB, aB, ab, ab, ab, ab]

```

## Credits

* **Domenico Rotolo** - [@rotolonico](https://twitter.com/rotolonico)

## License

This library is shared under **Apache License 2.0**
