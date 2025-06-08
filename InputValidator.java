package util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputValidator {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
    public int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
    
    public double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    public String getStringInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public boolean confirmAction(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("y")) return true;
            if (response.equals("n")) return false;
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
        }
    }
    
    public LocalDate getDateFromUser(Scanner scanner, String prompt) {
        while (true) {
            String dateStr = getStringInput(scanner, prompt);
            
            if (dateStr.isEmpty()) {
                return null;
            }

            try {
                return LocalDate.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            }
        }
    }
    
    public <T extends Enum<T>> T enumSelect(Scanner scanner, Class<T> enumClass, String prompt) {
        while(true) {
        	T[] values = enumClass.getEnumConstants();
            System.out.println("\nAvailable " + enumClass.getSimpleName());
            for (int i = 0; i < values.length; i++) {
                System.out.println((i + 1) + ") " + values[i]);
            }
            
            int index = getIntInput(scanner, prompt) - 1;
            if (index >= 0 && index < values.length) {
                return values[index];
            }else {
            	System.out.println("Invalid input!");
            }
        }
    }
} 