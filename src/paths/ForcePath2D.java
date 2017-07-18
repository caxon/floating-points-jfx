
package paths;

import java.util.ArrayList;

import forces.Force2D;
import main.Particle;

public class ForcePath2D extends ParticlePath{


	static ArrayList<FlushItem> flush_list = new ArrayList<>();
	
	ArrayList<Force2D> forces= new ArrayList<>();

	double mass=1;
	double fx;
	double fy; // Net force components
	
	double vx;
	double vy; // Velocity components
	
	public ForcePath2D(Force2D force) {
		addForce(force);
	}
	
	public ForcePath2D() {
	}

	@Override
	public void updatePath(Particle p) {
		// Reset fx, fy calculations
		fx = 0;
		fy = 0;
		
		for (Force2D force : forces) {
			force.calculateForce(p);
			fx+=force.getFx();
			fy+=force.getFy();
		}

		
		vx += fx/mass;
		vy += fy/mass;
		// Moves all particles at once AFTER calculating forces
		flush_list.add(new FlushItem(p, vx, vy));
	}
	
	public void addForce(Force2D f) {
		forces.add(f);
	}
	
	public void push(double pushx, double pushy) {
		vx += pushx;
		vy += pushy;
	}
	
	public static void flush() {
		for (FlushItem f : flush_list) {
			f.particle.setX(f.particle.getX()+f.vx);
			f.particle.setY(f.particle.getY()+f.vy);
		}
		
		flush_list.clear();
	}

	// General 3-tuple class for particle and force data
	class FlushItem{

		Particle particle;
		double vx;
		double vy;
		
		public FlushItem(Particle particle, double vx, double vy) {
			this.particle = particle;
			this.vx = vx;
			this.vy = vy;
		}
	}
	
	public double getFx() {
		return fx;
	}
	public double getFy() {
		return fy;
	}
	public double getVx() {
		return vx;
	}
	public double getVy() {
		return vy;
	}
	public void setMass(double mass) {
		this.mass = mass;
	}
}
