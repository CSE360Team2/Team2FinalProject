/*
 * Login page
 *
 * Lin Sun
 * Kyle Sun
 * Jingyi Li
 */
package CSE360;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Login extends JPanel{

	public String user;
	private JTextField name_text;
	public JLabel text;
	private JButton exit_button;
	public Login(){
		this.setBackground(Color.PINK.brighter());
		name_text = new JTextField();
		text = new JLabel ("Username");
		exit_button = new JButton("EXIT QUIZ");
		this.setLayout(new GridLayout(4,1));
		this.add(text);
		this.add(name_text);
		
		Font bigFont = name_text.getFont().deriveFont(Font.PLAIN,50f);
		Font smallFont = name_text.getFont().deriveFont(Font.PLAIN,20f);
		name_text.setFont(bigFont);
		text.setFont(smallFont);
		this.add(exit_button);

		name_text.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				user = name_text.getText();
				System.out.println(user);
			}
		});
		
		exit_button.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});	
	}
}
