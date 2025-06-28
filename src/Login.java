import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField ID;
    private JPasswordField Password;
    private JButton btnAdminLogin;
    private JButton btnStudentLogin;
    
    public static String loggedInStudentID = ""; //it will help to identify student in StudentDisplay class
    public static String loggedInAdminID = "";
    
    //Main for login. / Start the app
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    public Login() {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 380);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Buttons for Admin or Student first
        btnAdminLogin = new JButton("Admin Login");
        btnAdminLogin.setBounds(170, 120, 160, 40);
        btnAdminLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(btnAdminLogin);

        btnStudentLogin = new JButton("Student Login");
        btnStudentLogin.setBounds(170, 200, 160, 40);
        btnStudentLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(btnStudentLogin);

        // Action listeners for the login buttons. So I condensed different classes into one
        btnAdminLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLoginForm("Admin");
            }
        });

        btnStudentLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLoginForm("Student");
            }
        });
    }

    // Function to show the login form based on Admin or Student choice
    private void showLoginForm(String loginType) {
        // Clear the main page. Otherwise you can't out everything on top of each other.
        contentPane.removeAll();
        contentPane.repaint();
        
        // Setup new login details for Admin or Student
        JLabel lblLoginType = new JLabel(loginType + " Login"); //to clarify for user
        lblLoginType.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblLoginType.setBounds(160, 30, 150, 30);
        contentPane.add(lblLoginType);

        //Labels for ID and Password
        JLabel lblID = new JLabel("Login ID");
        lblID.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblID.setBounds(130, 70, 80, 16);
        contentPane.add(lblID);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(130, 140, 80, 16);
        contentPane.add(lblPassword);
        
        // Create ID and Password fields
        ID = new JTextField();
        ID.setBounds(130, 90, 220, 26);
        contentPane.add(ID);
        ID.setColumns(10);

        Password = new JPasswordField();
        Password.setBounds(130, 160, 220, 26);
        contentPane.add(Password);
        Password.setColumns(10);

        //Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnLogin.setBounds(185, 200, 117, 29);
        contentPane.add(btnLogin);

        // Action for the Login Button
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loginType.equals("Admin")) {
                    // Validate Admin credentials
                    try {
                       
                    	DataBaseAdmin.ReadFromDB(ID.getText(),(Password.getText()));
                    	
                        if (DataBaseAdmin.IsAdminValid()) {
                        	loggedInAdminID= ID.getText(); ////store the ID to use in AdminDisplay
                            AdminDisplay frame = new AdminDisplay();
                            frame.setVisible(true);
                            setVisible(false); //nice, now i can hide the login panel.
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Admin Credentials", "Login Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else if (loginType.equals("Student")) {
                    // Validate Student credentials
                    try {
                    	DataBaseStudent.ReadFromDB(ID.getText(),(Password.getText()));
                    	
                        if (DataBaseStudent.isStudentValid()) {
                            loggedInStudentID = ID.getText(); //store the ID to use in StudentDisplay hehehe
                            StudentDisplay frame = new StudentDisplay();
                            frame.setVisible(true);
                            setVisible(false); //nice, now i can hide the login panel.
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Student Credentials", "Login Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });

        // Refresh the UI
        contentPane.revalidate();
        contentPane.repaint();
    }
}
