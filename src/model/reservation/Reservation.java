package model.reservation;

import model.customer.Customer;
import model.room.IRoom;

import java.util.Date;
import java.util.Objects;

public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(final Customer customer, final IRoom room, final Date checkInDate, final Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public IRoom getRoom() {
        return this.room;
    }

    public Date getCheckInDate() {
        return this.checkInDate;
    }

    public Date getCheckOutDate() {
        return this.checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "Reservation Information: " + this.customer.toString()
                + "\nRoom Information: " + this.room.toString()
                + "\nCheckIn Date: " + this.checkInDate
                + "\nCheckOut Date: " + this.checkOutDate
                + "\n-----------------------------------";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return Objects.equals(customer, that.customer) && Objects.equals(getRoom(), that.getRoom()) && Objects.equals(getCheckInDate(), that.getCheckInDate()) && Objects.equals(getCheckOutDate(), that.getCheckOutDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, getRoom(), getCheckInDate(), getCheckOutDate());
    }
}
