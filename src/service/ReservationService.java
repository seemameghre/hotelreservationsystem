package service;

import models.Customer;
import models.IRoom;
import models.Reservation;

import java.util.*;

/** Service class for reservation related services
 *
 */
public class ReservationService {

    private static ReservationService reservationService = null;
    private Set<IRoom> rooms = new HashSet<IRoom>();
    private Set<Reservation> reservations = new HashSet<Reservation>();

    private ReservationService(){}

    /** Instantiates and returns new instance of ReservationService if not instantiated already
     *
     * @return ReservationService
     */
    public static ReservationService getInstance(){
        if(reservationService == null){
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    /** Add a new room to the collection of rooms
     *
     * @param room
     */
    public void addRoom(IRoom room){
        if(rooms.add(room)){
            System.out.println("Room added successfully.");
        }else {
            System.out.println("Room with same number already exists.");
        }
    }

    /** Returns a room from the collection for matching room number
     *
     * @param roomId
     * @return room
     */
    public IRoom getARoom(String roomId){
        Iterator iterator = rooms.iterator();
        IRoom room = null;

        while(iterator.hasNext()) {
            room = (IRoom) iterator.next();
            if (room.getRoomNumber().equals(roomId)){
                return room;
            }
        }
        return null;
    }

    /** checks if dates overlap with existing reservation
     * Reserves a room if not overlapping
     * @param customer
     * @param room
     * @param checkInDate
     * @param checkOutDate
     * @return Reservation on success, null if not reserved
     */
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        //Check if dates overlap with any existing reservation for the room
        for(Reservation reservation:reservations){
            if(reservation.getRoom().equals(room) &&
                areDatesOverlapping(checkInDate,checkOutDate,reservation.getCheckInDate(),reservation.getCheckOutDate())){
                    System.out.println("Reservation overlaps with existing reservation");
                    return null;
            }
        }
        //Dates are not overlapping. Add new reservation
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(newReservation);
        return newReservation;
    }

    /** Returns an arraylist of reservations for a given customer
     *
     * @param customer
     * @return ArrayList<Reservation>
     */
    public Collection<Reservation> getCustomerReservations(Customer customer){

        ArrayList<Reservation> customerReservations = new ArrayList<Reservation>();
        Reservation currentReservation = null;

        Iterator iterator = reservations.iterator();
        while (iterator.hasNext()){
            currentReservation = (Reservation) iterator.next();
            if(currentReservation.getCustomer().equals(customer)){
                    //Found reservation for the given customer
                customerReservations.add(currentReservation);
            }
        }
        return customerReservations;
    }

    /** checks reservations to find all room available for given dates
     *
      * @param checkInDate
     * @param checkOutDate
     * @return HashSet availableRooms
     */
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        //Initially add all rooms to available rooms
        HashSet<IRoom> availableRooms = new HashSet<IRoom>(rooms);
        IRoom reservedRoom = null;

        for(Reservation reservation:reservations) {
            reservedRoom = reservation.getRoom();

            //If room is already removed from available rooms, no need to check its dates
            if (availableRooms.contains(reservedRoom)) {
                if (areDatesOverlapping(checkInDate, checkOutDate, reservation.getCheckInDate(), reservation.getCheckOutDate())) {
                    availableRooms.remove(reservedRoom);
                }
            }
        }
        return availableRooms;
    }

    /** Displays all the reservations
     *
     */
    public void printAllReservations(){

        if(reservations.isEmpty()){
            System.out.println("There are no reservations.");
            return;
        }
        System.out.println("All Reservations: ");
        for (Reservation reservation: reservations) {
            System.out.println(reservation);
        }
    }
    /** returns all the rooms in collection */
    public Collection<IRoom> getAllRooms(){
        return rooms;
    }

    /** checks if 2 sets of check in and check out dates overlap
     *
     * @param checkIn1
     * @param checkOut1
     * @param checkIn2
     * @param checkOut2
     * @return true if dates overlap, false otherwise
     */
    boolean areDatesOverlapping(Date checkIn1, Date checkOut1, Date checkIn2, Date checkOut2){

        if(!(checkOut2.before(checkIn1) || checkIn2.after(checkOut1)))
            return true;
        else
            return false;
    }
}
