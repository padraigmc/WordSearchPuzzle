/**
 * @author Pádraig McCarthy
 * @author Bandile Tshabalala
 * @author Aaron Foster
 */
import java.util.function.UnaryOperator;
import java.util.*;
import java.io.* ;

public class WordSearchPuzzle {
    ArrayList<String> words, answers;
    char[][] grid;
    int dim;
    char BLANK_CHAR = '0';
    
    
    /**
     * Constructor method. If the ArrayList contains words it will sort them from longest to shortest. Otherwise it will
     * be populated with default words.
     * 
     * @param input An ArrayList which contains the words to be used in the wordsearch.
     * @see <code>sortLongestFirst</code>
     * @see <code>addWords</code>
     */
    public WordSearchPuzzle(ArrayList<String> input) {
        if (input.size() <= 0) {
            System.out.println("No words found...\nPopulating list now...");
            addWords(input);
        }

        // changes all words to upperccase in the user inputted list
        ArrayListToUpperCase(input);
        sortLongestFirst(input);
        words = sortLongestFirst(input);
    }

    /**
     * Constructor method. Reads a file (one word per line) and outputs each line that is longer and shorter than the
     * shortest and longest params to an ArrayList. Words are then removed to set length to wordCount and sorted from 
     * longest to shortest.
     * 
     * @param filename  Name of file in the same directory as src files. This should be like "*filename*.txt"
     * @param wordCount Number of words to be taken from the .txt
     * @param shortest  The length of the shortest word to be used
     * @param longest   The length of the longest word to be used
     * @see <code>reduceToWordCount</code>
     * @see <code>sortLongestFirst</code>
     * @see <code>addWords</code>
     */
    public WordSearchPuzzle(String filename, int wordCount, int shortest, int longest) {
        ArrayList<String> wordsFromFile = new ArrayList<String>();;
        try {
            FileReader aFileReader = new FileReader(filename);
            BufferedReader aBufferReader = new BufferedReader(aFileReader);
            String lineFromFile;
            //wordsFromFile = new ArrayList<String>();
            lineFromFile = aBufferReader.readLine() ;
            while (lineFromFile != null) {  
                if (shortest <= lineFromFile.length() && lineFromFile.length() <= longest) {
                    wordsFromFile.add(lineFromFile.toUpperCase());
                }
                lineFromFile = aBufferReader.readLine() ;
            }
            aBufferReader.close();
            aFileReader.close();

            
            if (wordsFromFile.size() > 0) {
                wordsFromFile = reduceToWordCount(wordsFromFile, wordCount);
            } else {    // if the user supplies a .txt with no words in it, words are added to the list automatically
                System.out.println("Empty file given...");
                System.out.println("Adding words now...");
                addWords(wordsFromFile);
            }
            
            words = sortLongestFirst(wordsFromFile);
        }
        catch(IOException x) {
            System.out.println("Failed to read file, populating wordlist now...");
            addWords(words);
        }
    }
    
    /**
     * 
     * @return Arraylist of words to be used in the wordsearch.
     */
    public ArrayList<String> getWordSearchList() {
        return words;
    }    
    
    /**
     * 
     * @return  2D char array used as the wordsearch.
     */
    public char[][] getPuzzleAsGrid() {
        return grid;
    }

