package models;

import ui.HotelApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/** Model class for storing all details of reservation */
public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    private static final DateFormat dateFormat = new SimpleDateFormat(HotelApplication.DATEFORMAT);

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString(){
        return "Customer: " + customer.getEmail() +
                " Room: " + room.getRoomNumber() +
                " Check In: " + dateFormat.format(checkInDate) +
                " Check Out: " + dateFormat.format(checkOutDate);
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }
}
