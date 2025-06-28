import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DataBaseStudent {

	//Implemented later on. Solution to problem: I need to check the validity, because otherwise even if the admin login is wrong, it still allows to login. 
	static boolean x= false;


	public static void ReadFromDB(String studentID_in, String studentPassword_in) throws SQLException {

		String driver = "jdbc:sqlite";
		String db = "src/ProjectAttendanceDataBase.db"; 
		String url = driver + ":" + db;
		Connection c = DriverManager.getConnection(url);

		System.out.println("Connection to db\n");

		String sql = "SELECT * FROM [UNIQUE_STUDENTS_WITH_PASSWORDS] WHERE [StudentID] = " + "'" + studentID_in + "'" + "AND [Password] IS " + "'" + studentPassword_in + "'";


		PreparedStatement stmnt = c.prepareStatement(sql);
		ResultSet results = stmnt.executeQuery();

		x=false;

		if (results.next()) {
			String name = results.getString("StudentID");
			System.out.println("Welcome "+name);
			x=true;
		}

		//Error message condition
		else {
			JOptionPane.showMessageDialog(null, "Invalid ID or Password. (Case Sensitive)", "ErrorMessage", 0);
		}

		//Good practice and habit. Like closing BufferedReader/Writers
		stmnt.close();
		c.close();
	}

	public static boolean isStudentValid(){
		return x;

	}

}
