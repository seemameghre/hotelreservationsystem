package models;

import java.util.Objects;

/** This class implements IRoom interface */
public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType roomType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    public Room(String roomNumber, Double price, RoomType roomType) throws IllegalArgumentException{
        if(price < 0){
            throw new IllegalArgumentException("Price cannot be less than 0.");
        }
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString(){
        return "Room No: "+ roomNumber +
                " Price: " + price +
                " Type: " + roomType;
    }

}
