package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/** This class helps the menu classes to read input from user.
 * It reads input and does basic format check on input.
 * Application specific checks are done by InputValidator class.
 */
public class MenuHelper {
    /* reads a string input and returns it */
    public String readString(String inputName){
        boolean done = false;
        String str = "";

        do {
            try {
                Scanner scanner = new Scanner(System.in);
                str = scanner.nextLine();
                done = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid "+ inputName);
                continue;
            }
        }while(!done);
        return str;
    }
    /* tries to read valid int until suceessful and returns the int */
    public int readInt(){
        boolean done = false;
        int number = 0;

        do {
            try {
                Scanner scanner = new Scanner(System.in);
                number = scanner.nextInt();
                done = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }
        }while(!done);
        return number;
    }
    /* keeps asking the user to enter double value until successful. then returns the value */
    public Double readDouble(){
        boolean done = false;
        double number = 0.0;

        do {
            try {
                Scanner scanner = new Scanner(System.in);
                number = scanner.nextDouble();
                done = true;
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid number.");
                continue;
            }
        }while(!done);
        return number;
    }
    /* keeps asking user to enter a character and returns the character */
    public char readChar(){
        boolean done = false;
        String input = "";
        do {
            try {
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextLine();
                done = true;
            } catch (InputMismatchException e) {
                System.out.print("Please enter a response");
                continue;
            }
        }while(!done);
        return input.charAt(0);
    }
    /* reads date in specific format. keeps asking to enter valid format date until successful
    * returns the date on successfully reading the date.
     */
    public Date readDate(){
        boolean done = false;
        Date date = new Date();
        do{
            try{
                Scanner scanner = new Scanner(System.in);
                String str = scanner.nextLine();
                SimpleDateFormat sdf = new SimpleDateFormat(HotelApplication.DATEFORMAT);
                sdf.setLenient(false);
                date = sdf.parse(str);
                done = true;
            }catch (ParseException ex){
                System.out.print("Please enter a valid date in format ("+HotelApplication.DATEFORMAT+"): ");
                continue;
            }
            catch (Exception ex){
                System.out.println(ex.getLocalizedMessage());
                continue;
            }
        }while (! done);
        return date;
    }
}
