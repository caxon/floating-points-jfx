package main;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class InputHandler {

	World world;
	
	public InputHandler(World world) {
		this.world = world;
	}
	
	public void primaryClick(MouseEvent event) {
		world.spawnParticle(event.getX(), event.getY());
		System.out.println(String.format("CLICK AT %3d %3d", (int) event.getX(), (int) event.getY()));
	}
	
	public void secondaryClick(MouseEvent event) {
		world.shot.setX(10000);
		world.shot.setY(10000);
	}
	
	public void keyboardPressed(KeyEvent event) {
		
	}
}
