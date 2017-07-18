package forces;

import main.Particle;

public class ConstantForce2D extends Force2D{

	double x;
	double y;
	
	public ConstantForce2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	
	@Override
	public void calculateForce(Particle p) {
		fx = x;
		fy = y;
		
	}

}
