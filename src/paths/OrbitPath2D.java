package paths;

import main.Particle;

public class OrbitPath2D extends ParticlePath{

	Particle center;
	double theta_0;
	double radius;
	long time;
	
	public OrbitPath2D(Particle this_particle, Particle center, double speed) {
		this.center = center;
		this.speed = speed;
		
		time = 0;
		
		double dx = this_particle.getX()-center.getX();
		double dy = this_particle.getY()-center.getY();
		
		radius = Math.hypot( dx, dy);
		theta_0 = Math.atan2(dx, dy);
		
	}
	
	@Override
	public void updatePath(Particle p) {
		double theta = time*speed*2*Math.PI;
		p.setX(Math.cos(theta) * radius + center.getX());
		p.setY(Math.sin(theta) *radius + center.getY());
		
		time++;
	}

}
