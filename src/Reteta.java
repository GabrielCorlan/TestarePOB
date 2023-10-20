import java.util.List;
import java.util.Objects;

public class Reteta {

    private String denumire;
    private List<Ingredient> ingrediente;
    private int timpDePregatire;

    public Reteta(String denumire, List<Ingredient> ingrediente, int timpDePregatire) {
        this.denumire = denumire;
        this.ingrediente = ingrediente;
        this.timpDePregatire = timpDePregatire;
    }

    public String getDenumire() {
        return denumire;
    }

    public List<Ingredient> getIngrediente() {
        return ingrediente;
    }

    public int getTimpDePregatire() {
        return timpDePregatire;
    }

    public int calculeazaNumarTotalDeCalorii() {
        int totalCalorii = 0;
        for (Ingredient ingredient : ingrediente) {
            totalCalorii += ingredient.getNumarCalorii();
        }
        return totalCalorii;
    }

    // Override equals and hashCode methods for proper comparison and hashing
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reteta recipe = (Reteta) o;
        return timpDePregatire == recipe.timpDePregatire &&
                Objects.equals(denumire, recipe.denumire) &&
                Objects.equals(ingrediente, recipe.ingrediente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(denumire, ingrediente, timpDePregatire);
    }
}
