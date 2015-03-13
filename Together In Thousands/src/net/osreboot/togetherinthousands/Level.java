package net.osreboot.togetherinthousands;

import java.util.ArrayList;

public class Level {

	protected int enemySpeed, playerr, playerg, playerb, playerd, playerre, playerdea;
	protected float reff = 200, geff = 30, beff = 100, breff = 200, bgeff = 50, bbeff = 200;
	protected FalseCell neutral;
	protected ArrayList<FalseCell> layout = new ArrayList<FalseCell>();
	protected int rewardMultiplier = 0;

	public Level(ArrayList<FalseCell> layout){
		this.layout = layout;
	}

	public void apply(){
		Game.youwin.fadeOut();
		Game.title1.fadeOut();
		Game.title2.fadeOut();
		Game.title3.fadeOut();
		
		
		for(int x = 0; x < 1280/Physics.spacing; x++){
			for(int y = 0; y < 720/Physics.spacing; y++){
				if(Physics.distance(x * Physics.spacing, y * Physics.spacing, 640, 360) < 340){
					Cell c = Physics.getNearestCell(x * Physics.spacing, y * Physics.spacing);
					c.matchStats(this.neutral);
					for(FalseCell c2 : this.layout) if(c2.x == c.o.x && c2.y == c.o.y) c.matchStats(c2);
				}
			}
		}
		Main.justReset = true;
		Game.resetting = 0;
		Main.reff = this.reff;
		Main.geff = this.geff;
		Main.beff = this.beff;
		Main.breff = this.breff;
		Main.bgeff = this.bgeff;
		Main.bbeff = this.bbeff;
	}

}
