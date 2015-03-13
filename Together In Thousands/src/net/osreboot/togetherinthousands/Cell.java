package net.osreboot.togetherinthousands;

import java.util.ArrayList;
import java.util.Random;

public class Cell {

	public static ArrayList<Cell> cells = new ArrayList<Cell>();

	protected float resistance, speed, zap;

	protected float health, retainr, retaing, retainb;

	protected Object o;

	protected int team;

	protected ArrayList<Cell> neighbors;

	public Cell(float x, float y, float r, float g, float b, int team, float health, float retain){
		this.resistance = r;
		this.speed = g;
		this.zap = b;
		this.team = team;
		this.health = health;
		this.retainr = retain;
		this.retaing = retain;
		this.retainb = retain;
		this.o = new Object(x, y, Physics.spacing + 6, Physics.spacing + 6, r, g, b);
		this.neighbors = new ArrayList<Cell>();
		cells.add(this);
	}

	public void calculateNeighbors(){
		this.neighbors.clear();
		this.neighbors = new ArrayList<Cell>();
		if(this.getDirection("up") != null) this.neighbors.add(this.getDirection("up"));
		if(this.getDirection("left") != null) this.neighbors.add(this.getDirection("left"));
		if(this.getDirection("right") != null) this.neighbors.add(this.getDirection("right"));
		if(this.getDirection("down") != null) this.neighbors.add(this.getDirection("down"));
	}

	public Cell getDirection(String direction){
		for(Cell c : cells){
			if(direction == "up") if(c.o.x == this.o.x && c.o.y == this.o.y + Physics.spacing) return c;
			if(direction == "left") if(c.o.x == this.o.x - Physics.spacing && c.o.y == this.o.y) return c;
			if(direction == "right") if(c.o.x == this.o.x  + Physics.spacing && c.o.y == this.o.y) return c;
			if(direction == "down") if(c.o.x == this.o.x && c.o.y == this.o.y - Physics.spacing) return c;
		}
		return null;
	}

	private float tempr, tempg, tempb;

	public void update(){
		if(this.team != 5){
			if(this.retainr < 1) this.retainr = 1; 
			if(this.retaing < 1) this.retaing = 1; 
			if(this.retainb < 1) this.retainb = 1; 
			if(this.retainr > Physics.maxRetain) this.retainr = Physics.maxRetain;
			if(this.retaing > Physics.maxRetain) this.retaing = Physics.maxRetain;
			if(this.retainb > Physics.maxRetain) this.retainb = Physics.maxRetain;
			if(this.health < 0 && this.team != 0){
				this.team = 0;
				this.health = Physics.neutralHealth;
			}
			if(this.health < Physics.maxHealth && this.team != 0) this.health += 0.001f;

			if(this.neighbors.size() > 0 && this.team != 0){
				Cell c = this.neighbors.get(new Random().nextInt(this.neighbors.size()));
				if(this.team == c.team){
					if(c.resistance <= this.resistance) if(c.resistance < Physics.maxCapacity && this.resistance >= Physics.transferRate && new Random().nextInt((int)this.retainr) == 0){ c.resistance += Physics.transferRate; this.resistance -= Physics.transferRate; }
					if(c.speed <= this.speed) if(c.speed < Physics.maxCapacity && this.speed >= Physics.transferRate && new Random().nextInt((int)this.retaing) == 0){ c.speed += Physics.transferRate; this.speed -= Physics.transferRate; }
					if(c.zap <= this.zap) if(c.zap < Physics.maxCapacity && this.zap >= Physics.transferRate && new Random().nextInt((int)this.retainb) == 0){ c.zap += Physics.transferRate; this.zap -= Physics.transferRate; }
				}else{
					if((float)new Random().nextInt((int)Physics.maxSpeedUse)/100 < this.speed){
						if(this.speed > (c.resistance/2)) c.health -= this.speed - (c.resistance/2); else c.health -= 0.1f;
						if(c.health < 0){
							c.team = this.team;
							c.health = Physics.reviveHealth;
							tickRewards(true);
						}
					}
					if(new Random().nextInt((int)(Physics.zapChance))/100 < this.zap){
						c.health *= 0.98;
					}
				}
				if(c.team != this.team){
					this.retainr += 0.01f;
					this.retaing += 0.01f;
					this.retainb += 0.01f;
				}else{
					this.retainr -= 0.002f;
					this.retaing -= 0.002f;
					this.retainb -= 0.002f;
				}
			}

			this.tempr = this.resistance/2;
			this.tempg = this.speed/2;
			this.tempb = this.zap/2;

			if(this.resistance > 1) this.tempr = 0.5f;
			if(this.speed > 1) this.tempg = 0.5f;
			if(this.zap > 1) this.tempb = 0.5f;

			this.o.r = 1 - (this.tempg) - (this.tempb);
			this.o.g = 1 - (this.tempr) - (this.tempb);
			this.o.b = 1 - (this.tempg) - (this.tempr);

			if(this.team != 100){
				this.o.r *= 0.6;
				this.o.g *= 0.6;
				this.o.b *= 0.6;
				this.o.r += (float)this.team/10;
				this.o.g += (float)this.team/10;
				this.o.b += (float)this.team/10;
			}
			
		}else{
			if(Game.resetting == 2){
				this.team = 0;
				this.health = Physics.neutralHealth;
				this.resistance = 0.1f;
				this.speed = 0.1f;
				this.zap = 0.1f;
				this.retainr = 50.0f;
				this.retaing = 50.0f;
				this.retainb = 50.0f;
			}else{
				for(Cell c : this.neighbors) if(new Random().nextInt(50) == 0){
					c.team = 5;
				}
			}
			this.o.r = 1;
			this.o.g = 1;
			this.o.b = 1;
		}
	}
	
	public void tickRewards(boolean enemy){
		if(this.team == 1){
			if(new Random().nextInt(400 + Game.selected.playerr) == 0) Main.c1++;
			if(new Random().nextInt(400 + Game.selected.playerg) == 0) Main.c2++;
			if(new Random().nextInt(400 + Game.selected.playerb) == 0) Main.c3++;
			if(new Random().nextInt(300 + Game.selected.playerd) == 0) Main.c4++;
			if(new Random().nextInt(3000 + Game.selected.playerdea) == 0) Main.c5++;
			if(new Random().nextInt(300 + Game.selected.playerre) == 0) Main.c6++;
		}else if(enemy){
			if(new Random().nextInt(800 + Game.selected.enemySpeed) == 0) new Droplet(this, 200, 0, 0);
			if(new Random().nextInt(800 + Game.selected.enemySpeed) == 0) new Droplet(this, 0, 20, 0);
			if(new Random().nextInt(800 + Game.selected.enemySpeed) == 0) new Droplet(this, 0, 0, 100);
			if(new Random().nextInt(8000 + Game.selected.enemySpeed) == 0) new Droplet(this, 200, 50, 200);
		}
	}
	
	public void matchStats(FalseCell c){
		this.resistance = c.resistance;
		this.speed = c.speed;
		this.zap = c.zap;
		this.retainr = c.retainr;
		this.retaing = c.retaing;
		this.retainb = c.retainb;
		this.team = c.team;
	}

}
