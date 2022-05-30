package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import app.DB.DBRepo;

public class MainFrame extends JFrame{

	DBRepo Repository;
	JPanel menuPanel;
	JPanel midPanel;
	ArrayList<Page> pages = new ArrayList<Page>(); //
	
	public MainFrame() {
		Repository = new DBRepo();
		this.setSize(500, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		midPanel = new JPanel();
		menuPanel = new JPanel();	
		pages.add(new StartPage(Repository));
		pages.add(new ItemPage(Repository));
		pages.add(new PartyPage(Repository));
		pages.add(new PlayerPage(Repository));	
		pages.add(new SearchPage(Repository));
		menuPanel.setLayout(new GridLayout(1,0));
		for (Page page : pages) {
			JButton btn = new JButton(page.name);			
			btn.addActionListener(new PageAction());
			menuPanel.add(btn);			
		}
		
		
		
		
		this.add(menuPanel,BorderLayout.PAGE_START);
		this.add(midPanel,BorderLayout.CENTER);		
		LoadPage(pages.get(0));
		}
	public void LoadPage(Page page) { //---Changes the currently active panels with the panels from the given page.
		JPanel[] array = page.Load();
		this.remove(midPanel);
		midPanel = new JPanel();  //The name here is bad. This panel represents the whole screen excluding the buttons on the top.
		midPanel.setLayout(new GridLayout(3,1));
		for (JPanel jPanel : array) {
			midPanel.add(jPanel);
		}		
		
		this.add(midPanel,BorderLayout.CENTER);		
		this.setVisible(true);
	}
	class PageAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton bt= (JButton) e.getSource(); //---95% sure this is a stupid way but it works so.. 
			for (Page page : pages) {  //---Finds the page behind the button
				if(page.name == bt.getText())
					{
						LoadPage(page);
					};
			}
		}
		
	}
}
