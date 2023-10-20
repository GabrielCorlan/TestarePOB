import java.util.Objects;

public class Ingredient {

    private String nume;
    private String unitateDeMasura;
    private int cantitate;
    private int numarCalorii;

    public String getNume() {
        return nume;
    }

    public String getUnitateDeMasura() {
        return unitateDeMasura;
    }

    public int getCantitate() {
        return cantitate;
    }

    public int getNumarCalorii() {
        return numarCalorii;
    }

    public Ingredient(String nume, String unitateDeMasura, int cantitate, int numarCalorii) {
        this.nume = nume;
        this.unitateDeMasura = unitateDeMasura;
        this.cantitate = cantitate;
        this.numarCalorii = numarCalorii;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient ingredient = (Ingredient) o;
        return Double.compare(ingredient.cantitate, cantitate) == 0 &&
                numarCalorii == ingredient.numarCalorii &&
                Objects.equals(unitateDeMasura, ingredient.unitateDeMasura) &&
                Objects.equals(nume, ingredient.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, cantitate, unitateDeMasura, numarCalorii);
    }
}
