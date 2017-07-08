package CSE360;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileNotFoundException;

import javax.swing.*;

public class Project2 extends JFrame{
	
	//ExamBrain eb = ExamBrain.getInstance();
	private static JLayeredPane layeredPane;
	private static int FRAME_WIDTH = 500;
    private static int FRAME_HEIGHT = 500;
    SwitchPage switchPage;
    JScrollPane scroll;
    private Dimension newSize;
   // BlackBoard bb = new BlackBoard();
   
    public Project2 () throws FileNotFoundException {
    	//eb.addObserver(bb);
    	
     layeredPane = new JLayeredPane();
     layeredPane.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));  
     GridLayout grid = new GridLayout(1,1);   
     setLayout(grid);
 
     switchPage = new SwitchPage();
     switchPage.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
     
     CompanionPanel companion = new CompanionPanel();
     companion.setBounds(0,0,FRAME_WIDTH, FRAME_HEIGHT);
     //switch panel on bottom, companion on top
     layeredPane.setOpaque(false);               
     layeredPane.add(companion, new Integer(1));
     scroll = new JScrollPane(switchPage);
     layeredPane.add(scroll, new Integer(0));
     scroll.setBounds(0,0,FRAME_WIDTH, FRAME_HEIGHT);
		
     this.add(layeredPane);

     Thread compThread = new Thread(companion);
     compThread.start();

     this.setTitle("CSE360 Quiz");
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
     this.setVisible(true);
     //this.setContentPane(layeredPane);
     
     //keep track of the frame dimension
     this.addComponentListener(new ComponentAdapter(){
         @Override
         public void componentResized(ComponentEvent e) {
             FRAME_WIDTH = getWidth(); 
             FRAME_HEIGHT = getHeight();
             //System.out.println(FRAME_WIDTH);
             //System.out.println(FRAME_WIDTH);
             resize();
         }
     });
    }
    
    public void resize(){
    	layeredPane.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT)); 
    	switchPage.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
    	scroll.setBounds(0,0,FRAME_WIDTH, FRAME_HEIGHT);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Project2 quiz = new Project2();
    }
}
