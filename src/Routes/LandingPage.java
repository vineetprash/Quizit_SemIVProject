// To be implemented later

package Routes;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LandingPage {
    public static JPanel panel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Welcome to QUIZIT LOLLLL!"));

        return panel;
    }
}
