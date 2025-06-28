import java.awt.*;
import javax.swing.*;

class Slice {
    double value;
    Color color;
    String label;

    public Slice(double value, Color color, String label) {
        this.value = value;
        this.color = color;
        this.label = label;
    }
}

public class PieChart extends JPanel {
    private Slice[] slices;

    public PieChart(Slice[] slices) {
        this.slices = slices;
        setPreferredSize(new Dimension(400, 400)); // Set default size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPie((Graphics2D) g, getBounds(), slices);
    }

    private void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
        double total = 0;
        //i will add if value>0 because otherwise all text overlap.
        for (Slice slice : slices) {
        	if (slice.value>0) {
            total += slice.value;
        	}
        }

        double curValue = 0.0;
        int startAngle;
      //i will add if value>0 because otherwise all text(Attended/LAte/Yes) overlap.
        for (Slice slice : slices) {
        	if (slice.value>0) {
        	
            startAngle = (int) (curValue * 360 / total);
            int arcAngle = (int) (slice.value * 360 / total);
            g.setColor(slice.color);
            g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);

            // Draw text labels
           
            
            double angle = Math.toRadians(startAngle + arcAngle / 2.0);
            int labelX = area.x + area.width / 2 + (int) (area.width / 3 * Math.cos(angle));
            int labelY = area.y + area.height / 2 - (int) (area.height / 3 * Math.sin(angle));

            g.setColor(Color.BLACK);
            g.drawString(slice.label + " (" + String.format("%.1f", (slice.value / total) * 100) + "%)", labelX, labelY);
            g.setFont( new Font("Lucida Grande", Font.PLAIN, 10));

            curValue += slice.value;
        	}
            }
        
    }
}
