package api;

import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;
import service.customer.CustomerService;
import service.reservation.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class HotelResource {

    private static final HotelResource INSTANCE = new HotelResource();

    private final CustomerService customerService = CustomerService.getCustomerServiceInstance();
    private final ReservationService reservationService = ReservationService.getReservationServiceInstance();

    private HotelResource() {}

    public static HotelResource getHotelResourceInstance() {
        return INSTANCE;
    }

    public boolean isCustomerExisted(String email) {
        return customerService.isCustomerExisted(email);
    }

    public boolean isRoomExisted(String roomNumber) {
        for ( IRoom room : reservationService.getAllRooms()) {
            if (Objects.equals(room.getRoomNumber(), roomNumber)) {
                return true;
            }
        }
        return false;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return reservationService.getCustomersReservation(getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(final Date checkIn, final Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    public Collection<IRoom> findAlternativeRooms(final Date checkInDate, final Date checkOutDate) {
        return reservationService.findAlternativeRooms(checkInDate, checkOutDate);
    }

    public Date addExtendedDay(final Date date) {
        return reservationService.extendReservationDay(date);
    }
}
