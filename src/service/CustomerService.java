package service;

import models.Customer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


/** Service class for customer related operations
 *
 */
public class CustomerService {
    private static CustomerService customerService = null;
    private HashSet<Customer> customers = new HashSet<Customer>();
    private CustomerService(){}

    /** method creates the single instance of CustomerService if already not created
     * and returns it
     * @return customerService
     */
    public static CustomerService getInstance(){
        if(customerService == null){
            customerService = new CustomerService();
        }
        return customerService;
    }

    /** Creates new customer and adds it to collection
     *
     * @param firstName
     * @param lastName
     * @param email
     */
    public void addCustomer(String firstName, String lastName, String email){

        Customer customer = new Customer(firstName, lastName, email);
        if(customers.add(customer)){
            System.out.println("New customer added.");
        }else {
            System.out.println("Customer with the email exists already.");
        }
    }

    /** Finds and returns the customer from given email
     *
     * @param email
     * @return Customer if found, else returns null
     */
    public Customer getCustomer(String email){

        Iterator iterator = customers.iterator();
        Customer customer = null;

        while (iterator.hasNext()){
            customer = (Customer) iterator.next();
            if(customer.getEmail().equals(email))
                return customer;
        }
        return null;
    }

    /** returns all the customers
     *
     * @return collection of all customers
     */
    public Collection<Customer> getAllCustomers(){
        return customers;
    }
}
