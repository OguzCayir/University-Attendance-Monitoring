import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class DatePicker extends JPanel {
    private JComboBox<Integer> dayBox;
    private JComboBox<String> monthBox;
    private JComboBox<Integer> yearBox;

    public DatePicker() {
        setLayout(new FlowLayout());

        // Day ComboBox (1 to 31)
        dayBox = new JComboBox<>();
        dayBox.addItem(null); // Add null as the first item (no selection)
        for (int i = 1; i <= 31; i++) {
            dayBox.addItem(i);
        }

        // Month ComboBox
        // **Add null as the first item (no selection)
        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        monthBox = new JComboBox<>(months);
        

        // Year ComboBox (from currentYear - 10 to currentYear + 1)
        yearBox = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearBox.addItem(null); // Add null as the first item (no selection)
        for (int i = currentYear - 10; i <= currentYear + 1; i++) {
            yearBox.addItem(i);
        }
        yearBox.setSelectedItem(currentYear); // Set default year to current year

        // Add to panel
        add(dayBox);
        add(monthBox);
        add(yearBox);
    }

    
    public String getSelectedDate() {
    	Integer day = (Integer) dayBox.getSelectedItem();
    	int monthIndex = monthBox.getSelectedIndex();
    	Integer year = (Integer) yearBox.getSelectedItem();
        
     // Check if any field is null (no selection)
        if (day == null || monthIndex == -1 || year == null) { //i had to cast "Integer" above to day==null... making sense.
            return null; // Return null if no date is selected
        }
        int month = monthBox.getSelectedIndex() + 1; // Convert 0-based index to 1-based
        
        return String.format("%02d/%02d/%04d", day, month, year); // Ensures format DD/MM/YYYY
    }
    
    // Method to clear the selected date
    public void clearSelectedDate() {
        dayBox.setSelectedItem(null);
        monthBox.setSelectedItem(null);
        yearBox.setSelectedItem(null);
    }
}
