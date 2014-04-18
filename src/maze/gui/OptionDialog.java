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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import maze.builder.Default;
import maze.builder.Generator;
import maze.logic.Dragon.DragonMode;

public class OptionDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private GamePanel panel;
	private JSlider widthSlider = new JSlider();
	private JSlider heightSlider = new JSlider();
	private JSlider dragonSlider = new JSlider();
	private JRadioButton defaultMode = new JRadioButton();
	private JRadioButton randomMode = new JRadioButton();
	private JRadioButton fixedMode = new JRadioButton();
	private JRadioButton movingMode = new JRadioButton();
	private JRadioButton movingAndSleepingMode = new JRadioButton();
	private JTextField rightControlField = new JTextField("", 3);
	private JTextField downControlField = new JTextField("", 3);
	private JTextField upControlField = new JTextField("", 3);
	private JTextField leftControlField = new JTextField("", 3);
	private JTextField eagleControlField = new JTextField("", 3);
	private JButton save = new JButton("Save");
	private JButton cancel = new JButton("Cancel");
	
	
	private int oldValueWidthSlider;
	private int oldValueHeightSlider;
	private int oldValueDragonsSlider;
	private boolean oldValueMazeDefault;
	private boolean oldValueMazeRandom;
	private boolean oldValueDragonModeFixed;
	private boolean oldValueDragonModeMoving;
	private boolean oldValueDragonModeMovingSleeping;
	private String oldRightControl;
	private String oldLeftControl;
	private String oldUptControl;
	private String oldDownControl;
	private String oldEagleControl;

	//

	public OptionDialog(GamePanel gamePanel) {
		this.panel = gamePanel;
		
		setTitle("Options");
		getContentPane().setLayout(new GridLayout(11, 1));

		// Setting up dialog content
		SetUpTypeOfMazeSection();		
		SetUpMazeDimensionSection();
		SetUpDragonOptionsSection();
		SetUpControls();
		SetSaveOrCancel();
		
		// Dialog Details
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);

		Dimension dim2 = new Dimension(500, 600);
		setSize(dim2);
	}

	/**
	 * Sets up the maze type (Default or Generator).
	 */
	public void SetUpTypeOfMazeSection() {
		JLabel lblMazeType = new JLabel("MazeType");
		lblMazeType.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblMazeType);

		JPanel gameMode = new JPanel();
		getContentPane().add(gameMode);

		JLabel lblGameMode = new JLabel("Mode");
		gameMode.add(lblGameMode);

		// default button
		defaultMode.setText("Default");
		defaultMode.setSelected(true);

		// generator button
		randomMode.setText("Random");

		// add all buttons to panel
		ButtonGroup bg1 = new ButtonGroup();

		bg1.add(defaultMode);
		bg1.add(randomMode);

		gameMode.add(defaultMode);
		gameMode.add(randomMode);
		
		oldValueMazeDefault = defaultMode.isSelected();
		oldValueMazeRandom = randomMode.isSelected();
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
	 * Sets up controls
	 */
	public void SetUpControls() {
		JLabel lblControls = new JLabel("Controls");
		lblControls.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblControls);
		
		JPanel controlsMode = new JPanel();
		getContentPane().add(controlsMode);
		
		String controlW = new String("W");
		upControlField.setToolTipText("Up");
		upControlField.setText(controlW);
		upControlField.setHorizontalAlignment(JTextField.CENTER);
		controlsMode.add(upControlField);
		
		String controlA = new String("A");
		leftControlField.setToolTipText("Left");
		leftControlField.setText(controlA);
		leftControlField.setHorizontalAlignment(JTextField.CENTER);
		controlsMode.add(leftControlField);
		
		String controlS = new String("S");
		downControlField.setToolTipText("Down");
		downControlField.setText(controlS);
		downControlField.setHorizontalAlignment(JTextField.CENTER);
		controlsMode.add(downControlField);
		
		String controlD = new String("D");
		rightControlField.setToolTipText("Right");
		rightControlField.setText(controlD);
		rightControlField.setHorizontalAlignment(JTextField.CENTER);
		controlsMode.add(rightControlField);
		
		String controlEagle = new String("L");
		eagleControlField.setToolTipText("Launch Eagle");
		eagleControlField.setText(controlEagle);
		eagleControlField.setHorizontalAlignment(JTextField.CENTER);
		controlsMode.add(eagleControlField);
		
		oldDownControl = downControlField.getText();
		oldUptControl = upControlField.getText();
		oldLeftControl = leftControlField.getText();
		oldRightControl = rightControlField.getText();
		oldEagleControl = eagleControlField.getText();
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
				oldValueHeightSlider = heightSlider.getValue();
				oldValueWidthSlider = widthSlider.getValue();
				
				panel.setDragonNumber(dragonSlider.getValue());
				oldValueDragonsSlider = dragonSlider.getValue();
				
				if(defaultMode.isSelected()) {
					panel.setMaze(new Default());
					oldValueMazeDefault = true;
					oldValueMazeRandom = false;
				}
				else if(randomMode.isSelected()) {
					panel.setMaze(new Generator(widthSlider.getValue()));
					oldValueMazeRandom = true;
					oldValueMazeDefault = false;
				}
				
				if(fixedMode.isSelected()) {
					panel.setDragonMode(DragonMode.FIXED);
					oldValueDragonModeFixed = true;
					oldValueDragonModeMoving = false;
					oldValueDragonModeMovingSleeping = false;
				}
				else if(movingMode.isSelected()) {
					panel.setDragonMode(DragonMode.MOVING);
					oldValueDragonModeFixed = false;
					oldValueDragonModeMoving = true;
					oldValueDragonModeMovingSleeping = false;
				}
				else {
					panel.setDragonMode(DragonMode.MOVINGANDSLEEPING);
					oldValueDragonModeFixed = false;
					oldValueDragonModeMoving = false;
					oldValueDragonModeMovingSleeping = true;
				}
				
				panel.setGameControls(rightControlField.getText(), leftControlField.getText(),
						upControlField.getText(), downControlField.getText(), eagleControlField.getText());
				saveOldControls(upControlField.getText(), leftControlField.getText(), downControlField.getText(),
						rightControlField.getText(), eagleControlField.getText());
				putOptionDialogCharControlsToUpperCase();
				
				panel.isCreatingGame(false);
				setVisible(false);
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				widthSlider.setValue(oldValueWidthSlider);
				heightSlider.setValue(oldValueHeightSlider);
				dragonSlider.setValue(oldValueDragonsSlider);
				defaultMode.setSelected(oldValueMazeDefault);
				randomMode.setSelected(oldValueMazeRandom);
				fixedMode.setSelected(oldValueDragonModeFixed);
				movingMode.setSelected(oldValueDragonModeMoving);
				movingAndSleepingMode.setSelected(oldValueDragonModeMovingSleeping);
				rightControlField.setText(oldRightControl);
				downControlField.setText(oldDownControl);
				upControlField.setText(oldUptControl);
				leftControlField.setText(oldLeftControl);
				eagleControlField.setText(oldEagleControl);
				setVisible(false);
			}
		});
	}
	
	public boolean getRandomMode() {
		return randomMode.isSelected();
	}
	
	public int getMazeWidth() {
		return widthSlider.getValue();
	}
	
	public void putOptionDialogCharControlsToUpperCase() {
		this.upControlField.setText(upControlField.getText().toUpperCase());
		this.leftControlField.setText(leftControlField.getText().toUpperCase());
		this.downControlField.setText(downControlField.getText().toUpperCase());
		this.rightControlField.setText(rightControlField.getText().toUpperCase());
		this.eagleControlField.setText(eagleControlField.getText().toUpperCase());
	}
	
	public void saveOldControls(String up, String left, String down, String right, String eagle) {
		this.oldUptControl = up.toUpperCase();
		this.oldLeftControl = left.toUpperCase();
		this.oldDownControl = down.toUpperCase();
		this.oldRightControl = right.toUpperCase();
		this.oldEagleControl = eagle.toUpperCase();
	}
	
	public void setSlidersEnableOrDisable(boolean s) {
		this.widthSlider.setEnabled(s);
		this.heightSlider.setEnabled(s);
	}
	
	public boolean getRandomModeStatus() {
		return randomMode.isSelected();
	}
	
	public boolean getDefaultModeStatus() {
		return defaultMode.isSelected();
	}
	
	public JRadioButton getRandom() {
		return randomMode;
	}
	
	public JRadioButton getDefault() {
		return defaultMode;
	}
};
