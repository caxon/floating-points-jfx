package main;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import paths.ForcePath2D;

public class Renderer {

	public static final double VELOCITY_SCALE = 20.0;
	public static final double FORCE_SCALE = 1000.0;
	public static final double VECTOR_WIDTH = 3.0;
	
	public boolean show_forces = false;
	public boolean show_tangents = false;
	
	AnimationTimer animationtimer;
	Canvas canvas;
	GraphicsContext gc;
	World world;

	public Renderer(Canvas canvas, World world) {
		this.world = world;
		this.canvas = canvas;
		gc = canvas.getGraphicsContext2D();
	}
	
	// Start animationtimer and renderer loop. Currently supports logic loop as well.
	public void start() {
		animationtimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				renderGame();
				world.tick(now);
			}
		};
		animationtimer.start();
	}
	public void pause() {
		if (animationtimer != null)
			animationtimer.stop();
		else
			System.err.println("TIMER NULL. CANNOT PAUSE.");
	}
	public void resume() {
		if (animationtimer!= null)
			animationtimer.start();
		else
			System.err.println("TIMER NULL. CANNOT RESUME (START)");
	}
	
	private void renderGame(){
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(),canvas.getHeight());
		
		for (Particle p : world.particles) {
			renderParticle(p);
			
			// Toggle gridparticles
			if (true)
			if (p instanceof GridParticle) {

				gc.setStroke(Color.BLACK);
				gc.setLineWidth(1);
				for (Particle pc : ((GridParticle)p).getConnections()) {
					gc.strokeLine(p.getX(), p.getY(), pc.getX(), pc.getY());
				}
			}
		}
		
		gc.setLineWidth(VECTOR_WIDTH);
		
		if (show_forces)
			renderTangents();
		if (show_tangents)
			renderForces();
		Particle COM = world.getCenterOfMass();
		System.out.println(COM.getX() + "," + COM.getY());
		renderParticle(COM);
	}
	
	private void renderTangents() {
		gc.setStroke(Color.GREEN);
		for (Particle p : world.particles) {
			if (p.getPath() instanceof ForcePath2D)
				gc.strokeLine(p.getX(), p.getY() , 
						((ForcePath2D)p.getPath()).getVx()*VELOCITY_SCALE + p.getX() , ((ForcePath2D)p.getPath()).getVy()*VELOCITY_SCALE + p.getY());
		}
	}
	private void renderForces() {
		gc.setStroke(Color.RED);
		for (Particle p : world.particles) {
			if (p.getPath() instanceof ForcePath2D) {
				gc.strokeLine(p.getX(), p.getY() , 
						((ForcePath2D)p.getPath()).getFx()*FORCE_SCALE + p.getX() , ((ForcePath2D)p.getPath()).getFy()*FORCE_SCALE + p.getY());
				
			}
		}
	}
	private void renderParticle(Particle p) {

		gc.setFill(p.getColor());
		gc.fillOval(p.getX()-p.getSize()/2, p.getY()-p.getSize()/2, p.getSize(), p.getSize());
	}
}
