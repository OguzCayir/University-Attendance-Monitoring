import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AdminDisplay extends JFrame {

	private static final long serialVersionUID = 1L;
    private static JPanel contentPane;
    private static JTable table;
    private JTextField searchfield;
    private static DatePicker DateField;
    private JLabel EnterDateLabel,CourseLengthLabel,CourseIDLabel;
    private JButton btnsearch;
    private JTextArea resultArea;
    private JMenuBar menuBar;
    private Button HomeButton, CourseSelectionButton, ApplicationsButton, AttendanceButton, SportsSchoolButton, RestaurantsButton, FinancesButton, RegisterStudentButton;
    private Label UMSTitle;
    private PieChart PieChart;
    private JLabel CourseNameLabel;
    private JTextField CourseID, TotalStudentsInCourseBox,CourseNameBox,CourseNameBox2,LengthofCourse;
    private boolean isDateSet = false; // Adding flag to track if the date is explicitly set
    private static JLabel logo;
	Color mycolor = new Color(60,181,160); //Tyson Teal. Ref.; Brunel Colour Palette
	Color mycolor2 = new Color(227,234,246); //Mack Mist. Ref.; Brunel Colour Palette
	
	
	public AdminDisplay() {
		//1) Main UI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 1024);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(mycolor);
		
		resultArea = new JTextArea(10, 30);
		resultArea.setEditable(false);
		getContentPane().add(new JScrollPane(resultArea));

		//2) Menu Bar 
		menuBar = new JMenuBar();
		menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.PAGE_AXIS));
		menuBar.setBounds(0, 0, 277, 996);
		menuBar.setBackground(mycolor2);
		contentPane.add(menuBar);
		
		String x = "UNIVERSITY MANAGEMENT";
		UMSTitle = new Label(("<html>"+ x +"</html>"));
		UMSTitle.setAlignment(Label.CENTER);
		UMSTitle.setForeground(new Color(0, 42, 83));
		UMSTitle.setFont(new Font("Dialog", Font.BOLD, 20));
		menuBar.add(UMSTitle);
		
		HomeButton = new Button("Home");
		HomeButton.setBackground(Color.PINK);
		HomeButton.setActionCommand("Home");
		HomeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				initialInfoDisplay();
				//tried making piechart disappear, not exactly worked, it is a problem.
			}
			
		});
		menuBar.add(HomeButton);
		
		
		CourseSelectionButton = new Button("Course Selection");
		CourseSelectionButton.setActionCommand("Course Selection");
		menuBar.add(CourseSelectionButton);
		
		ApplicationsButton = new Button("Applications");
		ApplicationsButton.setActionCommand("Applications");
		menuBar.add(ApplicationsButton);
		
		AttendanceButton = new Button("Attendance");
		AttendanceButton.setActionCommand("Attendance");
		menuBar.add(AttendanceButton);
		
		SportsSchoolButton = new Button("Sports School");
		SportsSchoolButton.setActionCommand("SportsSchoolButton");
		menuBar.add(SportsSchoolButton);
		
		RestaurantsButton = new Button("Restaurants");
		RestaurantsButton.setActionCommand("Restaurants");
		menuBar.add(RestaurantsButton);
		
		FinancesButton = new Button("Finances");
		FinancesButton.setActionCommand("Finances");
		menuBar.add(FinancesButton);
		
		RegisterStudentButton = new Button("<html>"+"Register New Student"+"</html>");
		RegisterStudentButton.setBackground(Color.PINK);
		RegisterStudentButton.setActionCommand("RegisterNewStudent");
		menuBar.add(RegisterStudentButton);
		RegisterStudentButton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e)  {
				RegistrationForm frame = new RegistrationForm();
				frame.setVisible(true);
				
			} 
		});
		
		//3) Search Button And Search Fields
		searchfield = new JTextField();
		searchfield.setBounds(397, 21, 180, 35);
		searchfield.setBackground(mycolor2);
		contentPane.add(searchfield);
		searchfield.setColumns(10);
		
		JButton btnsearch = new JButton("Search");
		btnsearch.setBounds(1111, 21, 149, 35);
		contentPane.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//3.1) Date AND ID search TOGETHER
				if (DateField.getSelectedDate()!=null && !searchfield.getText().isEmpty()) {
					int attendedgetter = 0;
					int lategetter =0;
					int absentgetter = 0;
					
// if (isValidDate(DateTextField.getSelectedDate())) {

						try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
								PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ATTENDANCE WHERE SESSIONDATE = " + "'" + DateField.getSelectedDate() + "'" + " AND " + "[STUDENTID] LIKE " + "'%" + searchfield.getText() + "%'") ){
							
							ResultSet rs = stmt.executeQuery();

							DefaultTableModel model = new DefaultTableModel()
							{
							    @Override
							    public boolean isCellEditable(int row, int column) {
							        return false; // Make all cells non-editable
							    }
							};

							model.addColumn("STUDENTID");
							model.addColumn("COURSEID");
							model.addColumn("SESSIONDATE");
							model.addColumn("ATTENDANCE");
							
							if (!rs.next()) {
								JOptionPane.showMessageDialog(null, "No records found.", "ErrorMessage", 0);
							}
							
							resultArea.setText(""); // Clear previous results
							while (rs.next()) {
								
								String studentid = rs.getString("STUDENTID");
								String courseid = rs.getString("COURSEID");
								String sessiondate = rs.getString("SESSIONDATE");
								String attendance = rs.getString("ATTENDANCE");

								Object [] row = {studentid, courseid,sessiondate,attendance};

								model.addRow(row);
								
								//that should help to create pie chart later on.
								if (attendance.equalsIgnoreCase("Yes")) {
								    attendedgetter += 1;
								} else if (attendance.equalsIgnoreCase("Late")) {
								    lategetter += 1;
								} else if (attendance.equalsIgnoreCase("Absent")) {
								    absentgetter += 1;
								}
								
							}
							table.setModel(model);

						}
						catch (SQLException e1){

							JOptionPane.showMessageDialog(null, "Data not found.");
						}
						
