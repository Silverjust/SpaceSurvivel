package buildings;

import buildings.Farm.Pannel;
import components.ResNames;
import components.RessourceGroup;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import guiElements.GUIpannel;
import main.Game;
import processing.core.PApplet;
import states.BuildingWait;
import states.BuildingWork;
import states.StorageWait;
import states.Storing;

public class Storage extends Building {

	RessourceGroup resHandler;
	private StorageWait wait;

	public Storage(Game game, int x, int y) {
		super(game, x, y);
		build = new BuildingWork();
		wait = new StorageWait();
		resHandler = new RessourceGroup();

		RessourceGroup res = new RessourceGroup();
		res.addToRessource(ResNames.METALL, 1000);
		((Storing) wait).getInput().add(res);
		setState(wait, this);
	}

	@Override
	public void draw(PApplet app) {
		app.fill(200, 200, 0);
		app.rect(x * Game.gridSize + 5, y * Game.gridSize + 5, 40, 40);
	}

	public void addToRessource(ResNames res, int amount) {
		this.resHandler.addToRessource(res, amount);
	}

	ResNames[] getStoredRessources() {
		return resHandler.getRessources();
	}

	@Override
	public void startGui() {
		game.pannel = new Pannel();
	}

	public class Pannel extends GUIpannel {

		private GButton close;
		private GButton repair;


		public Pannel() {
			close = new GButton(game.app, 100, 100, 200, 100, "close");
			close.addEventHandler(this, "handleButtonEvents");
		}

		public void handleButtonEvents(GButton button, GEvent event) {
			if (button == close)
				game.disposePannel();

		}

		@Override
		public void dispose() {
			close.dispose();

		}

		@Override
		public void update() {
			game.app.text(wait.getInput().getText(), 500, 500);
		}
	}
}