package states;

import java.util.ArrayList;

import buildings.Entity;
import buildings.Human;
import buildings.Kompostierer;
import buildings.Unit;
import components.ResNames;
import components.RessourceGroup;
import components.Slot;
import main.Game;

public class BuildingWork extends State implements Storing {

	ArrayList<Unit> carriers = new ArrayList<Unit>();
	ArrayList<Unit> workers = new ArrayList<Unit>();
	private int workerNeeded = 1;
	private int Wmax;
	private float W = 0;
	Slot in = new Slot(), out = new Slot();
	private boolean dontAcssesWorkers;
	
	public BuildingWork() {
		super();
	}

	public BuildingWork setWorkers(int n) {
		workerNeeded = n;
		return this;
	}

	public BuildingWork setW(int i) {
		Wmax = i;
		return this;
	}

	public BuildingWork setRepeat(boolean b) {
		repeat = b;
		return this;
	}

	public BuildingWork setInput(RessourceGroup res) {
		in.setMin(res);
		return this;
	}

	public BuildingWork setOutput(RessourceGroup res) {
		out.setMin(res);
		return this;
	}

	@Override
	public void onStart(Entity e, State old) {
		/*
		 * if (!inputHasMin()) callGetter(e); else if (needsWorker())
		 * callWorker(e);
		 */
	}

	@Override
	public void onEnd(Entity e) {
		dontAcssesWorkers = true;
		for (Unit unit : workers) {
			unit.endState();
		}
		dontAcssesWorkers = false;
		workers.clear();
	}

	@Override
	public void update(Entity e) {
		if (!inputHasMin())
			callGetter(e);
		else if (needsWorker())
			callWorker(e);
		if (W >= Wmax) {
			W = 0;
			in.add(in.getMin().inv());
			out.add(out.getMin());
			callTaker(out);
			if (repeat)
				e.addState(this, this);
			e.endState();
		}
	}

	public void addW(Entity worker, Entity target, float w) {
		if (inputHasMin())
			W += w;
		else {
			worker.insertState(worker.wait, this);
			((Human) worker).setHasWork(false);
			workers.remove(worker);

			callGetter(target);
		}
	}

	/** test if this has enough ressources to produce output */
	private boolean inputHasMin() {
		return in.hasMin();
	}

	public boolean needsWorker() {
		return workers.size() < workerNeeded;
	}

	private void callWorker(Entity e) {
		for (Entity entity : e.game.getEntities()) {
			Human human = (Human) entity;
			if (entity instanceof Human && !human.hasWork() && needsWorker()) {
				workers.add(human);
				human.gotoWork.setTarget(e, human);
				human.insertState(human.gotoWork, this);
			}
		}
	}

	private void callGetter(Entity e) {
		for (ResNames name : in.getMin().getRessources()) {
			new HumanGetQuest().requestMin(in.getMin().get(name), e,carriers);
		}
		/*
		 * ResNames[] res = in.getMin().getRessources(); for (int n = 0; n <
		 * res.length; n++) { //
		 * System.out.println("BuildingWork.callGetter()"); for (Entity entity :
		 * e.game.getEntities()) { if (entity instanceof Human && !((Human)
		 * entity).hasWork()) { Human human = (Human) entity; for (int i = 0; i
		 * < Game.gridW; i++) { for (int j = 0; j < Game.gridH; j++) { Entity
		 * building = e.game.getBuildings()[i][j]; if (building != null &&
		 * building.getState() instanceof Storing && ((Storing)
		 * building.getState()).getOutput().containsPure(in.getMin().get(res[n])
		 * ) && !enoughCarrierOTW()) { carriers.add(human);
		 * //System.out.println("BuildingWork.callGetter()"+building.getX());
		 * ((HumanCarry) human.carry).setTargets(building, e, human,
		 * in.getMin().get(res[n])); entity.insertState(human.carry, this); } }
		 * } } }
		 * 
		 * }
		 */
	}

	/** tests if there are already enough ressources on the way (unfinished) */
	private boolean enoughCarrierOTW() {
		// TODO Auto-generated method stub
		return carriers.size() > 0;
	}

	private void callTaker(Slot out2) {
		// TODO Auto-generated method stub

	}

	public float getProgress() {
		if (Wmax == 0)
			return 1;
		if (W > Wmax)
			return 1;
		return W / Wmax;
	}

	@Override
	public Slot getInput() {
		return in;
	}

	@Override
	public Slot getOutput() {
		return out;
	}

	public ArrayList<Unit> getWorkers() {
		return workers;
	}

	public ArrayList<Unit> getCarriers() {
		return carriers;
	}
	
	public void removeWorker(Entity e) {
		if (!dontAcssesWorkers)
			workers.remove(e);

	}
}
