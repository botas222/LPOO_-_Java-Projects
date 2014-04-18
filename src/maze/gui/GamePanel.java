package maze.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import maze.builder.Builder;
import maze.builder.Default;
import maze.logic.Game;
import maze.logic.Dragon.DragonMode;
import maze.logic.Point;

public class GamePanel extends JPanel implements ActionListener {
	private Window window;
	private PauseMenu pauseMenu;
	private GamePanel gamePanel;

	// Image resources
	private BufferedImage wallImg, pathImg, exitImg, dragonImg, heroImg,
			heroArmImg, swordImg, eagleImg;

	// Game Options
	private int dragonNumber = 1;
	private Builder maze = new Default();
	private DragonMode mode = DragonMode.FIXED;
	private String upControl = "w", downControl = "s", leftControl = "a",
			rightControl = "d", eagleControl = "l";

	// Game Creating Options
	private int sizeOfMaze = 7;
	private boolean isCreatingGame = false;
	private Point point = new Point();
	private Timer timer;

	// Attributes from game
	private Game game;
	private boolean showGame = false;
	private int tileWidth;
	private int tileHeight;

	public GamePanel(Window window) {
		this.window = window;
		gamePanel = this;
		addKeyListener(new MyKeyboardAdapter());
		MyMouseAdapter mouseAdapter = new MyMouseAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);

