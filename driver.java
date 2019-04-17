/**
 * @author Pádraig McCarthy
 * @author Bandile Tshabalala
 * @author Aaron Foster
 */

import java.util.*;

public class driver {    
    public static void main(String[] args) { 
        
        
        System.out.println("\n~ P U Z Z L E ~\nWords read from file.");
        System.out.println("Unused spaces filed and answers below.\n");
        
        WordSearchPuzzle fromText = new WordSearchPuzzle("wordFile.txt", 5, 6, 7);
        fromText.generateWordSearchPuzzle();
        fromText.showWordSearchPuzzle(false);


        System.out.println("\n~ P U Z Z L E ~");
        System.out.println("Unused spaces filed and answers hidden.\nThe words sorted in order of length:");
        
        ArrayList<String> food = new ArrayList<String>();

        food.add("beef");
        food.add("avocado");
        food.add("water");
        food.add("beans");
        food.add("wheat");
        food.add("flour");
        
        WordSearchPuzzle ws = new WordSearchPuzzle(food);
        ws.generateWordSearchPuzzle();
        
        String puzzle = ws.getPuzzleAsString();
        System.out.println("\n" + puzzle);

        for (int i = 0; i < ws.getWordSearchList().size(); i++) {
            System.out.println(ws.getWordSearchList().get(i));
        }
        
        
        System.out.println("\n~ P U Z Z L E ~");
        System.out.println("Empty list supplied by the user:");
        
        ArrayList<String> countries = new ArrayList<String>();
        WordSearchPuzzle ws1 = new WordSearchPuzzle(countries);
        ws1.generateWordSearchPuzzle();
        ws1.showWordSearchPuzzle(false);
    }
}
