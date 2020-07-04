package ca.bcit.comp2613.bookstore.data;

import java.time.LocalDate;

public class Customer {
	
	private final int customerID;
	private String firstName;
	private String lastName;
	private String streetName;
	private String city;
	private String postalCode;
	private String phoneNumber;
	private String emailAddress;
	private LocalDate joinDate;

	public static class Builder {

		private final int customerID;
		private String firstName;
		private String lastName;
		private String streetName;
		private String city;
		private String postalCode;
		private String phoneNumber;
		private String emailAddress;
		private LocalDate joinDate;

		/**
		 * The builder constructor. requires ID and Phone Number to be set.
		 *
		 * @param id
		 *            - a long
		 *            - the ID to set
		 * @param phoneNumber
		 *            - a string
		 *            - the phone number to set
		 */
		public Builder(final int customerID, final String phoneNumber) {
			this.customerID = customerID;
			this.phoneNumber = phoneNumber;
		}

		public Builder firstName(final String var) {
			if (!var.isEmpty() && var != null) {
				firstName = var;
			}
			return this;
		}

		public Builder lastName(final String var) {
			if (!var.isEmpty() && var != null) {
				lastName = var;
			}
			return this;
		}

		public Builder streetName(final String var) {
			if (!var.isEmpty() && var != null) {
				streetName = var;
			}
			return this;
		}

		public Builder city(final String var) {
			if (!var.isEmpty() && var != null) {
				city = var;
			}
			return this;
		}

		public Builder postalCode(final String var) {
			if (!var.isEmpty() && var != null) {
				postalCode = var;
			}
			return this;
		}

		public Builder emailAddress(final String var) {
			if (!var.isEmpty() && var != null) {
				emailAddress = var;
			}
			return this;
		}

		public Builder joinDate(final LocalDate var) {
			joinDate = var;
			return this;
		}

		public Customer build() {
			return new Customer(this);
		}

	} // builder end ------------------------------------

	/**
	 * Customer Constructor. Use data passed to it from the inner Builder class.
	 *
	 * @param builder
	 *            - the builder class data
	 */
	private Customer(final Builder builder) {
		customerID = builder.customerID;
		firstName = builder.firstName;
		lastName = builder.lastName;
		streetName = builder.streetName;
		city = builder.city;
		postalCode = builder.postalCode;
		phoneNumber = builder.phoneNumber;
		emailAddress = builder.emailAddress;
		joinDate = builder.joinDate;

	}

	@Override
	public String toString() {
		return "Customer [id=" + customerID + ", firstName=" + firstName + ", lastName=" + lastName + ", streetName=" + streetName + ", city=" + city
				+ ", postalCode=" + postalCode + ", phoneNumber=" + phoneNumber + ", emailAddress=" + emailAddress + ", joinDate=" + joinDate + "]";
	}

	// getters ------------------------------------

	/**
	 * @return the id as a string
	 */
	public int getId() {
		return customerID;
	}

	/**
	 * @return the firstName as a string
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName as a string
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the streetName as a string
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * @return the city as a string
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the postalCode as a string
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @return the phoneNumber as a string
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the emailAddress as a string
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @return the joinDate as a string
	 */
	public LocalDate getJoinDate() {
		return joinDate;
	}

	// setters ------------------------------------

	/**
	 * @param firstName
	 *            - a string
	 *            - the firstName to set
	 */
	public void setFirstName(final String firstName) {
		if (!firstName.isEmpty() && firstName != null) {
			this.firstName = firstName;
		}
	}

	/**
	 * @param lastName
	 *            - a string
	 *            - the lastName to set
	 */
	public void setLastName(final String lastName) {
		if (!lastName.isEmpty() && lastName != null) {
			this.lastName = lastName;
		}
	}

	/**
	 * @param streetName
	 *            - a string
	 *            - the streetName to set
	 */
	public void setStreetName(final String streetName) {
		if (!streetName.isEmpty() && streetName != null) {
			this.streetName = streetName;
		}
	}

	/**
	 * @param city
	 *            - a string
	 *            - the city to set
	 */
	public void setCity(final String city) {
		if (!city.isEmpty() && city != null) {
			this.city = city;
		}
	}

	/**
	 * @param postalCode
	 *            - a string
	 *            - the postalCode to set
	 */
	public void setPostalCode(final String postalCode) {
		if (!postalCode.isEmpty() && postalCode != null) {
			this.postalCode = postalCode;
		}
	}

	/**
	 * @param phoneNumber
	 *            - a string
	 *            - the phoneNumber to set
	 */
	public void setPhoneNumber(final String phoneNumber) {
		if (!phoneNumber.isEmpty() && phoneNumber != null) {
			this.phoneNumber = phoneNumber;
		}
	}

	/**
	 * @param emailAddress
	 *            - a string
	 *            - the emailAddress to set
	 */
	public void setEmailAddress(final String emailAddress) {
		if (!emailAddress.isEmpty() && emailAddress != null) {
			this.emailAddress = emailAddress;

		}
	}

	/**
	 * @param joinDate
	 *            - a string
	 *            - * the joinDate to set
	 */
	public void setJoinDate(final LocalDate joinDate) {
		this.joinDate = joinDate;
	}

}
