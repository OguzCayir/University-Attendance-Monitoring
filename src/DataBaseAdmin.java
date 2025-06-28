import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DataBaseAdmin {

	//Implemented later on. Solution to problem: I need to check the validity, because otherwise even if the admin login is wrong, it still allows to login. 
	static boolean x= false;


	public static void ReadFromDB(String admin_name, String admin_password) throws SQLException {

		String driver = "jdbc:sqlite";
		String db = "src/ProjectAttendanceDataBase.db"; 
		String url = driver + ":" + db;
		Connection c = DriverManager.getConnection(url);

		System.out.println("Connection to db\n");

		String sql = "SELECT * FROM [ADMINS] WHERE [Admin Name] LIKE " + "'" + admin_name + "'" + "AND [Admin Password] IS " + "'" + admin_password + "'";


		PreparedStatement stmnt = c.prepareStatement(sql);
		ResultSet results = stmnt.executeQuery();

		x=false;

		if (results.next()) {
			String name = results.getString("Admin Name");
			System.out.println("Welcome "+name);
			x=true;
		}

		//Error message condition
		else  {
			JOptionPane.showMessageDialog(null, "Please enter a valid ID or Password", "ErrorMessage", 0);
		}

		//Good practice and habit. Like closing BufferedReader/Writers
		stmnt.close();
		c.close();

	}

	public static boolean IsAdminValid(){
		return x;

	}


}
