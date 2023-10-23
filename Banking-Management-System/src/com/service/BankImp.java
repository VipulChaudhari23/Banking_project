package com.service;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types; // Import the Types class from java.sql
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import com.Connection.JdbcConnection;
import com.entity.Account;
import com.entity.Branch;
import com.entity.Customer;
import com.entity.Employee;
import com.entity.Loan;
import com.entity.Transaction;

public class BankImp implements Bank {
//	BankImp bankImp = new BankImp();
	// Private static instance variable
	private static BankImp instance = null;

	// Private constructor to prevent external instantiation
	public BankImp() {
	}

	// Public method to get or create the instance
	public static BankImp getInstance() {
		if (instance == null) {
			instance = new BankImp();
		}
		return instance;
	}

//  ------------------------------------ Customer Operation Start ------------------------------------------------------	

	// create customer
	public Customer add() throws ParseException, SQLException {

		Scanner scanner = new Scanner(System.in);
		System.out.println("*********** Enter Customer Details to Create Customer ****************");

		// Gather customer information from the user

//		System.out.print("Id: ");
//		int id = scanner.nextInt();

		System.out.print("Login: ");
		String login = scanner.next();

		System.out.print("Password: ");
		String plainPassword = scanner.next();
		// Hash the password before storing it
		String hashedPassword = hashPassword(plainPassword);

		System.out.print("Name: ");
		String name = scanner.next();

		System.out.print("Phone: ");
		String phone = scanner.next();

		System.out.print("Email: ");
		String email = scanner.next();

		System.out.print("Registration Date: ");
		String date = scanner.next();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		java.util.Date utilDate = sdf.parse(date);
		Date registrationDate = new Date(utilDate.getTime());

		// Create a Customer object
		Customer newCustomer = new Customer();
//		newCustomer.setId(id);
		newCustomer.setLogin(login);
		newCustomer.setPasshash(plainPassword);
		newCustomer.setName(name);
		newCustomer.setPhone(phone);
		newCustomer.setEmail(email);
		newCustomer.setRegistrationDate(registrationDate);

		// Database Connection
		JdbcConnection connection = new JdbcConnection();

		// Insert the customer into the database

		Connection con = connection.getconnection();
		PreparedStatement preparedStatement = null;
		try {

			String insertQuery = "INSERT INTO customer (login, passhash, name, phone, email, registrationDate) VALUES (?, ?, ?, ?, ?, ?)";
			preparedStatement = con.prepareStatement(insertQuery);
//			preparedStatement.setInt(1, newCustomer.getId());
			preparedStatement.setString(1, newCustomer.getLogin());
			preparedStatement.setString(2, hashedPassword);
			preparedStatement.setString(3, newCustomer.getName());
			preparedStatement.setString(4, newCustomer.getPhone());
			preparedStatement.setString(5, newCustomer.getEmail());
			preparedStatement.setDate(6, newCustomer.getRegistrationDate());
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Customer added successfully!");
			} else {
				System.out.println("Failed to add customer.");
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Cant able to close the connection.....");
			}
		}
		return newCustomer;

	}


// ----------------------------------------------------------------------- Admin Start -------------------------------------------------------------------

	// Print all Manager records
	public List<Customer> findAllmanager() throws SQLException {
		ArrayList columnNames = new ArrayList();
		ArrayList data = new ArrayList();

		JdbcConnection connection = new JdbcConnection();
		Connection con = connection.getconnection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM employee WHERE manager_id IS NOT NULL");
//				while(rs.next()) {
//					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
//				}

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			// Get column names
			for (int i = 1; i <= columns; i++) {

				columnNames.add(md.getColumnName(i));
			}
			// Get row data
			while (rs.next()) {
				ArrayList row = new ArrayList(columns);

				for (int i = 1; i <= columns; i++) {
					row.add(rs.getObject(i));
				}
				System.out.println(row);
				data.add(row);
			}
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		} finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Cant able to close the connection.....");
			}
		}
		return data;
	}

	// Print all Customer records
	public List<Customer> findAllCustomers() throws SQLException {
		ArrayList columnNames = new ArrayList();
		ArrayList data = new ArrayList();

		JdbcConnection connection = new JdbcConnection();
		Connection con = connection.getconnection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from customer");
//				while(rs.next()) {
//					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
//				}

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			// Get column names
			for (int i = 1; i <= columns; i++) {

				columnNames.add(md.getColumnName(i));
			}
			// Get row data
			while (rs.next()) {
				ArrayList row = new ArrayList(columns);

				for (int i = 1; i <= columns; i++) {
					row.add(rs.getObject(i));
				}
				System.out.println(row);
				data.add(row);
			}
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		} finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Cant able to close the connection.....");
			}
		}
		return data;
	}

	// Create Branch
	@Override
	public Branch addBranch() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("*********** Enter Branch Details to Create Branch ****************");

		// Gather customer information from the user
		System.out.print("ID: ");
		String id = scanner.next();

		System.out.print("Branch: ");
		String branchinput = scanner.next();

		System.out.print("Branch Phone: ");
		String branchphone = scanner.next();

		// Create a Customer object
		Branch branch = new Branch();
		branch.setId(id);
		branch.setbranch(branchinput);
		branch.setPhone(branchphone);

		// Database Connection
		JdbcConnection connection = new JdbcConnection();

		// Insert the customer into the database

		Connection con = connection.getconnection();
		PreparedStatement preparedStatement = null;
		try {

			String insertQuery = "INSERT INTO branches (id, branch, phone) VALUES (?, ?, ?)";
			preparedStatement = con.prepareStatement(insertQuery);
			preparedStatement.setString(1, branch.getId());
			preparedStatement.setString(2, branch.getbranch());
			preparedStatement.setString(3, branch.getPhone());
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Branch added successfully!");
			} else {
				System.out.println("Failed to add Branch.");
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Cant able to close the connection.....");
			}
		}
		return branch;

	};

	// update branch records using the branch id
	public boolean updateBranch() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("*********** Update Branch Details by id ****************");

		System.out.print("Enter Branch Id: ");
		String branchId = scanner.next();

		// Gather branch information from the user
		System.out.print("Branch: ");
		String branch = scanner.next();

		System.out.print("Branch Phone: ");
		String branchPhone = scanner.next();

		// Create a Branch object
		Branch updatedBranch = new Branch();
		updatedBranch.setId(branchId); // Assuming you have a method to set the branch's ID.
		updatedBranch.setbranch(branch);
		updatedBranch.setPhone(branchPhone);

		// Database Connection
		JdbcConnection connection = new JdbcConnection();
		Connection con = connection.getconnection();
		PreparedStatement preparedStatement = null;
		try {
			// Update the branch in the database

			String updateQuery = "UPDATE branches SET branch = ?, phone = ? WHERE id = ?";
			preparedStatement = con.prepareStatement(updateQuery);
			preparedStatement.setString(1, updatedBranch.getbranch());
			preparedStatement.setString(2, updatedBranch.getPhone());
			preparedStatement.setString(3, updatedBranch.getId());

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Branch updated successfully!");
			} else {
				System.out.println("No branch found with the provided ID.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Cant able to close the connection.....");
			}
		}
		return true;
	}

	public boolean setManager() throws SQLException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("*********** Set Manager for Employee by ID ****************");

		System.out.print("Enter Employee ID: ");
		int employeeId = scanner.nextInt();

		System.out.print("Enter Manager ID (or 0 if no manager): ");
		int managerId = scanner.nextInt();

		// Database Connection
		JdbcConnection connection = new JdbcConnection();
		Connection con = connection.getconnection();
		PreparedStatement preparedStatement = null;

		try {
			// Update the manager for the employee in the database
			String updateQuery = "UPDATE employee SET manager_id = ? WHERE id = ?";
			preparedStatement = con.prepareStatement(updateQuery);
			preparedStatement.setInt(1, managerId);
			preparedStatement.setInt(2, employeeId);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Manager updated successfully!");
				return true;
			} else {
				System.out.println("No employee found with the provided ID.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Can't able to close the connection.");
			}
		}

		return false; // Return false on failure
	}

	public boolean deleteEmployee() throws SQLException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("*********** Remove Employee by ID ****************");

		System.out.print("Enter Employee ID: ");
		int employeeId = scanner.nextInt();

		// Database Connection
		JdbcConnection connection = new JdbcConnection();
		Connection con = connection.getconnection();
		PreparedStatement preparedStatement = null;

		try { // Delete the employee from the database
			String deleteQuery = "DELETE FROM employee WHERE id = ?";
			preparedStatement = con.prepareStatement(deleteQuery);
			preparedStatement.setInt(1, employeeId);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Employee removed successfully!");
				return true;
			} else {
				System.out.println("No employee found with the provided ID.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Can't able to close the connection.");
			}
		}

		return false; // Return false on failure
	}
	
	public Employee addEmployee() throws SQLException {
	    Scanner scanner = new Scanner(System.in);

	    System.out.println("*********** Create Employee ****************");

	    System.out.print("Enter Employee ID: ");
	    String id = scanner.nextLine();
	    
	    System.out.print("Enter Employee Name: ");
	    String name = scanner.nextLine();

	    System.out.print("Enter Employee Phone: ");
	    String phone = scanner.next();

	    System.out.print("Enter Employee Position: ");
	    String position = scanner.next();

	    // Check if the provided branch ID exists in the branches table
	    String branchId = null;
	    while (branchId == null) {
	        System.out.print("Enter Branch ID: ");
	        String inputBranchId = scanner.next();
	        if (branchExists(inputBranchId)) {
	            branchId = inputBranchId;
	        } else {
	            System.out.println("No branch found with the provided ID. Please try again.");
	        }
	    }

	    System.out.print("Enter Manager's Employee ID (0 if none, or press Enter): ");
	    String managerIdInput = scanner.next();
	    Integer managerId = null;

	    if (!managerIdInput.isEmpty() && !managerIdInput.equals("0")) {
	        try {
	            managerId = Integer.parseInt(managerIdInput);
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid Manager ID. The manager will be set to NULL.");
	        }
	    }

	    System.out.print("Enter Employee Login: ");
	    String login = scanner.next();

	    System.out.print("Enter Employee Password: ");
	    String plainPassword = scanner.next();
	    

	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();
	    PreparedStatement preparedStatement = null;

	    try {
	        // Insert the employee into the database, allowing manager_id to be null
	        String insertQuery = "INSERT INTO employee (id, name, phone, position, branch_id, manager_id, login, passhash) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        preparedStatement = con.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
	        preparedStatement.setString(1, id);
	        preparedStatement.setString(2, name);
	        preparedStatement.setString(3, phone);
	        preparedStatement.setString(4, position);
	        preparedStatement.setString(5, branchId);
	        if (managerId != null) {
	            preparedStatement.setInt(6, managerId);
	        } else {
	            preparedStatement.setNull(6, Types.INTEGER);
	        }
	        preparedStatement.setString(7, login);
	        preparedStatement.setString(8, plainPassword);

	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            // Retrieve the auto-generated employee ID
	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int employeeId = generatedKeys.getInt(1);
	                return new Employee();
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) {
	            con.close();
	            System.out.println("Closing Connection......");
	        } else {
	            System.out.println("Can't able to close the connection.....");
	        }
	    }
	    return null; // Handle if the employee creation fails
	}



