package service.reservation;

import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;

import java.util.*;

public class ReservationService {

    private static final ReservationService INSTANCE = new ReservationService();
    private static final int EXTENDED_DAY_DEFAULT = 7;
    private final Set<IRoom> rooms = new HashSet<>();
    private final Set<Reservation> reservations = new HashSet<>();

    private ReservationService() {}

    public static ReservationService getReservationServiceInstance() {
        return INSTANCE;
    }

    public void addRoom(final IRoom room) {
        if (getARoom(room.getRoomNumber()) == null) {
            rooms.add(room);
            System.out.println("Room added!" +
                    "\nDo you want to add more room?");
        } else {
            System.out.println("Room already exists. Do you want to add a different room?");
        }
    }

    public IRoom getARoom(final String roomNumber) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    public Collection<IRoom> getAllRooms() {
        return rooms;
    }

    public Reservation reserveARoom(final Customer customer, final IRoom room, final Date checkInDate, final Date checkOutDate) {
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(newReservation);
        return newReservation;
    }

    private boolean isRoomAvailable(IRoom room, final Date checkInDate, final Date checkOutDate) {
        boolean isAvailable = true;
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room)) {
                if (!(checkOutDate.before(reservation.getCheckInDate())
                        || checkInDate.after(reservation.getCheckOutDate()))) {
                    return false;
                }
            }
        }
        return isAvailable;
    }

    public Collection<IRoom> findRooms(final Date checkInDate, final Date checkOutDate) {
        return findAvailableRooms(checkInDate, checkOutDate);
    }

    public Collection<IRoom> findAlternativeRooms(final Date checkInDate, final Date checkOutDate) {
        // Find rooms within the day range
        return findAvailableRooms(extendReservationDay(checkInDate), extendReservationDay(checkOutDate));
    }

    private Collection<IRoom> findAvailableRooms(final Date checkInDate, final Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : rooms) {
            if (isRoomAvailable(room, checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Date extendReservationDay(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, EXTENDED_DAY_DEFAULT);
        return calendar.getTime();
    }

    public Collection<Reservation> getCustomersReservation(final Customer customer) {
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservations ) {
            if (reservation.getCustomer().equals(customer)){
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    public void printAllReservation() {
        Collection<Reservation> reservations = getAllReservations();
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                System.out.println(reservation + "\n");
            }
        } else {
            System.out.println("No reservations found. Please select other functions");
        }
    }

    private Collection<Reservation> getAllReservations() {
        return reservations;
    }
}
