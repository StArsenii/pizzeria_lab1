import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataManager {
    public ObjectMapper mapper = new ObjectMapper();

    public void exportPizzas(List<Pizza> pizzas, File file, Comparator<Pizza> sortBy) throws IOException {
        List<Pizza> sorted = new ArrayList<>(pizzas);
        sorted.sort(sortBy);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, sorted);
    }

    public List<Pizza> importPizzas(File file) throws IOException {
        Pizza[] pizzasArray = mapper.readValue(file, Pizza[].class);
        return new ArrayList<>(Arrays.asList(pizzasArray));
    }
}