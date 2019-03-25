public class driver {    
    public static void main(String[] args) {
        ArrayList<String> word = new ArrayList<>();
        
        WordSearchPuzzle driver = new WordSearchPuzzle(word);
        driver.generateWordSearchPuzzle();
        System.out.println("\n" + driver.getPuzzleAsString());
    }
}