//}	
//else {
/* Update: isValidDate no longer needed. I changed it into DatePicker
if (!isValidDate(DateTextField.getSelectedDate())) {
	 JOptionPane.showMessageDialog(null, "Please enter a valid date in dd/MM/yyyy format.");
	 return;
}
*/
//}
				
					//if all 0, it doesn't create pie chart but text labels are printed from PieChart class. SO it is the solution;
					if (attendedgetter > 0 || lategetter > 0 || absentgetter > 0) {
				        PieChartMaker(attendedgetter, lategetter, absentgetter);
				        logo.setVisible(false);
				    }
				}
				

				//3.2) This is for ID search ONLY
				else if (DateField.getSelectedDate()==null && !searchfield.getText().isEmpty()) {
					int attendedgetter = 0;
					int lategetter =0;
					int absentgetter = 0;

					try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
							PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM ATTENDANCE WHERE [STUDENTID] LIKE  " + "'%" + searchfield.getText() + "%'") ){

						ResultSet results = stmnt.executeQuery();

						DefaultTableModel model = new DefaultTableModel(){
						    @Override
						    public boolean isCellEditable(int row, int column) {
						        return false; // Make all cells non-editable
						    }
						};

						model.addColumn("STUDENTID");
						model.addColumn("COURSEID");
						model.addColumn("SESSIONDATE");
						model.addColumn("ATTENDANCE");

						if (!results.next()) {
							JOptionPane.showMessageDialog(null, "No records found.", "ErrorMessage", 0);
						}
						
						while (results.next()) {
							String studentid = results.getString("STUDENTID");
							String courseid = results.getString("COURSEID");
							String sessiondate = results.getString("SESSIONDATE");
							String attendance = results.getString("ATTENDANCE");

							Object [] row = {studentid, courseid,sessiondate,attendance};

							model.addRow(row);
							
							//that should help to create pie chart later on.
							if (attendance.equalsIgnoreCase("Yes")) {
							    attendedgetter += 1;
							} else if (attendance.equalsIgnoreCase("Late")) {
							    lategetter += 1;
							} else if (attendance.equalsIgnoreCase("Absent")) {
							    absentgetter += 1;
							}
						}

						table.setModel(model);
					}
					catch (SQLException e1){
						JOptionPane.showMessageDialog(null, "Error. Please input a valid ID");
					}
					
					if (attendedgetter > 0 || lategetter > 0 || absentgetter > 0) {
				        PieChartMaker(attendedgetter, lategetter, absentgetter);
				        logo.setVisible(false);
				    }
				}

				//3.3) This is for Date Search ONLY
				else if (DateField.getSelectedDate()!=null && searchfield.getText().isEmpty()) {
					int attendedgetter = 0;
					int lategetter =0;
					int absentgetter = 0;

//if (isValidDate(DateTextField.getSelectedDate())) {

						//I forgot "jdbc:sqlite:" part first. Note it to my Debugging file. Also i didn't know we could use parameter () and write SQL connection inside.
						//reference: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
						try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
								PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ATTENDANCE WHERE SESSIONDATE =" + "'" + DateField.getSelectedDate() + "'" ) ){
							
							ResultSet rs = stmt.executeQuery();

							DefaultTableModel model = new DefaultTableModel(){
							    @Override
							    public boolean isCellEditable(int row, int column) {
							        return false; // Make all cells non-editable
							    }
							};

							model.addColumn("STUDENTID");
							model.addColumn("COURSEID");
							model.addColumn("SESSIONDATE");
							model.addColumn("ATTENDANCE");
							
							if (!rs.next()) {
								JOptionPane.showMessageDialog(null, "No records found.", "ErrorMessage", 0);
							}
							
							resultArea.setText(""); // Clear previous results
							while (rs.next()) {
								
								String studentid = rs.getString("STUDENTID");
								String courseid = rs.getString("COURSEID");
								String sessiondate = rs.getString("SESSIONDATE");
								String attendance = rs.getString("ATTENDANCE");

								Object [] row = {studentid, courseid,sessiondate,attendance};

								model.addRow(row);
								
								//that should help to create pie chart later on.
								if (attendance.equalsIgnoreCase("Yes")) {
								    attendedgetter += 1;
								} else if (attendance.equalsIgnoreCase("Late")) {
								    lategetter += 1;
								} else if (attendance.equalsIgnoreCase("Absent")) {
								    absentgetter += 1;
								}
								
							}
							table.setModel(model);

						}
						catch (SQLException e1){

							JOptionPane.showMessageDialog(null, "Invalid date format.");
						}
