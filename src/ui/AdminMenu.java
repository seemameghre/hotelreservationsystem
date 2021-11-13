package ui;

import api.AdminResource;
import api.HotelResource;
import models.*;
import util.InputValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/** This class handles all admin menu operations.
 * It has methods to display main menu, read user input and
 * then calling corresponding resource methods
 */
public class AdminMenu {
    private AdminResource adminResource = AdminResource.getInstance();
    private HotelResource hotelResource = HotelResource.getInstance();
    MenuHelper menuHelper = new MenuHelper();

    /** This is the entry point in admin menu. It reads admin menu choice and calls corresponding method
     * until user selects option to go back to main menu
     */
    public void start(){
        boolean done = false;
        int choice;

        do {
            displayAdminMenu();
            System.out.print("Enter your choice: ");
            choice = menuHelper.readInt();
            switch (choice) {
                case 1: seeAllCustomers(); break;
                case 2: seeAllRooms(); break;
                case 3: seeAllReservations(); break;
                case 4: addARoom(); break;
                case 5: addTestData();break;
                case 6: done = true; break;
                default: System.out.println("Please enter a valid choice:");
            }
        }while(!done);
        return;
    }
    /** Displays admin menu options */
    private void displayAdminMenu(){
        System.out.println("__________Admin Menu________");
        System.out.println("1.See all customers");
        System.out.println("2.See all rooms");
        System.out.println("3.See all reservations");
        System.out.println("4.Add a room");
        System.out.println("5.Add test data");
        System.out.println("6.Back to Main Menu");
    }
    /** Calls resource method to get all customers in collection
     * and displays the result */
    private void seeAllCustomers(){
        Collection<Customer>allCustomers = adminResource.getAllCustomers();
        if(allCustomers != null){
            if(allCustomers.isEmpty()){
                System.out.println("There are no customers.");
            }else{
                for(Customer customer:allCustomers){
                    System.out.println(customer);
                }
            }
        }
    }

    /** Calls resource methis to get all rooms in collection
     * and displays the result
     */
    private void seeAllRooms(){
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        if(allRooms == null || allRooms.isEmpty()){
            System.out.println("There are no rooms added");
        }else{
            for(IRoom room:allRooms){
                System.out.println(room);
            }
        }
    }
    /** calls resource method to show all reservations */
    private void seeAllReservations(){
        adminResource.displayAllReservations();
    }

    /** Reads and validates details of a new room from admin user
     * and calls resource method to add room
     */
    private void addARoom(){
        int roomTypeNo = 0;

        System.out.print("Enter room number: ");
        String roomNo = "";
        while (! InputValidator.isValidRoomNumber(roomNo = menuHelper.readString("room number"))){
            System.out.print("Please enter a valid room number: ");
        }
        System.out.print("Enter price per night: ");
        Double roomPrice = (Double) menuHelper.readDouble();

        System.out.print("Room type (1:SINGLE, 2: DOUBLE): ");
        while( ! InputValidator.isValidRoomType(roomTypeNo = menuHelper.readInt())){
            System.out.println("Please enter 1 or 2");
        }
        RoomType roomType = roomTypeNo == 1 ?  RoomType.SINGLE : RoomType.DOUBLE;
        IRoom newRoom = new Room(roomNo,roomPrice,roomType);
        List<IRoom> newRooms = new ArrayList<IRoom>();
        newRooms.add(newRoom);
        adminResource.addRooms(newRooms);
    }
    /** Asks type of test data to read from file and calls corresponding method */
    private void addTestData(){
        System.out.print("Enter data type (1: Room data, 2: Customer data): ");
        int choice = 0;
        choice = menuHelper.readInt();
        switch (choice){
            case 1: addRoomData();break;
            case 2: addCustomerData();break;
            default:System.out.println("Invalid input");
        }
    }

    /** Reads comma separate room data from text file and adds to rooms collection
     *
      */
    private void addRoomData(){
        System.out.println("Data format: Room number, Price, Room type (1 for Single, 2 for Double");
        System.out.println("Enter room data file name: ");
        File text = new File(menuHelper.readString("file name"));

        ArrayList<IRoom> newRooms = new ArrayList<>();
            try {
                Scanner fileScanner = new Scanner(text);

                while (fileScanner.hasNext()) {
                    Scanner lineScanner = new Scanner(fileScanner.nextLine());
                    lineScanner.useDelimiter(",");
                    String roomNumber = lineScanner.next();
                    Double price = lineScanner.nextDouble();
                    int type = lineScanner.nextInt();
                    IRoom room = null;
                    if (InputValidator.isValidRoomNumber(roomNumber) &&
                            InputValidator.isValidRoomType(type)) {
                        RoomType roomType = type == 1 ? RoomType.SINGLE : RoomType.DOUBLE;

                        if (price <= 0) {
                            room = new FreeRoom(roomNumber, price, roomType);
                        } else {
                            room = new Room(roomNumber, price, roomType);
                        }
                    }else{
                        continue;
                    }
                    newRooms.add(room);
                    lineScanner.close();
                }
                adminResource.addRooms(newRooms);
            }catch (FileNotFoundException ex){
                System.out.println("File not found.");
            }
            catch (Exception ex){
                System.out.println("Error in reading file");
            }
    }

    /** Reads comma separated customer data from text file and adds to customer colection
     */
    private void addCustomerData(){
        System.out.println("Data format: Firstname, Lastname, email");
        System.out.println("Enter customer data file name: ");
        File text = new File(menuHelper.readString("file name"));

        try {
            Scanner fileScanner = new Scanner(text);
            while (fileScanner.hasNextLine()) {
                Scanner lineScanner = new Scanner(fileScanner.nextLine());
                lineScanner.useDelimiter(",");
                String firstName = lineScanner.next();
                String lastName = lineScanner.next();
                String email = lineScanner.next();
                if(InputValidator.isValidName(firstName) &&
                    InputValidator.isValidName(lastName) &&
                        InputValidator.isValidEmail(email)){
                    hotelResource.createACustomer(firstName,lastName,email);
                }else{
                    continue;
                }
                lineScanner.close();
            }

        }catch (FileNotFoundException ex){
            System.out.println("File not found.");
        }
        catch (Exception ex){
            System.out.println("Error in reading file");
        }
    }
} //end of AdminMenu class
