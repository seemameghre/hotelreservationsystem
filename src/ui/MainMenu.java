package ui;

import api.HotelResource;
import models.IRoom;
import models.Reservation;
import util.InputValidator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/** This class handles all main menu operations i.e
 *  displaying main menu, reading user input and then calling corresponding resource methods
 */
public class MainMenu {
    private HotelResource hotelResource = HotelResource.getInstance();
    private MenuHelper menuHelper = new MenuHelper();
    public final SimpleDateFormat sdf = new SimpleDateFormat(HotelApplication.DATEFORMAT);

    /** This is the only public method and entry point in Main Menu
     * It keeps reading menu option from user until user exits the application
     */
    public void start(){
        boolean done = false;
        int choice;

        do{
            displayMainMenu();
            System.out.print("Enter your choice: ");
            choice = menuHelper.readInt() ;
            switch (choice){
                case 1: bookARoom();break;
                case 2: seeMyReservations();break;
                case 3: createAccount();break;
                case 4: {
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.start();
                    break;
                }
                case 5: {
                    System.out.println("Thank you!");
                    done = true;
                    break;
                }
                default:{
                    System.out.println("Please enter a valid choice:");
                }
            }
        }while(!done);
    }
    /** shows main menu options */
    private void displayMainMenu(){
        System.out.println("Main Menu");
        System.out.println("1.Find and reserve a room");
        System.out.println("2.See my reservations");
        System.out.println("3.Create an account");
        System.out.println("4.Go to Admin menu");
        System.out.println("5.Exit");
    }

    /** Reads and validates customer email id and
     * calls resource method to get all reservations for the id.
     */
    private void seeMyReservations(){
        String customerEmail = "";

        System.out.print("Enter email id (username@domain.com): ");
        while( ! InputValidator.isValidEmail(customerEmail = menuHelper.readString("email id"))){
            System.out.print("Please enter valid email id: ");
        }
        Collection<Reservation> customerReservations = hotelResource.getCustomersReservations(customerEmail);
        if(customerReservations != null) {
            if (customerReservations.isEmpty())
                System.out.println("There are no reservations for the customer.");
            else {
                for (Reservation reservation : customerReservations) {
                    System.out.println(reservation);
                }
            }
        }
    }

    /** Reads customer details, validates the inputs and
     * calls resource method to create new account
     */
    private void createAccount(){
        String email, firstName, lastName;

        System.out.print("Enter email id (username@domain.com): ");
        while(! InputValidator.isValidEmail(email = menuHelper.readString("email id"))){
            System.out.print("Please enter a valid email id:");
        }

        if((hotelResource.getCustomer(email)) != null){
            System.out.println("Customer with email id already exists.");
            return;
        }

        System.out.print("Please enter your first name: ");
        while (! InputValidator.isValidName(firstName = menuHelper.readString("first name"))){
            System.out.println("Please enter a valid first name");
        }

        System.out.print("Please enter your last name: ");
        while (! InputValidator.isValidName(lastName = menuHelper.readString("last name"))){
            System.out.println("Please enter a valid last name");
        }

        hotelResource.createACustomer(firstName,lastName,email);
    }
    /** This method handles the flow for booking a room depending on user input */
    private void bookARoom(){
        char choice = 'n';
        char haveAccount = 'N';
        String email = "";
        String roomNo = "";

        Date checkInDate, checkOutDate;
        boolean roomsFound = false;

        do{
            //Get valid check in, check out dates from user
            System.out.print("Enter check in date ("+HotelApplication.DATEFORMAT+"): ");
            checkInDate = menuHelper.readDate();
            System.out.print("Enter check out date ("+HotelApplication.DATEFORMAT+"): ");
            checkOutDate = menuHelper.readDate();
        }while (! InputValidator.areDatesValid(checkInDate,checkOutDate));

        //Dates are valid, search rooms now
        Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        if( availableRooms == null || availableRooms.isEmpty()) {
            System.out.println("No rooms are available for the given dates.");
            System.out.print("How many days out do you want to search? ");
            int numberOfDays = menuHelper.readInt();
            //search for alternate days
            Calendar c = Calendar.getInstance();
            c.setTime(checkInDate);
            c.add(Calendar.DAY_OF_MONTH, numberOfDays);
            checkInDate = c.getTime();
            c.setTime(checkOutDate);
            c.add(Calendar.DAY_OF_MONTH, numberOfDays);
            checkOutDate = c.getTime();
            //If dates are valid, search for rooms
            if(InputValidator.areDatesValid(checkInDate,checkOutDate))
                availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);

            if(availableRooms != null && !availableRooms.isEmpty()){
                roomsFound = true;
            }else{
                System.out.println("Sorry! No rooms are available on the alternate days either.");
            }
        }
        else {
            roomsFound = true;
        }

        if(roomsFound){
            System.out.println("Rooms available for "+sdf.format(checkInDate)+" to "+sdf.format(checkOutDate));
            for (IRoom room : availableRooms) {
                System.out.println(room);
            }
        }else
            return;

        System.out.println("Do you want to book a room? (Y/N): ");
        while (! InputValidator.isYNvalid(choice = menuHelper.readChar())){
            System.out.print("Please enter a valid choice: ");
        }
        if(choice == 'N' || choice =='n'){
            //Customer does not want to book a room
            return;
        }else{
            System.out.print("Do you have an account? (Y/N): ");
            while (! InputValidator.isYNvalid(haveAccount = menuHelper.readChar())){
                System.out.print("Please enter a valid choice: ");
            }
            if(haveAccount == 'n' || haveAccount == 'N'){
                //Customer does not have an account. Ask to create account
                System.out.println("Please create an account and then book a room.");
//                createAccount();
                return;
            }else {
                //Customer has an account
                System.out.print("Enter email id (username@domain.com): ");
                while (! InputValidator.isValidEmail(email = menuHelper.readString("email"))){
                    System.out.print("Please enter a valid email id: ");
                }
                //search if customer with this email id exist
                if(hotelResource.getCustomer(email) == null){
                    System.out.println("There is no account with this email id. Please create an account and then book a room.");
                    return;
                }else {
                    //Customer found. Proceed to book a room
                    boolean validRoomNo = false;
                    while(!validRoomNo){
                        System.out.print("Enter the room number to book: ");
                        while(! InputValidator.isValidRoomNumber(roomNo = menuHelper.readString("room number"))){
                            System.out.println("Please enter a valid room number.");
                        }
//                        System.out.println(roomNo);
                        IRoom room = hotelResource.getRoom(roomNo);
//                        System.out.println(room);
//                        System.out.println(availableRooms);
                        if(room == null || ! availableRooms.contains(room)){
                            System.out.println("Please enter the room number from available rooms list.");
                            validRoomNo = false;
                            roomNo="";
                        }else {
                            Reservation reservation = hotelResource.bookARoom(email,room,checkInDate,checkOutDate);
                            System.out.println(reservation);
                            validRoomNo = true;
                        }
                    }
                }
            }
        }
    }
} //end of MainMenu class
