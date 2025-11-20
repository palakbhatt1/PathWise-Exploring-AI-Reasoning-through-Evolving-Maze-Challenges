package ie.gmit.sw.gameassets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ie.gmit.sw.maze.Cell;

public class Player implements Sprite {

	private BufferedImage[] walkFrames = new BufferedImage[10];
	private BufferedImage[] weaponFrames = new BufferedImage[9];
	private static int mana = 0;
	private int health = 3;
	private static boolean weapon = false;
	private static Spell spell = null;
	private int currentFrame = 0;
	private int weaponFrame = 0;

	public Player(){
		try {
			// Load animated walk frames
			for (int i = 0; i < 10; i++) {
				String filename = "ninjagirlnew/png/Run__00" + i + ".png";
				walkFrames[i] = ImageIO.read(new File(filename));
			}
			// Load weapon frames
			for (int i = 1; i <= 9; i++) {
				String filename = "with_sword_frames/frame_" + i + ".png";
				weaponFrames[i-1] = ImageIO.read(new File(filename));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Cell move() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage getImage() {
		if(weapon){
			// Animate weapon frames
			weaponFrame = (weaponFrame + 1) % 9;
			return weaponFrames[weaponFrame];
		}
		else {
			// Animate by cycling through normal frames
			currentFrame = (currentFrame + 1) % 10;
			return walkFrames[currentFrame];
		}
	}
	@Override
	public void setImage(BufferedImage image) {
		// Set initial frame or for other purposes
		walkFrames[0] = image;
	}
	
	public static void incrementMana(){
		mana += 20;
	}
	
	public static void decrementMana(){
		mana = Math.max(mana - 20, 0);
	}
	public static int getMana() {
		return mana;
	}
	
	public static void setMana(int man) {
		mana = man;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}

	public static void setWeapon(boolean hasweapon){
		weapon = hasweapon;
	}
	
	public static boolean getWeapon(){
		return weapon;
	}
	public static Spell getSpell() {
		return spell;
	}
	public static void setSpell(Spell spel) {
		spell = spel;
	}

}
