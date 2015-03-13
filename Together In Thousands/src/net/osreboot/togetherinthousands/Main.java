package net.osreboot.togetherinthousands;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Main {

	public static Texture background, white, font;

	public static FadingWord label1, label2, label3, label4, label5, label6, count1, count2, count3, count4, count5, count6, reset;
	public static int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, c6 = 0;

	public static float reff = 200, geff = 30, beff = 100, breff = 200, bgeff = 50, bbeff = 200;

	public static Object moder;

	public static int mode = 0;

	public static boolean justReset = false;

	public static ArrayList<Droplet> player = new ArrayList<Droplet>();
	public static ArrayList<Droplet> toDestroy = new ArrayList<Droplet>();

	public static void main(String args[]){
		try{
			Display.setDisplayMode(new DisplayMode(1280, 720));
			Display.setIcon(new ByteBuffer[]{new ImageIOImageData().imageToByteBuffer(ImageIO.read(new FileInputStream("res/Icon32.png")), false, false, null)});
			Display.setTitle("Together In Thousands by Os_Reboot");
			Display.create();
		}catch(Exception e){
			e.printStackTrace();
		}

		initTextures();
		Font.init();
		Game.init();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glClearColor(0, 0, 0, 0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_MATRIX_MODE);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 1280, 720, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		new Object(0, 0, 2048, 2048, background);

		moder = new Object(-10, -10, 16, 16, 0.1f, 0.1f, 0.1f);

		label1 = new FadingWord(250, 130, "resistance", 1);
		label2 = new FadingWord(250, 340, "attack", 1);
		label3 = new FadingWord(250, 540, "zapping", 1);
		label4 = new FadingWord(720, 130, "dilution", 1);
		label5 = new FadingWord(840, 340, "death", 1);
		label6 = new FadingWord(680, 540, "retention", 1);

		reset = new FadingWord(1120, 10, "exit", 1);

		count1 = new FadingWord(140, 130, "0", 1);
		count2 = new FadingWord(140, 340, "0", 1);
		count3 = new FadingWord(140, 540, "0", 1);
		count4 = new FadingWord(1110, 130, "0", 0);
		count5 = new FadingWord(1110, 340, "0", 1);
		count6 = new FadingWord(1110, 540, "0", 1);


		for(int x = 0; x < 1280/Physics.spacing; x++){
			for(int y = 0; y < 720/Physics.spacing; y++){
				if(Physics.distance(x * Physics.spacing, y * Physics.spacing, 640, 360) < 340){
					/*if(x == 72 && y == 36) new Cell((x * Physics.spacing), (y * Physics.spacing), 10.0f, 10.0f, 10.0f, 1, 50f, 50f);
					else if(x == 50 && y == 36) new Cell((x * Physics.spacing), (y * Physics.spacing), 100.0f, 100.0f, 1000.0f, 2, 50f, 50f);
					else if(x == 57 && y == 20) new Cell((x * Physics.spacing), (y * Physics.spacing), 100.0f, 100.0f, 1000.0f, -1, 50f, 50f);
					else */new Cell((x * Physics.spacing), (y * Physics.spacing), 0.1f, 0.1f, 0.1f, 0, 50f, 50f);
				}
				if(Physics.distance(x * Physics.spacing, y * Physics.spacing, 150, 150) < 57) new Cell((x * Physics.spacing), (y * Physics.spacing), 1.0f, 0.5f, 0.5f, 100, 50f, 50f);
				if(Physics.distance(x * Physics.spacing, y * Physics.spacing, 150, 360) < 57) new Cell((x * Physics.spacing), (y * Physics.spacing), 0.5f, 1.0f, 0.5f, 100, 50f, 50f);
				if(Physics.distance(x * Physics.spacing, y * Physics.spacing, 150, 560) < 57) new Cell((x * Physics.spacing), (y * Physics.spacing), 0.5f, 0.5f, 1.0f, 100, 50f, 50f);
				if(Physics.distance(x * Physics.spacing, y * Physics.spacing, 1120, 150) < 57) new Cell((x * Physics.spacing), (y * Physics.spacing), 0.1f, 0.1f, 0.1f, 100, 50f, 50f);
				if(Physics.distance(x * Physics.spacing, y * Physics.spacing, 1120, 360) < 57) new Cell((x * Physics.spacing), (y * Physics.spacing), 0.9f, 0.9f, 0.9f, 100, 50f, 50f);
				if(Physics.distance(x * Physics.spacing, y * Physics.spacing, 1120, 560) < 57) new Cell((x * Physics.spacing), (y * Physics.spacing), 0.5f, 0.5f, 0.5f, 100, 50f, 50f);
			}
		}

		for(Cell c : Cell.cells) c.calculateNeighbors();

		while(!Display.isCloseRequested()){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			if(Mouse.isInsideWindow()){
				if(player.size() == 0){
					if(Mouse.isButtonDown(0)){
						for(Cell c : Cell.cells) if(c.o.x/10 == Mouse.getX()/10 && c.o.y/10 == (720 - Mouse.getY())/10){
							if(c.team == 1 && mode == 1 && c1 > 0){ player.add(new Droplet(c, reff, 0, 0)); c1--; }
							if(c.team == 1 && mode == 2 && c2 > 0){ player.add(new Droplet(c, 0, geff, 0)); c2--; }
							if(c.team == 1 && mode == 3 && c3 > 0){ player.add(new Droplet(c, 0, 0, beff)); c3--; }
							if(c.team != 100 && mode == 4 && c4 > 0){ player.add(new Droplet(c, 0, 0, 0, 1)); c4--; }
							if(c.team == 1 && mode == 5 && c5 > 0){ player.add(new Droplet(c, breff, bgeff, bbeff)); c5--; }
							if(c.team != 100 && mode == 6 && c6 > 0){ player.add(new Droplet(c, 0.5f, 0.5f, 0.5f, 2)); c6--; }
						}
					}else if(Mouse.isButtonDown(1)){
						/*for(Cell c : Cell.cells) if(c.o.x/10 == Mouse.getX()/10 && c.o.y/10 == (720 - Mouse.getY())/10 && c.team == 1){
							new Droplet(c, 200, 200, 200);
						}*///TODO
					}
				}

				if(Game.resetting == 0){
					if(Physics.distance(Mouse.getX(), Mouse.getY(), 155, 720 - 155) < 60){ label1.fadeIn(); count1.fadeIn(); }else label1.fadeOut();
					if(Physics.distance(Mouse.getX(), Mouse.getY(), 155, 720 - 365) < 60){ label2.fadeIn(); count2.fadeIn(); }else label2.fadeOut();
					if(Physics.distance(Mouse.getX(), Mouse.getY(), 155, 720 - 565) < 60){ label3.fadeIn(); count3.fadeIn(); }else label3.fadeOut();
					if(Physics.distance(Mouse.getX(), Mouse.getY(), 1120, 720 - 155) < 60){ label4.fadeIn(); count4.fadeIn(); }else label4.fadeOut();
					if(Physics.distance(Mouse.getX(), Mouse.getY(), 1120, 720 - 365) < 60){ label5.fadeIn(); count5.fadeIn(); }else label5.fadeOut();
					if(Physics.distance(Mouse.getX(), Mouse.getY(), 1120, 720 - 565) < 60){ label6.fadeIn(); count6.fadeIn(); }else label6.fadeOut();
					if(Physics.distance(Mouse.getX(), Mouse.getY(), 1280, 720) < 100){ reset.fadeIn(); }else reset.fadeOut();

					if(Keyboard.isKeyDown(Keyboard.KEY_1)) mode = 1;
					if(Keyboard.isKeyDown(Keyboard.KEY_2)) mode = 2;
					if(Keyboard.isKeyDown(Keyboard.KEY_3)) mode = 3;
					if(Keyboard.isKeyDown(Keyboard.KEY_Q)) mode = 4;
					if(Keyboard.isKeyDown(Keyboard.KEY_W)) mode = 5;
					if(Keyboard.isKeyDown(Keyboard.KEY_E)) mode = 6;

					if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)){
						if(Physics.distance(Mouse.getX(), Mouse.getY(), 155, 720 - 155) < 60) mode = 1;
						if(Physics.distance(Mouse.getX(), Mouse.getY(), 155, 720 - 365) < 60) mode = 2;
						if(Physics.distance(Mouse.getX(), Mouse.getY(), 155, 720 - 565) < 60) mode = 3;
						if(Physics.distance(Mouse.getX(), Mouse.getY(), 1120, 720 - 155) < 60) mode = 4;
						if(Physics.distance(Mouse.getX(), Mouse.getY(), 1120, 720 - 365) < 60) mode = 5;
						if(Physics.distance(Mouse.getX(), Mouse.getY(), 1120, 720 - 565) < 60) mode = 6;
						if(Physics.distance(Mouse.getX(), Mouse.getY(), 1280, 720) < 100) Game.boardReset();
					}
				}
			}

			if(!count1.word.equals(c1 + "") || mode == 1) count1.fadeIn();
			if(!count2.word.equals(c2 + "") || mode == 2) count2.fadeIn();
			if(!count3.word.equals(c3 + "") || mode == 3) count3.fadeIn();
			if(!count4.word.equals(c4 + "") || mode == 4) count4.fadeIn();
			if(!count5.word.equals(c5 + "") || mode == 5) count5.fadeIn();
			if(!count6.word.equals(c6 + "") || mode == 6) count6.fadeIn();

			count1.word = c1 + "";
			count2.word = c2 + "";
			count3.word = c3 + "";
			count4.word = c4 + "";
			count5.word = c5 + "";
			count6.word = c6 + "";

			if(count1.transparency > 0.99f) count1.fadeOut();
			if(count2.transparency > 0.99f) count2.fadeOut();
			if(count3.transparency > 0.99f) count3.fadeOut();
			if(count4.transparency > 0.99f) count4.fadeOut();
			if(count5.transparency > 0.99f) count5.fadeOut();
			if(count6.transparency > 0.99f) count6.fadeOut();

			if(mode == 1){ moder.x = 50; moder.y = 150; }
			if(mode == 2){ moder.x = 50; moder.y = 360; }
			if(mode == 3){ moder.x = 50; moder.y = 560; }
			if(mode == 4){ moder.x = 1230; moder.y = 150; }
			if(mode == 5){ moder.x = 1230; moder.y = 360; }
			if(mode == 6){ moder.x = 1230; moder.y = 560; }

			if(!justReset){
				int global = Cell.cells.get(new Random().nextInt(Cell.cells.size() - 1)).team;
				boolean ended = true;

				for(Cell c : Cell.cells){
					for(int i = 0; i < 10; i++) c.update();
					if(c.team != global && c.team != 100) ended = false;
				}

				if(ended){
					if(global == 5) Game.resetting = 2; else Game.boardReset();
				}
			}else{
				justReset = false;
				Game.level1.fadeOut();
				Game.level2.fadeOut();
				Game.level3.fadeOut();
				Game.level4.fadeOut();
				Game.level5.fadeOut();
				Game.level6.fadeOut();
				mode = 1;
			}

			if(Game.selected.rewardMultiplier > 0) if(new Random().nextInt(Game.selected.rewardMultiplier) == 0) for(Cell c : Cell.cells) if(new Random().nextInt(Game.selected.rewardMultiplier) == 0) c.tickRewards(false);
			Game.update();
			for(Object o : Object.objects) o.draw();
			for(FadingWord f : FadingWord.fws) f.draw();
			for(Droplet d : Droplet.droplets){
				d.update();
				if(d.dead) toDestroy.add(d);
			}
			for(Droplet d : toDestroy){
				Droplet.droplets.remove(d);
				if(player.contains(d)) player.remove(d);
			}

			Display.update();
		}
		Display.destroy();
	}

	public static void initTextures(){
		try{
			white = TextureLoader.getTexture("PNG", new FileInputStream("res/White.png"));
			background = TextureLoader.getTexture("PNG", new FileInputStream("res/Background.png"));
			font = TextureLoader.getTexture("PNG", new FileInputStream("res/Font.png"));
		}catch(Exception e){}
	}

}
