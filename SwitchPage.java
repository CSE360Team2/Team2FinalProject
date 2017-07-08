/*
 * When the start quiz button is clicked
 * swtich from login page to exam page
 * Lin Sun
 * Kyle Sun
 * Jingyi Li
 */

package CSE360;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileNotFoundException;


public class SwitchPage extends JPanel implements ActionListener{

	private Login loginPage;
	private ExamPanel examPage;
	
	public SwitchPage() throws FileNotFoundException{
		loginPage = new Login();
		examPage = new ExamPanel();
		JButton next_button = new JButton("START QUIZ");
		JButton back_button = new JButton("GO BACK");
		
		next_button.addActionListener(this);
		back_button.addActionListener(this);
		
		loginPage.add(next_button);
		examPage.add(back_button);
		add(loginPage);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 for (Component component : getComponents())
	            if (loginPage == component && loginPage.user!= null &&loginPage.user!= " ") {
	                remove(loginPage);
	                add(examPage);

	            } 
	            else if (loginPage == component && (loginPage.user == null || loginPage.user == " ")){
	            	loginPage.text.setText("Click ENTER after you type in user name");
	            }
	            else {
	                remove(examPage);
	                add(loginPage);
	            }

	        repaint();
	        revalidate();
	}
}
