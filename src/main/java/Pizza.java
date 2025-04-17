import java.util.Objects;
import java.util.Random;

public class Pizza {
    private int id;
    private String name;
    private double price;

    public Pizza(){}

    public Pizza(String name, double price) {
        this.name = name;
        this.price = price;
        Random rnd = new Random();
        this.id = rnd.nextInt(1000);
    }


    public void applyDiscount(double percent) {
        if (percent > 0 && percent <= 100) {
            double oldPrice = this.price;
            this.price = price - (price * percent / 100);

        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return id == pizza.id && Double.compare(price, pizza.price) == 0 && Objects.equals(name, pizza.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }


}