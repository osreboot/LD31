package net.osreboot.togetherinthousands;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

public class Game {

	public static int resetting = 0;

	public static FadingWord level1, level2, level3, level4, level5, level6, youwin, title1, title2, title3;

	public static Level one, two, three, four, five, six, selected;
	
	public static void init(){
		level1 = new FadingWord(250, 130, "start", 1);
		level2 = new FadingWord(250, 340, "duel", 1);
		level3 = new FadingWord(250, 540, "quad", 1);
		level4 = new FadingWord(880, 130, "null", 1);
		level5 = new FadingWord(880, 340, "null", 1);
		level6 = new FadingWord(850, 540, "close", 1);
		youwin = new FadingWord(410, 430, "level select", 1);
		title1 = new FadingWord(490, 250, "together", 0);
		title2 = new FadingWord(610, 310, "in", 0);
		title3 = new FadingWord(470, 370, "thousands", 0);
		
		ArrayList<FalseCell> lv1 = new ArrayList<FalseCell>();
		lv1.add(new FalseCell(720, 360, 100f, 1.0f, 100f, 1, 50, 50f));
		lv1.add(new FalseCell(540, 360, 0.0001f, 0.0001f, 0.0001f, -1, 50, 50f));
		
		one = new Level(lv1);
		one.neutral = new FalseCell(0, 0, 0.1f, 0.0001f, 0.001f, 0, 100, 50f);
		one.enemySpeed = 1000000;
		one.geff = 60;
		one.beff = 400;
		one.reff = 400;
		one.rewardMultiplier = 200;
		
		ArrayList<FalseCell> lv2 = new ArrayList<FalseCell>();
		lv2.add(new FalseCell(720, 360, 100f, 1.0f, 100f, 1, 50, 50f));
		lv2.add(new FalseCell(540, 360, 100f, 1.0f, 100f, 2, 50, 50f));
		
		two = new Level(lv2);
		two.neutral = new FalseCell(0, 0, 0.1f, 0.0001f, 0.001f, 0, 100, 50f);
		two.enemySpeed = 1000;
		two.geff = 60;
		two.beff = 400;
		two.reff = 400;
		two.rewardMultiplier = 200;
		
		ArrayList<FalseCell> lv3 = new ArrayList<FalseCell>();
		lv3.add(new FalseCell(720, 360, 100f, 1.0f, 100f, 1, 50, 50f));
		lv3.add(new FalseCell(540, 360, 100f, 1.0f, 100f, 2, 50, 50f));
		lv3.add(new FalseCell(620, 280, 100f, 1.0f, 100f, 3, 50, 50f));
		lv3.add(new FalseCell(620, 440, 100f, 1.0f, 100f, -1, 50, 50f));
		
		three = new Level(lv3);
		three.neutral = new FalseCell(0, 0, 0.1f, 0.0001f, 0.001f, 0, 100, 50f);
		three.enemySpeed = 10000;
		three.geff = 30;
		three.beff = 200;
		three.reff = 200;
		three.rewardMultiplier = 300;
		
		ArrayList<FalseCell> lv6 = new ArrayList<FalseCell>();
		lv6.add(new FalseCell(640, 360, 10f, 10f, 10f, 1, 50, 50f));
		lv6.add(new FalseCell(540, 360, 10f, 10f, 10f, -1, 50, 50f));
		lv6.add(new FalseCell(740, 360, 10f, 10f, 10f, 2, 50, 50f));
		
		six = new Level(lv6);
		six.neutral = new FalseCell(0, 0, 0.5f, 0.5f, 0.5f, 0, 100, 50f);
		six.enemySpeed = 1000;
		six.playerre = 1000;
		six.playerd = 100;
		six.playerr = 200;
		six.playerg = 200;
		six.playerb = 200;
		
		selected = one;
	}

	public static void update(){
		if(resetting == 3){
			if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)){
				if(Physics.distance(Mouse.getX(), Mouse.getY(), 155, 720 - 155) < 60) one.apply();
				if(Physics.distance(Mouse.getX(), Mouse.getY(), 155, 720 - 365) < 60) two.apply();
				if(Physics.distance(Mouse.getX(), Mouse.getY(), 155, 720 - 565) < 60) three.apply();
				//if(Physics.distance(Mouse.getX(), Mouse.getY(), 1120, 720 - 155) < 60) mode = 4;
				//if(Physics.distance(Mouse.getX(), Mouse.getY(), 1120, 720 - 365) < 60) mode = 5;
				if(Physics.distance(Mouse.getX(), Mouse.getY(), 1120, 720 - 565) < 60) six.apply();
			}

			if(Physics.distance(Mouse.getX(), Mouse.getY(), 155, 720 - 155) < 60) level1.fadeIn(); else level1.fadeOut();
			if(Physics.distance(Mouse.getX(), Mouse.getY(), 155, 720 - 365) < 60) level2.fadeIn(); else level2.fadeOut();
			if(Physics.distance(Mouse.getX(), Mouse.getY(), 155, 720 - 565) < 60) level3.fadeIn(); else level3.fadeOut();
			if(Physics.distance(Mouse.getX(), Mouse.getY(), 1120, 720 - 155) < 60) level4.fadeIn(); else level4.fadeOut();
			if(Physics.distance(Mouse.getX(), Mouse.getY(), 1120, 720 - 365) < 60) level5.fadeIn(); else level5.fadeOut();
			if(Physics.distance(Mouse.getX(), Mouse.getY(), 1120, 720 - 565) < 60) level6.fadeIn(); else level6.fadeOut();
		}
	}

	public static void boardReset(){
		if(resetting == 0){
			resetting = 1;
			new Droplet(Physics.getNearestCell(640, 360), 0, 0, 0, 3);
		}else if(resetting == 2){
			Main.moder.x = -10;
			Main.moder.y = -10;
			youwin.fadeIn();
			title1.fadeIn();
			title2.fadeIn();
			title3.fadeIn();
			Main.c1 = 0;
			Main.c2 = 0;
			Main.c3 = 0;
			Main.c4 = 0;
			Main.c5 = 0;
			Main.c6 = 0;
			Main.mode = 0;
			resetting = 3;
			Main.reset.fadeOut();
		}
	}

}
