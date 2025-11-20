package ie.gmit.sw.gameassets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ie.gmit.sw.ai.BestFirstMovementStrategy;
import ie.gmit.sw.ai.DepthFirstMovementStrategy;
import ie.gmit.sw.ai.MovementStrategy;
import ie.gmit.sw.ai.RandomMovementStrategy;
import ie.gmit.sw.ai.StrategyType;
import ie.gmit.sw.maze.Cell;

public class DeathEater extends Enemy {
	private MovementStrategy strategy;
	private BufferedImage[] runFrames = new BufferedImage[8];
	private int currentFrame = 0;

	public DeathEater(Cell initial, StrategyType strategytype) {
		if(strategytype == StrategyType.DEPTH_FIRST){
			strategy = new DepthFirstMovementStrategy(initial, this);
		}
		else if(strategytype == StrategyType.BEST_FIRST){
			strategy = new BestFirstMovementStrategy(initial, this);
		}else if(strategytype == StrategyType.RANDOM){
			strategy = new RandomMovementStrategy(initial, this);
		}

		this.setStrength(45);
		try {
			for (int i = 0; i < 8; i++) {
				String filename = "robotfree/png/Run (" + (i + 1) + ").png";
				runFrames[i] = ImageIO.read(new File(filename));
			}
			setImage(runFrames[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Cell move() {
		return this.strategy.move();
	}

	@Override
	public BufferedImage getImage() {
		currentFrame = (currentFrame + 1) % 8;
		return runFrames[currentFrame];
	}
	
	public void setImage(BufferedImage image){
		super.setImage(image);
	}
}
