package api;

import models.Customer;
import models.IRoom;
import models.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

/** This class is intermediary between UI and service */
public class HotelResource {

    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    private HotelResource(){
    }
    private static HotelResource hotelResource = null;

    public static HotelResource getInstance(){
        if(hotelResource == null){
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }
    public void createACustomer(String firstName, String lastName, String email){
            customerService.addCustomer(firstName, lastName, email);
    }
    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }
    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }
    public Reservation bookARoom(String email, IRoom room, Date checkInDate, Date checkOutDate){
        Customer customer = customerService.getCustomer(email);
        if(customer != null){
            return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
        }else {
            System.out.println("Customer does not exist.");
            return null;
        }
    }
    public Collection<Reservation> getCustomersReservations(String email){
        Customer customer = customerService.getCustomer(email);
        if(customer != null){
            return reservationService.getCustomerReservations(customer);
        }else{
            System.out.println("Customer does not exist.");
            return null;
        }
    }
    public Collection<IRoom> findARoom(Date checkInDate, Date checOutDate){
        return reservationService.findRooms(checkInDate, checOutDate);
    }
} //end of HotelResource class
