package main;

public class CombinationLock {

	String word;

	public CombinationLock(String word) {

		// Can be any length. If not, you can use the substring method like this: .subString(0, 3) to
		// insure that it is the right length.

		this.word = word.toLowerCase();

	}

	public String getClue(String guess) {

		guess = guess.toLowerCase().substring(0, Math.min(word.length(), guess.length()));
		
		// Could use .charAt() but that is bulkier
		char[] guessArr = guess.toCharArray();
		char[] wordArr = word.toCharArray();

		String result = "";

		
		// In case the guessed word is less than the word length
		int loopTime = word.length();

		if (guess.length() < word.length()) {

			loopTime = guess.length();

		}

		// Reads through every character
		for (int i = 0; i < loopTime; i++) {

			if (guessArr[i] == wordArr[i]) {

				// If the current character of the guess is the same as the current character of the word
				result += wordArr[i];

			} else if (word.contains(String.valueOf(guessArr[i]))) {

				// If the word contains the current character of the guess (String.valueOf() converts to string)
				result += "+";

			} else {

				// Not in word
				result += "*";

			}

		}

		return result;

	}

	public int length() {

		return word.length();

	}

}