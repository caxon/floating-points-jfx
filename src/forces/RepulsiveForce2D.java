package forces;

import main.Particle;

public class RepulsiveForce2D extends Force2D{

	public static final double MIN_DISTANCE = 0.5;
	
	Particle applier;
	double coefficient;
	
	public RepulsiveForce2D(Particle applier, double coefficient) {
		this.applier=applier;
		this.coefficient=coefficient;
	}
	
	@Override
	public void calculateForce(Particle p) {
		double dx = p.getX()-applier.getX();
		double dy = p.getY()-applier.getY();
		double dist = Math.hypot(dx, dy);
		
		// If particle is closer than MIN_DISTANCE, set to MIN_DISTANCE to avoid absurd forces
		dist = (dist > MIN_DISTANCE) ? dist : MIN_DISTANCE;
		
		double fmagnitude = coefficient/(dist*dist);
		double dxnorm = dx/dist;
		double dynorm = dy/dist;
		
		fx = fmagnitude * dxnorm;
		fy = fmagnitude * dynorm;
	}

}
