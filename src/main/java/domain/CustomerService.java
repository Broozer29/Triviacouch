package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerService {
	/*
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	
	public Customer createCustomer(String nm, String em) {
		Customer customer = new Customer(nm, em);
		customers.add(customer);
		return customer;
	}
	
	public Customer getCustomer(int id) {
		for (Customer c : customers) {
			if (c.getId() == id) {
				return c;
			}
		}
		
		return null;
	}
	
	public Customer updateCustomer(int id, String nm, String em) {
		for (Customer c : customers) {
			if (c.getId() == id) {
				c.setName(nm);
				c.setEmail(em);
				return c;
			}
		}
		
		return null;
	}
	
	public boolean deleteCustomer(int id) {
		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getId() == id) {
				customers.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public List<Customer> getAllCustomers() {
		return Collections.unmodifiableList(customers);
	}
	*/
}
