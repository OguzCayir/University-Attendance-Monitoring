import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudentDisplay extends JFrame {

	// UI Components  
	private JPanel contentPane;  
	private static JTable table;  
	private JTextArea resultArea;  
	private JMenuBar menuBar;  
	private PieChart PieChart;  
	// Buttons  
	private JButton btnsearch;  
	private Button HomeButton, CourseSelectionButton, ApplicationsButton, AttendanceButton,SportsSchoolButton, RestaurantsButton, FinancesButton;  
	// Course Information Fields  
	private JLabel CourseNameLabel;  
	private JTextField CourseIDDisplayBox, TotalStudentsInCourseBox, CourseNameField1,CourseNameTitle2, KISLevel3;  
	// Title Label  
	private Label UMSTitle; 
	//New colours
	Color mycolor = new Color(60,181,160); //TysonTeal Ref.; Brunel Colour Palette
	Color mycolor2 = new Color(227,234,246); //Mack Mist Ref. Brunel Colour Palette
	private DatePicker DateField1;
	private DatePicker DateField2;
	

	public StudentDisplay() throws SQLException {
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
		
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(289, 688, 985, 300);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);

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
		HomeButton.setActionCommand("Home");
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

		//Implementing a different panel to display student infos.

		JPanel studentInfoPanel = new JPanel();
		studentInfoPanel.setBounds(289, 15, 600, 660);
		contentPane.add(studentInfoPanel);
		studentInfoPanel.setBackground(mycolor2);
		studentInfoPanel.setLayout(null);

		JLabel WelcomeUserLabel = new JLabel("");
		WelcomeUserLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 30));
		WelcomeUserLabel.setBounds(6, 41, 408, 59);
		studentInfoPanel.add(WelcomeUserLabel);
		WelcomeUserLabel.setText("Welcome "+Login.loggedInStudentID);
		
		JLabel logo = new JLabel("");
		logo.setBounds(352, 154, 201, 204);
		studentInfoPanel.add(logo);
		logo.setIcon(new ImageIcon(getClass().getResource("/defaultprofile.png")));
		
		JLabel logo2 = new JLabel("");
		logo2.setBounds(1004, 15, 190, 250);
		contentPane.add(logo2);
		logo2.setIcon(new ImageIcon(getClass().getResource("/bruneloldlogo.png")));
		
		CourseNameLabel = new JLabel("Course ID");
		CourseNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		CourseNameLabel.setBounds(6, 112, 141, 30);
		studentInfoPanel.add(CourseNameLabel);

		CourseIDDisplayBox = new JTextField();
		CourseIDDisplayBox.setEditable(false);
		CourseIDDisplayBox.setBounds(6, 154, 282, 46);
		studentInfoPanel.add(CourseIDDisplayBox);
		CourseIDDisplayBox.setColumns(10);
		CourseIDDisplayBox.setText(""); //get the data in here.

		//Initially Setting searchbox results. It is different from AdminDisplay because it is independent from mouselistener and a table
		Connection conn1 = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
		PreparedStatement stmt1 = conn1.prepareStatement("SELECT COURSEID FROM ATTENDANCE WHERE STUDENTID = '" + Login.loggedInStudentID +"'");
		ResultSet rs1 = stmt1.executeQuery();;
		CourseIDDisplayBox.setText(rs1.getString(1));

		JLabel TotalStudentsInCourseLabel = new JLabel("Total Students In Course");
		TotalStudentsInCourseLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		TotalStudentsInCourseLabel.setBounds(6, 212, 201, 30);
		studentInfoPanel.add(TotalStudentsInCourseLabel);

		TotalStudentsInCourseBox = new JTextField();
		TotalStudentsInCourseBox.setText("");
		TotalStudentsInCourseBox.setEditable(false);
		TotalStudentsInCourseBox.setColumns(10);
		TotalStudentsInCourseBox.setBounds(6, 254, 282, 46);
		studentInfoPanel.add(TotalStudentsInCourseBox);
		//SETTING TEXT BUT it is different from Admin so, no mouselistener needed.
		int numberofrecords = 0;
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
				PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT STUDENTID FROM ATTENDANCE WHERE COURSEID =" + "'" + CourseIDDisplayBox.getText()  + "'" ) ){

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				numberofrecords +=1;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		};
		int totalstudentsincourse = numberofrecords;
		TotalStudentsInCourseBox.setText(String.valueOf( totalstudentsincourse)); // Display in text field
		//SETTING TEXT BUT it is different from Admin so, no mouselistener needed.//END

		JLabel lblCourseName = new JLabel("Course Name");
		lblCourseName.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblCourseName.setBounds(6, 323, 141, 30);
		studentInfoPanel.add(lblCourseName);
		
		CourseNameField1 = new JTextField();
		CourseNameField1.setText("");
		CourseNameField1.setEditable(false);
		CourseNameField1.setColumns(10);
		CourseNameField1.setBounds(6, 365, 282, 35);
		studentInfoPanel.add(CourseNameField1);

		//setting text from database search
		String OutputLegalName="";
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
				PreparedStatement stmt = conn.prepareStatement("SELECT LEGAL_NAME FROM CombinedCourseAndInstitution WHERE KISCOURSEID = " +"'"+ CourseIDDisplayBox.getText()+"'" )  ){

			ResultSet rs = stmt.executeQuery();

			OutputLegalName = rs.getString(1);

		}
		CourseNameField1.setText(OutputLegalName);

		CourseNameTitle2 = new JTextField();
		CourseNameTitle2.setText("");
		CourseNameTitle2.setEditable(false);
		CourseNameTitle2.setColumns(10);
		CourseNameTitle2.setBounds(6, 412, 282, 35);
		studentInfoPanel.add(CourseNameTitle2);

		//setting text from database search
		String OutputTitle = "";
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
				PreparedStatement stmt = conn.prepareStatement("SELECT TITLE FROM CombinedCourseAndInstitution WHERE KISCOURSEID = " +"'"+ CourseIDDisplayBox.getText()+"'" )  ){

			ResultSet rs = stmt.executeQuery();

			OutputTitle = rs.getString(1);

		}
		CourseNameTitle2.setText(OutputTitle);


		KISLevel3 = new JTextField();
		KISLevel3.setText("");
		KISLevel3.setEditable(false);
		KISLevel3.setColumns(10);
		KISLevel3.setBounds(6, 459, 282, 35);
		studentInfoPanel.add(KISLevel3);

		//setting text from database search
		String OutputLength = "";
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
				PreparedStatement stmt = conn.prepareStatement("SELECT KISLEVEL FROM CombinedCourseAndInstitution WHERE KISCOURSEID = " +"'"+ CourseIDDisplayBox.getText()+"'" )  ){

			ResultSet rs = stmt.executeQuery();

			OutputLength = rs.getString(1);

		}
		KISLevel3.setText("Length of Course: "+OutputLength);
				
				DateField1 = new DatePicker();
				DateField1.setBounds(6, 554, 282, 35);
				studentInfoPanel.add(DateField1);
				DateField1.setBackground(mycolor2);
				DateField1.setLayout(new GridLayout(1, 0, 0, 0));
				
				DateField2 = new DatePicker();
				DateField2.setBounds(6, 591, 282, 35);
				studentInfoPanel.add(DateField2);
				DateField2.setBackground(mycolor2);
				DateField2.setLayout(new GridLayout(1, 0, 0, 0));
				
				JButton clearDateButton = new JButton("Clear Dates");
				clearDateButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				clearDateButton.setForeground(Color.RED);
				clearDateButton.setBounds(159, 519, 129, 35); 
				studentInfoPanel.add(clearDateButton);
				clearDateButton.addActionListener(e -> {
				    DateField1.clearSelectedDate(); // Clear the selected dates -> method comes from DatePicker class.
				    DateField2.clearSelectedDate();
				    try {
						AttenadanceTable();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				
				
				btnsearch = new JButton("Filter Dates");
				btnsearch.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				btnsearch.setForeground(Color.BLUE);
				btnsearch.setBounds(6, 519, 141, 35);
				studentInfoPanel.add(btnsearch);
				btnsearch.addActionListener(new ActionListener() {	
					public void actionPerformed(ActionEvent e) {
						int attendedgetter = 0, lategetter = 0, absentgetter = 0;
						//TWO dates are entered:
						if (DateField1.getSelectedDate()!=null && DateField2.getSelectedDate()!=null) {
							//issue with only 'Having 2 dates". SQl database dates are "text" formats. Comparing is not possible. Solution is to format user inputs in SQL query.
								String formattedDateField1 = reformatDate(DateField1.getSelectedDate()) ;
								String formattedDateField2 = reformatDate(DateField2.getSelectedDate()) ;
								try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
										PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Attendance " +
							                     "WHERE (substr(SessionDate, 7, 4) || '-' || substr(SessionDate, 4, 2) || '-' || substr(SessionDate, 1, 2)) " +
							                     "BETWEEN '" + formattedDateField1+"' AND '"+formattedDateField2+"' " +
							                     "AND StudentID = '" + Login.loggedInStudentID + "'" )  ){

									ResultSet rs =  stmt.executeQuery();

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
										JOptionPane.showMessageDialog(null, "No records found between this dates.", "ErrorMessage", 0);
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

								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}


							}

						

						//ONE date is entered
						else if (DateField1.getSelectedDate()!=null && DateField2.getSelectedDate()==null) {
							
							try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
									PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ATTENDANCE WHERE SESSIONDATE = '" + DateField1.getSelectedDate() + "'"+ " AND STUDENTID = '" + Login.loggedInStudentID + "'" )  ){

								ResultSet rs =  stmt.executeQuery();

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
									JOptionPane.showMessageDialog(null, "No record found on this date.", "ErrorMessage", 0);
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

							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
						else { //If user only fills second field but not the first one
							JOptionPane.showMessageDialog(null, "To search specific date; please use only the first field ", "ErrorMessage", 0);
						}
						//PieChartMaker data
						if (attendedgetter > 0 || lategetter > 0 || absentgetter > 0) {
					        PieChartMaker(attendedgetter, lategetter, absentgetter);
					    } //Make a piechart after the search function and table-creation has been initialized
					}
					
				});
		
				
		
		//LAUNCH ALL ATTAENDANCE ON ENTRY
		AttenadanceTable();

	}

	//METHODS 

	//Update: Not valid anymore, i will use DateField class instead of textfield for date input from user.
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
		chartContainer.setBounds(920, 320, 350, 350);  // Position on AdminDisplay //If i want to reposition the chart, it is this parameters.
		chartContainer.setBackground(mycolor);
		chartContainer.add(pieChart, BorderLayout.CENTER);
		chartContainer.setName("chartContainer"); // Set a name to identify it later //Condition to remove piechart with every search button action.

		// Add to contentPane
		contentPane.add(chartContainer);
		pieChart.setBackground(mycolor);
		contentPane.revalidate(); //Necessary to reload (kind of) the pie chart panel
		contentPane.repaint();

	}
	public void AttenadanceTable () throws SQLException{
		int attendedgetter = 0, lategetter = 0, absentgetter = 0;
		
		Connection conn1 = DriverManager.getConnection("jdbc:sqlite:src/ProjectAttendanceDataBase.db");
		PreparedStatement stmt1 = conn1.prepareStatement("SELECT * FROM ATTENDANCE WHERE STUDENTID = '" + Login.loggedInStudentID +"'");
		ResultSet rs1 = stmt1.executeQuery();;

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

		resultArea.setText(""); // Clear previous results
		while (rs1.next()) {

			String studentid = rs1.getString("STUDENTID");
			String courseid = rs1.getString("COURSEID");
			String sessiondate = rs1.getString("SESSIONDATE");
			String attendance = rs1.getString("ATTENDANCE");

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
		
		if (attendedgetter > 0 || lategetter > 0 || absentgetter > 0) {
	        PieChartMaker(attendedgetter, lategetter, absentgetter);
	    } //Make a piechart after the search function and table-creation has been initialized

		DateField1.clearSelectedDate(); //Upon login, datefields set January 2025 for some reason.Solution; <-
		DateField2.clearSelectedDate();
	}
	//SQl database date formats are texts. In order to filter the dates, i have to covert user inputs. (Only for BETWEEN command.) Because having 2 dates and searching a "range" does work otherwise. 
	//This could have been sorted as changing the SQl database completely and formatting as "date". 
	public static String reformatDate(String dateDDMMYYYY) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateDDMMYYYY, inputFormatter);
        return date.format(outputFormatter);
    }
}
