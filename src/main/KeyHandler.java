package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyHandler implements KeyListener {

	public HashMap<String, Boolean> keys = new HashMap<String, Boolean>();
	String charDown = "";

	String lastKeyDown;

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		charDown = String.valueOf((char) e.getKeyCode());

		checkChar(e.getKeyCode());

		keys.putIfAbsent(charDown, true);

		lastKeyDown = charDown;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

		charDown = String.valueOf((char) e.getKeyCode());

		keys.putIfAbsent(charDown, false);

		checkChar(e.getKeyCode());

		lastKeyDown = "";
		
	}

	private void checkChar(int keyCode) {

		if (keyCode == KeyEvent.VK_BACK_SPACE) {

			charDown = "delete";

		}

		if (keyCode == KeyEvent.VK_ENTER) {

			charDown = "enter";

		}

	}

	public boolean keyPressed(String ch) {

		keys.putIfAbsent(ch, false);

		return keys.get(ch);

	}

	public String keyDown() {

		if (lastKeyDown == "") {

			return null;

		} else {

			return lastKeyDown;

		}

	}

}
