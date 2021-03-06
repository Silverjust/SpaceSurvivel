package main;

import java.util.ArrayList;

import buildings.Entity;
import buildings.Unit;
import g4p_controls.GButton;
import processing.core.PApplet;

public class Helper {

	public static byte getDirection(float x, float y, float tx, float ty) {
		float a = (float) Math.toDegrees(Math.atan2(y - ty, x - tx));
		a += 112.5;
		if (a < 0) {
			a += 360;
		}
		byte b = (byte) (a * 8 / 360);
		return (0 <= b && b <= 8) ? b : 0;
	}

	@Deprecated
	public static boolean StringToBoolean(String S) {
		boolean b = false;
		if (S.equals("true")) {
			b = true;
		} else if (S.equals("false")) {
			b = false;
		} else {
			throw new IllegalArgumentException("String is no boolean");
		}
		return b;
	}

	public static boolean isOver(float x, float y, float x1, float y1, float x2, float y2) {
		boolean b = x1 <= x && x <= x2 && y1 <= y && y <= y2;
		return b;
	}

	public static boolean isBetween(float x, float y, float x1, float y1, float x2, float y2) {
		boolean b = (x1 < x2 ? (x1 <= x && x <= x2) : (x2 <= x && x <= x1))
				&& (y1 < y2 ? (y1 <= y && y <= y2) : (y2 <= y && y <= y1));
		return b;
	}

	public static boolean isMouseOver(PApplet app, float x1, float y1, float x2, float y2) {
		boolean b = x1 <= app.mouseX && app.mouseX <= x2 && y1 <= app.mouseY && app.mouseY <= y2;
		return b;
	}

	public static boolean listContainsInstanceOf(Class<?> c, ArrayList<Unit> arrlist) {
		if (c == null) {
			return true;
		}
		for (Entity e : arrlist) {
			if (c.isAssignableFrom(e.getClass())) {
				return true;
			}

		}
		return false;
	}

	public static int listContainsInstancesOf(Class<?> c, ArrayList<Unit> arrlist) {
		if (c == null) {
			return 0;
		}
		int i = 0;
		for (Entity e : arrlist) {
			if (c.isAssignableFrom(e.getClass())) {
				i++;
			}

		}
		return i;
	}

	/**
	 * creates a GButton coords in percent of window */
	public static GButton createButton(PApplet app, Object handlerClass, float x, float y, float w, float h,
			String name) {
		float b = 0.005f;
		GButton gButton = new GButton(app, app.width * (x + b), app.height * (y + b), app.width * (w - 2 * b),
				app.height * (h - 2 * b), name);
		gButton.addEventHandler(handlerClass, "handleButtonEvents");
		return gButton;
	}

	static public class Timer {
		public int cooldown;
		private int cooldownTimer;
		private GameTime time;

		public Timer(GameTime time) {
			this.time = time;
		}

		public Timer(GameTime time, int cooldown) {
			this.cooldown = cooldown;
			this.time = time;
		}

		public void startCooldown() {
			cooldownTimer = time.getMillis() + cooldown;
		}

		public boolean isNotOnCooldown() {
			return cooldownTimer <= time.getMillis();
		}

		public float getCooldownPercent() {
			float f = 1 - (float) (cooldownTimer - time.getMillis()) / cooldown;
			return f > 1 || f < 0 ? 1 : f;
		}

		public float getTimeLeft() {
			return (cooldownTimer - time.getMillis());
		}

	}
}
