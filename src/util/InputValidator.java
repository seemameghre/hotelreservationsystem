package util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This class has static methods to validate input data for application specific validations
 *
 */
public class InputValidator {
    private static InputValidator inputValidator = null;
    private InputValidator(){}

    public static InputValidator getInstance(){
        if(inputValidator == null){
            inputValidator = new InputValidator();
        }
        return inputValidator;
    }
    /** Checks if room type input is one of the possible types */
    public static boolean isValidRoomType(int roomTypeNo){
        if(roomTypeNo == 1 || roomTypeNo ==2 ){
            return true;
        }else
            return false;
    }
    /** Checks if email id follows specific pattern */
    public static boolean isValidEmail(String str){
        Pattern pattern = Pattern.compile("^.+@.+\\.com$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    /** Check if room number is numeric */
    public static boolean isValidRoomNumber(String str){
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    /** Checks if name contains only alphabets */
    public static boolean isValidName(String str){
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    /** Does sanity check on check in and check out date */
    public static boolean areDatesValid(Date checkInDate, Date checkOutDate){
        Date today = new Date();
        if(checkInDate.before(today)){
            System.out.println("Check in date must be after today's date.");
            return false;
        }else if(checkOutDate.before(today)){
            System.out.println("Check out date must be after today's date.");
            return false;
        }else if(checkOutDate.before(checkInDate)){
            System.out.println("Check out date must be after check in date.");
            return false;
        }
        return true;
    }
    /** Checks if Yes No response is one of the allowed characters */
    public static boolean isYNvalid(char c){
        if(c == 'Y' || c == 'y' || c == 'n' || c== 'N'){
            return true;
        }else
            return false;
    }
}
