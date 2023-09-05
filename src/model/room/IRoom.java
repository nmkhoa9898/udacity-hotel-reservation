package model.room;

import model.room.enums.RoomType;

public interface IRoom {
    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getEnumeration();
    public boolean isFree();
}
