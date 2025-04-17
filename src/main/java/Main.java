import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pizzeria pizzeria = new Pizzeria("Celentano");
        DataManager dataManager = new DataManager();
        File file = new File("src/main/resources/pizzas.json");

        while (true) {
            System.out.println("\n=== Меню Піцерії ===");
            System.out.println("1. Додати піцу");
            System.out.println("2. Додати замовника");
            System.out.println("3. Зробити замовлення");
            System.out.println("4. Переглянути всі піци");
            System.out.println("5. Переглянути інформацію про замовника");
            System.out.println("6. Експортувати піци за ціною");
            System.out.println("7. Імпортувати піци");
            System.out.println("8. Оновити піцу");
            System.out.println("9. Оновити замовника");
            System.out.println("10. Видалити замовника");
            System.out.println("11. Видалити піцу");
            System.out.println("12. Виговити піци");
            System.out.println("0. Вийти");
            System.out.print("Виберіть опцію: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    System.out.print("Назва піци: ");
                    String pizzaName = scanner.nextLine();
                    System.out.print("Ціна піци: ");
                    double price = scanner.nextDouble();
                    pizzeria.makePizza(new Pizza( pizzaName, price));
                    System.out.println("Піцу додано.");
                    scanner.nextLine();
                    break;
                case 2:
                    System.out.print("ID замовника: ");
                    int custId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ім'я: ");
                    String custName = scanner.nextLine();
                    System.out.print("Телефон: ");
                    String phone = scanner.nextLine();
                    pizzeria.addCustomer(new Customer(custId, custName, phone));
                    System.out.println("Замовника додано.");
                    scanner.nextLine();
                    break;
                case 3:
                    System.out.print("ID замовника: ");
                    int custOrderId = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = pizzeria.getCustomer(custOrderId);
                    if (customer == null) {
                        System.out.println("Замовника не знайдено.");
                        break;
                    }
                    System.out.print("Назва піци для замовлення: ");
                    String orderPizzaName = scanner.nextLine().trim();
                    if (customer.orderPizza(pizzeria, orderPizzaName)) {
                        System.out.println("Замовлення прийнято.");
                        System.out.println("Загальна сума: " + customer.getOrderPrice() + " грн");
                    } else {
                        System.out.println("Такої піци немає в наявності.");
                    }
                    scanner.nextLine();
                    break;
                case 4:
                    System.out.println("Доступних піц: " + pizzeria.getAvailablePizza() + ", проданих піц: "+ pizzeria.getSelledPizza());
                    System.out.println("Список піц:");
                    for (Pizza p : pizzeria.getPizzas()) {
                        System.out.println(p.getId() + ": " + p.getName() + " - " + p.getPrice() + " грн");
                    }
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.print("ID замовника: ");
                    int custInfoId = scanner.nextInt();
                    Customer cust = pizzeria.getCustomer(custInfoId);
                    if (cust != null) {
                        System.out.println("Ім’я: " + cust.getName());
                        System.out.println("Телефон: " + cust.getPhone());
                        System.out.println("Сума замовлення: " + cust.getOrderPrice() + " грн");
                    } else {
                        System.out.println("Замовника не знайдено.");
                    }
                    scanner.nextLine();
                    break;
                case 6:

                    try {

                        dataManager.exportPizzas(pizzeria.getPizzas(), file, Comparator.comparing(Pizza::getPrice).reversed());
                        System.out.println("Піци успішно експортовано у файл з сортуванням за ціною: " + file.getAbsolutePath());

                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    scanner.nextLine();
                    break;
                case 7:
                    scanner.nextLine();
                    try {
                        List<Pizza> importedPizzas = dataManager.importPizzas(file);
                        pizzeria.setPizzas(importedPizzas);
                        System.out.println("\nІмпортовані піци:");
                        for (Pizza p : importedPizzas) {
                            System.out.println(p.getId() + ": " + p.getName() + " - " + p.getPrice() + " грн");
                        }

                    }
                    catch (IOException e){
                        throw new RuntimeException();
                    }
                    break;

                case 8:
                    System.out.print("ID піци для оновлення: ");
                    int updPizzaId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Нова назва: ");
                    String newPizzaName = scanner.nextLine();
                    System.out.print("Нова ціна: ");
                    double newPrice = scanner.nextDouble();
                    if (pizzeria.updatePizza(updPizzaId, newPizzaName, newPrice)) {
                        System.out.println("Піцу оновлено.");
                    } else {
                        System.out.println("Піцу не знайдено.");
                    }
                    break;
                case 9:
                    System.out.print("ID замовника для оновлення: ");
                    int updCustId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Нове ім'я: ");
                    String newCustName = scanner.nextLine();
                    System.out.print("Новий телефон: ");
                    String newPhone = scanner.nextLine();
                    if (pizzeria.updateCustomer(updCustId, newCustName, newPhone)) {
                        System.out.println("Дані оновлено.");
                    } else {
                        System.out.println("Замовника не знайдено.");
                    }
                    scanner.nextLine();
                    break;
                case 10:
                    System.out.print("ID замовника для видалення: ");
                    int delCustId = scanner.nextInt();
                    if (pizzeria.deleteCustomer(delCustId)) {
                        System.out.println("Замовника видалено.");
                    } else {
                        System.out.println("Замовника не знайдено.");
                    }
                    scanner.nextLine();
                    break;
                case 11:
                    System.out.print("ID піци для видалення: ");
                    int delPizzaId = scanner.nextInt();
                    if (pizzeria.deletePizza(delPizzaId)) {
                        System.out.println("Піцу видалено.");
                    } else {
                        System.out.println("Піцу не знайдено.");
                    }
                    scanner.nextLine();
                    break;
                case 12:
                    pizzeria.makePizza(new Pizza( "Margarita", 120));
                    pizzeria.makePizza(new Pizza("Pepperoni", 140));
                    pizzeria.makePizza(new Pizza("Hawaiian", 130));
                    pizzeria.makePizza(new Pizza("Diablo", 149));
                    break;
                case 0:
                    System.out.println("Вихід...");
                    return;
                default:
                    System.out.println("Невірний вибір.");
            }
        }
    }
}