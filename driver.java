import java.util.*;

public class driver {    
    public static void main(String[] args) { 
        
        WordSearchPuzzle driver = new WordSearchPuzzle("wordFile.txt", 5, 5, 6);
        driver.generateWordSearchPuzzle();
        System.out.println("\n~ P U Z Z L E ~");
        System.out.println("Unused spaces filed and answers below.\n");
        driver.showWordSearchPuzzle(false);


        ArrayList<String> food = new ArrayList<String>();

        food.add("beef");
        food.add("avocado");
        food.add("water");
        food.add("beans");
        food.add("wheat");
        food.add("flour");

        WordSearchPuzzle ws = new WordSearchPuzzle(food);
        ws.generateWordSearchPuzzle();
        System.out.println("\n~ P U Z Z L E ~");
        System.out.println("Unused spaces filed and answers hidden.\nThe words sorted in order of length:");
        for (int i = 0; i < ws.getWordSearchList().size(); i++) {
            System.out.println(ws.getWordSearchList().get(i));
        }
        String puzzle = ws.getPuzzleAsString();
        System.out.println("\n\n" + puzzle);

    }
}
