package main;

import java.util.ArrayList;

public class GridParticle extends Particle {

	ArrayList<Particle> connections;
	
	public GridParticle(double x, double y, double size) {
		super(x, y, size);
		connections= new ArrayList<>();
	}
	
	public void connect(Particle particle) {
		connections.add(particle);
	}
	
	public ArrayList<Particle> getConnections() {
		return connections;
	}
}
