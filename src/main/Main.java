package main;
	
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	public static final String serial_version = "0.1";
	public static final int CANVAS_WIDTH = 800;
	public static final int CANVAS_HEIGHT = 600;
	
	World world;
	Renderer renderer;
	
	@Override
	public void start(Stage stage) throws Exception{
		BorderPane borderpane = new BorderPane();
		MenuBar menubar = new MenuBar();
		
		// General game options
		Menu game_menu = new Menu("Game");
		CheckMenuItem pauseplay = new CheckMenuItem("Pause");
		MenuItem reset = new MenuItem("Reset");
		MenuItem test = new MenuItem("TEST");
		game_menu.getItems().addAll(pauseplay, reset, test);
		
		Menu render_menu = new Menu("Render");
		CheckMenuItem show_forces_item = new CheckMenuItem("Show forces");
		CheckMenuItem show_tangents_item = new CheckMenuItem("Show tangents");
		render_menu.getItems().addAll(show_forces_item, show_tangents_item);
		
		// Spawning new points, etc.
		Menu points_menu = new Menu("Points");
		MenuItem dots = new MenuItem("Dots");
		MenuItem paths = new MenuItem("Paths");
		MenuItem builders = new MenuItem("Builders");
		points_menu.getItems().addAll(dots,paths,builders);
		
		// File specific options
		Menu file_menu = new Menu("File");
		MenuItem save = new MenuItem("Save");
		MenuItem load = new MenuItem("Load");
		MenuItem undo = new MenuItem("Undo");
		file_menu.getItems().addAll(save, load, new SeparatorMenuItem(), undo);
		
		menubar.getMenus().addAll(game_menu, points_menu, file_menu, render_menu);
		
		// Everything drawn on canvas
		Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
	
		borderpane.setCenter(canvas);
		borderpane.setTop(menubar);
		
		Scene scene = new Scene(borderpane);
		stage.setTitle(String.format("Floating Points JFX  v%s", serial_version));
		stage.setScene(scene);
		stage.show();
		
		// Initiate world FIRST
		World world = new World();
		
		// Begin renderer and timer SECON
		InputHandler inputhandler = new InputHandler(world);

		// Button and mouse handling
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (event)->{
			if (event.getButton() == MouseButton.PRIMARY)
				inputhandler.primaryClick(event);
			if (event.getButton() == MouseButton.SECONDARY)
				inputhandler.secondaryClick(event);
		});
		canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, (event)->{
			
		});
		canvas.addEventHandler(KeyEvent.KEY_PRESSED, (event)->{
			inputhandler.keyboardPressed(event);
		});
		
		renderer = new Renderer(canvas, world);
		renderer.start();
		
		show_forces_item.setSelected(renderer.show_forces);
		show_tangents_item.setSelected(renderer.show_tangents);
		show_forces_item.setOnAction((event)->{
			renderer.show_forces = show_forces_item.isSelected();
		});
		show_tangents_item.setOnAction((event)->{
			renderer.show_tangents = show_tangents_item.isSelected();
		});
		
		pauseplay.setOnAction((event)->{
			world.paused = pauseplay.isSelected();
		});
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