		timer = new Timer(100, (ActionListener) this);
	}

	private static final long serialVersionUID = 1L;

	public void Initialize() {
		setVisible(true);
		// loading images
		try {
			wallImg = ImageIO.read(new File("res/wall.png"));
			pathImg = ImageIO.read(new File("res/path.png"));
			exitImg = ImageIO.read(new File("res/exit.png"));
			dragonImg = ImageIO.read(new File("res/dragon.png"));
			heroImg = ImageIO.read(new File("res/hero.png"));
			swordImg = ImageIO.read(new File("res/sword.png"));
			heroArmImg = ImageIO.read(new File("res/heroArmed.png"));
			eagleImg = ImageIO.read(new File("res/eagle.png"));

		} catch (IOException ex) {
			System.out.println("Error while loading images.");
		}

		// Initializing game
		if (maze.getClass().getSimpleName().equals("Default")
				&& dragonNumber == 1) {
			game = new Game(maze, mode, 'd');
		} else if (maze.getClass().getSimpleName().equals("Default")
				&& dragonNumber != 1) {
			game = new Game(maze, mode, dragonNumber);
		} else
			game = new Game(maze, mode, dragonNumber);

		showGame = true;
		requestFocus();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		// clearing screen
		super.paintComponent(g);

		// drawing game
		if (showGame)
			Draw(g);
	}

	private void Draw(Graphics g) {
		DrawLabyrinth(g);
		if (!isCreatingGame)
			DrawElements(g, game);
	}

	private void DrawLabyrinth(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		tileWidth = (int) (1.0 * getWidth() / game.getBoard().length);
		tileHeight = (int) (1.0 * getHeight() / game.getBoard().length);

		for (int i = 0; i < game.getBoard().length; i++) {
			for (int j = 0; j < game.getBoard().length; j++) {
				BufferedImage tile = wallImg;

				switch (game.getBoard()[i][j]) {
				case 'S':
					tile = exitImg;
					break;
				case ' ':
					tile = pathImg;
					break;
				default:
					break;
				}

				int dx1 = j * tileWidth, dx2 = dx1 + tileWidth, dy1 = i
						* tileHeight, dy2 = dy1 + tileHeight;

				g2d.drawImage(tile, dx1, dy1, dx2, dy2, 0, 0, tile.getWidth(),
						tile.getHeight(), null);
			}
		}

		if (getIsCreatingGame()) {
			g2d.setColor(Color.cyan);

			// vertical lines
			int dx1 = point.getX() * tileWidth;
			dx1 += (getWidth() - tileWidth
					* game.getCreateMaze().getBoardSize()) / 2.0;
			int dx2 = dx1 + tileWidth;

			g2d.drawLine(dx1, 0, dx1, getHeight());
			g2d.drawLine(dx2, 0, dx2, getHeight());

			// horizontal lines
			int dy1 = (int) (point.getY() * (tileHeight - 0.37 * tileHeight));
			dy1 += (getHeight() - (tileHeight - 0.37 * tileHeight)
					* game.getCreateMaze().getBoardSize()) / 2.0;
			int dy2 = (int) (dy1 + tileHeight - 0.15 * tileHeight);

			g2d.drawLine(0, dy1, getWidth(), dy1);
			g2d.drawLine(0, dy2, getWidth(), dy2);
		}
	}

	private void DrawElements(Graphics g, Game game) {
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage tile = dragonImg;
		int tileWidth = (int) (1.0 * getWidth() / game.getBoard().length);
		int tileHeight = (int) (1.0 * getHeight() / game.getBoard().length);
		int dx1, dx2, dy1, dy2;

		for (int i = 0; i < game.getDragons().length; i++) {

			if (game.getDragons()[i].isAlive()) {
				dx1 = game.getDragons()[i].getX() * tileWidth;
				dx2 = dx1 + tileWidth;
				dy1 = game.getDragons()[i].getY() * tileHeight;
				dy2 = dy1 + tileHeight;

				g2d.drawImage(tile, dx1, dy1, dx2, dy2, 0, 0, tile.getWidth(),
						tile.getHeight(), null);
			}
		}

		// drawing sword
		tile = swordImg;

		if (!game.getSword().isCaught()) {
			dx1 = game.getSword().getX() * tileWidth;
			dx2 = dx1 + tileWidth;
			dy1 = game.getSword().getY() * tileHeight;
			dy2 = dy1 + tileHeight;

			g2d.drawImage(tile, dx1, dy1, dx2, dy2, 0, 0, tile.getWidth(),
					tile.getHeight(), null);
		}

		// drawing eagle
		tile = eagleImg;
		if (game.getEagle().hasBeenLaunched() && game.getEagle().isAlive()) {
			dx1 = game.getEagle().getX() * tileWidth;
			dx2 = dx1 + tileWidth;
			dy1 = game.getEagle().getY() * tileHeight;
			dy2 = dy1 + tileHeight;

			g2d.drawImage(tile, dx1, dy1, dx2, dy2, 0, 0, tile.getWidth(),
					tile.getHeight(), null);
		}

		// drawing hero
		if (!game.getHero().isArmed())
			tile = heroImg;
		else
			tile = heroArmImg;

		dx1 = game.getHero().getX() * tileWidth;
		dx2 = dx1 + tileWidth;
		dy1 = game.getHero().getY() * tileHeight;
		dy2 = dy1 + tileHeight;

		g2d.drawImage(tile, dx1, dy1, dx2, dy2, 0, 0, tile.getWidth(),
				tile.getHeight(), null);

	}

	private class MyKeyboardAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (!getIsCreatingGame()) {
				String movement = "";
				if (e.getKeyCode() == KeyEvent.VK_RIGHT
						|| e.getKeyCode() == Utilities
								.StringToKey(rightControl))
					movement = "d";

				else if (e.getKeyCode() == KeyEvent.VK_LEFT
						|| e.getKeyCode() == Utilities.StringToKey(leftControl))
					movement = "a";

				else if (e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == Utilities.StringToKey(upControl))
					movement = "w";

				else if (e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == Utilities.StringToKey(downControl))
					movement = "s";

				else if (e.getKeyCode() == Utilities.StringToKey(eagleControl))
					movement = "l";

				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					movement = "";
					pauseMenu = new PauseMenu(window);
					window.getLblBackGround().setVisible(false);
					gamePanel.setVisible(false);
					pauseMenu.setVisible(true);
				}

				game.heroMoves(movement);
				game.update();
				repaint();
				if (game.isOver() && game.getHero().hasKilledADragon()) {
					JOptionPane.showMessageDialog(getRootPane(),
							"You Win!         :)");
					setVisible(false);
				} else if (!game.getHero().isAlive()) {
					JOptionPane.showMessageDialog(getRootPane(),
							"You Die!\nTry Again :)");
					setVisible(false);
				}
			}
			
			else {
				// saving maze and other user options
				if (e.getKeyCode() == KeyEvent.VK_F1) {
					Game mazeCreateGame = new Game(maze, mode, dragonNumber);
					gamePanel.setVisible(false);
					setGame(mazeCreateGame);
					try {
						game.save("puta");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	};

	private class MyMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (!getIsCreatingGame())
				return;

			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				if (0 < point.getX()
						&& point.getX() < game.getCreateMaze().getBoardSize() - 1
						&& 0 < point.getY()
						&& point.getY() < game.getCreateMaze().getBoardSize() - 1)
					game.getCreateMaze().getBoard()[point.getY()][point.getX()] = ' ';
			case MouseEvent.BUTTON2:
				game.getCreateMaze().getBoard()[point.getY()][point.getX()] = 'S';
				break;
			case MouseEvent.BUTTON3:
				game.getCreateMaze().getBoard()[point.getY()][point.getX()] = 'X';
				break;
			}
		}

		public void mouseMoved(MouseEvent e) {
			if (!getIsCreatingGame())
				return;

			int tileWidth = gamePanel.getWidth()
					/ game.getBoard().length;

			int x = (int) ((e.getX() - (getWidth() - tileWidth
					* game.getBoard().length) / 2.0) / tileWidth);
			int y = (int) ((e.getY() - (getHeight() - (tileWidth - 0.27 * tileWidth)
					* game.getBoard().length) / 2.0) / (tileWidth - 0.27 * tileWidth));

			point.setX(x);
			point.setY(y);

			repaint();
		}

		public void mouseDragged(MouseEvent e) {
			if (!getIsCreatingGame())
				return;

			int x = (int) ((e.getX() - (getWidth() - tileWidth
					* game.getBoard().length) / 2.0) / tileWidth);
			int y = (int) ((e.getY() - (getHeight() - (tileWidth - 0.27 * tileWidth)
					* game.getBoard().length) / 2.0) / (tileWidth - 0.27 * tileWidth));

			point.setX(x);
			point.setY(y);

			if (0 < point.getX()
					&& point.getX() < game.getBoard().length - 1
					&& 0 < point.getY()
					&& point.getY() < game.getBoard().length - 1) {
				if (SwingUtilities.isLeftMouseButton(e))
					game.getCreateMaze().getBoard()[point.getY()][point.getX()] = ' ';
			} else if (SwingUtilities.isMiddleMouseButton(e))
				game.getCreateMaze().getBoard()[point.getY()][point.getX()] = 'S';

			if (SwingUtilities.isRightMouseButton(e))
				game.getCreateMaze().getBoard()[point.getY()][point.getX()] = 'X';

			repaint();
		}
	}

	public Game getGame() {
		return game;
	}

	public void setMaze(Builder newMaze) {
		this.maze = newMaze;
	}

	public void setDragonNumber(int newDragonNumber) {
		this.dragonNumber = newDragonNumber;
	}

	public void setDragonMode(DragonMode newMode) {
		this.mode = newMode;
	}

	public void setGameControls(String right, String left, String up,
			String down, String eagle) {
		this.rightControl = right;
		this.leftControl = left;
		this.upControl = up;
		this.downControl = down;
		this.eagleControl = eagle;
	}

	public void setGame(Game newGame) {
		this.game = newGame;
	}

	public void setSizeOfMaze(int newSize) {
		this.sizeOfMaze = newSize;
	}

	public void startCreateMaze() {
		game = new Game(sizeOfMaze, sizeOfMaze);

		setGame(game);
		setMaze(game.getCreateMaze());

		setVisible(false);
		setVisible(true);

		window.getContentPane().add(this);
		window.setVisible(true);

		requestFocus();
		timer.start();
	}

	public void isCreatingGame(boolean s) {
		this.isCreatingGame = s;
	}

	public boolean getIsCreatingGame() {
		return this.isCreatingGame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
