import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RegistrationForm extends JFrame {
    private JTextField nameField, surnameField;
    private JPasswordField passwordField;
    private static JComboBox<String> universityBox; //set static to use it in identifyCourseID method
	private static JComboBox<String> departmentBox; //set static to use it in identifyCourseID method
    private JButton registerButton, cancelButton;

    public RegistrationForm() {
        setTitle("Register New Student");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); //https://docs.oracle.com/javase/6/docs/api/java/awt/Window.html#setLocationRelativeTo(java.awt.Component)
        setLayout(new GridLayout(6, 2, 5, 5));
        
        // Labels and Inputs
        add(new JLabel("Name and Surname:"));
        nameField = new JTextField();
        add(nameField);
        
        add(new JLabel("Set Password:"));
        passwordField = new JPasswordField();
        add(passwordField);
        
        add(new JLabel("University:"));
        universityBox = new JComboBox<>();
        //method implementation. Choose the uni from existing universities
        loadUniqueUniversities(universityBox);
        add(universityBox);
        
        add(new JLabel("Department:"));
        departmentBox = new JComboBox<>();
        //method implementation. Choose the department from existing universities
        loadUniqueDepartments(departmentBox);
        add(departmentBox);
        
        
        
        add(new JLabel("ASSIGNED COURSE ID:"));
        JTextField AssignedCourseID = new JTextField();
        AssignedCourseID.setEditable(false); //not editable
        add(AssignedCourseID);
      //Setting text by using search method from selected Department and uni
        universityBox.addActionListener(new ActionListener() {
     
            public void actionPerformed(ActionEvent e) {
            	AssignedCourseID.setText(identifyCourseID());
            }
        });

        departmentBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	AssignedCourseID.setText(identifyCourseID());
            }
        });

        //Quick date implementation for Register button
        LocalDate currentDate = LocalDate.now(); // Get the current date
        DateTimeFormatter myformat = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formatting to fit among other dates in Attendance database
        String formattedDate = currentDate.format(myformat); // Format the date
        
        // Buttons
        registerButton = new JButton("Register");
        add(registerButton);
        
        registerButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (nameField.getText().isEmpty() || passwordField.getText().isEmpty() || universityBox.equals("Select University") || departmentBox.equals("Select Department")) {
        			JOptionPane.showMessageDialog(null, "Please fill all fields correctly.");
        			return;
        		}

        			try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
        				PreparedStatement stmt = conn.prepareStatement("INSERT INTO Unique_Students_With_Passwords (StudentID, Password) VALUES ('" + nameField.getText()+  "','" +passwordField.getText() + "')");
        				PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO ATTENDANCE (StudentID, COURSEID, SESSIONDATE,ATTENDANCE) VALUES ('" + nameField.getText()+  "','" +AssignedCourseID.getText() + "','" +formattedDate +"','" +"REGISTERED')");
                		){

        			
        			stmt.executeUpdate();
        			
        			stmt2.executeUpdate();
        			
        			
        			} catch (SQLException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        			}

        		JOptionPane.showMessageDialog(null, "Student Registered Successfully!"); //Reference: ShowMessageDialog learned from Zear Ibrahim. 
        		dispose(); // Close window after successful registration
        	}
        });
        
        cancelButton = new JButton("Cancel");
        add(cancelButton);
        
        
        // Cancel button action
        cancelButton.addActionListener(e -> dispose()); //got help from AI
        
    }
    
   
    //METHODS
    
    public static void loadUniqueUniversities(JComboBox<String> mydropbox1){
    	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
    			PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT LEGAL_NAME FROM CombinedCourseAndInstitution");) {
    		
    		ResultSet rs = stmt.executeQuery();
    		ArrayList<String> universityList = new ArrayList<>();
    		universityList.add("Select University"); //to help register and add a condition to not leave it empty
    		
    		while (rs.next()) {
    			universityList.add(rs.getString("LEGAL_NAME"));
    		}

    		// Convert list to array and add to JComboBox/dropbox
    		for (int i = 0; i < universityList.size(); i++) {
    			mydropbox1.addItem(universityList.get(i));
    		}


    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public static void loadUniqueDepartments(JComboBox<String> mydropbox2){
    	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
    			PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT TITLE FROM CombinedCourseAndInstitution");) {
    		
    		ResultSet rs = stmt.executeQuery();
    		ArrayList<String> departmentList = new ArrayList<>();
    		departmentList.add("Select Department"); //to help register and add a condition to not leave it empty
    		
    		while (rs.next()) {
    			departmentList.add(rs.getString("TITLE"));
    		}

    		// Convert list to array and add to JComboBox/dropbox
    		for (int i = 0; i < departmentList.size(); i++) {
    			mydropbox2.addItem(departmentList.get(i));
    		}


    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public static String identifyCourseID() {
    	String x = "Course not found in this university"; //set as fixed in case try does not re-write this. So error handling of sort...
    	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
    			PreparedStatement stmt = conn.prepareStatement("SELECT KISCOURSEID FROM CombinedCourseAndInstitution WHERE TITLE = '"+ departmentBox.getSelectedItem().toString() +"' AND LEGAL_NAME =  '"+ universityBox.getSelectedItem().toString() +"'");) {
    		
    		ResultSet rs = stmt.executeQuery();
    		
    		if (rs.next()) {
    		 x = rs.getString("KISCOURSEID");
    		}
    		
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return x;
    	
    }
    
   
}
