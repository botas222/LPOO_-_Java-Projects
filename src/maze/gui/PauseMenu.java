package maze.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PauseMenu extends JPanel {
	private static final long serialVersionUID = 1L;

	private Window window;
	
	private String response = "";
	
	private final JButton btnResumeGame = new JButton("Resume Game");
	private final JButton btnSaveGame = new JButton("Save Game");
	private final JButton btnReturnToMainMenu = new JButton("Return to Main Menu");
	private final JButton btnQuitGame = new JButton("Quit Game");
	
	public PauseMenu(Window window) {
		this.window = window;
		
		setLayout(new GridLayout(4, 1, 0, 0));
		
		// adding buttons to buttons panel
		add(btnResumeGame);
		btnResumeGame.setFocusable(false);
		add(btnSaveGame);
		btnSaveGame.setFocusable(false);
		add(btnReturnToMainMenu);
		btnReturnToMainMenu.setFocusable(false);
		add(btnQuitGame);
		btnQuitGame.setFocusable(false);
		window.getContentPane().add(this, BorderLayout.CENTER);
		
		isSelected();
	}
	
	public void isSelected() {
		btnResumeGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				window.getGamePanel().setVisible(true);
				window.getGamePanel().requestFocus();
			}
		});
		
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String message = "Insert the name for your game", title = "Save Game",
						errorMessage = "Game Not Saved! Please Insert a Name...";
				
			    if((response = JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE)) != null) {
			    	if(response.equals("")) {
			    		JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.WARNING_MESSAGE);
			    	}
			    	else {
			    		try {
							window.getGamePanel().getGame().save(response);
						} catch (IOException e) {
							e.printStackTrace();
						}
			    	}
			    }
			}
		});
		
		btnReturnToMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				window.getGamePanel().setVisible(false);
				window.getLblBackGround().setVisible(true);
			}
		});
		
		btnQuitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.setVisible(false);
			}
		});
	}
};
