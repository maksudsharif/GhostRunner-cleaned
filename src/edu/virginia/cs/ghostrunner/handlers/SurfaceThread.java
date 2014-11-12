package edu.virginia.cs.ghostrunner.handlers;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import edu.virginia.cs.ghostrunner.entities.Ghost;
import edu.virginia.cs.ghostrunner.entities.SmallGhostsItem;
import edu.virginia.cs.ghostrunner.views.GameView;

public class SurfaceThread extends Thread {
	private SurfaceHolder sh;
	private GameView gameView;
	private boolean running = false;
	private String difficulty;
	

	public SurfaceThread(SurfaceHolder sh, GameView gameView) {
		this.sh = sh;
		this.gameView = gameView;
		difficulty = gameView.getDifficulty();
		
	}

	public void setRunning(boolean b) {
		running = b;
	}

	@SuppressLint("WrongCall")
	public void run() {
		Canvas c;

		while (running) {
			c = null;
			try {
				c = sh.lockCanvas(null);
				synchronized (sh) {
					// update ghosts
					
						if (gameView.size() < (15 * gameView.getGhostspawnconstant())) {
							Log.d("getGhostspawnconstant" , ""  + gameView.getGhostspawnconstant());
							if (Math.random() > (.96 * gameView.getGhostfrequencyconstant()) && Math.random() > (.40 * gameView.getGhostfrequencyconstant())) {
								gameView.add(new Ghost(
									(float) (Math.random() * gameView
											.getWidthPixels()), 0, gameView

							));
							}
						}
						//Spawn Items
						if (gameView.getItems().size() < 3) {
							//if (Math.random() > .40 && Math.random() > .40) {
								gameView.add(new SmallGhostsItem ((float) Math.random() * gameView.getWidthPixels(), 0, gameView));
								Log.d("ItemAdded" , "SmallGhostsItem");
							//}
						}
				
				}
					// gameView.checkBounds(); //Call here or in View onDraw?
					gameView.onDraw(c);	
			}
			 finally {
				if (c != null) {
					sh.unlockCanvasAndPost(c);
				}

			}
		}
	}
}
