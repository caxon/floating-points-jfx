package paths;

import main.Particle;

public class LinearPath2D extends ParticlePath {

	double dx;
	double dy;
	
	public LinearPath2D(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public void updatePath(Particle p) {
		p.setX(p.getX()+dx);
		p.setY(p.getY()+dy);
	}

	
}
