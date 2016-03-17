package states;

import java.util.ArrayList;

import buildings.Entity;
import buildings.Human;
import buildings.Unit;

public class BuildingWork extends State {

	ArrayList<Unit> workers = new ArrayList<Unit>();
	private int workerNeeded = 1;
	private int Wmax;
	private float W = 0;
	ArrayList<Slot> in = new ArrayList<Slot>(), out = new ArrayList<Slot>();

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

	@Override
	public void onStart(Entity e) {
		inputHasMin();
		super.onStart(e);
	}

	public void addW(float w) {
		if (inputHasMin())
			W += w;
	}

	private boolean inputHasMin() {
		boolean hasMin = false;
		if (!in.isEmpty()) {
			for (Slot slot : in) {
				if (slot.hasMin())
					hasMin = true;
			}
		} else
			hasMin = true;
		if (hasMin)
			return true;
		callCarrier(in);
		return false;
	}

	private void callCarrier(ArrayList<Slot> slots) {
		// TODO make carrier (free human) transport stuff either from storage to
		// machine or from machine to storage

	}

	@Override
	public boolean needsWorker() {
		return workers.size() < workerNeeded;
	}

	@Override
	public void update(Entity e) {
		if (W >= Wmax) {
			W = 0;
			for (Slot slot : out) {
				slot.add(slot.getMin());
				callCarrier(out);
			}
			e.Statefinished(this);
		}
	}

	@Deprecated
	public void addWorker(Entity e) {

	}

	public float getProgress() {
		if (Wmax == 0)
			return 1;
		if (W > Wmax)
			return 1;

		return W / Wmax;
	}

	@Override
	public void onEnd(Entity e) {
		for (Unit unit : workers) {
			unit.setState(((Human) unit).wait, this);
		}
		workers.clear();
	}

	public ArrayList<Unit> getWorkers() {
		return workers;
	}

	public void registerAsWorker(Unit u) {
		if (needsWorker())
			workers.add((Unit) u);
	}
}
