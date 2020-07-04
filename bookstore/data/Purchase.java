package ca.bcit.comp2613.bookstore.data;

public class Purchase {
		
	private final int purchaseID;
	private int customerID;
	private int bookID;
	private float price;
	
	public static class Builder{
		
		private int purchaseID;
		private int customerID;
		private int bookID;
		private float price;
		
		public Builder(final int purchaseID, final int customerID, int bookID) {
			this.purchaseID = purchaseID;
			this.customerID = customerID;
			this.bookID = bookID;			
		}

		public Builder price(final float var) {
			this.price = var;
			return this;
		}
		
		public Purchase build() {
			return new Purchase(this);
		}
		
	}
	public Purchase() {
		purchaseID = 1;
	}
	
	public Purchase(Builder builder) {
		purchaseID = builder.purchaseID;
		customerID = builder.customerID;
		bookID = builder.bookID;
		price = builder.price;
	}
	
	@Override
	public String toString() {
		return "Builder [purchaseID=" + purchaseID + ", customerID=" + customerID + ", bookID=" + bookID
				+ ", price=" + price + "]";
	}

	// getters -----------------------------------------
	public int getPurchaseID() {
		return purchaseID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public int getBookID() {
		return bookID;
	}

	public float getPrice() {
		return price;
	}

	// setters -------------------------------------------

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * 
	 * Inner class to define purchase history objects.
	 *
	 */
	public class PurchaseHistory {
		private String firstName;
		private String lastName;
		private String title;
		private float purchasePrice;
		
		public PurchaseHistory(String firstName, String lastName, String title, float purchasePrice) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.title = title;
			this.purchasePrice = purchasePrice;
		}

		public String getFirstName() {
			return firstName;
		}
		
		public String getLastName() {
			return lastName;
		}
		
		public String getTitle() {
			return title;
		}

		public float getPrice() {
			return purchasePrice;
		}
	}
	
	

}
