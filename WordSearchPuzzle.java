/**
 *
 * @author Pádraig
 */
import java.util.*;
import java.io.* ;

public class WordSearchPuzzle {
    ArrayList<String> words;
    char[][] grid;
    int dim;
    char BLANK_CHAR = '0';
    
    public WordSearchPuzzle(ArrayList<String> input) {
        Collections.sort(input, Comparator.comparing(String::length));
        Collections.reverse(input);
        words = input;
    }
    

    // Dermot sent us this
    public WordSearchPuzzle(String filename) {
        // BasicEnglish.txt - the 850 words of Basic English
        // BNCwords.txt - 5456 words
        try {
            FileReader aFileReader = new FileReader(filename);
            BufferedReader aBufferReader = new BufferedReader(aFileReader);
            String lineFromFile;
            ArrayList<String> words = new ArrayList<String>();
            lineFromFile = aBufferReader.readLine() ;
            while (lineFromFile != null) {  
                words.add(lineFromFile.toUpperCase());
                lineFromFile = aBufferReader.readLine() ;
            }
            aBufferReader.close();
            aFileReader.close();
        }
        catch(IOException x) {
            System.out.println("error: exception caught in filename read");
        }
    }
    
    // Returns the list of words to be used
    public List<String> getWordSearchList() {
        return words;
    }    
    
    // Calculates the dimensions of the wordsearch
    public int calcDim() {
        double length = 0;
        
        // Add all chars
        for (int i = 0; i < words.size(); i++) {
            length += words.get(i).length();
        }
        
        // Find root of (lentgh * 1.75) and round up (Math.ceil)
        dim = (int) Math.ceil(Math.sqrt(length * 1.75));
        return dim;
    }
    
    public char[][] getPuzzleAsGrid() {
        return grid;
    }
    
    
    
    public void generateWordSearchPuzzle() {
        calcDim();
        grid = new char[dim][dim];
        
        // Sets each value in grid to zero (BLANK_CHAR)
        for (int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                grid[i][j] = BLANK_CHAR;
            }
        }
    }
    
    /*
        Tests if the word will fit in word search at the current coordinates
    */
    public char testLength(int row, int col, String word) {
        char dir = 'n';
        for (int i = 0; i < 4; i++) {
            int orientation = (int)(Math.random() * 4);
            
            switch (orientation) {
                case 0: // left to right
                    if (col + word.length() - 1 <= grid.length-1) {
                        dir = 'r';
                    }
                    break;
                case 1: // right to left
                    if (col - word.length() + 1 >= 0) {
                        dir = 'l';
                    }
                    break;
                case 2: // up to down
                    if (row + word.length() - 1 <= grid.length-1) {
                        dir = 'd';
                    }
                    break;
                default: // down to up
                    if (row - word.length() + 1 >= 0) {
                        dir = 'u';
                    }
                    break;
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
                    grid[row][col+i] = word.charAt(i);
                }
                break;
            case 'l':
                for (int i = word.length()-1; i >= 0; i--) {
                    grid[row][col-i] = word.charAt(i);
                }
                break;
            case 'd':
                for (int i = 0; i < word.length(); i++) {
                    grid[row+i][col] = word.charAt(i);
                }
                break;
            default:
                for (int i = word.length()-1; i >= 0; i--) {
                    grid[row-i][col] = word.charAt(i);
                }
            
        }
    }
}
