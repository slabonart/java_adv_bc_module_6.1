package pl.slabonart.module_6.loader;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleValuesLoader implements ValuesLoader {

    @Override
    public Map<String, String> loadValues() {

        Map<String, String> values = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        String continueInput;
        int count = 1;

        do {
            System.out.print("Key " + count + "? ");
            String key = scanner.nextLine();

            System.out.print("Value " + count + "? ");
            String value = scanner.nextLine();

            values.put(key, value);
            count++;

            System.out.print("Do you want to add another pair? (yes/no): ");
            continueInput = scanner.nextLine().trim().toLowerCase();
        } while (continueInput.equals("yes"));

        return values;
    }
}
