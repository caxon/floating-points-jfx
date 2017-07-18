package builders;

import java.util.ArrayList;

import forces.ElasticForce2D;
import forces.Force2D;
import javafx.scene.paint.Color;
import main.GridParticle;
import main.Particle;
import paths.ForcePath2D;

public class ParticlesBuilder {

	ArrayList<Particle> particles;
	
	// Default constructor
	public ParticlesBuilder() {	
		particles = new ArrayList<Particle>();
	}
	// New particle_builder based on initial array
	public ParticlesBuilder(ArrayList<Particle> initial_particles) {
		particles=new ArrayList<>(initial_particles);
	}
	public ParticlesBuilder genGrid(double x0, double y0, double width, double height, int n) {
		ParticlesBuilder ret = new ParticlesBuilder();
		
		double area = width * height;
		double linear_density = Math.sqrt(n/area);
		int number_width = (int) (width*linear_density);
		int number_height = (int)(height*linear_density);

		System.out.println(linear_density);
		
		for (int i = 0; i< number_width; i++) {
			for (int j = 0; j< number_height;j++) {
				ret.get().add(new Particle(i/linear_density+x0, j/linear_density+y0, 3));
				System.out.println((i/linear_density+x0) + ","+ (j/linear_density+y0));
			}
		}
		
		int total = number_height*number_width;
		
		System.out.println(String.format("GenGrid generated %d particles. %d x %d", total, number_height, number_width));
		return ret;
	}
	public ParticlesBuilder genGridGP(double x0, double y0, double width, double height, int n) {
		ParticlesBuilder ret = new ParticlesBuilder();
		
		double area = width * height;
		double linear_density = Math.sqrt(n/area);
		int number_width = (int) (width*linear_density);
		int number_height = (int)(height*linear_density);

		System.out.println(linear_density);
		
		// Generate GP's (row first, then columns)
		for (int j = 0; j< number_height; j++) {
			for (int i = 0; i< number_width; i++) {
				ret.get().add(new GridParticle(i/linear_density+x0, j/linear_density+y0, 3));
			}
		}
		// Add vertical connections
		for (int j = 0; j< number_height - 1; j++) {
			for (int i = 0; i< number_width; i++) {
				int index = j*number_width+i;
				((GridParticle)ret.get().get(index)).connect(ret.get().get(index+number_width));;
			}
		}
		// Add horizontal connections
		for (int j = 0; j< number_height; j++) {
			for (int i = 0; i< number_width-1; i++) {
				int index = j*number_width+i;
				((GridParticle)ret.get().get(index)).connect(ret.get().get(index+1));
			}
		}
		
		int total = number_height*number_width;
		
		System.out.println(String.format("GenGrid generated %d grid particles. %d x %d", total, number_height, number_width));
		return ret;
	}
	
	public ParticlesBuilder addForce(Force2D force){
		
		for (Particle i : particles) {
			ForcePath2D fp;
			
			if (!(i.getPath() instanceof ForcePath2D)) {
				fp = new ForcePath2D();
				i.setPath(fp);
			}
			else
				fp = (ForcePath2D)i.getPath();
			fp.addForce(force);
		}
		
		return this;
	}
	
	public ParticlesBuilder addElasticForceGP(double coefficient) {
		for (Particle p: particles) {
			if (!(p instanceof GridParticle))
				continue;
			GridParticle gp = (GridParticle)p;
			ForcePath2D gp_forcepath;
			if (gp.getPath() instanceof ForcePath2D)
				gp_forcepath = (ForcePath2D) gp.getPath();
			else {
				gp_forcepath = new ForcePath2D();
				gp.setPath(gp_forcepath);
			}
			
			for (Particle p2 : gp.getConnections()) {
				ForcePath2D p2_forcepath;
				if (p2.getPath() instanceof ForcePath2D)
					p2_forcepath = (ForcePath2D) p2.getPath();
				else {
					p2_forcepath= new ForcePath2D();
					p2.setPath(p2_forcepath);
				}
				gp_forcepath.addForce(new ElasticForce2D(gp, p2, coefficient));
				p2_forcepath.addForce(new ElasticForce2D(p2, gp, coefficient));
				
				
			}
		}
		
		return this;
	}
	
	public ParticlesBuilder setSize(double size) {
		for (Particle p : particles) 
			p.setSize(size);
		return this;
	}
	
	public ParticlesBuilder setColor(Color color) {
		for (Particle p : particles)
			p.setColor(color);
		
		return this;
	}
	
	public ArrayList<Particle> get(){
		return particles;
	}
	public void add(Particle particle){
		particles.add(particle);
	}
}
