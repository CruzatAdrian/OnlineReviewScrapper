package Views;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import Utilities.ContentMatch;



public class ContentMatchMainView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 324159248354189539L;
	
	private JPanel Panel = new JPanel();
	private JButton Run = new JButton("RUN!");
	private JLabel KeywordLabel = new JLabel("Enter keyword filename (Include \".txt\")");
	private JLabel ReviewFileLabel = new JLabel("Enter review filename (Include \".txt\")");
	private JLabel OutputNameLabel = new JLabel("Enter out put filename (Exclude \".txt\")");
	private JTextField KeywordField = new JTextField("Keywords.txt");
	private JTextField ReviewFileField = new JTextField("Reviews.txt");
	private JTextField OutputNameField = new JTextField("Output");
	private String KeywordFileName;
	private String ReviewFileName;
	private String OutputFileName;

	public ContentMatchMainView(){
		setTitle( "Content Match" );
		setSize( 600, 200 );
		setLocation(500,280);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground( Color.gray );


		Panel.setLayout(new GridLayout(4,2));
		Panel.add(KeywordLabel);
		Panel.add(KeywordField);
		Panel.add(ReviewFileLabel);
		Panel.add(ReviewFileField);
		Panel.add(OutputNameLabel);
		Panel.add(OutputNameField);
		Panel.add(Run);

		Run.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				KeywordFileName = KeywordField.getText();
				ReviewFileName = ReviewFileField.getText();
				OutputFileName = OutputNameField.getText();
				ContentMatch.contentMatch(KeywordFileName, ReviewFileName, OutputFileName);
			}
		});


		getContentPane().add(Panel);
		setVisible(true);

	}

}
