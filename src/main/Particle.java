package main;

import javafx.scene.paint.Color;
import paths.ParticlePath;

public class Particle {

	private double x;
	private double y;
	
	private double size;
	
	private Color color;
	private ParticlePath path;
	
	public Particle(double x, double y, double size) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.color= Color.BLUE;
	}
	
	public void move() {
		if (path != null)
			path.updatePath(this);
	}
	
	
	public double getSize() {
		return size;
	}
	public Color getColor() {
		return color;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setPath(ParticlePath path){
		this.path=path;
	}
	public void setColor(Color col) {
		this.color = col;
	}
	public ParticlePath getPath() {
		return path;
	}
	public void setSize(double size) {
		this.size = size;
	}
}
