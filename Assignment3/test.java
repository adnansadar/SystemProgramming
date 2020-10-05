   
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        ArrayList<String> cars = new ArrayList<String>();
        cars.add("Volvo");
        cars.add("Ford");
        cars.add(1,"BMW");
        // cars.add("Mazda");
        System.out.println(cars);
    }
}

