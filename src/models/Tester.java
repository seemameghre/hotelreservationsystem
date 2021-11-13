package models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/** Tester class for the models
 *
 */
public class Tester {
    public static void main(String [] args){
        Customer customer = new Customer("seema","meghre","seema@a.com");
        Customer c2 = new Customer("Sumedh","Mehta","sumedh@gmail.com");

        IRoom r1 = new Room("101",100.0,RoomType.DOUBLE);
        IRoom r2 = new FreeRoom("101",0.0,RoomType.SINGLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2021,9,3);
        Date checkInDate = calendar.getTime();
        calendar.set(2021,10,9);
        Date checkOutDate = calendar.getTime();

        Set<Date> set1 = getDatesBetween(checkInDate,checkOutDate);

        calendar.set(2021,10,7);
        Date searchCheckInDate = calendar.getTime();
        calendar.set(2021,10,10);
        Date searchCheckOutDate = calendar.getTime();

        Reservation res1 = new Reservation(customer,r1,checkInDate,checkOutDate);
        Reservation res2 = new Reservation(c2,r1,searchCheckInDate,searchCheckOutDate);
        System.out.println(areDatesOverlapping(checkInDate,checkOutDate,searchCheckInDate,searchCheckOutDate));

    }
    public static Set<Date> getDatesBetween(Date date1, Date date2){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HashSet<Date> datesBetween = new HashSet<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);

        do{
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            date1 = calendar.getTime();
            datesBetween.add(date1);
        }while(date1.before(date2));

        return datesBetween;
    }
    public static boolean areDatesOverlapping(Date checkIn1, Date checkOut1, Date checkIn2, Date checkOut2){

        Set<Date> set1 = getDatesBetween(checkIn1, checkOut1);
        Set<Date> set2 = getDatesBetween(checkIn2, checkOut2);

        set1.retainAll(set2);
        return !set1.isEmpty();
    }
}
