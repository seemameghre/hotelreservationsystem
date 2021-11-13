package api;

import models.Customer;
import models.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

/** This class is intermediary between admin UI and admin services */
public class AdminResource {
    private static AdminResource adminResource = null;
    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();

    private AdminResource(){
    }
    public static AdminResource getInstance(){
        if(adminResource == null){
            adminResource = new AdminResource();
        }
        return adminResource;
    }
    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }
    public void addRooms(List<IRoom> rooms){
        try{
            for(IRoom room:rooms){
                reservationService.addRoom(room);
            }
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
    public Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }
    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    public void displayAllReservations(){
        reservationService.printAllReservations();
    }
}
