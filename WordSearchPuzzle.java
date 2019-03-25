/**
 *
 * @author Pádraig
 * @author Bandi
 * @author Aaron
 */

import java.util.*;
import java.io.* ;

public class WordSearchPuzzle {
    ArrayList<String> words;
    char[][] grid;
    int dim;
    char BLANK_CHAR = '0';
    
    public WordSearchPuzzle(ArrayList<String> input) {
        if (input.size() < 0) {
            // Sorts list from longest to shortest
            Collections.sort(input, Comparator.comparing(String::length));
            Collections.reverse(input);
            words = input;
        } else {
            System.out.println("No words found...\nPopulating list now...");
            addWords(input);
            words = input;
        }
    }

    // Dermot sent us this
    public WordSearchPuzzle(String wordFile, int wordCount, int shortest, int longest) {
        // BasicEnglish.txt - the 850 words of Basic English
        // BNCwords.txt - 5456 words
        ArrayList<String> temp = new ArrayList<String>();
        try {
            FileReader aFileReader = new FileReader(wordFile);
            BufferedReader aBufferReader = new BufferedReader(aFileReader);
            String lineFromFile;
            lineFromFile = aBufferReader.readLine() ;
            while (lineFromFile != null) {  
                temp.add(lineFromFile.toUpperCase());
                lineFromFile = aBufferReader.readLine() ;
            }
            aBufferReader.close();
            aFileReader.close();

            while (words.size() < wordCount) {
                int rand = (int) (Math.random() * temp.size());
                String wordInFile = temp.get(rand);
                if (wordFile.length() >= shortest && wordInFile.length() <= longest) {
                    words.add(wordInFile);
                }
            }
        }
        catch(IOException x) {
            System.out.println("error: exception caught in filename read");
        }



    }
    
    // Returns the list of words to be used
    public List<String> getWordSearchList() {
        return words;
    }    
    
    // Returns the word search as an array
    public char[][] getPuzzleAsGrid() {
        return grid;
    }

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
    
    // Calculates the dimensions of the wordsearch
    public int calcDim() {
        double charCount = 0;
        // find char count
        for (int i = 0; i < words.size(); i++) {
            charCount += words.get(i).length();
        }
        // Find root of (lentgh * 1.75) and round up (Math.ceil)
        dim = (int) Math.ceil(Math.sqrt(charCount * 1.75));
        return dim;
    }

    //   Tests if the word will fit in word search at the current coordinates
    public char testLength(int row, int col, String word) {
        char dir = 'n';
        for (int i = 0; i < 4; i++) {
            int orientation = (int) (Math.random() * 4);
            
            switch (orientation) {
                case 0: // left to right
                    if (col + word.length() - 1 <= grid.length-1 && testClash(row, col, 'r', word) == true) {
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
                    if (row + word.length() - 1 <= grid.length-1 && testClash(row, col, 'd', word) == true) {
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
        return dir;
    }
    
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
    
    public void placeWord(int row, int col, char orientation,String word) {
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

    // Populates puzzle with random chars
    public void fillUnused() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (grid[i][j] == BLANK_CHAR) {
                    // Generates random number from 65 - 90 and casts to char (ASCII table A-Z)
                    grid[i][j] = (char) (Math.random() * 25 + 66);
                }
            }
            
        }
    }
    
    public void generateWordSearchPuzzle() {
        calcDim();
        for (int i = 0; i < 100; i++) {
            grid = new char[dim][dim];
            
            // Sets each value in grid to zero (BLANK_CHAR)
            for (int j = 0; j < dim; j++) {
                for(int k = 0; k < dim; k++) {
                    grid[j][k] = BLANK_CHAR;
                }
            }
            for (int l = 0; l < words.size(); l++) {
                String wordToBePlaced = words.get(l);
                for (int m = 0; m < dim*dim; m++) {
                    
                    // Random coordinates
                    int row = (int) (Math.random() * dim);
                    int col = (int) (Math.random() * dim);
                    
                    // Test fit and clash
                    char test = testLength(row, col, wordToBePlaced);
                    if (test != 'n') {
                        placeWord(row, col, test, wordToBePlaced);
                        break;
                    }
                }
            }
        }
        fillUnused();
    }

    // Adds words to list if an empty one was supplied
    public void addWords(ArrayList input) {
        input.add("hello");
        input.add("boolean");
        input.add("binary");
        input.add("python");
        input.add("stack");
        input.add("java");
    }
}
