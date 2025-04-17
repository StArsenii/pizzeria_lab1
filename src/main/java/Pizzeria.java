import java.util.*;

public class Pizzeria {
    private List<Pizza> pizzas = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private String name;
    private int selledPizza;

    private int availablePizza;
    public Pizzeria(){}

    public Pizzeria(String name){
        this.name = name;
        selledPizza = 0;
        availablePizza = 0;
    }


    public Pizza sellPizza(String name){
        int index = 0;
        if(isPizzaExist(name)){
            for(Pizza pizza : pizzas){
                if(pizza.getName().equals(name)){
                    index = pizzas.indexOf(pizza);
                    break;
                }

            }
            Pizza selledPizza = pizzas.get(index);
            pizzas.remove(index);
            return selledPizza;
        }
        return null;

    }
    public boolean isPizzaExist(String name){
        for(Pizza pizza : pizzas){
            if(pizza.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //CRUD Pizza
    public void makePizza(Pizza pizza){
        pizzas.add(pizza);
        availablePizza++;
    }
    public Pizza getPizza(int id){
        for (Pizza p : pizzas) {
            if (p.getId() == id) return p;
        }
        return null;
    }
    public boolean updatePizza(int id, String newName, double newPrice) {
        Pizza pizza = getPizza(id);
        if (pizza != null) {
            pizza.setName(newName);
            pizza.setPrice(newPrice);
            return true;
        }
        return false;
    }

    public boolean deletePizza(int id){
        Pizza pizza = getPizza(id);
        return pizza != null && pizzas.remove(pizza);
    }
    //CRUD Customer
    public void addCustomer(Customer customer){
        customers.add(customer);
    }
    public Customer getCustomer(int id){
        for (Customer customer : customers) {
            if (customer.getId() == id) return customer;
        }
        return null;
    }

    public boolean updateCustomer(int id, String newName, String newPhone){
        Customer customer = getCustomer(id);
        if (customer != null) {
            customer.setName(newName);
            customer.setPhone(newPhone);
            return true;
        }
        return false;
    }

    public boolean deleteCustomer(int id){
        Customer customer = getCustomer(id);
        return customer != null && customers.remove(customer);
    }

    public int getAvailablePizza() {
        return availablePizza;
    }

    public void setAvailablePizza(int availablePizza) {
        this.availablePizza = availablePizza;
    }

    public int getSelledPizza() {
        return selledPizza;
    }

    public void setSelledPizza(int selledPizza) {
        this.selledPizza = selledPizza;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pizzeria pizzeria = (Pizzeria) o;
        return Objects.equals(pizzas, pizzeria.pizzas) && Objects.equals(name, pizzeria.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}