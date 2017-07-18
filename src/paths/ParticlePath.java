package paths;

import main.Particle;

public abstract class ParticlePath {

	double time;
	double speed;
	
	public abstract void updatePath(Particle p);
}
