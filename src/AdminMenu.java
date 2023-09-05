import api.AdminResource;
import model.customer.Customer;
import model.room.IRoom;
import model.room.Room;
import model.room.enums.RoomType;
import utility.Utility;

import java.util.*;

import static utility.Utility.scanner;

public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getAdminResourceInstance();

    private static void printAdminMenu() {
        System.out.print("""
                Admin Menu
                --------------------------------------------
                1. See all Customers
                2. See all Rooms
                3. See all Reservations
                4. Add a Room
                5. Back to Main Menu
                --------------------------------------------
                Please select a number to continue:
                """);
    }

    public static void adminMenu() {
        printAdminMenu();
        boolean exitLoop = false;
        int input;
        while (!exitLoop) {
            input = Utility.getUserSelection(5);
            switch (input) {
                case 1 -> printAllCustomers();
                case 2 -> printAllRooms();
                case 3 -> printAllReservations();
                case 4 -> addRoom();
                case 5 -> {
                    MainMenu.mainMenu();
                    exitLoop = true;
                }
                default -> System.out.println("Please enter a valid input");
            }
        }
    }

    private static void printAllReservations() {
        adminResource.displayAllReservations();
        adminMenu();
    }

    private static void addRoom() {
        System.out.println("Enter room number:");
        String roomNumber = scanner.nextLine();
        System.out.println("Enter price/night:");
        double roomPrice = Utility.getUserDoubleInput();
        System.out.println("Enter room type: 1 for SINGLE BED, 2 for DOUBLE BED:");
        RoomType roomType = Utility.getUserRoomTypeInput();

        Room room = new Room(roomNumber, roomPrice, roomType);
        List<IRoom> rooms = new ArrayList<>();
        rooms.add(room);
        adminResource.addRoom(rooms);
        addMoreRoom();
    }

    private static void addMoreRoom() {
        try {
            String input = Utility.getUserBinarySelection(scanner.nextLine());
            switch (input) {
                case "Y" -> addRoom();
                case "N" -> adminMenu();
            }
        } catch (StringIndexOutOfBoundsException ex) {
            addMoreRoom();
        }
    }

    private static void printAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if(!rooms.isEmpty()) {
            adminResource.getAllRooms().forEach(System.out::println);
        } else {
            System.out.println("No room found. Please select other functions");
        }
        adminMenu();
    }

    private static void printAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (!customers.isEmpty()) {
            adminResource.getAllCustomers().forEach(System.out::println);
        } else {
            System.out.println("No customers found. Please select other functions");
        }
        adminMenu();
    }
}
