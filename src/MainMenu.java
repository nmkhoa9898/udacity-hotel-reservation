import api.HotelResource;
import model.reservation.Reservation;
import model.room.IRoom;

import java.util.Collection;
import java.util.Date;

import utility.Utility;

import static utility.Utility.scanner;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getHotelResourceInstance();
    public static void printMainMenu()
    {
        System.out.print("""
                Hotel Reservation Application
                --------------------------------------------
                1. Find and reserve a room
                2. See my reservations
                3. Create new account
                4. Admin Menu
                5. Exit
                --------------------------------------------
                Please select a number to continue:
                """);
    }

    public static void mainMenu() {
        printMainMenu();
        boolean exitLoop = false;
        int input;
        while (!exitLoop) {
            input = Utility.getUserSelection(5);
            switch (input) {
                case 1 -> findAndReserveRoom();
                case 2 -> printUserReservations();
                case 3 -> createNewAccount();
                case 4 -> AdminMenu.adminMenu();
                case 5 -> {
                    System.out.println("Exit");
                    exitLoop = true;
                }
                default -> System.out.println("Please enter a valid input");
            }
        }
    }

    private static void processReservation(Date checkInDate, Date checkOutDate) {
        // Try to find most suitable rooms
        Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        if (!availableRooms.isEmpty()) {
            printRooms(availableRooms);
            reserveRoom(checkInDate, checkOutDate, availableRooms);
        } else {
            // Try to find alternative rooms
            Collection<IRoom> alternativeRooms = hotelResource.findAlternativeRooms(checkInDate, checkOutDate);
            if (!alternativeRooms.isEmpty()) {
                Date alternativeCheckInDate = hotelResource.addExtendedDay(checkInDate);
                Date alternativeCheckOutDate = hotelResource.addExtendedDay(checkOutDate);
                System.out.println("There are alternative rooms available on these days: Check-In Date:" + alternativeCheckInDate + " Check-Out Date:" + alternativeCheckOutDate);
                printRooms(alternativeRooms);
                reserveRoom(alternativeCheckInDate, alternativeCheckOutDate, alternativeRooms);
            } else {
                System.out.println("No rooms found. Please select other functions");
                mainMenu();
            }
        }
    }

    private static void findAndReserveRoom() {
        Date checkInDate = Utility.getUserDateInput("Check in date");
        Date checkOutDate = Utility.getUserDateInput("Check out date");
        if (checkInDate != null && checkOutDate != null) {
            processReservation(checkInDate, checkOutDate);
        }
    }

    private static void validateAndBookRoom (String email, Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        System.out.println("Please enter room number");
        String roomNumber = String.valueOf(Utility.getUserNumberInput());

        for (IRoom availableRoom : rooms ) {
            if (availableRoom.getRoomNumber().equals(roomNumber)){
                IRoom room = hotelResource.getRoom(roomNumber);
                Reservation reservation = hotelResource.bookARoom(email, room, checkInDate, checkOutDate);
                System.out.println(reservation);
                return;
            }
        }
        System.out.println("Error: room number not available.");
        mainMenu();
    }

    private static void validateUserAccount(Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        System.out.println("Do you have an account?");
        String isCustomerHasAccount = Utility.getUserBinarySelection(scanner.nextLine());
        switch (isCustomerHasAccount) {
            case "Y" -> {
                System.out.println("Please enter your email in the format of name@domain.com");
                String email = scanner.nextLine();
                if (hotelResource.isCustomerExisted(email)){
                    validateAndBookRoom(email, checkInDate, checkOutDate, rooms);
                } else {
                    System.out.println("We cannot find your account, please create a new one.");
                    mainMenu();
                }
            }
            case "N" -> {
                System.out.println("Please create a new account to continue");
                mainMenu();
            }
        }
    }

    private static void reserveRoom(Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        System.out.println("Do you want to book a room?");
        String input = Utility.getUserBinarySelection(scanner.nextLine());
        switch (input) {
            case "Y" -> validateUserAccount(checkInDate, checkOutDate, rooms);
            case "N" -> mainMenu();
        }
    }

    private static void printRooms(final Collection<IRoom> rooms) {
        if (!rooms.isEmpty()) {
            rooms.forEach(System.out::println);
        } else {
            System.out.println("No rooms found. Please select other functions");
        }
    }

    private static void printUserReservations() {
        System.out.println("Enter your email (format: name@domain.com)");
        final String customerEmail = scanner.nextLine();
        printReservations(hotelResource.getCustomersReservations(customerEmail));
    }

    private static void printReservations(final Collection<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found. Please select other functions");
        } else {
            reservations.forEach(reservation -> System.out.println("\n" + reservation));
        }
    }

    private static void createNewAccount() {
        System.out.println("Please enter your email in the format of name@domain.com");
        final String email = scanner.nextLine();
        System.out.println("First Name:");
        final String firstName = scanner.nextLine();
        System.out.println("Last Name:");
        final String lastName = scanner.nextLine();
        try {
            hotelResource.createACustomer(email, firstName, lastName);
            mainMenu();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            createNewAccount();
        }
    }
}
