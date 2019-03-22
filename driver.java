public class driver {    
    public static void main(String[] args) {
        WordSearchPuzzle driver;
        
        ArrayList<String> word = new ArrayList<>();
        word.add("hello");
        word.add("who");
        word.add("are");
        word.add("you?");
        
        driver = new WordSearchPuzzle(word);
        
        driver.generateWordSearchPuzzle();
        char[][] c = driver.getPuzzleAsGrid();
        for (int i = 0; i < driver.dim; i++) {
            for (int j = 0; j < driver.dim; j++) {
                System.out.print(c[i][j] + " ");
            }
            System.out.println();
        }
        
        driver.grid[2][2] = 'p';
        System.out.println(driver.testClash(0, 5, 'd', "hello"));
        driver.placeWord(0, 5, 'd', "hello");
        
        
        for (int i = 0; i < driver.dim; i++) {
            for (int j = 0; j < driver.dim; j++) {
                System.out.print(c[i][j] + " ");
            }
            System.out.println();
        }
    }
}
