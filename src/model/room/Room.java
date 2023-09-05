package model.room;

import model.room.enums.RoomType;

import java.util.Objects;

public class Room implements IRoom {

    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;

    public Room(final String roomNumber, final Double price, final RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    public String getRoomNumber() {
        return this.roomNumber;
    }

    public Double getRoomPrice() {
        return this.price;
    }

    public RoomType getEnumeration() {
        return this.enumeration;
    }

    public boolean isFree() {
        return this.price != null && this.price.equals(0.0);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Room room)) {
            return false;
        }
        return Objects.equals(this.roomNumber, room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    @Override
    public String toString() {
        return "Room Information:" +
                "\nRoom Number: " + this.roomNumber + "\nRoom Price: $" + this.price + "\nRoom Type: " + this.enumeration
                + "\n-----------------------------------";
    }
}
