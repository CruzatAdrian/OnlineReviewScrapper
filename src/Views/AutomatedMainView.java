package Views;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Utilities.Automated;

public class AutomatedMainView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel Panel = new JPanel();
	private JButton Run = new JButton("RUN!");
	private JLabel URLLabel = new JLabel("Enter URL filename (Include \".txt\")");
	private JTextField URLField = new JTextField("URLList.txt");
	private String URLFileName;
	
	public AutomatedMainView(){
		setTitle( "Review Getter" );
		setSize( 500, 150 );
		setLocation(500,280);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground( Color.gray );
		
		Panel.setLayout(new GridLayout(2,2));
		Panel.add(URLLabel);
		Panel.add(URLField);
		Panel.add(Run);
		
		Run.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				URLFileName = URLField.getText();
				Automated.getReviews(URLFileName);
			}
		});
		
		getContentPane().add(Panel);
		setVisible(true);
	}
	
}
