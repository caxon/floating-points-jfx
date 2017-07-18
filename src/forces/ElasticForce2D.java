package forces;

import main.Particle;

public class ElasticForce2D extends Force2D {

	Particle applier;
	double coefficient;
	double distance_0;
	
	public ElasticForce2D(Particle this_particle, Particle applier, double coefficient) {
		this.applier=applier;
		this.coefficient=coefficient;
		
		distance_0 = Math.hypot(applier.getX()-this_particle.getX(), applier.getY()-this_particle.getY());
	}
	
	public ElasticForce2D(Particle applier, double coefficient, double distance_0) {
		this.applier= applier;
		this.coefficient=coefficient;
		this.distance_0=distance_0;
	}
	
	@Override
	public void calculateForce(Particle p) {
		
		double dx = p.getX()-applier.getX();
		double dy = p.getY()-applier.getY();
		
		double distance=  Math.hypot(dx, dy);
		double displacement = distance - distance_0;
		double magnitude = -displacement*coefficient;
		
		fx = magnitude*dx;
		fy = magnitude*dy;
		
	}

}
