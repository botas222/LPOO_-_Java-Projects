package maze.gui;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;

import javax.swing.JButton;

import maze.builder.Generator;
import maze.logic.Game;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Vector;

import javax.swing.JLabel;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public JFrame seeAllGamesSaved;
	public JPanel panel;
	private final JButton btnOptions = new JButton("Options");
	private final JButton btnNewGame = new JButton("New Game");
	private final JButton btnLoadGame = new JButton("Load Game");
	private final JButton btnCreateGame = new JButton("Create Game");
	private final GamePanel gamePanel = new GamePanel(this);
	private final OptionDialog optionDialog = new OptionDialog(gamePanel);
	private final MiniOptionsCreateGame miniOptions = new MiniOptionsCreateGame(gamePanel);
	private Vector<String> fileNames;
	private JComboBox<String> chooseGame;
	
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private final JLabel lblBackground = new JLabel(new ImageIcon("res/background.png"));

	public Window() {
		
		// setting title
		setTitle("Dragon Dungeon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setting size and location
		setSize(500, 500);
		setLocation((int) (dim.getWidth() / 2 - getWidth() / 2),
				(int) (dim.getHeight() / 2 - getHeight() / 2));

		// setting buttons panel
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 4, 0, 0));

		// adding buttons to buttons panel
		panel.add(btnNewGame);
		btnNewGame.setFocusable(false);
		panel.add(btnOptions);
		btnOptions.setFocusable(false);
		panel.add(btnLoadGame);
		btnLoadGame.setFocusable(false);
		panel.add(btnCreateGame);
		btnCreateGame.setFocusable(false);
		
		getContentPane().add(lblBackground, BorderLayout.CENTER);

		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(!gamePanel.getIsCreatingGame()) {
				
				String message = "Are you sure that you want to start a new game?", title = "Confirm Please";
			    int response = JOptionPane.showConfirmDialog(null, message, title,
			        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			    
			    if (response == JOptionPane.YES_OPTION) {
			    	if(optionDialog.getRandomMode()) {
						gamePanel.setMaze(new Generator(optionDialog.getMazeWidth()));
						gamePanel.isCreatingGame(false);
			    	}
			    	
			    	lblBackground.setVisible(false);
					getContentPane().add(gamePanel, BorderLayout.CENTER);
					start();
			    }
			}
				
				else {
					JOptionPane.showMessageDialog(null, "Please edit Options first.");
				}
		}
	});

		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				while (!optionDialog.isVisible()) {
					if (optionDialog.getDefaultModeStatus())
						optionDialog.setSlidersEnableOrDisable(false);

					else if (optionDialog.getRandomModeStatus())
						optionDialog.setSlidersEnableOrDisable(true);

					optionDialog.setVisible(true);

					optionDialog.getRandom().addActionListener(
							new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									optionDialog
											.setSlidersEnableOrDisable(true);
								}
							});

					optionDialog.getDefault().addActionListener(
							new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									optionDialog
											.setSlidersEnableOrDisable(false);
								}
							});
				}
			}
		});

		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				fileNames = new Vector<String>();
				updateFilesList();
				
				seeAllGamesSaved = new JFrame("Saved Games");
				seeAllGamesSaved.setSize(300, 150);
				seeAllGamesSaved.getContentPane().setLayout(new GridLayout(3, 1, 0, 0));
				
				seeAllGamesSaved.setLocation((int) (dim.getWidth() / 2 - seeAllGamesSaved.getWidth() / 2),
						(int) (dim.getHeight() / 2 - seeAllGamesSaved.getHeight() / 2));
				
				JPanel jPanelLoadOptions = new JPanel();
				JPanel jPanelLoadConfirm = new JPanel();
				jPanelLoadOptions.setLayout(new GridLayout(2, 2, 0, 0));
				jPanelLoadConfirm.setLayout(new GridLayout(1, 2, 0, 0));
				
				jPanelLoadConfirm.setSize(150, 30);
				
				JTextArea message = new JTextArea("Message");
				message.setText("Choose here the game you want:");
				message.setFont(new Font("Arial", Font.BOLD, 12));
				message.setEditable(false);
				jPanelLoadOptions.add(message);
				
				chooseGame = new JComboBox<String>();
				
				for(int i = 0; i < fileNames.size(); i++) {
					chooseGame.addItem(fileNames.get(i));
				}
				jPanelLoadOptions.add(chooseGame);
				
				JButton btnOk = new JButton("Ok");
				JButton btnCancel = new JButton("Cancel");
				jPanelLoadConfirm.add(btnOk);
				jPanelLoadConfirm.add(btnCancel);
				
				seeAllGamesSaved.getContentPane().add(jPanelLoadOptions);
				seeAllGamesSaved.getContentPane().add(new JPanel());
				seeAllGamesSaved.getContentPane().add(jPanelLoadConfirm);
				seeAllGamesSaved.setVisible(true);
				
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						seeAllGamesSaved.setVisible(false);
						if(fileNames.isEmpty()) {
							return;	
						}
						
						try {
							getContentPane().add(gamePanel, BorderLayout.CENTER);
							start();
							load(fileNames.get(chooseGame.getSelectedIndex()));
							gamePanel.setVisible(true);
							gamePanel.requestFocus();
						}
						catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
				
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					}
				});
			}
		});
		
		btnCreateGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSize(600, 600);
				setLocation(dim.width / 2 - 300, dim.height / 2 - 300);
				lblBackground.setVisible(false);
				miniOptions.setVisible(true);
			}
		});
	}

	public void start() {
		gamePanel.Initialize();
		setVisible(true);
	}
	
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	
	public JLabel getLblBackGround() {
		return lblBackground;
	}
	
	public void load(String saveName) throws IOException, ClassNotFoundException {
		FileInputStream fin = new FileInputStream(System.getProperty("user.dir") + "/Saved Games/" + saveName);
		ObjectInputStream is = new ObjectInputStream(fin);

		Object obj = is.readObject();

		if (obj instanceof Game)
			gamePanel.setGame((Game) obj);

		is.close();
	}
	
	public Vector<String> updateFilesList() {
		File folder = new File(System.getProperty("user.dir") + "/Saved Games/");
		if (!folder.isDirectory())
			return fileNames;

		for (File file : folder.listFiles())
			fileNames.add(file.getName());
		return fileNames;
	}
}
