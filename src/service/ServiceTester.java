package service;

import models.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class ServiceTester {
    public static void main(String [] args){
        CustomerService customerService = CustomerService.getInstance();
        ReservationService reservationService = ReservationService.getInstance();

        try{
            customerService.addCustomer("seema","meghre","seema@hotmail.com");
            customerService.addCustomer("sumedh","mehta","sumedh@hotmail.com");
            Customer c1 = customerService.getCustomer("sumedh@hotmail.com");
            Customer c2 = customerService.getCustomer("seema@hotmail.com");
//            System.out.println(c);
//            Collection<Customer> clist = customerService.getAllCustomers();
//            for (Customer customer: clist) {
//                System.out.println(customer);
//            }
//            reservationService.printAllReservations();
            IRoom r1 = new Room("101",120.0, RoomType.DOUBLE);
//            System.out.println(r1);
            reservationService.addRoom(r1);

            IRoom r2 = new Room("102",100.0, RoomType.SINGLE);
////            System.out.println(r2);
            reservationService.addRoom(r2);

            Calendar calendar = Calendar.getInstance();
            calendar.set(2021,10,5);
            Date checkInDate = calendar.getTime();
            calendar.set(2021,10,15);
            Date checkOutDate = calendar.getTime();

            reservationService.reserveARoom(c1,r1,checkInDate, checkOutDate);

            calendar.set(2021,10,6);
            Date searchCheckInDate = calendar.getTime();
            calendar.set(2021,10,20);
            Date searchCheckOutDate = calendar.getTime();
//            reservationService.reserveARoom(c2, r1, searchCheckInDate, searchCheckOutDate);
            reservationService.printAllReservations();

            Collection<IRoom> availableRooms = reservationService.findRooms(searchCheckInDate,searchCheckOutDate);

            System.out.println("Available rooms:");
            for (IRoom room:availableRooms){
                System.out.println(room);
                System.out.println("----------------------");
            }

        }catch (IllegalArgumentException e){
            System.out.println(e.getLocalizedMessage());
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }

    }
}
