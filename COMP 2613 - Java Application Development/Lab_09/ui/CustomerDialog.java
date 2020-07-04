/**
 * Project: COMP2613 - 09 - Lab 09
 * File: CustomerDialog.java
 * Date: June 16, 2019
 *
 * @author Matthew Simpson
 */

package lab09.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lab09.database.CustomerDAO;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Random;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CustomerDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField fieldID;
	private JTextField fieldFirstName;
	private JTextField fieldLastName;
	private JTextField fieldStreet;
	private JTextField fieldCity;
	private JTextField fieldPostal;
	private JTextField fieldPhone;
	private JTextField fieldEmail;
	private JTextField fieldJoinedDate;
	
	private CustomerDAO dao;
	
	Random random = new Random();

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public CustomerDialog(CustomerDAO newDao) throws SQLException {
		this.dao = newDao;
		int checkID = random.nextInt(5) + 1;
		setSize(450, 388);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[88.00][grow]", "[][][][][][][][][]"));
		{
			JLabel lblCustomerId = new JLabel("Customer ID");
			contentPanel.add(lblCustomerId, "cell 0 0,alignx trailing");
		}
		{
			fieldID = new JTextField();
			fieldID.setText(Integer.toString(checkID));
			fieldID.setEditable(false);
			contentPanel.add(fieldID, "cell 1 0,growx");
			fieldID.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("First Name");
			contentPanel.add(lblNewLabel, "cell 0 1,alignx trailing");
		}
		{
			fieldFirstName = new JTextField();
			fieldFirstName.setText(dao.getCustomer(checkID).getFirstName());
			contentPanel.add(fieldFirstName, "cell 1 1,growx");
			fieldFirstName.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			contentPanel.add(lblLastName, "cell 0 2,alignx trailing");
		}
		{
			fieldLastName = new JTextField();
			fieldLastName.setText(dao.getCustomer(checkID).getLastName());
			contentPanel.add(fieldLastName, "cell 1 2,growx");
			fieldLastName.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Street");
			contentPanel.add(lblNewLabel_1, "cell 0 3,alignx trailing");
		}
		{
			fieldStreet = new JTextField();
			fieldStreet.setText(dao.getCustomer(checkID).getStreetName());
			contentPanel.add(fieldStreet, "cell 1 3,growx");
			fieldStreet.setColumns(10);
		}
		{
			JLabel lblCity = new JLabel("City");
			contentPanel.add(lblCity, "cell 0 4,alignx trailing");
		}
		{
			fieldCity = new JTextField();
			fieldCity.setText(dao.getCustomer(checkID).getCity());
			contentPanel.add(fieldCity, "cell 1 4,growx");
			fieldCity.setColumns(10);
		}
		{
			JLabel lblPostal = new JLabel("Postal");
			contentPanel.add(lblPostal, "cell 0 5,alignx trailing");
		}
		{
			fieldPostal = new JTextField();
			fieldPostal.setText(dao.getCustomer(checkID).getPostalCode());
			contentPanel.add(fieldPostal, "cell 1 5,growx");
			fieldPostal.setColumns(10);
		}
		{
			JLabel lblPhone = new JLabel("Phone");
			contentPanel.add(lblPhone, "cell 0 6,alignx trailing");
		}
		{
			fieldPhone = new JTextField();
			fieldPhone.setText(dao.getCustomer(checkID).getPhoneNumber());
			contentPanel.add(fieldPhone, "cell 1 6,growx");
			fieldPhone.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Email");
			contentPanel.add(lblNewLabel_2, "cell 0 7,alignx trailing");
		}
		{
			fieldEmail = new JTextField();
			fieldEmail.setText(dao.getCustomer(checkID).getEmailAddress());
			contentPanel.add(fieldEmail, "cell 1 7,growx");
			fieldEmail.setColumns(10);
		}
		{
			JLabel lblJoinedDate = new JLabel("Joined Date");
			contentPanel.add(lblJoinedDate, "cell 0 8,alignx trailing");
		}
		{
			fieldJoinedDate = new JTextField();
			fieldJoinedDate.setText(dao.getCustomer(checkID).getJoinDate().toString());
			contentPanel.add(fieldJoinedDate, "cell 1 8,growx");
			fieldJoinedDate.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
