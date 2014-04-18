package maze.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import maze.logic.Dragon.DragonMode;

public class MiniOptionsCreateGame extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private GamePanel gamePanel;
	
	private JSlider widthSlider = new JSlider();
	private JSlider heightSlider = new JSlider();
	private JSlider dragonSlider = new JSlider();
	private JRadioButton fixedMode = new JRadioButton();
	private JRadioButton movingMode = new JRadioButton();
	private JRadioButton movingAndSleepingMode = new JRadioButton();
	private JButton save = new JButton("Save");
	private JButton cancel = new JButton("Cancel");
	
	
	private int oldValueWidthSlider;
	private int oldValueHeightSlider;
	private int oldValueDragonsSlider;
	private boolean oldValueDragonModeFixed;
	private boolean oldValueDragonModeMoving;
	private boolean oldValueDragonModeMovingSleeping;

	//

	public MiniOptionsCreateGame(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		setTitle("Options Create Maze");
		getContentPane().setLayout(new GridLayout(7, 1));

		// Setting up dialog content	
		SetUpMazeDimensionSection();
		SetUpDragonOptionsSection();
		SetSaveOrCancel();
		
		// Dialog Details
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);

		Dimension dim2 = new Dimension(500, 400);
		setSize(dim2);
	}

	/**
	 * Sets up the maze dimension section.
	 */
	public void SetUpMazeDimensionSection() {
		JLabel lblMazeDimension = new JLabel("Maze Dimension");
		lblMazeDimension.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblMazeDimension);
		
		JPanel mazeW = new JPanel();
		getContentPane().add(mazeW);
		JLabel lblWidth = new JLabel("Width");
		mazeW.add(lblWidth);
		widthSlider.setMinorTickSpacing(2);
		widthSlider.setPaintLabels(true);
		widthSlider.setPaintTicks(true);
		widthSlider.setSnapToTicks(true);
		widthSlider.setMajorTickSpacing(10);
		widthSlider.setMaximum(55);
		widthSlider.setMinimum(5);
		widthSlider.setValue(11);
		mazeW.add(widthSlider);

		JPanel mazeH = new JPanel();
		getContentPane().add(mazeH);
		mazeH.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel lblHeight = new JLabel("Height");
		mazeH.add(lblHeight);
		heightSlider.setSnapToTicks(true);
		heightSlider.setPaintTicks(true);
		heightSlider.setPaintLabels(true);
		heightSlider.setMinorTickSpacing(2);
		heightSlider.setMinimum(5);
		heightSlider.setMaximum(55);
		heightSlider.setMajorTickSpacing(10);
		heightSlider.setValue(11);
		mazeH.add(heightSlider);
		
		oldValueHeightSlider = heightSlider.getValue();
		oldValueWidthSlider = widthSlider.getValue();
	}

	/**
	 * Sets up the maze dragon section.
	 */
	public void SetUpDragonOptionsSection() {
		JLabel lblDragonOptions = new JLabel("DragonOptions");
		lblDragonOptions.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblDragonOptions);

		JPanel numberDragons = new JPanel();
		getContentPane().add(numberDragons);

		JLabel lblNumber = new JLabel("Number");
		numberDragons.add(lblNumber);

		dragonSlider.setMaximum(46);
		dragonSlider.setSnapToTicks(true);
		dragonSlider.setPaintLabels(true);
		dragonSlider.setPaintTicks(true);
		dragonSlider.setMinorTickSpacing(2);
		dragonSlider.setMajorTickSpacing(5);
		dragonSlider.setMinimum(1);
		dragonSlider.setValue(3);
		numberDragons.add(dragonSlider);

		JPanel dragonMode = new JPanel();
		getContentPane().add(dragonMode);

		JLabel lblDragonMode = new JLabel("DragonMode");
		dragonMode.add(lblDragonMode);

		// fixed button
		fixedMode.setText("Fixed");
		fixedMode.setSelected(true);

		// moving button
		movingMode.setText("Moving");

		// moving and sleeping button
		movingAndSleepingMode.setText("Moving and Sleeping");

		// add all buttons to panel
		ButtonGroup bg1 = new ButtonGroup();

		bg1.add(fixedMode);
		bg1.add(movingMode);
		bg1.add(movingAndSleepingMode);

		dragonMode.add(fixedMode);
		dragonMode.add(movingMode);
		dragonMode.add(movingAndSleepingMode);
		
		oldValueDragonModeFixed = fixedMode.isSelected();
		oldValueDragonModeMoving = movingMode.isSelected();
		oldValueDragonModeMovingSleeping = movingAndSleepingMode.isSelected();
	}
	
	/**
	 * Save or Cancel
	 */
	public void SetSaveOrCancel() {
		JPanel saveCancel = new JPanel();
		
		saveCancel.add(save);
		save.setFocusable(false);
		saveCancel.add(cancel);
		cancel.setFocusable(false);
		
		getContentPane().add(saveCancel);
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				gamePanel.setSizeOfMaze(widthSlider.getValue());
				oldValueWidthSlider = widthSlider.getValue();
				
				gamePanel.setDragonNumber(dragonSlider.getValue());
				oldValueDragonsSlider = dragonSlider.getValue();
				
				
				if(fixedMode.isSelected()) {
					gamePanel.setDragonMode(DragonMode.FIXED);
					oldValueDragonModeFixed = true;
					oldValueDragonModeMoving = false;
					oldValueDragonModeMovingSleeping = false;
				}
				else if(movingMode.isSelected()) {
					gamePanel.setDragonMode(DragonMode.MOVING);
					oldValueDragonModeFixed = false;
					oldValueDragonModeMoving = true;
					oldValueDragonModeMovingSleeping = false;
				}
				else {
					gamePanel.setDragonMode(DragonMode.MOVINGANDSLEEPING);
					oldValueDragonModeFixed = false;
					oldValueDragonModeMoving = false;
					oldValueDragonModeMovingSleeping = true;
				}
				
				setVisible(false);
				gamePanel.isCreatingGame(true);
				gamePanel.startCreateMaze();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				widthSlider.setValue(oldValueWidthSlider);
				heightSlider.setValue(oldValueHeightSlider);
				dragonSlider.setValue(oldValueDragonsSlider);
				fixedMode.setSelected(oldValueDragonModeFixed);
				movingMode.setSelected(oldValueDragonModeMoving);
				movingAndSleepingMode.setSelected(oldValueDragonModeMovingSleeping);
				setVisible(false);
				if(!gamePanel.getIsCreatingGame())
					gamePanel.isCreatingGame(false);
			}
		});
	}
	
	public int getMazeWidth() {
		return widthSlider.getValue();
	}
}
