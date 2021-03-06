package main;

import java.awt.Toolkit;

import guiElements.BuildPannel;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Input {

	Game game;
	PApplet app;

	boolean strgMode;// rename
	boolean shiftMode;// rename

	int doubleClickIntervall;
	int doubleClickStart;
	private boolean isMPressedOutOfFocus;
	private float screenSpeed = 30;
	private int strgKey = PConstants.CONTROL;
	private int shiftKey = PConstants.SHIFT;

	public Input(Game game) {

		this.game = game;
		this.app = game.app;
		app.registerMethod("mouseEvent", this);
		app.registerMethod("keyEvent", this);

		doubleClickIntervall = (int) Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");
	}

	public void update() {// ********************************************************
		int rimSize = 10;
		if (app.focused && !isMPressedOutOfFocus && game.getPannel() == null) {
			if (Helper.isMouseOver(app, -5, -5, rimSize, app.height) && game.xOffset < 0)
				game.xOffset += screenSpeed;
			if (Helper.isMouseOver(app, app.width - rimSize, -5, app.width, app.height)
					&& -game.xOffset + app.width <= main.Game.gridSize * main.Game.gridW * game.zoom)
				game.xOffset -= screenSpeed;
			if (Helper.isMouseOver(app, -5, -5, app.width, rimSize) && game.yOffset < 0)
				game.yOffset += screenSpeed;
			if (Helper.isMouseOver(app, -5, app.height - rimSize, app.width, app.height)
					&& -game.yOffset + app.height <= main.Game.gridSize * main.Game.gridH * game.zoom)
				game.yOffset -= screenSpeed;
		}
		if (app.focused && !isMPressedOutOfFocus) {
			if (app.key == 'a' && game.xOffset < 0)
				game.xOffset += screenSpeed;
			if (app.key == 'd' && -game.xOffset + app.width <= main.Game.gridSize * main.Game.gridW * game.zoom)
				game.xOffset -= screenSpeed;
			if (app.key == 'w' && game.yOffset < 0)
				game.yOffset += screenSpeed;
			if (app.key == 's' && -game.yOffset + app.height <= main.Game.gridSize * main.Game.gridH * game.zoom)
				game.yOffset -= screenSpeed;
		}
	}

	public void keyPressed() {// ********************************************************

		if (app.keyCode == strgKey) {
			strgMode = true;
		}
		if (app.keyCode == shiftKey) {
			shiftMode = true;
		}
		if (app.key == ' ') {
			game.sidePannel.toggle();
		}
		if (app.key == 'e') {
			game.setPannel(new BuildPannel(game));
		}
		if (app.key == 'p') {
			if (game.updater.pause)
				game.gameTime.endPause();
			else
				game.gameTime.startPause();

		}

		// System.out.println(app.key);

	}

	public void keyReleased() {
		// ********************************************************

		if (app.keyCode == strgKey) {
			strgMode = false;
		}
		if (app.keyCode == shiftKey) {
			shiftMode = false;
		}
		app.key = 0;
	}

	public void mouseClicked() {// ********************************************************
	}

	public void mousePressed() {// ********************************************************
		int xCoord = ScreenToGrid(app.mouseX, game.xOffset, Game.gridW);
		int yCoord = ScreenToGrid(app.mouseY, game.yOffset, Game.gridH);
		if (xCoord != -1 && yCoord != -1) {
			if (game.getBuildings()[xCoord][yCoord] != null) {
				game.getBuildings()[xCoord][yCoord].startGui();
			}
			if (game.aimer != null) {
				game.aimer.click(xCoord, yCoord);
			}
		}
	}

	public int ScreenToGrid(float screen, float offset, int max) {
		int grid = PApplet.floor((screen - offset) / game.zoom / Game.gridSize);
		return 0 < grid && grid < max ? grid : -1;
	}

	public void mouseReleased() {// ********************************************************

	}

	public void mouseDragged() {// ********************************************************

	}

	public void mouseMoved() {// ********************************************************

	}

	public void mouseWheelMoved(MouseEvent event) {// ********************************************************
		if (event.getCount() == 1 && game.zoom >= 0.5)
			game.zoom -= 0.1;
		if (event.getCount() == -1 && game.zoom <= 1.5)
			game.zoom += 0.1;
	}

	public void keyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.PRESS && app.key == PConstants.ESC) {
			app.key = 0;
			game.disposePannel();
		}
		if (game.getPannel() == null) {
			switch (event.getAction()) {
			case KeyEvent.PRESS:
				keyPressed();
				break;
			case KeyEvent.RELEASE:
				keyReleased();
				break;
			default:
				break;
			}

		}
	}

	public void mouseEvent(MouseEvent event) {
		if (game.getPannel() == null) {
			switch (event.getAction()) {
			case MouseEvent.PRESS:
				mousePressed();
				break;
			case MouseEvent.RELEASE:
				mouseReleased();
				break;
			case MouseEvent.DRAG:
				mouseDragged();
				break;
			case MouseEvent.MOVE:
				mouseMoved();
				break;
			case MouseEvent.WHEEL:
				mouseWheelMoved(event);
				break;
			default:
				break;
			}
		}
	}
}
