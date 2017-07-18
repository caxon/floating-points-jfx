package main;

import java.util.ArrayList;

import builders.ParticlesBuilder;
import forces.ConstantForce2D;
import forces.ElasticForce2D;
import forces.RepulsiveForce2D;
import javafx.scene.paint.Color;
import paths.ForcePath2D;
import paths.LinearPath2D;
import paths.OrbitPath2D;


public class World {
	
	ArrayList<Particle> particles;
	public boolean paused = false;
	
	// Global particles and forces
	Particle shot = new Particle(-1000,-1000,20);
	
	public World() {
		System.out.println("GEN'd");
		particles = new ArrayList<>();
		particles.add(shot);
		generate();
	}

	// Pre-place all objects
	void generate() {
		particles.addAll(new ParticlesBuilder().genGridGP(0, 0, 800, 600, 4000).setColor(Color.BLUE).setSize(5)
				.addForce(new RepulsiveForce2D(shot, 20)).addElasticForceGP(0.0001).get());
		shot.setPath(new ForcePath2D());
	}
	
	// Tick and game logic
	public void tick(long now) {
		if (!paused) {
		for (Particle p : particles)
			p.move();
		ForcePath2D.flush();
		}
	}
	
	public void spawnParticle(double x, double y) {
		shot.setX(x);
		shot.setY(y);
		((ForcePath2D) shot.getPath() ).push(0, 0);
	}
	
	public Particle getCenterOfMass() {
		
		int count=0;
		double x=0;
		double y=0;
		
		for (Particle p : particles) {
			if (!(p.getPath() instanceof ForcePath2D))
				continue;
			ForcePath2D fp = (ForcePath2D) p.getPath();
			x += p.getX();
			y+=p.getY();
			count++;
		}
		x/=count;
		y/=count;
		
		Particle ret = new Particle(x, y, 30);
		ret.setColor(Color.GREEN);
		return ret;
	}
}
