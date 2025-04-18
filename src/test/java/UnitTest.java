import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UnitTest {


    @Test
    public void testOrderPizza_PizzaExists() {

        Pizzeria pizzeria = new Pizzeria();
        Pizza pizza = new Pizza("Margherita", 100.0);
        pizzeria.makePizza(pizza);


        Customer customer = new Customer(1, "Ivan", "123456789");


        boolean result = customer.orderPizza(pizzeria, "Margherita");


        assertTrue(result);
        assertEquals(100.0, customer.getOrderPrice(), 0.001);
    }

    @Test
    public void testOrderPizza_PizzaDoesNotExist() {

        Pizzeria pizzeria = new Pizzeria();

        Customer customer = new Customer(2, "Anna", "987654321");

        boolean result = customer.orderPizza(pizzeria, "Hawaii");

        assertFalse(result);
        assertEquals(0.0, customer.getOrderPrice(), 0.001);
    }
    @Test
    public void testApplyNegativeDiscount() {
        Pizza pizza = new Pizza("Hawaiian", 200.0);
        pizza.applyDiscount(-10);

        assertEquals(200.0, pizza.getPrice(), 0.001);
    }

    @Test
    public void testApplyTooHighDiscount() {
        Pizza pizza = new Pizza( "Veggie", 120.0);
        pizza.applyDiscount(150);

        assertEquals(120.0, pizza.getPrice(), 0.001);
    }

    @Test
    public void testSellPizza_PizzaExists() {
        Pizzeria pizzeria = new Pizzeria("Test Pizzeria");
        Pizza pizza1 = new Pizza("Margherita", 100.0);
        Pizza pizza2 = new Pizza("Pepperoni", 120.0);
        pizzeria.makePizza(pizza1);
        pizzeria.makePizza(pizza2);

        Pizza sold = pizzeria.sellPizza("Pepperoni");


        assertEquals(pizza2, sold);
        assertEquals(1, pizzeria.getPizzas().size());
        assertFalse(pizzeria.isPizzaExist("Pepperoni"));
    }

    @Test
    public void testSellPizza_PizzaDoesNotExist() {
        Pizzeria pizzeria = new Pizzeria("Test Pizzeria");


        Pizza result = pizzeria.sellPizza("NonExistent");


        assertNull(result);
    }

    @Test
    public void testApplyValidDiscount() {
        Pizza pizza = new Pizza("Four Cheese", 150.0);
        pizza.applyDiscount(10);

        assertEquals(135.0, pizza.getPrice(), 0.001);
    }

    @Test
    public void testSellPizza_MultipleSameNames() {
        Pizzeria pizzeria = new Pizzeria("Test Pizzeria");
        Pizza pizza1 = new Pizza("Capricciosa", 110.0);
        Pizza pizza2 = new Pizza("Capricciosa", 115.0);
        pizzeria.makePizza(pizza1);
        pizzeria.makePizza(pizza2);

        Pizza sold = pizzeria.sellPizza("Capricciosa");


        assertEquals(pizza1, sold);
        assertEquals(1, pizzeria.getPizzas().size());
    }

    @Test
    public void testGetPizza_ExistingId() {
        Pizzeria pizzeria = new Pizzeria("Test Pizzeria");
        Pizza pizza = new Pizza("Diablo", 130.0);
        pizzeria.makePizza(pizza);

        Pizza found = pizzeria.getPizza(pizza.getId());

        assertEquals(pizza, found);
    }

    @Test
    public void testGetPizza_NonExistingId() {
        Pizzeria pizzeria = new Pizzeria("Test Pizzeria");

        Pizza found = pizzeria.getPizza(999);

        assertNull(found);
    }

    @Test
    public void testUpdatePizza_Success() {
        Pizzeria pizzeria = new Pizzeria("Test Pizzeria");
        Pizza pizza = new Pizza("Pepperoni", 95.0);
        pizzeria.makePizza(pizza);
        int pizzaId = pizza.getId();
        boolean updated = pizzeria.updatePizza(pizzaId, "Pepperoni Special", 105.0);

        assertTrue(updated);
        assertEquals("Pepperoni Special", pizzeria.getPizza(pizzaId).getName());
        assertEquals(105.0, pizzeria.getPizza(pizzaId).getPrice());
    }

    @Test
    public void testUpdatePizza_Fail() {
        Pizzeria pizzeria = new Pizzeria("Test Pizzeria");

        boolean updated = pizzeria.updatePizza(999, "NewName", 150.0);

        assertFalse(updated);}


    @Test
    public void testExportPizzas() throws IOException {
        DataManager dataManager = new DataManager();
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        File mockFile = mock(File.class);
        dataManager.mapper = mockObjectMapper;

        Pizza pizza1 = new Pizza("Margarita", 110);
        Pizza pizza2 = new Pizza("Pepperoni", 120);
        List<Pizza> pizzas = Arrays.asList(pizza1, pizza2);

        Comparator<Pizza> sortByPrice = Comparator.comparing(Pizza::getPrice);

        ObjectWriter mockObjectWriter = mock(ObjectWriter.class);
        when(mockObjectMapper.writerWithDefaultPrettyPrinter()).thenReturn(mockObjectWriter);
        doNothing().when(mockObjectWriter).writeValue(mockFile, pizzas);
        dataManager.exportPizzas(pizzas, mockFile, sortByPrice);

        verify(mockObjectMapper, times(1)).writerWithDefaultPrettyPrinter();
        verify(mockObjectWriter, times(1)).writeValue(mockFile, pizzas);
    }

    @Test
    public void testImportPizzas() throws IOException {
        DataManager dataManager = new DataManager();
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        File mockFile = mock(File.class);
        dataManager.mapper = mockObjectMapper;

        Pizza pizza1 = new Pizza("Margherita", 8.99);
        Pizza pizza2 = new Pizza("Pepperoni", 9.99);
        Pizza[] pizzasArray = {pizza1, pizza2};


        when(mockObjectMapper.readValue(mockFile, Pizza[].class)).thenReturn(pizzasArray);
        List<Pizza> importedPizzas = dataManager.importPizzas(mockFile);

        assert importedPizzas.size() == 2;
        assert importedPizzas.contains(pizza1);
        assert importedPizzas.contains(pizza2);

        verify(mockObjectMapper, times(1)).readValue(mockFile, Pizza[].class);
    }
}