    /**
     * 
     * @return The wordsearch as a string.
     */
    public String getPuzzleAsString() {
        String ws = "";
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                ws = ws + grid[i][j] + " ";
            }
            ws = ws + "\n";
        }
        return ws;
    }

    /**
     * 
     * @return  Arraylist containing the answers for the puzzle
     */
    public String getAnswersAsString() {
        String ans = "";
        for (int i = 0; i < answers.size(); i++) {
            ans = ans + answers.get(i) + "\n";
        }
        
        return ans;
    }

    /**
     * Prints the word search to the screen.
     * 
     * @param hide  If false, the puzzle and the answers will be printed. If true, the answers will be hidden.
     * @see <code>getPuzzleAsString</code>
     */
    public void showWordSearchPuzzle(boolean hide) {
        System.out.println(getPuzzleAsString());
        if (!hide) {
            for (int i = 0; i < answers.size(); i++) {
                System.out.println(answers.get(i));
            }
        }
    }

    /**
     * Randomly remove words from the arraylist to reduce its length to the wordcount. Adds words if length of original
     * list is shorter than the wordcount.
     * 
     * @param original  Arraylist to be reduced in size
     * @param newLength The arraylist's new length after execution
     * @return          The new, saller arraylist       
     */
    public ArrayList<String> reduceToWordCount(ArrayList<String> original, int newLength) {
        while (original.size() > newLength) {
            int random = (int) (Math.random() * original.size());
            original.remove(random);
        }
        return original;
    }

    /**
     * Converts all Strings in the supplied ArrayList to uppercase.
     * 
     * @param input String ArrayList to be converted
     * @return      ArrayList in uppercase
     */
    public ArrayList<String> ArrayListToUpperCase(ArrayList<String> input) {
        UnaryOperator<String> uo = (x) -> x.toUpperCase();
        input.replaceAll(uo);
        return input;
    }
   

    /**
     * Sorts list from longest to shortest
     * 
     * @param original  String ArrayList to be sorted
     * @return          Sorted ArrayList
     */
    public ArrayList<String> sortLongestFirst(ArrayList<String> original) {
        Collections.sort(original, Comparator.comparing(String::length));
        Collections.reverse(original);
        return original;
    }
    
    /**
     * Sets the int variable dim by summing the characters of the words, multiplying by 1.75. This is then 
     * square rooted and rounded up.
    */ 
    public void calcDim() {
        double charCount = 0;
        // find char count
        for (int i = 0; i < words.size(); i++) {
            charCount += words.get(i).length();
        }
        // Find root of (length * 1.75) and round up (Math.ceil)
        dim = (int) Math.ceil(Math.sqrt(charCount * 1.75));
    }

    /**
     * Tests if the word can be placed in the grid at the supplied coordinates given an orientation.
     * 
     * @param row           Row in grid to be tested.
     * @param col           Column in grid to be tested.
     * @param word          Word to be tested.
     * @param orientation   Orientation of the word.
     * @return Char signifying the orientation ('r' for left to right, 'l' for right to left, 'd' for bottom to top and 'u' for bottom to top)
     * @see <code>testClash</code>
     */
    public char testLength(int row, int col, String word, int orientation) {
        if (word.length() <= dim) {

			switch (orientation) {
				case 0: // left to right
					if (col + word.length() <= grid.length && testClash(row, col, 'r', word) == true) {
						return 'r';
					} else {
						break;
					}
				case 1: // right to left
					if (col - word.length() + 1 >= 0 && testClash(row, col, 'l', word) == true) {
						return 'l';
					} else {
						break;
					}
				case 2: // up to down
					if (row + word.length() <= grid.length && testClash(row, col, 'd', word) == true) {
						return 'd';
					} else {
						break;
					}
				default: // down to up
					if (row - word.length() + 1 >= 0  && testClash(row, col, 'u', word) == true) {
						return 'u';
					} else {
						break;
					}
			}
		}     
		// if test fails, 'n' returned
        return 'n';
    }
    
    /**
     * Tests if the word will clash with another word placed in a given orientation. Also returns true if overlapping of words can occur.
     * 
     * @param row           The row of the first char
     * @param col           The column of the first char
     * @param orientation   The direction of the word being tested
     * @param word          The word to be tested
     * @return True if word is placeable otherwise false.
     * @see <code>testLength</code>
     */
    public boolean testClash(int row, int col, char orientation, String word) {
        boolean noClash = true;         // presume word does NOT clash
        
        switch(orientation) {
            case 'r': // left to right
                for (int i = 0; i < word.length(); i++) {
                    if (grid[row][col+i] != BLANK_CHAR && grid[row][col+i] != word.charAt(i)) {
                        noClash = false;
                        break;
                    }
                }
                break;
            case 'l': // right to left (backwards)
                for (int i = word.length()-1; i >= 0; i--) {
                    if (grid[row][col-i] != BLANK_CHAR && grid[row][col-i] != word.charAt(i)) {
                        noClash = false;
                        break;
                    }
                }
                break;
            case 'd': // downwards
                for (int i = 0; i < word.length(); i++) {
                    if (grid[row+i][col] != BLANK_CHAR && grid[row+i][col] != word.charAt(i)) {
                        noClash = false;
                        break;
                    }
                }
                break;
            default: // upwards
                for (int i = word.length()-1; i >= 0; i--) {
                    if (grid[row-i][col] != BLANK_CHAR && grid[row-i][col] != word.charAt(i)) {
                        noClash = false;
                        break;
                    }
                }
          }
        return noClash;
    }
    
    /**
     * Populates unused positions in the wordsearch grid with random characters
     */
    public void fillUnused() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (grid[i][j] == BLANK_CHAR) {
                    // Generates random char from A - Z using the ASCII representation of chars.
                    grid[i][j] = (char) (Math.random() * 26 + 'A');
                }
            }
            
        }
    }
    
    /**
     * Writes the word to the wordsearch grid
     * 
     * @param row           The row of the first char to be written
     * @param col           The column of the first char to be written
     * @param orientation   The direction in which to write the word
     * @param word          The word to be written
     * @see <code>testLength</code>
     * @see <code>testClash</code>
     */
    public void placeWord(int row, int col, char orientation, String word) {
        switch(orientation) {
            case 'r':
                for (int i = 0; i < word.length(); i++) {
                    char ch = word.charAt(i);
                    grid[row][col+i] = Character.toUpperCase(ch);
                }
                break;
            case 'l':
                for (int i = word.length()-1; i >= 0; i--) {
                    char ch = word.charAt(i);
                    grid[row][col-i] = Character.toUpperCase(ch);
                }
                break;
            case 'd':
                for (int i = 0; i < word.length(); i++) {
                    char ch = word.charAt(i);
                    grid[row+i][col] = Character.toUpperCase(ch);
                }
                break;
            default:
                for (int i = word.length()-1; i >= 0; i--) {
                    char ch = word.charAt(i);
                    grid[row-i][col] = Character.toUpperCase(ch);
                }
            
        }
    }
    
    /**
     * Method randomly selects an orientation and tests the word placement for many coordinates with that orientation.
     * If the word can be placed, it is written into the puzzle.
     * 
     * @param word  Word to be tested and placed.
     * @return      Boolean value whether the word placement was successfull or not
     * @see <code>testLength</code>
     * @see <code>placeWord</code>
     */
    public boolean getCoord(String word) {
        ArrayList<Integer> direction = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(direction);

		for (int i = 0; i < 4; i++) {
            int orientation = direction.get(i);
            
            for (int j = 0; j < dim*dim; j++) {
                
                // generates new coordinates & tests a placement
				int row = (int) (Math.random() * dim);
                int col = (int) (Math.random() * dim);
                
				char test = testLength(row, col, word, orientation);

				if (test != 'n') {
                    placeWord(row, col, test, word);
                    // adds word to the answer list
                    answers.add(word.toUpperCase() + "[" + row + "][" + col + "]" + Character.toUpperCase(test));
                    return true;
                }
			}
		}
		return false;
	} 
    
    /**
     * Calculates the dimensions of the grid and creates it, places each word into the grid and populates remaining spaces with random chars
     * 
     * @see <code>calcDim</code>
     * @see <code>testLength</code>
     * @see <code>fillUnused</code>
     */
    public void generateWordSearchPuzzle() {
        calcDim();

		// creates new grid
		for (int i = 0; i < 100; i++) {
            grid = new char[dim][dim];
            answers = new ArrayList<String>();
            
            // Sets each value in grid to zero (BLANK_CHAR)
            for (int j = 0; j < dim; j++) {
                for(int k = 0; k < dim; k++) {
                    grid[j][k] = BLANK_CHAR;
                }
            }
			
			// move to next word
            for (int l = 0; l < words.size(); l++) {
				String wordToBePlaced = words.get(l);
				
				// loop through orientations
				if (!getCoord(wordToBePlaced)) {
                    break;
                }
            }
            
            if (answers.size() == words.size()) {
				break;
			}
        }
        fillUnused();
    }

    /**
     * Adds words to a supplied arraylist.
     * @param input The arraylist to be populated.
     * @see <code>sortLongestFirst</code>
     */
    public void addWords(ArrayList<String> input) {
        input.add("COMPILE");
        input.add("ANDROID");
        input.add("BOOLEAN");
        input.add("STACK");
        input.add("BINARY");
        input.add("PYTHON");
        sortLongestFirst(input);
    }
}
