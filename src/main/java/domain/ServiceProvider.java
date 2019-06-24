package domain;

public class ServiceProvider {
	private static CustomerService customerService = new CustomerService();

	public static CustomerService getCustomerService() {
		return customerService;
	}
}