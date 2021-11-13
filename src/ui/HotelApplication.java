package ui;

/** This class has the main method that starts the application */
public class HotelApplication {
    public static final String DATEFORMAT = "dd/MM/yyyy";

    public static void main(String []args){
        MainMenu mainMenu = new MainMenu();

        System.out.println("Welcome to Hotel Reserva1tion System!");
        System.out.println("____________________________________");
        mainMenu.start();
    }
}