// ----------------------------------------------------------------------- Admin End -------------------------------------------------------------------

// ----------------------------------------------------------------------- Teller Start -----------------------------------------------------------------

	public static void changePassword() throws SQLException {
		Scanner sc = new Scanner(System.in);

		System.out.println("*********** Change Password for Employee by ID ****************");

		System.out.print("Enter Employee ID: ");
		int employeeId = sc.nextInt();
		sc.nextLine(); // Consume the newline character left by nextInt()

		System.out.print("Enter your new password: ");
		String newPassword = sc.nextLine();

		// Establish a new database connection
		JdbcConnection connection = new JdbcConnection();
		Connection con = connection.getconnection();

		// Update the password in the database
		try {
			String query = "UPDATE employee SET passhash = ? WHERE id = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, newPassword);
			preparedStatement.setInt(2, employeeId);

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows > 0) {
				System.out.println("Password updated successfully.");
			} else {
				System.out.println("Failed to update password.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
					System.out.println("Closing Connection......");
				} else {
					System.out.println("Can't able to close the connection.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		sc.close(); // Close the Scanner
	}
	
	private static String hashPassword(String plainPassword) {
		// Generate a salt for the password hash (use a secure random salt)

		String salt = BCrypt.gensalt(12);

		// Hash the password with the salt
		String hashedPassword = BCrypt.hashpw(plainPassword, salt);

		return hashedPassword;
	}

	public void addWithAccount() throws ParseException, SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*********** Enter Customer Details and Create Customer & Account ****************");

        // Gather customer information from the user
        System.out.print("Login: ");
        String login = scanner.next();

        System.out.print("Password: ");
        String plainPassword = scanner.next();
        String hashedPassword = hashPassword(plainPassword);

        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("Phone: ");
        String phone = scanner.next();

        System.out.print("Email: ");
        String email = scanner.next();

        System.out.print("Registration Date (yyyy-MM-dd): ");
        String date = scanner.next();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = sdf.parse(date);
        Date registrationDate = new Date(utilDate.getTime());

        // Create a Customer object
        Customer newCustomer = new Customer();
        newCustomer.setLogin(login);
        newCustomer.setPasshash(plainPassword);
        newCustomer.setName(name);
        newCustomer.setPhone(phone);
        newCustomer.setEmail(email);
        newCustomer.setRegistrationDate(registrationDate);

        // Database Connection
        JdbcConnection connection = new JdbcConnection();
        Connection con = connection.getconnection();
        PreparedStatement preparedStatement = null;

        try {
            // Insert the customer into the database
            String insertCustomerQuery = "INSERT INTO customer (login, passhash, name, phone, email, registrationDate) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = con.prepareStatement(insertCustomerQuery);
            preparedStatement.setString(1, newCustomer.getLogin());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, newCustomer.getName());
            preparedStatement.setString(4, newCustomer.getPhone());
            preparedStatement.setString(5, newCustomer.getEmail());
            preparedStatement.setDate(6, newCustomer.getRegistrationDate());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer added successfully!");
                
                System.out.print("Branch id : ");
                String id = scanner.next();

                // Provide options for branch selection
                System.out.println("Select Branch from the list:");
                System.out.println("1. Pune");
                System.out.println("2. Mumbai");
                System.out.print("Enter the branch option (1 or 2): ");
                int branchOption = scanner.nextInt();

                // Map the user's branch selection to branch ID and collect branch details
                String branchinput = (branchOption == 1) ? "PUNE" : "MUMBAI";

                System.out.print("Branch Phone: ");
                String branchPhone = scanner.next();

                // Insert the branch into the database
                String insertBranchQuery = "INSERT INTO branches (id, branch, phone) VALUES (?, ?, ?)";
                preparedStatement = con.prepareStatement(insertBranchQuery);
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, branchinput);
                preparedStatement.setString(3, branchPhone);

                rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Branch added successfully!");

                    // Collect account information
                    System.out.print("Opening Date (yyyy-MM-dd): ");
                    String openingDateStr = scanner.next();
                    Date openingDate = Date.valueOf(openingDateStr);

                    System.out.print("Current Balance: ");
                    BigDecimal currentBalance = scanner.nextBigDecimal();

                    System.out.print("Interest Rate: ");
                    BigDecimal interestRate = scanner.nextBigDecimal();

                    // Insert the account into the database
                    String insertAccountQuery = "INSERT INTO account (customer_id, branch_id, opening_date, current_balance, interest_rate) VALUES (?, ?, ?, ?, ?)";

                    // Retrieve the customer ID using a SELECT query
                    String getCustomerIdQuery = "SELECT id FROM customer WHERE login = ?";
                    preparedStatement = con.prepareStatement(getCustomerIdQuery);
                    preparedStatement.setString(1, newCustomer.getLogin());

                    ResultSet resultSet = preparedStatement.executeQuery();

                    int customerId = -1;
                    if (resultSet.next()) {
                        customerId = resultSet.getInt("id");
                    }

                    if (customerId != -1) {
                        // Use the retrieved customer ID when inserting the account
                        preparedStatement = con.prepareStatement(insertAccountQuery);
                        preparedStatement.setInt(1, customerId);
                        preparedStatement.setString(2, id);
                        preparedStatement.setDate(3, openingDate);
                        preparedStatement.setBigDecimal(4, currentBalance);
                        preparedStatement.setBigDecimal(5, interestRate);

                        rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Account created successfully!");
                        } else {
                            System.out.println("Failed to create account.");
                        }
                    } else {
                        System.out.println("Failed to retrieve the customer ID.");
                    }
                } else {
                    System.out.println("Failed to add branch.");
                }
            } else {
                System.out.println("Failed to add customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
                System.out.println("Closing Connection......");
            } else {
                System.out.println("Can't able to close the connection.....");
            }
        }
    }

	private Branch getBranchById(Branch branchId) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Establish a database connection
			JdbcConnection connection = new JdbcConnection();
			con = connection.getconnection();

			// Prepare and execute the query to fetch the branch information
			String query = "SELECT * FROM branches WHERE id = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, branchId.getId());
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// Create a Branch object and populate it with data from the result set
				Branch branch = new Branch();
				branch.setId(resultSet.getString("id"));
				branch.setbranch(resultSet.getString("branch"));
				branch.setPhone(resultSet.getString("phone"));

				return branch;
			} else {
				// Branch not found
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			// Close database resources
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
					System.out.println("Closing Connection......");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Print all Customer records
	// call finallcustomer m,ethod from admin
	
	public boolean updateCustomerById() throws SQLException {

		Scanner scanner = new Scanner(System.in);
		System.out.println("*********** Update Customer by Id ****************");

		System.out.print("Enter Customer Id: "); 
		int customerIdtoUpdate = scanner.nextInt();

		// Gather customer information from the user 
		System.out.print("Login: ");
		String login = scanner.next();

		System.out.print("Password: "); 
		String plainPassword = scanner.next(); // Hash the password before storing it 
		String hashedPassword = hashPassword(plainPassword);

		System.out.print("Name: "); 
		String name = scanner.next();

		System.out.print("Phone: "); 
		String phone = scanner.next();

		System.out.print("Email: "); 
		String email = scanner.next();

		System.out.print("Registration Date (dd/mm/yyyy): "); 
		String date = scanner.next(); 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		// Note the correct date format.

				// Database Connection 
		JdbcConnection connection = new JdbcConnection();
		Connection con = connection.getconnection(); 
		try { 
			java.util.Date utilDate = sdf.parse(date); 
			Date registrationDate = new Date(utilDate.getTime());

				// Create a Customer object 
			Customer updatedCustomer = new Customer();
			updatedCustomer.setId(customerIdtoUpdate); // Assuming you have a method to set the customer's ID. 
			updatedCustomer.setLogin(login);
			updatedCustomer.setPasshash(plainPassword); updatedCustomer.setName(name);
			updatedCustomer.setPhone(phone); updatedCustomer.setEmail(email);
			updatedCustomer.setRegistrationDate(registrationDate);

			PreparedStatement preparedStatement = null; 
			try {

			// Update the customer in the database
			String updateQuery = "UPDATE customer SET login = ?, passhash = ?, name = ?, phone = ?, email = ?, registrationDate = ? WHERE id = ?"; 
				preparedStatement = con.prepareStatement(updateQuery);
				preparedStatement.setString(1, updatedCustomer.getLogin());
				preparedStatement.setString(2, hashedPassword);
				preparedStatement.setString(3, updatedCustomer.getName());
				preparedStatement.setString(4, updatedCustomer.getPhone());
				preparedStatement.setString(5, updatedCustomer.getEmail());
				preparedStatement.setDate(6, updatedCustomer.getRegistrationDate());
				preparedStatement.setInt(7, updatedCustomer.getId());

				int rowsAffected = preparedStatement.executeUpdate();

				if (rowsAffected > 0) { 
					System.out.println("Customer updated successfully!");
				} else { 
					System.out.println("No customer found with the provided ID."); 
					} 
				} catch (SQLException e) { e.printStackTrace(); 
				} 
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("Invalid date format. Please use dd/MM/yyyy."); 
			} finally { 
				if (con != null) 
				{ 
					con.close();
					System.out.println("Closing Connection......"); 
					} else {
						System.out.println("Cant able to close the connection....."); 
						} 
			} 
		return true; 
	}
	
	public boolean deleteCustomer() throws SQLException {
	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();

	    try {
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("*********** Remove Customer Details by ID ****************");

	        System.out.print("Enter Customer ID To remove details: ");
	        int customerIdToRemove = scanner.nextInt();

	        // First, delete records from the account table for the specified customer
	        String deleteAccountQuery = "DELETE FROM account WHERE customer_id = ?";
	        PreparedStatement accountStatement = con.prepareStatement(deleteAccountQuery);
	        accountStatement.setInt(1, customerIdToRemove);
	        int accountRowsAffected = accountStatement.executeUpdate();

	        // Then, check if there are branches related to the customer's accounts and delete them
	        String deleteBranchQuery = "DELETE FROM branches WHERE id = ?";
	        PreparedStatement branchStatement = con.prepareStatement(deleteBranchQuery);

	        String selectBranchesQuery = "SELECT branch_id FROM account WHERE customer_id = ?";
	        PreparedStatement selectBranchesStatement = con.prepareStatement(selectBranchesQuery);
	        selectBranchesStatement.setInt(1, customerIdToRemove);
	        ResultSet branchResultSet = selectBranchesStatement.executeQuery();

	        while (branchResultSet.next()) {
	            String branchIdToDelete = branchResultSet.getString("branch_id");
	            branchStatement.setString(1, branchIdToDelete);
	            branchStatement.executeUpdate();
	        }

	        // Finally, delete the customer from the customer table
	        String deleteCustomerQuery = "DELETE FROM customer WHERE id = ?";
	        PreparedStatement customerStatement = con.prepareStatement(deleteCustomerQuery);
	        customerStatement.setInt(1, customerIdToRemove);

	        int customerRowsAffected = customerStatement.executeUpdate();

	        if (customerRowsAffected > 0) {
	            System.out.println("Customer removed successfully!");
	            return true;
	        } else {
	            System.out.println("No customer found with the specified ID.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) {
	            con.close();
	            System.out.println("Closing Connection......");
	        } else {
	            System.out.println("Can't able to close the connection.....");
	        }
	    }
	    return false;
	}

		 
	
	// print customer details using the Id
	public Customer findCustomerByID() throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("*********** Find Customer by ID ****************");

		System.out.print("Enter Customer ID: ");
		int customerId = scanner.nextInt();

		// Create a Customer object to store the retrieved data
		Customer customer = null;

		JdbcConnection connection = new JdbcConnection();
		Connection con = connection.getconnection();
		try { // Database Connection

			// Query to retrieve customer data by ID
			String selectQuery = "SELECT login, passhash, name, phone, email, registrationDate FROM customer WHERE id = ?";

			PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
			preparedStatement.setInt(1, customerId);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				// Inside the loop, you can print each row of data found
				System.out.println("Found customer with ID " + customerId + " Details are: ");
				System.out.println("Login: " + resultSet.getString("login"));
				System.out.println("Passhash: " + resultSet.getString("passhash"));
				System.out.println("Name: " + resultSet.getString("name"));
				System.out.println("Phone: " + resultSet.getString("phone"));
				System.out.println("Email: " + resultSet.getString("email"));
				System.out.println("Registration Date: " + resultSet.getDate("registrationDate"));

				// Create a Customer object with the retrieved data
				customer = new Customer();
				customer.setId(customerId); // Set the ID
				customer.setLogin(resultSet.getString("login"));
				customer.setPasshash(resultSet.getString("passhash"));
				customer.setName(resultSet.getString("name"));
				customer.setPhone(resultSet.getString("phone"));
				customer.setEmail(resultSet.getString("email"));
				customer.setRegistrationDate(resultSet.getDate("registrationDate"));
			}

			if (customer == null) {
				System.out.println("Customer with ID " + customerId + " not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Cant able to close the connection.....");
			}
		}

		return customer;
	}
		  
	
	public boolean updateEmployee() throws SQLException {
	    Scanner scanner = new Scanner(System.in);

	    System.out.println("*********** Update Employee Details by ID ****************");

	    System.out.print("Enter Employee ID: ");
	    int employeeId = scanner.nextInt();

	    // Check if the employee with the provided ID exists
	    if (!employeeExists(employeeId)) {
	        System.out.println("No employee found with the provided ID.");
	        return false;
	    }

	    // Gather employee information from the user
	    System.out.print("Enter Employee Name: ");
	    String name = scanner.next();

	    System.out.print("Enter Employee Phone: ");
	    String phone = scanner.next();

	    System.out.print("Enter Employee Position: ");
	    String position = scanner.next();

	    System.out.print("Enter Manager's Employee ID (0 if none): ");
	    int managerId = scanner.nextInt();

	    // Check if the provided branch ID exists in the branches table
	    String branchId = null;
	    while (branchId == null) {
	        System.out.print("Enter Branch ID: ");
	        String inputBranchId = scanner.next();
	        if (branchExists(inputBranchId)) {
	            branchId = inputBranchId;
	        } else {
	            System.out.println("No branch found with the provided ID. Please try again.");
	        }
	    }

	    System.out.print("Enter Employee Login: ");
	    String login = scanner.next();

	    System.out.print("Enter Employee Passhash: ");
	    String passhash = scanner.next();

	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();
	    PreparedStatement preparedStatement = null;

	    try { // Update the employee in the database
	        String updateQuery = "UPDATE employee SET name = ?, phone = ?, position = ?, branch_id = ?, manager_id = ?, login = ?, passhash = ? WHERE id = ?";
	        preparedStatement = con.prepareStatement(updateQuery);
	        preparedStatement.setString(1, name);
	        preparedStatement.setString(2, phone);
	        preparedStatement.setString(3, position);
	        preparedStatement.setString(4, branchId);
	        preparedStatement.setInt(5, managerId);
	        preparedStatement.setString(6, login);
	        preparedStatement.setString(7, passhash);
	        preparedStatement.setInt(8, employeeId);

	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Employee updated successfully!");
	            return true;
	        } else {
	            System.out.println("Failed to update employee.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 

	    return false; // Return false on failure
	}

	// Method to check if an employee with a given ID exists
	private boolean employeeExists(int employeeId) throws SQLException {
	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();
	    PreparedStatement preparedStatement = null;

	    try {
	        String checkQuery = "SELECT id FROM employee WHERE id = ?";
	        preparedStatement = con.prepareStatement(checkQuery);
	        preparedStatement.setInt(1, employeeId);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        return resultSet.next();
	    } finally {
	        if (con != null) {
	            con.close();
	        }
	    }
	}

	// Method to check if a branch with a given ID exists
	private boolean branchExists(String branchId) throws SQLException {
	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();
	    PreparedStatement preparedStatement = null;

	    try {
	        String checkQuery = "SELECT id FROM branches WHERE id = ?";
	        preparedStatement = con.prepareStatement(checkQuery);
	        preparedStatement.setString(1, branchId);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        return resultSet.next();
	    } finally {
	        if (con != null) {
	            con.close();
	            System.out.println("Closing Connection......");
	        } else {
	            System.out.println("Can't able to close the connection.");
	        }
	    }
	    }
	


// ----------------------------------------------------------------------- Teller End ------------------------------------------------------------------
	
// ----------------------------------------------------------------------- Customer Start------------------------------------------------------------------

	
	
	public static void changePasswordCustomer() throws SQLException {
	    Scanner sc = new Scanner(System.in);

	    System.out.println("*********** Change Password for Employee by ID ****************");

	    System.out.print("Enter customerID: ");
	    int customerId = sc.nextInt();
	    sc.nextLine(); // Consume the newline character left by nextInt()

	    System.out.print("Enter your new password: ");
	    String newPassword = sc.nextLine();

	    // Hash the new password
	    String hashedPassword = hashPassword(newPassword);

	    // Establish a new database connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();

	    // Update the password in the database
	    try {
	        String query = "UPDATE Customer SET passhash = ? WHERE id = ?";
	        PreparedStatement preparedStatement = con.prepareStatement(query);
	        preparedStatement.setString(1, hashedPassword);
	        preparedStatement.setInt(2, customerId);

	        int updatedRows = preparedStatement.executeUpdate();

	        if (updatedRows > 0) {
	            System.out.println("Password updated successfully.");
	        } else {
	            System.out.println("Failed to update password.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (con != null) {
	                con.close();
	                System.out.println("Closing Connection......");
	            } else {
	                System.out.println("Can't able to close the connection.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    sc.close(); // Close the Scanner
	}

	public BigDecimal viewBalanceByCustomerId() throws SQLException {
	    Scanner scanner = new Scanner(System.in);
	    System.out.println("*********** View Balance by Customer ID ****************");

	    System.out.print("Enter Customer ID: ");
	    int customerId = scanner.nextInt();

	    BigDecimal balance = null;

	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();

	    try {
	        // Query to retrieve the balance by customer ID
	        String selectQuery = "SELECT current_balance FROM account WHERE customer_id = ?";

	        PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
	        preparedStatement.setInt(1, customerId);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            balance = resultSet.getBigDecimal("current_balance");
	            System.out.println("Balance for Customer ID " + customerId + ": " + balance);
	        } else {
	            System.out.println("No account found for Customer ID " + customerId);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) {
	            con.close();
	            System.out.println("Closing Connection......");
	        } else {
	            System.out.println("Can't able to close the connection.");
	        }
	    }

	    return balance;
	}

	public void viewCustomerDetails() throws SQLException {
	    Scanner scanner = new Scanner(System.in);

	    System.out.print("Enter Customer ID: ");
	    int customerId = scanner.nextInt();

	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();

	    try {
	        // Select customer, account, and branch details using a JOIN
	        String selectQuery = "SELECT c.id, c.login, c.name, c.phone, c.email, c.registrationdate, " +
	                "a.customer_id, a.opening_date, a.current_balance, a.interest_rate, " +
	                "b.id AS branch_id, b.branch, b.phone " +
	                "FROM customer c " +
	                "LEFT JOIN account a ON c.id = a.customer_id " +
	                "LEFT JOIN branches b ON a.branch_id = b.id " +
	                "WHERE c.id = ?";

	        PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
	        preparedStatement.setInt(1, customerId);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            // Customer details
	            System.out.println("Customer ID: " + resultSet.getInt("id"));
	            System.out.println("Login: " + resultSet.getString("login"));
	            System.out.println("Name: " + resultSet.getString("name"));
	            System.out.println("Phone: " + resultSet.getString("phone"));
	            System.out.println("Email: " + resultSet.getString("email"));
	            System.out.println("Registration Date: " + resultSet.getDate("registrationdate"));

	            // Account details
	            System.out.println("Account ID: " + resultSet.getInt("customer_id"));
	            System.out.println("Opening Date: " + resultSet.getDate("opening_date"));
	            System.out.println("Current Balance: " + resultSet.getBigDecimal("current_balance"));
	            System.out.println("Interest Rate: " + resultSet.getBigDecimal("interest_rate"));

	            // Branch details
	            System.out.println("Branch ID: " + resultSet.getString("branch_id"));
	            System.out.println("Branch: " + resultSet.getString("branch"));
	            System.out.println("Branch Phone: " + resultSet.getString("phone"));
	        } else {
	            System.out.println("Customer not found with ID: " + customerId);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) {
	            con.close();
	            System.out.println("Closing Connection......");
	        } else {
	            System.out.println("Can't able to close the connection.....");
	        }
	    }
	}

	public boolean addDeposit() throws SQLException {
	    // Take user-defined inputs
	    Scanner scanner = new Scanner(System.in);
	    System.out.println("Enter Customer ID: ");
	    int customerId = scanner.nextInt();
	    System.out.println("Enter the amount to deposit: ");
	    BigDecimal amount = scanner.nextBigDecimal();

	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();

	    try {
	        // Retrieve the current balance for the customer's account
	        String selectQuery = "SELECT current_balance FROM account WHERE customer_id = ?";
	        PreparedStatement selectStatement = con.prepareStatement(selectQuery);
	        selectStatement.setInt(1, customerId);

	        ResultSet resultSet = selectStatement.executeQuery();

	        if (resultSet.next()) {
	            BigDecimal currentBalance = resultSet.getBigDecimal("current_balance");

	            // Update the current balance
	            currentBalance = currentBalance.add(amount);

	            // Update the current_balance in the account table
	            String updateQuery = "UPDATE account SET current_balance = ? WHERE customer_id = ?";
	            PreparedStatement updateStatement = con.prepareStatement(updateQuery);
	            updateStatement.setBigDecimal(1, currentBalance);
	            updateStatement.setInt(2, customerId);

	            int rowsAffected = updateStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                // Insert a new transaction record
	                String insertTransactionQuery = "INSERT INTO transaction (datetime, amount, customer_id) VALUES (NOW(), ?, ?)";
	                PreparedStatement insertTransactionStatement = con.prepareStatement(insertTransactionQuery);
	                insertTransactionStatement.setBigDecimal(1, amount);
	                insertTransactionStatement.setInt(2, customerId);

	                int transactionRowsAffected = insertTransactionStatement.executeUpdate();

	                if (transactionRowsAffected > 0) {
	                    System.out.println("Deposit successful.");
	                    return true;
	                }
	            }
	        } else {
	            System.out.println("No account found with the provided Customer ID.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) {
	            con.close();
	            System.out.println("Closing Connection......");
	        } else {
	            System.out.println("Can't able to close the connection.....");
	        }
	    }
	    return false;
	}

	
	public boolean withdraw() throws SQLException {
	    // Take user-defined inputs
	    Scanner scanner = new Scanner(System.in);
	    System.out.println("Enter Customer ID: ");
	    int customerId = scanner.nextInt();
	    System.out.println("Enter the amount to withdraw: ");
	    BigDecimal amount = scanner.nextBigDecimal();

	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();

	    try {
	        // Retrieve the current balance for the customer's account
	        String selectQuery = "SELECT current_balance FROM account WHERE customer_id = ?";
	        PreparedStatement selectStatement = con.prepareStatement(selectQuery);
	        selectStatement.setInt(1, customerId);

	        ResultSet resultSet = selectStatement.executeQuery();

	        if (resultSet.next()) {
	            BigDecimal currentBalance = resultSet.getBigDecimal("current_balance");

	            // Check if there are sufficient funds for withdrawal
	            if (currentBalance.compareTo(amount) >= 0) {
	                // Update the current balance
	                currentBalance = currentBalance.subtract(amount);

	                // Update the current_balance in the account table
	                String updateQuery = "UPDATE account SET current_balance = ? WHERE customer_id = ?";
	                PreparedStatement updateStatement = con.prepareStatement(updateQuery);
	                updateStatement.setBigDecimal(1, currentBalance);
	                updateStatement.setInt(2, customerId);

	                int rowsAffected = updateStatement.executeUpdate();

	                if (rowsAffected > 0) {
	                    // Insert a new transaction record for the withdrawal
	                    String insertTransactionQuery = "INSERT INTO transaction (datetime, amount, customer_id) VALUES (NOW(), ?, ?)";
	                    PreparedStatement insertTransactionStatement = con.prepareStatement(insertTransactionQuery);
	                    insertTransactionStatement.setBigDecimal(1, amount);
	                    insertTransactionStatement.setInt(2, customerId);

	                    int transactionRowsAffected = insertTransactionStatement.executeUpdate();

	                    if (transactionRowsAffected > 0) {
	                        System.out.println("Withdrawal successful.");
	                        return true;
	                    }
	                }
	            } else {
	                System.out.println("Insufficient funds for withdrawal.");
	            }
	        } else {
	            System.out.println("No account found with the provided Customer ID.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) {
	            con.close();
	            System.out.println("Closing Connection......");
	        } else {
	            System.out.println("Can't able to close the connection.....");
	        }
	    }
	    return false;
	}



//	public boolean transferMoney() throws SQLException {
//	    // Take user-defined inputs
//	    Scanner scanner = new Scanner(System.in);
//	    
//	    // Get source account ID
//	    System.out.print("Enter Source Account ID: ");
//	    int sourceAccountId = scanner.nextInt();
//
//	    // Get destination account ID
//	    System.out.print("Enter Destination Account ID: ");
//	    int destinationAccountId = scanner.nextInt();
//
//	    System.out.print("Enter the amount to transfer: ");
//	    BigDecimal amount = scanner.nextBigDecimal();
//
//	    // Database Connection
//	    JdbcConnection connection = new JdbcConnection();
//	    Connection con = connection.getconnection();
//	    PreparedStatement updateStatement = null;
//
//	    try {
//	        con.setAutoCommit(false); // Start a transaction
//
//	        // Retrieve the current balance for the source account
//	        String selectSourceQuery = "SELECT current_balance FROM account WHERE customer_id = ?";
//	        PreparedStatement selectSourceStatement = con.prepareStatement(selectSourceQuery);
//	        selectSourceStatement.setInt(1, sourceAccountId);
//	        ResultSet sourceResultSet = selectSourceStatement.executeQuery();
//
//	        if (sourceResultSet.next()) {
//	            BigDecimal sourceCurrentBalance = sourceResultSet.getBigDecimal("current_balance");
//
//	            // Check if there are sufficient funds for the transfer
//	            if (sourceCurrentBalance.compareTo(amount) >= 0) {
//	                // Update the current balance for the source account
//	                sourceCurrentBalance = sourceCurrentBalance.subtract(amount);
//
//	                // Update the source account's current_balance
//	                String updateSourceQuery = "UPDATE account SET current_balance = ? WHERE customer_id = ?";
//	                updateStatement = con.prepareStatement(updateSourceQuery);
//	                updateStatement.setBigDecimal(1, sourceCurrentBalance);
//	                updateStatement.setInt(2, sourceAccountId);
//	                int rowsAffectedSource = updateStatement.executeUpdate();
//
//	                if (rowsAffectedSource > 0) {
//	                    // Retrieve the current balance for the destination account
//	                    String selectDestinationQuery = "SELECT current_balance FROM account WHERE customer_id = ?";
//	                    PreparedStatement selectDestinationStatement = con.prepareStatement(selectDestinationQuery);
//	                    selectDestinationStatement.setInt(1, destinationAccountId);
//	                    ResultSet destinationResultSet = selectDestinationStatement.executeQuery();
//
//	                    if (destinationResultSet.next()) {
//	                        BigDecimal destinationCurrentBalance = destinationResultSet.getBigDecimal("current_balance");
//
//	                        // Update the current balance for the destination account
//	                        destinationCurrentBalance = destinationCurrentBalance.add(amount);
//
//	                        // Update the destination account's current_balance
//	                        String updateDestinationQuery = "UPDATE account SET current_balance = ? WHERE customer_id = ?";
//	                        updateStatement = con.prepareStatement(updateDestinationQuery);
//	                        updateStatement.setBigDecimal(1, destinationCurrentBalance);
//	                        updateStatement.setInt(2, destinationAccountId);
//	                        int rowsAffectedDestination = updateStatement.executeUpdate();
//
//	                        if (rowsAffectedDestination > 0) {
//	                            // Commit the transaction
//	                            con.commit();
//	                            System.out.println("Money transfer successful.");
//	                            return true;
//	                        }
//	                    }
//	                }
//	            } else {
//	                System.out.println("Insufficient funds for the transfer from source account.");
//	            }
//	        } else {
//	            System.out.println("Source account not found with the provided Customer ID.");
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	        con.rollback(); // Rollback the transaction in case of an exception
//	    } finally {
//	        if (con != null) {
//	            con.setAutoCommit(true); // Set auto-commit back to true
//	            con.close();
//	            System.out.println("Closing Connection......");
//	        } else {
//	            System.out.println("Can't able to close the connection.....");
//	        }
//	    }
//	    return false;
//	}
	
	public boolean transferMoney() throws SQLException {
	    // Take user-defined inputs
	    Scanner scanner = new Scanner(System.in);

	    // Get source account ID
	    System.out.print("Enter Source Account ID: ");
	    int sourceAccountId = scanner.nextInt();

	    // Get destination account ID
	    System.out.print("Enter Destination Account ID: ");
	    int destinationAccountId = scanner.nextInt();

	    System.out.print("Enter the amount to transfer: ");
	    BigDecimal amount = scanner.nextBigDecimal();

	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();
	    PreparedStatement updateStatement = null;

	    try {
	        con.setAutoCommit(false); // Start a transaction

	        // Retrieve the current balance for the source account
	        String selectSourceQuery = "SELECT current_balance FROM account WHERE customer_id = ?";
	        PreparedStatement selectSourceStatement = con.prepareStatement(selectSourceQuery);
	        selectSourceStatement.setInt(1, sourceAccountId);
	        ResultSet sourceResultSet = selectSourceStatement.executeQuery();

	        if (sourceResultSet.next()) {
	            BigDecimal sourceCurrentBalance = sourceResultSet.getBigDecimal("current_balance");

	            // Check if there are sufficient funds for the transfer
	            if (sourceCurrentBalance.compareTo(amount) >= 0) {
	                // Update the current balance for the source account
	                sourceCurrentBalance = sourceCurrentBalance.subtract(amount);

	                // Update the source account's current_balance
	                String updateSourceQuery = "UPDATE account SET current_balance = ? WHERE customer_id = ?";
	                updateStatement = con.prepareStatement(updateSourceQuery);
	                updateStatement.setBigDecimal(1, sourceCurrentBalance);
	                updateStatement.setInt(2, sourceAccountId);
	                int rowsAffectedSource = updateStatement.executeUpdate();

	                if (rowsAffectedSource > 0) {
	                    // Retrieve the current balance for the destination account
	                    String selectDestinationQuery = "SELECT current_balance FROM account WHERE customer_id = ?";
	                    PreparedStatement selectDestinationStatement = con.prepareStatement(selectDestinationQuery);
	                    selectDestinationStatement.setInt(1, destinationAccountId);
	                    ResultSet destinationResultSet = selectDestinationStatement.executeQuery();

	                    if (destinationResultSet.next()) {
	                        BigDecimal destinationCurrentBalance = destinationResultSet.getBigDecimal("current_balance");

	                        // Update the current balance for the destination account
	                        destinationCurrentBalance = destinationCurrentBalance.add(amount);

	                        // Update the destination account's current_balance
	                        String updateDestinationQuery = "UPDATE account SET current_balance = ? WHERE customer_id = ?";
	                        updateStatement = con.prepareStatement(updateDestinationQuery);
	                        updateStatement.setBigDecimal(1, destinationCurrentBalance);
	                        updateStatement.setInt(2, destinationAccountId);
	                        int rowsAffectedDestination = updateStatement.executeUpdate();

	                        if (rowsAffectedDestination > 0) {
	                            // Insert a new transaction record for the transfer
	                            String insertTransactionQuery = "INSERT INTO transaction (datetime, amount, customer_id) VALUES (NOW(), ?, ?)";
	                            PreparedStatement insertTransactionStatement = con.prepareStatement(insertTransactionQuery);
	                            insertTransactionStatement.setBigDecimal(1, amount);
	                            insertTransactionStatement.setInt(2, sourceAccountId);

	                            int transactionRowsAffected = insertTransactionStatement.executeUpdate();

	                            if (transactionRowsAffected > 0) {
	                                // Commit the transaction
	                                con.commit();
	                                System.out.println("Money transfer successful.");
	                                return true;
	                            }
	                        }
	                    }
	                }
	            } else {
	                System.out.println("Insufficient funds for the transfer from the source account.");
	            }
	        } else {
	            System.out.println("Source account not found with the provided Customer ID.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        con.rollback(); // Rollback the transaction in case of an exception
	    } finally {
	        if (con != null) {
	            con.setAutoCommit(true); // Set auto-commit back to true
	            con.close();
	            System.out.println("Closing Connection......");
	        } else {
	            System.out.println("Can't able to close the connection.....");
	        }
	    }
	    return false;
	}

	public void viewTransactionHistory() throws SQLException {
	    // Take user-defined inputs
	    Scanner scanner = new Scanner(System.in);
	    System.out.println("Enter Customer ID: ");
	    int customerId = scanner.nextInt();

	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();

	    try {
	        // Select all transaction history for the specified customer
	        String selectQuery = "SELECT id, datetime, amount FROM transaction WHERE customer_id = ?";
	        PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
	        preparedStatement.setInt(1, customerId);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            int transactionId = resultSet.getInt("id");
	            java.sql.Timestamp datetime = resultSet.getTimestamp("datetime");
	            BigDecimal amount = resultSet.getBigDecimal("amount");

	            System.out.println("Transaction ID: " + transactionId);
	            System.out.println("Date and Time: " + datetime);
	            System.out.println("Amount: " + amount);
	            System.out.println("-----------------------------");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) {
	            con.close();
	            System.out.println("Closing Connection......");
	        } else {
	            System.out.println("Can't able to close the connection.....");
	        }
	    }
	}


		
// ----------------------------------------------------------------------- Customer End------------------------------------------------------------------


	@Override
	public Account addAcount() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Loan loan) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<Customer> findCustomersByName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee findEmployeeById() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> findAccountsOfCustomer() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Loan> findLoansOfCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTransactionDetails(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Branch BranchDetails() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean transfer() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAccount() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Loan loan) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAccount() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Loan loan) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBranch() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	/*
	 * 
	 * 
	 * 
	 * // print customer details using the Name public List<Customer>
	 * findCustomersByName() throws SQLException { List<Customer> customers = new
	 * ArrayList<>();
	 * 
	 * Scanner scanner = new Scanner(System.in);
	 * System.out.println("*********** Find Customer by Name ****************");
	 * 
	 * System.out.print("Enter Customer Name: "); String customerName =
	 * scanner.next();
	 * 
	 * JdbcConnection connection = new JdbcConnection(); Connection con =
	 * connection.getconnection(); try { // Database Connection
	 * 
	 * // Create a SQL query to find customers by name String selectQuery =
	 * "SELECT * FROM customer WHERE name LIKE ?"; PreparedStatement
	 * preparedStatement = con.prepareStatement(selectQuery);
	 * preparedStatement.setString(1, "%" + customerName + "%"); // Use '%' as a
	 * wildcard to match names containing // the // provided string
	 * 
	 * ResultSet resultSet = preparedStatement.executeQuery();
	 * 
	 * // Process the results and create Customer objects while (resultSet.next()) {
	 * // Inside the loop, you can print each row of data found
	 * System.out.println("Found customer with ID " + customerName +
	 * " Details are: "); System.out.println("Login: " +
	 * resultSet.getString("login")); System.out.println("Passhash: " +
	 * resultSet.getString("passhash")); System.out.println("Name: " +
	 * resultSet.getString("name")); System.out.println("Phone: " +
	 * resultSet.getString("phone")); System.out.println("Email: " +
	 * resultSet.getString("email")); System.out.println("Registration Date: " +
	 * resultSet.getDate("registrationDate"));
	 * 
	 * Customer customer = new Customer(); customer.setId(resultSet.getInt("id"));
	 * // Assuming 'id' is the primary key
	 * customer.setLogin(resultSet.getString("login"));
	 * customer.setPasshash(resultSet.getString("passhash")); // Retrieve the hashed
	 * password customer.setName(resultSet.getString("name"));
	 * customer.setPhone(resultSet.getString("phone"));
	 * customer.setEmail(resultSet.getString("email"));
	 * customer.setRegistrationDate(resultSet.getDate("registrationDate"));
	 * 
	 * customers.add(customer); }
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } finally { if (con != null)
	 * { con.close(); System.out.println("Closing Connection......"); } else {
	 * System.out.println("Cant able to close the connection....."); } }
	 * 
	 * return customers; }
	 * 
	 * // Update customer record using customer Id public boolean
	 * 
	 * public Customer getCustomerById(int customerId) throws SQLException { // TODO
	 * Auto-generated method stub return bankImp.findCustomerByID(); } //
	 * ------------------------------------ Customer Operation End
	 * ------------------------------------------------------
	 * 
	 * 
	 * // ------------------------------------ Branch Operation Start
	 * ------------------------------------------------------
	 * 
	 * 
	 * 
	 * // Print all branch details public Branch BranchDetails() throws SQLException
	 * {
	 * 
	 * Scanner scanner = new Scanner(System.in);
	 * System.out.println("*********** Get Branch Details by ID ****************");
	 * 
	 * System.out.print("Enter Branch ID to Get Details: "); String branchId =
	 * scanner.next();
	 * 
	 * // Database Connection JdbcConnection connection = new JdbcConnection();
	 * Connection con = connection.getconnection(); PreparedStatement
	 * preparedStatement = null; Branch branchDetails = null;
	 * 
	 * try { // Retrieve branch details based on the provided ID String selectQuery
	 * = "SELECT * FROM branches WHERE id = ?"; preparedStatement =
	 * con.prepareStatement(selectQuery); preparedStatement.setString(1, branchId);
	 * ResultSet resultSet = preparedStatement.executeQuery();
	 * 
	 * if (resultSet.next()) { String id = resultSet.getString("id"); String address
	 * = resultSet.getString("address"); String phone =
	 * resultSet.getString("phone");
	 * 
	 * // Create a new Branch object and set its attributes branchDetails = new
	 * Branch(id, address, phone); } else { branchDetails = null; // No branch found
	 * with the provided ID. } } catch (SQLException e) { e.printStackTrace();
	 * branchDetails = null; // Error retrieving branch details. } finally { try {
	 * if (preparedStatement != null) { preparedStatement.close(); } if (con !=
	 * null) { con.close(); System.out.println("Closing Connection......"); } else {
	 * System.out.println("Can't close the connection."); } } catch (SQLException e)
	 * { e.printStackTrace(); } }
	 * 
	 * return branchDetails; }
	 * 
	 * 
	 * 
	 * // Delete branch using branch id public boolean deleteBranch() throws
	 * SQLException { Scanner scanner = new Scanner(System.in);
	 * System.out.println("*********** Delete Branch by ID ****************");
	 * 
	 * System.out.print("Enter Branch ID to delete: "); String branchId =
	 * scanner.next();
	 * 
	 * // Database Connection JdbcConnection connection = new JdbcConnection();
	 * Connection con = connection.getconnection(); PreparedStatement
	 * preparedStatement = null;
	 * 
	 * try { // Check if the branch exists String checkQuery =
	 * "SELECT * FROM branches WHERE id = ?"; preparedStatement =
	 * con.prepareStatement(checkQuery); preparedStatement.setString(1, branchId);
	 * ResultSet resultSet = preparedStatement.executeQuery();
	 * 
	 * if (!resultSet.next()) {
	 * System.out.println("No branch found with the provided ID."); return false; }
	 * 
	 * // Delete the branch from the database String deleteQuery =
	 * "DELETE FROM branches WHERE id = ?"; preparedStatement =
	 * con.prepareStatement(deleteQuery); preparedStatement.setString(1, branchId);
	 * 
	 * int rowsAffected = preparedStatement.executeUpdate();
	 * 
	 * if (rowsAffected > 0) { System.out.println("Branch deleted successfully!");
	 * return true; } else { System.out.println("Failed to delete the branch.");
	 * return false; } } catch (SQLException e) { e.printStackTrace(); return false;
	 * } finally { try { if (preparedStatement != null) { preparedStatement.close();
	 * } if (con != null) { con.close();
	 * System.out.println("Closing Connection......"); } else {
	 * System.out.println("Can't close the connection."); } } catch (SQLException e)
	 * { e.printStackTrace(); } } }
	 * 
	 * // ------------------------------------ Branch Operation End
	 * ------------------------------------------------------
	 * 
	 * 
	 * // ------------------------------------ Account Operation Start
	 * ------------------------------------------------------
	 * 
	 * public Account addAcount() throws SQLException { // Initialize a new Account
	 * Account newAccount = new Account();
	 * 
	 * Scanner scanner = new Scanner(System.in);
	 * 
	 * System.out.println("Create a new account:");
	 * System.out.print("Customer ID: "); int customerId = scanner.nextInt();
	 * 
	 * System.out.print("Branch ID: "); String branchId = scanner.next();
	 * 
	 * System.out.print("Opening Date (yyyy-MM-dd): "); String openingDateStr =
	 * scanner.next(); Date openingDate = Date.valueOf(openingDateStr);
	 * 
	 * System.out.print("Current Balance: "); BigDecimal currentBalance =
	 * scanner.nextBigDecimal();
	 * 
	 * System.out.print("Interest Rate: "); BigDecimal interestRate =
	 * scanner.nextBigDecimal();
	 * 
	 * // Set the account details Customer customer = getCustomerById(customerId);
	 * // Implement a method to retrieve Customer by ID Branch branch =
	 * getBranchById(branchId); // Implement a method to retrieve Branch by ID
	 * 
	 * newAccount.setCustomer(customer); newAccount.setBranch(branch);
	 * newAccount.setOpeningDate((java.sql.Date) openingDate);
	 * newAccount.setCurrentBalance(currentBalance);
	 * newAccount.setInterestRate(interestRate);
	 * 
	 * // Insert the account into the database JdbcConnection connection = new
	 * JdbcConnection(); Connection con = connection.getconnection();
	 * PreparedStatement preparedStatement = null;
	 * 
	 * try { String insertQuery =
	 * "INSERT INTO account (customer_id, branch_id, opening_date, current_balance, interest_rate) VALUES (?, ?, ?, ?, ?)"
	 * ; preparedStatement = con.prepareStatement(insertQuery);
	 * preparedStatement.setInt(1, newAccount.getCustomer().getId());
	 * preparedStatement.setString(2, newAccount.getBranch().getId());
	 * preparedStatement.setDate(3, newAccount.getOpeningDate());
	 * preparedStatement.setBigDecimal(4, newAccount.getCurrentBalance());
	 * preparedStatement.setBigDecimal(5, newAccount.getInterestRate()); int
	 * rowsAffected = preparedStatement.executeUpdate();
	 * 
	 * if (rowsAffected > 0) { System.out.println("Account created successfully!");
	 * } else { System.out.println("Failed to create account."); } } catch
	 * (SQLException e) { e.printStackTrace(); } finally { if (con != null) {
	 * con.close(); System.out.println("Closing Connection......"); } else {
	 * System.out.println("Can't able to close the connection....."); } }
	 * 
	 * return newAccount; }
	 * 
	 * public List<Account> findAccountsOfCustomer() throws SQLException { Scanner
	 * scanner = new Scanner(System.in);
	 * System.out.println("*********** Find Accounts of a Customer ****************"
	 * );
	 * 
	 * System.out.print("Enter Customer ID: "); int customerId = scanner.nextInt();
	 * // Assuming the ID is an integer
	 * 
	 * // Database Connection JdbcConnection connection = new JdbcConnection();
	 * Connection con = connection.getconnection(); PreparedStatement
	 * preparedStatement = null; ResultSet resultSet = null; List<Account>
	 * customerAccounts = new ArrayList<>();
	 * 
	 * try { // Retrieve all accounts associated with the provided customer ID
	 * String selectQuery = "SELECT * FROM account WHERE customer_id = ?";
	 * preparedStatement = con.prepareStatement(selectQuery);
	 * preparedStatement.setInt(1, customerId);
	 * 
	 * resultSet = preparedStatement.executeQuery();
	 * 
	 * while (resultSet.next()) { // Extract account details int id =
	 * resultSet.getInt("id"); String branchId = resultSet.getString("branch_id");
	 * Date openingDate = resultSet.getDate("opening_date"); BigDecimal
	 * currentBalance = resultSet.getBigDecimal("current_balance"); BigDecimal
	 * interestRate = resultSet.getBigDecimal("interest_rate");
	 * 
	 * // Create an Account object and add it to the list Account account = new
	 * Account(); account.setId(id);
	 * account.setCustomer(getCustomerById(customerId)); // Retrieve the customer by
	 * ID account.setBranch(getBranchById(branchId)); // Retrieve the branch by ID
	 * account.setOpeningDate(openingDate);
	 * account.setCurrentBalance(currentBalance);
	 * account.setInterestRate(interestRate);
	 * 
	 * customerAccounts.add(account); } } catch (SQLException e) {
	 * e.printStackTrace(); } finally { if (con != null) { try { resultSet.close();
	 * preparedStatement.close(); con.close();
	 * System.out.println("Closing Connection......"); } catch (SQLException e) {
	 * e.printStackTrace(); } } else {
	 * System.out.println("Can't close the connection."); } }
	 * 
	 * return customerAccounts; }
	 * 
	 * public boolean updateAccount() throws SQLException { Scanner scanner = new
	 * Scanner(System.in); System.out.
	 * println("*********** Update Account Details by ID ****************");
	 * 
	 * System.out.print("Enter Account ID: "); int accountId = scanner.nextInt(); //
	 * Assuming the ID is an integer
	 * 
	 * // Retrieve the existing account details from the database Account
	 * existingAccount = (Account) getAccountById(accountId); // Implement a method
	 * to retrieve Account by ID
	 * 
	 * if (existingAccount == null) {
	 * System.out.println("No account found with the provided ID."); return false; }
	 * 
	 * System.out.println("Provide updated account information:");
	 * 
	 * System.out.print("Customer ID: "); int customerId = scanner.nextInt(); //
	 * Retrieve the customer from the database or use an existing customer object
	 * 
	 * System.out.print("Branch ID: "); String branchId = scanner.next(); //
	 * Retrieve the branch from the database or use an existing branch object
	 * 
	 * System.out.print("Opening Date (yyyy-MM-dd): "); String openingDateStr =
	 * scanner.next(); Date openingDate = Date.valueOf(openingDateStr);
	 * 
	 * System.out.print("Current Balance: "); BigDecimal currentBalance =
	 * scanner.nextBigDecimal();
	 * 
	 * System.out.print("Interest Rate: "); BigDecimal interestRate =
	 * scanner.nextBigDecimal();
	 * 
	 * // Update the existing account details
	 * existingAccount.setCustomer(getCustomerById(customerId)); // Implement a
	 * method to retrieve Customer by ID
	 * existingAccount.setBranch(getBranchById(branchId)); // Implement a method to
	 * retrieve Branch by ID existingAccount.setOpeningDate(openingDate);
	 * existingAccount.setCurrentBalance(currentBalance);
	 * existingAccount.setInterestRate(interestRate);
	 * 
	 * // Database Connection JdbcConnection connection = new JdbcConnection();
	 * Connection con = connection.getconnection(); PreparedStatement
	 * preparedStatement = null;
	 * 
	 * try { // Update the account in the database String updateQuery =
	 * "UPDATE account SET customer_id=?, branch_id=?, opening_date=?, current_balance=?, interest_rate=? WHERE id=?"
	 * ; preparedStatement = con.prepareStatement(updateQuery);
	 * preparedStatement.setInt(1, existingAccount.getCustomer().getId());
	 * preparedStatement.setString(2, existingAccount.getBranch().getId());
	 * preparedStatement.setDate(3, existingAccount.getOpeningDate());
	 * preparedStatement.setBigDecimal(4, existingAccount.getCurrentBalance());
	 * preparedStatement.setBigDecimal(5, existingAccount.getInterestRate());
	 * preparedStatement.setInt(6, existingAccount.getId());
	 * 
	 * int rowsAffected = preparedStatement.executeUpdate();
	 * 
	 * if (rowsAffected > 0) { System.out.println("Account updated successfully!");
	 * return true; } else {
	 * System.out.println("No account found with the provided ID."); } } catch
	 * (SQLException e) { e.printStackTrace(); } finally { if (con != null) {
	 * con.close(); System.out.println("Closing Connection......"); } else {
	 * System.out.println("Can't close the connection."); } } return false; }
	 * 
	 * public boolean deleteAccount() throws SQLException { Scanner scanner = new
	 * Scanner(System.in);
	 * System.out.println("*********** Remove Account by ID ****************");
	 * 
	 * System.out.print("Enter Account ID: "); int accountId = scanner.nextInt(); //
	 * Assuming the ID is an integer
	 * 
	 * // Database Connection JdbcConnection connection = new JdbcConnection();
	 * Connection con = connection.getconnection(); PreparedStatement
	 * preparedStatement = null;
	 * 
	 * try { // Delete the account from the database String deleteQuery =
	 * "DELETE FROM account WHERE id = ?"; preparedStatement =
	 * con.prepareStatement(deleteQuery); preparedStatement.setInt(1, accountId);
	 * 
	 * int rowsAffected = preparedStatement.executeUpdate();
	 * 
	 * if (rowsAffected > 0) { System.out.println("Account removed successfully!");
	 * return true; } else {
	 * System.out.println("No account found with the provided ID."); } } catch
	 * (SQLException e) { e.printStackTrace(); } finally { if (con != null) {
	 * con.close(); System.out.println("Closing Connection......"); } else {
	 * System.out.println("Can't close the connection."); } } return false; }
	 * 
	 * public List<Account> getAccountById(int accountId) throws SQLException { //
	 * TODO Auto-generated method stub return bankImp.findAccountsOfCustomer(); }
	 * 
	 * // ------------------------------------ Account Operation End
	 * ------------------------------------------------------
	 * 
	 * // ------------------------------------ Employee Operation Start
	 * ------------------------------------------------------
	 * 
	 * 
	 * public Employee findEmployeeById() throws SQLException {
	 * 
	 * Scanner scanner = new Scanner(System.in);
	 * 
	 * System.out.println("*********** Create Employee ****************");
	 * 
	 * System.out.print("Enter Employee Position: "); int employeeId =
	 * scanner.nextInt();
	 * 
	 * // Database Connection JdbcConnection connection = new JdbcConnection();
	 * Connection con = connection.getconnection(); PreparedStatement
	 * preparedStatement = null; ResultSet resultSet = null;
	 * 
	 * try { // Retrieve an employee by their ID from the database String
	 * selectQuery = "SELECT * FROM employee WHERE id = ?"; preparedStatement =
	 * con.prepareStatement(selectQuery); preparedStatement.setInt(1, employeeId);
	 * resultSet = preparedStatement.executeQuery();
	 * 
	 * if (resultSet.next()) { int id = resultSet.getInt("id"); String position =
	 * resultSet.getString("position"); int managerId =
	 * resultSet.getInt("manager_id"); String branchId =
	 * resultSet.getString("branch_id");
	 * 
	 * Employee manager = null;
	 * 
	 * // Check if a manager exists and avoid infinite recursion if (managerId > 0
	 * && managerId != employeeId) { manager = findEmployeeById(); // Recursively
	 * fetch manager } Branch branch = (branchId != null) ? getBranchById(branchId)
	 * : null; // Retrieve branch details if needed
	 * 
	 * Employee employee = new Employee(); employee.setId(id);
	 * employee.setPosition(position); employee.setManager(manager);
	 * employee.setBranch(branch); return employee; } } catch (SQLException e) {
	 * e.printStackTrace(); } finally { try { if (resultSet != null) {
	 * resultSet.close(); } if (preparedStatement != null) {
	 * preparedStatement.close(); } if (con != null) { con.close();
	 * System.out.println("Closing Connection......"); } else {
	 * System.out.println("Can't close the connection."); } } catch (SQLException e)
	 * { e.printStackTrace(); } } return null; // Return null if the employee is not
	 * found }
	 * 
	 * 
	 * 
	 * // Print all Manager records public List<Customer> findAllmanager() throws
	 * SQLException { ArrayList columnNames = new ArrayList(); ArrayList data = new
	 * ArrayList();
	 * 
	 * JdbcConnection connection = new JdbcConnection(); Connection con =
	 * connection.getconnection();
	 * 
	 * try { Statement stmt = con.createStatement(); ResultSet rs =
	 * stmt.executeQuery("select * from customer"); // while(rs.next()) { //
	 * System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)); //
	 * }
	 * 
	 * ResultSetMetaData md = rs.getMetaData(); int columns = md.getColumnCount();
	 * // Get column names for (int i = 1; i <= columns; i++) {
	 * 
	 * columnNames.add(md.getColumnName(i)); } // Get row data while (rs.next()) {
	 * ArrayList row = new ArrayList(columns);
	 * 
	 * for (int i = 1; i <= columns; i++) { row.add(rs.getObject(i)); }
	 * System.out.println(row); data.add(row); } } catch (Exception e) {
	 * System.out.println("ERROR" + e.getMessage()); } finally { if (con != null) {
	 * con.close(); System.out.println("Closing Connection......"); } else {
	 * System.out.println("Cant able to close the connection....."); } } return
	 * data; }
	 * 
	 * // ------------------------------------ Employee Operation End
	 * ------------------------------------------------------
	 * 
	 * 
	 * // ------------------------------------ Transaction Operation Start
	 * ------------------------------------------------------
	 * 
	 * 
	 * public boolean withdraw() throws SQLException { // Take user-defined inputs
	 * Scanner scanner = new Scanner(System.in);
	 * System.out.println("Enter Account ID: "); int accountId = scanner.nextInt();
	 * System.out.println("Enter the amount to withdraw: "); BigDecimal amount =
	 * scanner.nextBigDecimal();
	 * 
	 * // Assuming you have a TransactionDAO class for database operations
	 * TransactionDAO transactionDAO = new TransactionDAO(null); AccountDAO
	 * accountDAO = new AccountDAO(null);
	 * 
	 * // Retrieve the account from the database Account account =
	 * accountDAO.getAccountById(accountId);
	 * 
	 * if (account == null) {
	 * System.out.println("No account found with the provided ID."); return false; }
	 * 
	 * if (account.getCurrentBalance().compareTo(amount) < 0) {
	 * System.out.println("Insufficient funds."); return false; }
	 * 
	 * // Update the account balance
	 * account.setCurrentBalance(account.getCurrentBalance().subtract(amount));
	 * 
	 * // Create a new transaction record for the withdrawal Transaction transaction
	 * = new Transaction(); transaction.setAccount(account);
	 * transaction.setAmount(amount); transaction.setDate(new Date(accountId));
	 * 
	 * // Save the updated account and transaction to the database
	 * accountDAO.updateAccount(account);
	 * transactionDAO.createTransaction(transaction);
	 * 
	 * System.out.println("Withdrawal successful."); return true; }
	 * 
	 * 
	 * // ------------------------------------ Transaction Operation Start
	 * ------------------------------------------------------
	 * 
	 * 
	 * // ------------------------------------ Loan Operation Start
	 * ------------------------------------------------------
	 * 
	 * 
	 * @Override public boolean add(Loan loan) { // TODO Auto-generated method stub
	 * return false; }
	 * 
	 * 
	 * @Override public boolean update(Loan loan) { // TODO Auto-generated method
	 * stub return false; }
	 * 
	 * @Override public boolean delete(Loan loan) { // TODO Auto-generated method
	 * stub return false; }
	 * 
	 * @Override public List<Loan> findLoansOfCustomer() { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * // ------------------------------------ Loan Operation End
	 * ------------------------------------------------------
	 * 
	 */
}
