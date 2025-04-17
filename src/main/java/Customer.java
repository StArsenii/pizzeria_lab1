import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private List<Pizza> orderedPizzas;
    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        orderedPizzas = new ArrayList<Pizza>();
    }

    public boolean orderPizza(Pizzeria pizzeria, String name){
        if(pizzeria.isPizzaExist(name)){
            orderedPizzas.add(pizzeria.sellPizza(name));
            pizzeria.setAvailablePizza(pizzeria.getAvailablePizza()-1);
            pizzeria.setSelledPizza(pizzeria.getSelledPizza()+1);
            return true;
        }
        return false;
    }
    public double getOrderPrice(){
        double sum = 0;
        for(Pizza pizza : orderedPizzas){
            sum += pizza.getPrice();
        }
        return sum;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(name, customer.name) && Objects.equals(phone, customer.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone);
    }




}