package utility;

import model.room.enums.RoomType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public interface Utility {

    Scanner scanner = new Scanner(System.in);

    String DATE_FORMAT = "MM/dd/yyyy";
    static int getUserSelection(int maximumInputValueAllowed) {
        int input = 0;
        while(input < 1 || input > maximumInputValueAllowed) {
            System.out.print("Enter your choice (1-" + maximumInputValueAllowed + "): ");
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input < 1 || input > maximumInputValueAllowed) {
                    System.out.println("Invalid input. Please enter a number between 1 and " + maximumInputValueAllowed + ".");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return input;
    }

    static int getUserNumberInput () {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number format! Please enter a valid integer");
            getUserNumberInput();
        }
        return 0;
    }

    static String getUserBinarySelection(String input) {
        while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
            System.out.println("Please enter Y (Yes) or N (No)");
            input = scanner.nextLine();
        }
        return input.toUpperCase();
    }

    static double getUserDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid room price format! Please enter a valid amount");
            return getUserDoubleInput();
        }
    }

    static RoomType getUserRoomTypeInput() {
        try {
            return RoomType.getLabelValue(scanner.nextLine());
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid room type! Please choose 1 for SINGLE BED or 2 for DOUBLE BED");
            return getUserRoomTypeInput();
        }
    }

    static Date getUserDateInput(String dateType) {
        String message = "Please enter " + dateType + " in mm/dd/yyyy format";
        try {
            System.out.println(message);
            return new SimpleDateFormat(DATE_FORMAT).parse(Utility.scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Error: Invalid date.");
            getUserDateInput(dateType);
        }
        return null;
    }

}
