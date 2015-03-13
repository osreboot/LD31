package net.osreboot.togetherinthousands;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class Font {

	public static ArrayList<Character> chars = new ArrayList<Character>();

	public static void drawWord(String word, float transparency, float x, float y, float color){
		int count = -1;
		for(Character c : word.toCharArray()){
			count++;
			if(chars.contains(c)){
				int pos = chars.indexOf(c);
				GL11.glColor4f(color, color, color, transparency);
				Main.font.bind();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f((40/2048f)*pos, 0);
				GL11.glVertex2f(x + (count*40), y);
				GL11.glTexCoord2f((40/2048f)*pos + (40/2048f), 0);
				GL11.glVertex2f(x + 40 + (count*40), y);
				GL11.glTexCoord2f((40/2048f)*pos + (40/2048f), 50/2048f);
				GL11.glVertex2f(x + 40 + (count*40), y + 50);
				GL11.glTexCoord2f((40/2048f)*pos, 50/2048f);
				GL11.glVertex2f(x + (count*40), y + 50);
				GL11.glEnd();
			}
		}
	}

	public static void init(){
		chars.add('a');
		chars.add('b');
		chars.add('c');
		chars.add('d');
		chars.add('e');
		chars.add('f');
		chars.add('g');
		chars.add('h');
		chars.add('i');
		chars.add('j');
		chars.add('k');
		chars.add('l');
		chars.add('m');
		chars.add('n');
		chars.add('o');
		chars.add('p');
		chars.add('q');
		chars.add('r');
		chars.add('s');
		chars.add('t');
		chars.add('u');
		chars.add('v');
		chars.add('w');
		chars.add('x');
		chars.add('y');
		chars.add('z');
		chars.add('1');
		chars.add('2');
		chars.add('3');
		chars.add('4');
		chars.add('5');
		chars.add('6');
		chars.add('7');
		chars.add('8');
		chars.add('9');
		chars.add('0');
	}

}
