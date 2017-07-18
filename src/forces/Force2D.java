package forces;

import main.Particle;

abstract public class Force2D {

	double fx;
	double fy;
	
	public double getFx() {
		return fx;
	}
	
	public double getFy() {
		return fy;
	}
	
	public double getMagnitude() {
		return Math.hypot(fx, fy);
	}
	
	public abstract void calculateForce(Particle p);
}

