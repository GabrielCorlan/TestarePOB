import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<String> listaReteteText = new ArrayList<>();

        try {
            File file = new File("C:\\Users\\gabri\\IdeaProjects\\TestarePOB\\src\\resources\\Retete.txt");
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                String reteta = fileReader.nextLine();
                listaReteteText.add(reteta);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Reteta> retete = new ArrayList<>();

        for (String textLine : listaReteteText) {
            String[] arrayRetete = textLine.split("\\*");

            if (startsWithUpperAtoN(arrayRetete[0]) && containsOnlyLetters(arrayRetete[0])){
                String[] arrayIngrediente =  arrayRetete[1].split(";");

                retete.add(new Reteta(arrayRetete[0], getIngredientList(arrayIngrediente), Integer.parseInt(arrayRetete[2])));
            }

        }
        System.out.println();

        List<Reteta> reteteUnice = stergeDuplicate(retete);

        List<Reteta> reteteSortate = sorteazaRetete(retete);

        // Write the sorted recipes to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ReteteSortate.txt"))) {
            for (Reteta reteta : reteteSortate) {
                writer.write("Recipe Name: " + reteta.getDenumire() + "\n");
                writer.write("Preparation Time: " + reteta.getTimpDePregatire() + " minutes\n");
                writer.write("Ingredients:\n");
                for (Ingredient ingredient : reteta.getIngrediente()) {
                    writer.write("- " + ingredient.getNume() + ", Quantity: " + ingredient.getCantitate() +
                            "Weight: " + ingredient.getUnitateDeMasura() + " grams" +
                            ", Calories: " + ingredient.getNumarCalorii() + "\n");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Reteta reteta : retete) {
            int totalCalorii = reteta.calculeazaNumarTotalDeCalorii();
            System.out.println("Reteta: " + reteta.getDenumire());
            System.out.println("Total Calorii: " + totalCalorii);
            System.out.println();
        }

        // Create a map to store ingredient frequency
        Map<Ingredient, Integer> ingredientFrequency = new HashMap<>();

        // Count the frequency of each ingredient
        for (Reteta reteta : retete) {
            for (Ingredient ingredient : reteta.getIngrediente()) {
                ingredientFrequency.put(ingredient, ingredientFrequency.getOrDefault(ingredient, 0) + 1);
            }
        }

        // Find the most frequently used ingredient
        Ingredient mostUsedIngredient = null;
        int maxFrequency = 0;
        for (Map.Entry<Ingredient, Integer> entry : ingredientFrequency.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mostUsedIngredient = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }

        // Print the most used ingredient
        if (mostUsedIngredient != null) {
            System.out.println("Most Used Ingredient: " + mostUsedIngredient.getNume());
            System.out.println("Used in " + maxFrequency + " recipes:");

            // Print the names of recipes that contain the most used ingredient
            for (Reteta recipe : retete) {
                if (recipe.getIngrediente().contains(mostUsedIngredient)) {
                    System.out.println("- " + recipe.getDenumire());
                }
            }
        }
    }

    public static boolean startsWithUpperAtoN(String text) {
        return text.charAt(0) >= 'A' && text.charAt(0) <= 'N';
    }

    public static boolean containsOnlyLetters(String text) {
        char[] chars = text.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public static List<Ingredient> getIngredientList(String[] arrayIngrediente) {
        Ingredient ingredient;
        List<Ingredient> ingredientList = new ArrayList<>();

        if (Arrays.toString(arrayIngrediente).contains("_")) {
            for (String ingr : arrayIngrediente) {
                String[] valoriIngredient = ingr.split("_");
                ingredient = new Ingredient(
                        valoriIngredient[0],
                        valoriIngredient[1],
                        Integer.parseInt(valoriIngredient[2]), Integer.parseInt(valoriIngredient[3])
                );
                ingredientList.add(ingredient);
            }
        }
        return ingredientList;
    }

    public static List<Reteta> stergeDuplicate(List<Reteta> retete) {
        Set<Reteta> reteteUnice = new HashSet<>();
        List<Reteta> listaFinala = new ArrayList<>();

        for (Reteta reteta : retete) {
            if (reteteUnice.add(reteta)) {
                // If the recipe is successfully added to the set (not a duplicate), add it to the result list
                listaFinala.add(reteta);
            }
        }

        return listaFinala;
    }

    public static List<Reteta> sorteazaRetete(List<Reteta> retete) {
        // Sort recipes by preparation time and then by number of ingredients
        retete.sort(Comparator
                .comparingInt(Reteta::getTimpDePregatire)
                .thenComparingInt(r -> r.getIngrediente().size()));

        return retete;
    }
}