//}
//else {
//	JOptionPane.showMessageDialog(null, "Invalid date format. Use YYYY-MM-DD.");
//}
					
					if (attendedgetter > 0 || lategetter > 0 || absentgetter > 0) {
				        PieChartMaker(attendedgetter, lategetter, absentgetter);
				        logo.setVisible(false);
				    }

				}
				
				else {
					JOptionPane.showMessageDialog(null, "Invalid date format.");
					return;
				}


			}
		});

		//NOTE: Some of my sanity is lost throughout the process of implementing action listener to the search button. Kind Regards.

		table = new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(289, 388, 985, 602);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);

		JLabel WelcomeUserLabel = new JLabel("Welcome "+Login.loggedInAdminID);
		WelcomeUserLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 30));
		WelcomeUserLabel.setBounds(289, 109, 408, 59);
		contentPane.add(WelcomeUserLabel);
		
		JLabel StudentIDlabel = new JLabel("Student ID");
		StudentIDlabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		StudentIDlabel.setBounds(289, 23, 100, 30);
		contentPane.add(StudentIDlabel);
		
		
        		
		EnterDateLabel = new JLabel("Filter Date");
		EnterDateLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		EnterDateLabel.setBounds(289, 65, 100, 30);
		contentPane.add(EnterDateLabel);
		/* //This is no longer functional, because i replaced it with DatePicker class.
		DateTextField = new JTextField();
		DateTextField.setBounds(1069, 65, 179, 26);
		contentPane.add(DateTextField);
		DateTextField.setColumns(10);
		*/
		DateField = new DatePicker();
		DateField.setBounds(397, 60, 295, 35);
        DateField.setLayout(new GridLayout(1, 0, 0, 0));
        DateField.setBackground(mycolor);
        contentPane.add(DateField);
        DateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                isDateSet = !DateField.getSelectedDate().isEmpty(); // Set the flag if the date is not empty
                
            }
        });
        
		//4) Student Information Panel. This area display info from search function mostly.
		JPanel studentInfoPanel = new JPanel();
		studentInfoPanel.setBounds(289, 180, 600, 200);
		contentPane.add(studentInfoPanel);
		studentInfoPanel.setBackground(mycolor2);
		studentInfoPanel.setLayout(null);
		
		CourseNameLabel = new JLabel("Course Name");
		CourseNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		CourseNameLabel.setBounds(6, 6, 141, 30);
		studentInfoPanel.add(CourseNameLabel);
		
		CourseID = new JTextField();
		CourseID.setEditable(false);
		CourseID.setBounds(300, 48, 282, 46);
		studentInfoPanel.add(CourseID);
		CourseID.setColumns(10);
		CourseID.setText(""); //get the data in here.
		//Take the data from mouse click
		
		table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				int selectedRow = table.getSelectedRow(); // Get selected row index
				int columnIndex = 1; // Change this to the column index where Course Name is stored

				if (selectedRow != -1) { // Ensure a row is selected
					String selectedCourse = table.getValueAt(selectedRow, columnIndex).toString(); // Get course name
					CourseID.setText(selectedCourse); // Display in text field
				}

				
			}
		});
		

		JLabel TotalStudentsInCourseLabel = new JLabel("Total Students In Course");
		TotalStudentsInCourseLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		TotalStudentsInCourseLabel.setBounds(300, 106, 249, 30);
		studentInfoPanel.add(TotalStudentsInCourseLabel);
		
		TotalStudentsInCourseBox = new JTextField();
		TotalStudentsInCourseBox.setText("");
		TotalStudentsInCourseBox.setEditable(false);
		TotalStudentsInCourseBox.setColumns(10);
		TotalStudentsInCourseBox.setBounds(300, 148, 282, 46);
		studentInfoPanel.add(TotalStudentsInCourseBox);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				int selectedRow = table.getSelectedRow(); // Get selected row index

				if (selectedRow != -1) { // Ensure a row is selected
					
					// I want to execute this : SELECT  DISTINCT STUDENTID FROM ATTENDANCE WHERE COURSEID = 'BMS' 
					int numberofrecords = 0;
					try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
							PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT STUDENTID FROM ATTENDANCE WHERE COURSEID =" + "'" + CourseID.getText()  + "'" ) ){
						
						ResultSet rs = stmt.executeQuery();
												
						while (rs.next()) {
							numberofrecords +=1;
						}
							
					} catch (SQLException e1) {
						e1.printStackTrace();
					};
					int totalstudentsincourse = numberofrecords;
					TotalStudentsInCourseBox.setText(String.valueOf( totalstudentsincourse)); // Display in TotalStudentsInCourseBox field
				}

			}
		}); //that is a wild counting system. God have mercy. //That worked first time without error. :))
		
		JButton editaccessbutton = new JButton("Edit");
		editaccessbutton.setBounds(477, 9, 117, 29);
		studentInfoPanel.add(editaccessbutton);
		
		editaccessbutton.addActionListener(new ActionListener() {
			 private String IDupdate, nameupdate, nameupdate2;
		
			public void actionPerformed(ActionEvent e) {
				if (editaccessbutton.getText().equalsIgnoreCase("Edit")) {
					 IDupdate = CourseID.getText();
					 nameupdate = CourseNameBox.getText();
					 nameupdate2 = CourseNameBox2.getText(); 
		            // Switch to "Edit" mode
		            CourseID.setEditable(true);
		            CourseNameBox.setEditable(true);
		            CourseNameBox2.setEditable(true);
		            editaccessbutton.setText("Save"); // Change button text to "Save"
		        }

				
				else if(editaccessbutton.getText().equalsIgnoreCase("Save")) {

					
					String IDupdateD = CourseID.getText();
					String nameupdateD = CourseNameBox.getText();
					String nameupdate2D = CourseNameBox2.getText();
					
					if (IDupdateD.isEmpty() || nameupdateD.isEmpty() || nameupdate2D.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Course name cannot be empty.");
		                return;
		            }
					
					//updating real DataBase:
					try(Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");){
						try (PreparedStatement stmt = conn.prepareStatement("UPDATE ATTENDANCE SET COURSEID =" + "'"+ IDupdateD + "'" + " WHERE COURSEID = "+ "'" + IDupdate + "'" ) ){

							//Displaying message:
							int rowsUpdated =  stmt.executeUpdate();
							if (rowsUpdated > 0) {
								JOptionPane.showMessageDialog(null, "Course name updated successfully.");
							} else {
								JOptionPane.showMessageDialog(null, "No records were updated. Please check the course name.");
							}

						} 
						try (PreparedStatement stmt = conn.prepareStatement( "UPDATE CombinedCourseAndInstitution SET LEGAL_NAME ='"+ nameupdateD+"', TITLE ='"+nameupdate2D+"' WHERE LEGAL_NAME ='"+nameupdate+"' AND TITLE ='"+nameupdate2+"'" ) ){

							//Displaying message:
							int rowsUpdated =  stmt.executeUpdate();
							if (rowsUpdated > 0) {
								JOptionPane.showMessageDialog(null, "Course name updated successfully.");
							} else {
								JOptionPane.showMessageDialog(null, "No records were updated. Please check the course name.");
							}

						} 
					}
					catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error updating course name: " + e1.getMessage());
					};

					//Closing and setting things back to false and Edit.
					CourseID.setEditable(false);
		            CourseNameBox.setEditable(false);
		            CourseNameBox2.setEditable(false);
		            editaccessbutton.setText("Edit");
				}
			}
		});
		
		LengthofCourse = new JTextField();
		LengthofCourse.setText("");
		LengthofCourse.setEditable(false);
		LengthofCourse.setColumns(10);
		LengthofCourse.setBounds(6, 148, 282, 46);
		studentInfoPanel.add(LengthofCourse);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try(Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
						PreparedStatement stmt = conn.prepareStatement("SELECT KISLEVEL FROM CombinedCourseAndInstitution WHERE KISCOURSEID = '"+CourseID.getText() +"'" ) ){
					
					ResultSet x = stmt.executeQuery();
					LengthofCourse.setText("Course Length: "+x.getString("KISLEVEL"));

					
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		CourseLengthLabel = new JLabel("Course Length");
		CourseLengthLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		CourseLengthLabel.setBounds(6, 106, 249, 30);
		studentInfoPanel.add(CourseLengthLabel);
		
		CourseIDLabel = new JLabel("Course ID");
		CourseIDLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		CourseIDLabel.setBounds(300, 14, 165, 30);
		studentInfoPanel.add(CourseIDLabel);
		
		CourseNameBox = new JTextField();
		CourseNameBox.setText("");
		CourseNameBox.setEditable(false);
		CourseNameBox.setColumns(10);
		CourseNameBox.setBounds(6, 48, 282, 23);
		studentInfoPanel.add(CourseNameBox);
		
		CourseNameBox2 = new JTextField();
		CourseNameBox2.setText("");
		CourseNameBox2.setEditable(false);
		CourseNameBox2.setColumns(10);
		CourseNameBox2.setBounds(6, 71, 282, 23);
		
		studentInfoPanel.add(CourseNameBox2);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try(Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
					PreparedStatement stmt = conn.prepareStatement("SELECT LEGAL_NAME FROM CombinedCourseAndInstitution WHERE KISCOURSEID = '"+CourseID.getText() +"'" );
					PreparedStatement stmt2 = conn.prepareStatement("SELECT TITLE FROM CombinedCourseAndInstitution WHERE KISCOURSEID = '"+CourseID.getText() +"'" )		){
				
					ResultSet x = stmt.executeQuery();
					ResultSet y = stmt2.executeQuery();
					
					CourseNameBox.setText(x.getString("LEGAL_NAME"));
					CourseNameBox2.setText(y.getString("TITLE"));
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		
		
		JButton clearDateButton = new JButton("Clear Date");
		clearDateButton.setForeground(Color.RED);
		clearDateButton.setBounds(693, 60, 100, 35); // Adjust position as needed
		clearDateButton.addActionListener(e -> {
		    DateField.clearSelectedDate(); // Clear the selected date
		});
		contentPane.add(clearDateButton);
		
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(getClass().getResource("/bruneloldlogo.png")));
		logo.setBounds(1021, 109, 190, 250);
		
	initialInfoDisplay(); //I don't want it to look empty on welcome screen.
		
		
		
	}//end of the main ADMINDISPLAY 

	//METHODS 
	
	//Update: It became useless recently since i changed textfields to datepicker format, and it is always dd/mm/yyyy. Keeping it only for records and showing the process of development
	//w3 school SimpleDataFormat reference
	private boolean isValidDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		//Explanation: as default Lenient is true. And dates are auto-corrected. Therefore we have to set it false. So if someone inputs 30th of February It gives an error. If Lenient would be true, It would fix it as 1 March.
		//That is a problem because we want to search EXACT dates in our datebase.
		try {
			sdf.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	public void PieChartMaker (double attended, double late, double absent) {
		//removing if there is any other component, so it resets the pie chart.
		Component[] components = contentPane.getComponents();
	    for (Component component : components) {
	        if (component instanceof JPanel && component.getName() != null && component.getName().equals("chartContainer")) { //3 conditions to remove.
	            contentPane.remove(component);
	            break; // Remove only the first found chart
	        }
	    }
		
				// Create slices
				Slice[] slices = {
				    new Slice(attended, Color.GREEN, "Attended"), //first number is how many is attended. //it is actually does not make sense t assign "double" type, because a person can not be half.
				    new Slice(late, Color.YELLOW, "Late"),
				    new Slice(absent, Color.RED, "Absent")
				};

				// Create the PieChart
				PieChart pieChart = new PieChart(slices);

				// Create a container panel with BorderLayout to handle resizing
				JPanel chartContainer = new JPanel();
				chartContainer.setLayout(new BorderLayout());
				chartContainer.setBounds(924, 68, 350, 300);  // Position on AdminDisplay //If i want to reposition the chart, it is this parameters.
				chartContainer.setBackground(mycolor);
				chartContainer.add(pieChart, BorderLayout.CENTER);
				chartContainer.setName("chartContainer"); // Set a name to identify it later //Condition to remove piechart with every search button action.

				// Add to contentPane
				contentPane.add(chartContainer);
				pieChart.setBackground(mycolor);
				contentPane.revalidate(); //Necessary to reload (kind of) the pie chart panel
			    contentPane.repaint();
			    logo.setVisible(false);
		
	}
	
	public static void initialInfoDisplay() {
		String query = "SELECT LEGAL_NAME,KISCOURSEID, TITLE FROM CombinedCourseAndInstitution";

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        DefaultTableModel model = new DefaultTableModel() {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // Make the table non-editable
	            }
	        };
  
	        model.addColumn("Legal Name");
	        model.addColumn("KIS Course ID");
	        model.addColumn("Title");
	        

	        while (rs.next()) {
	            String legalName = rs.getString("LEGAL_NAME");
	            String kisCourseId = rs.getString("KISCOURSEID");
	            String title = rs.getString("TITLE");
	            
	            model.addRow(new Object[]{legalName,kisCourseId, title });
	        }

	        table.setModel(model);

	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error loading initial data: " + e.getMessage());
	    }
	    
	    DateField.clearSelectedDate(); // Clear the selected date because it always starts with a date for some reason
	    
	    
		contentPane.add(logo);
		logo.setVisible(true);
		
	
	}
}//end of class
