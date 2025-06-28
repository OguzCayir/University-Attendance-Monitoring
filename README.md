# University Management System – Attendance Module

This is the **Attendance Monitoring** module from a larger **University Management System** project developed as a group coursework for university.

## 🔍 Overview

This module allows students to:
- Allows Admins to view and filter attendance records by date, course, or student.
- Enables Students to view their personal attendance and filter by date.
- Displays attendance summaries using a custom-built pie chart.
- Includes login access control, student registration, and editable course data (for Admins only).

The attendance data is pulled from a `ProjectAttendanceDataBase.db` file using JDBC.

## 🧠 Group Project Note

This is **only one part** of a larger group project. Other modules (developed by team members) included:
- Course Selection
- Student Applications
- Sports and Facilities
- Financial Overview

## 📄 Full Implementation Report

For full documentation, code explanation, and screenshots, see:
(AttendanceModule_Report.pdf)


## 📁 This Repo Includes

The application begins with a Login interface and branches to different views depending on access level:

- **Login.java**
Launch screen that handles user login. Verifies credentials and routes the user either to the AdminDisplay or StudentDisplay page based on role selection.
- **AdminDisplay.java**
Main interface for Admin users. Allows date-based attendance filtering, displays attendance records in a table, and generates visual pie chart summaries. Admins can also update course and student data.
- **StudentDisplay.java**
A student-facing interface that restricts access to only the logged-in student's records. Students can filter and view their own attendance.
- **PieChart.java and Slice.java**
Custom-rendered pie chart using AWT and Swing to visually represent attendance statistics (attended, late, absent).
- **DatePicker.java**
A lightweight custom date selector using JComboBox—built from scratch without third-party libraries.
- **ProjectAttendanceDataBase.db**
SQLite database containing student, course, and attendance records. Used throughout the application via JDBC.
- **AssessmentB_Report.pdf**
Full implementation report including screenshots, explanation of functions, testing steps, and module context within the group project.

## 🔧 Technologies Used

- Java Swing (UI)
- SQLite (DB)
- JDBC (Java DB Connectivity)
- Java AWT for pie chart rendering

## 🚀 How to Run

Clone or download this repository to your computer.
Open the project in your Java IDE (like Eclipse or IntelliJ).
Make sure this file is in your project folder:
ProjectAttendanceDataBase.db
Add the SQLite JDBC driver to your project classpath if needed.
Run Login.java to start the program.
Choose access type:
Admin Login opens the full admin dashboard (AdminDisplay.java)
Student Login opens the student view (StudentDisplay.java)
📌 Login credentials for both Admin and Student users can be found inside the ProjectAttendanceDataBase.db file (see ADMINS and Unique_Students_With_Passwords tables).

## 📜 License
This code is for academic purposes only and part of coursework at Brunel University.

## 📌 Author

👤 Oguz Cayir – 1st Year BSc Computer Science Student

Feel free to fork, use or contact me for questions!
