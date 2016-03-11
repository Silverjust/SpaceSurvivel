package main;

import java.awt.Toolkit;
import java.awt.event.MouseWheelEvent;

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

	public Input(Game game) {

		this.game = game;
		this.app = game.app;
		app.registerMethod("mouseEvent", this);
		app.registerMethod("keyEvent", this);

		doubleClickIntervall = (int) Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");
	}

	public void update() {// ********************************************************
		int screenSpeed = 30;
		int rimSize = 10;
		if (app.focused && !isMPressedOutOfFocus) {
			if (Helper.isMouseOver(app, 0, 0, rimSize, app.height) && game.xOffset < 0)
				game.xOffset += screenSpeed;
			if (Helper.isMouseOver(app, app.width - rimSize, 0, app.width, app.height)
					&& -game.xOffset + app.width <= main.Game.gridSize * main.Game.gridW * game.zoom)
				game.xOffset -= screenSpeed;
			if (Helper.isMouseOver(app, 0, 0, app.width, rimSize) && game.yOffset < 0)
				game.yOffset += screenSpeed;
			if (Helper.isMouseOver(app, 0, app.height - rimSize, app.width, app.height)
					&& -game.yOffset + app.height <= main.Game.gridSize * main.Game.gridH * game.zoom)
				game.yOffset -= screenSpeed;
		}
	}

	public void keyPressed() {// ********************************************************

		/*
		 * if (app.keyCode == SettingHandler.setting.strg) { strgMode = true; }
		 * if (app.keyCode == SettingHandler.setting.shift) { shiftMode = true;
		 * }
		 */

		// System.out.println(app.key);

	}

	public void keyReleased() {
		// ********************************************************
		/*
		 * if (app.keyCode == SettingHandler.setting.strg) { strgMode = false; }
		 * if (app.keyCode == SettingHandler.setting.shift) { shiftMode = false;
		 * }
		 */}

	public void mouseClicked() {// ********************************************************
	}

	public void mousePressed() {// ********************************************************

	}

	public void mouseReleased() {// ********************************************************

	}

	public void mouseDragged() {// ********************************************************

	}

	public void mouseMoved() {// ********************************************************

	}

	public void mouseWheelMoved(MouseWheelEvent e) {// ********************************************************

	}

	public void keyEvent(KeyEvent event) {
		if (true) {
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
			if (app.key == PConstants.ESC) {
				app.key = 0;
			}
		}
	}

	public void mouseEvent(MouseEvent event) {
		if (true) {
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
			default:
				break;
			}
		}
	}
}
