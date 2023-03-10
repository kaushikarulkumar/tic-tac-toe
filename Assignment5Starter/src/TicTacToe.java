import com.sun.security.jgss.GSSUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe
{
    // Static variables for the TicTacToe class, effectively configuration options
    // Use these instead of hard-coding ' ', 'X', and 'O' as symbols for the game
    // In other words, changing one of these variables should change the program
    // to use that new symbol instead without breaking anything
    // Do NOT add additional static variables to the class!
    static char emptySpaceSymbol = ' ';
    static char playerOneSymbol = 'X';
    static char playerTwoSymbol = 'O';

    public static void main(String[] args)
    {
        // TODO
        // This is where the main menu system of the program will be written.
        // Hint: Since many of the game runner methods take in an array of player names,
        // you'll probably need to collect names here.

        Scanner in = new Scanner(System.in);
        String playerOne = "";
        String playerTwo = "";
        String[] playerNames = new String[]{playerOne,playerTwo};
        ArrayList<char[][]> gameHistorySaveArea = new ArrayList<>();


        char mainMenuChoice = ' ';
        while( mainMenuChoice != 'Q')
        {

            System.out.println("Welcome to the game of Tic Tac Toe, choose one of the following options from below: ");
            System.out.println("1. Single player");
            System.out.println("2. Two player");
            System.out.println("D. Display last match");
            System.out.println("Q. Quit");
            System.out.print("What do you want to do: ");

            mainMenuChoice = in.next().charAt(0);



            if (mainMenuChoice == '2' )
            {
                System.out.println();
                System.out.println("Enter player 1 name: ");
                playerOne = in.next();
                System.out.println("Enter player 2 name: ");
                playerTwo = in.next();
                playerNames[0] = playerOne;
                playerNames[1] = playerTwo;


              gameHistorySaveArea =  runTwoPlayerGame(playerNames);
            }
            else if (mainMenuChoice == '1')
            {
                System.out.println(" ");
                System.out.println("Enter player 1 name: ");
                playerOne = in.next();
                playerNames[0] = playerOne;
                playerNames[1] = "Computer";
                System.out.println("Tossing a coin to decide who goes first!");
                Random chooseWhoIsFirst = new Random();
                int selectFirstChoice = chooseWhoIsFirst.nextInt(2);
                System.out.println(playerNames[selectFirstChoice] + " gets to go first!");

               gameHistorySaveArea = runOnePlayerGame(playerNames);

            }
            else if (mainMenuChoice == 'D')
            {
                if (gameHistorySaveArea.isEmpty())
                {
                    System.out.println("No prior game please try again");
                }
                else
                {
                    runGameHistory(playerNames, gameHistorySaveArea);
                }




            }
            else
            {
                System.out.println("Invalid input please try again");
            }
        }
        System.out.println("Thank you for playing, have a good day!");
    }

    // Given a state, return a String which is the textual representation of the tic-tac-toe board at that state.
    private static String displayGameFromState(char[][] state)
    {
        // TODO
        // Hint: Make use of the newline character \n to get everything into one String
        // It would be best practice to do this with a loop, but since we hardcode the game to only use 3x3 boards
        // it's fine to do this without one.

        String boardDisplay = "";

        for (int i = 0; i < getInitialGameState().length; i++)
        {
            for (int j = 0; j < getInitialGameState().length; j++)
            {

                if (j == getInitialGameState().length -1)
                {
                    boardDisplay = boardDisplay.concat(String.valueOf(state[i][j])) ;
                }
                else
                {

                    boardDisplay = boardDisplay.concat(state[i][j] + " | " );

                }
            }

            if (i == getInitialGameState().length -1)
            {
                boardDisplay =  boardDisplay.concat((""));
            }
            else
            {
                boardDisplay = boardDisplay.concat("\n" + "----------" + "\n");
            }

        }
        boardDisplay = boardDisplay.concat("\n");

        return boardDisplay;
    }

    // Returns the state of a game that has just started.
    // This method is implemented for you. You can use it as an example of how to utilize the static class variables.
    // As you can see, you can use it just like any other variable, since it is instantiated and given a value already.
    private static char[][] getInitialGameState()
    {
        return new char[][]{{emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol}};
    }

    // Given the player names, run the two-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runTwoPlayerGame(String[] playerNames) {
        // TODO

        Scanner in = new Scanner(System.in);

        char [][] state = new char[getInitialGameState().length] [getInitialGameState()[0].length];
        ArrayList<char[][]> gameHistory = new ArrayList<>();

        for (int i = 0; i < state.length; i++)
        {
            for (int j = 0; j < state[0].length; j++)
            {
                state[i] = Arrays.copyOf(getInitialGameState()[i], getInitialGameState().length);
            }
        }

        Random chooseWhoIsFirst = new Random();
        int selectFirstChoice = chooseWhoIsFirst.nextInt(2);
        System.out.println(playerNames[selectFirstChoice] + " gets to go first!");


        boolean gameOver = false;
//        if (selectFirstChoice == 1)
//        {
//            selectFirstChoice = 0;
//        }
//        else
//        {
//            selectFirstChoice = 1;
//        }
        System.out.println(displayGameFromState(state));
        gameHistory.add(state);


        while(!gameOver)
        {

            if (selectFirstChoice == 0)
            {
                state = runPlayerMove(playerNames[selectFirstChoice], playerOneSymbol, state);
                gameHistory.add(state);
                System.out.println(displayGameFromState(state));
                if(checkWin(state))
                {
                    System.out.println(playerNames[selectFirstChoice] + " Wins!");
                    gameOver = true;
                }
                else if (checkDraw(state))
                {
                    System.out.println("Game is a draw!");
                    gameOver = true;
                }
                selectFirstChoice = 1;
            } else
            {
                state = runPlayerMove(playerNames[selectFirstChoice], playerTwoSymbol, state);
                gameHistory.add(state);
                System.out.println(displayGameFromState(state));
                if(checkWin(state)){
                    System.out.println(playerNames[selectFirstChoice] + " Wins!");
                    gameOver = true;
                }
                else if (checkDraw(state))
                {
                    System.out.println("Game is a draw!");
                    gameOver = true;
                }
                selectFirstChoice = 0;
            }


        }

        return gameHistory;
    }

    // Given the player names (where player two is "Computer"),
    // Run the one-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runOnePlayerGame(String[] playerNames) {
        // TODO
        Scanner in = new Scanner(System.in);

        char [][] state = new char[getInitialGameState().length] [getInitialGameState()[0].length];
        ArrayList<char[][]> gameHistory = new ArrayList<>();

        for (int i = 0; i < state.length; i++)
        {
            for (int j = 0; j < state[0].length; j++)
            {
                state[i] = Arrays.copyOf(getInitialGameState()[i], getInitialGameState().length);
            }
        }

        Random chooseWhoIsFirst = new Random();
        int selectFirstChoice = chooseWhoIsFirst.nextInt(2);
        System.out.println(playerNames[selectFirstChoice] + " gets to go first!");

        boolean gameOver = false;
        if (selectFirstChoice == 1)
        {
            selectFirstChoice = 0;
        }
        else
        {
            selectFirstChoice = 1;
        }

        System.out.println(displayGameFromState(state));
        while(!gameOver)
        {

            if (selectFirstChoice == 0)
            {
                state = runPlayerMove(playerNames[selectFirstChoice], playerOneSymbol, state);
                gameHistory.add(state);
                System.out.println(displayGameFromState(state));
                if(checkWin(state))
                {
                    System.out.println(playerNames[selectFirstChoice] + " Wins!");
                    gameOver = true;
                }
                else if (checkDraw(state))
                {
                    System.out.println("Game is a draw!");
                    gameOver = true;
                }
                selectFirstChoice = 1;
            }
            else
            {
               state =  getCPUMove(state);
                gameHistory.add(state);
                System.out.println(displayGameFromState(state));

                if(checkWin(state)){
                    System.out.println(playerNames[selectFirstChoice] + " Wins!");
                    gameOver = true;
                }
                else if (checkDraw(state))
                {
                    System.out.println("Game is a draw!");
                    gameOver = true;
                }
                selectFirstChoice = 0;
            }


        }

        return gameHistory;
    }

    // Repeatedly prompts player for move in current state, returning new state after their valid move is made
    private static char[][] runPlayerMove(String playerName, char playerSymbol, char[][] currentState) {
        Scanner sc = new Scanner(System.in);
        // TODO

        char [][] newCurrentState;
        int [] inBounds = getInBoundsPlayerMove(playerName);

        while(!(checkValidMove(inBounds, currentState))){
            System.out.println("This is an invalid move!");
            inBounds = getInBoundsPlayerMove(playerName);
        }

        newCurrentState = makeMove(inBounds, playerSymbol, currentState);

        return newCurrentState;
    }

    // Repeatedly prompts player for move. Returns [row, column] of their desired move such that row & column are on
    // the 3x3 board, but does not check for availability of that space.
    private static int[] getInBoundsPlayerMove(String playerName) {

        Scanner sc = new Scanner(System.in);
        // TODO
        int playerRow, playerCol;
        int [] validMove = new int [2];
        System.out.println(playerName + "'s turn");
        System.out.println(playerName + " enter a row: ");
        playerRow = sc.nextInt();

        validMove[0] = playerRow;
        while((playerRow < 0  || playerRow > 2))
        {
            System.out.println("Enter a row in bounds: ");
            playerRow = sc.nextInt();
        }
        validMove[0] = playerRow;
        System.out.println(playerName + " enter a column: ");
        playerCol = sc.nextInt();

        while((playerCol < 0 || playerCol > 2))
        {
            System.out.println("Enter a column in bounds: ");
            playerCol = sc.nextInt();
        }
        validMove[1] = playerCol;

        return validMove;
    }

    // Given a [row, col] move, return true if a space is unclaimed.
    // Doesn't need to check whether move is within bounds of the board.
    private static boolean checkValidMove(int[] move, char[][] state) {
        // TODO

        return (state[move[0]][move[1]] == emptySpaceSymbol);
    }

    // Given a [row, col] move, the symbol to add, and a game state,
    // Return a NEW array (do NOT modify the argument currentState) with the new game state
    private static char[][] makeMove(int[] move, char symbol, char[][] currentState) {
        // TODO:
        // Hint: Make use of Arrays.copyOf() somehow to copy a 1D array easily
        // You may need to use it multiple times for a 1D array

        char [][] newState = new char[currentState.length][currentState[0].length];
        for (int i = 0; i < currentState.length; i++) {
            newState[i] = Arrays.copyOf(currentState[i], currentState[i].length);
        }
        newState[move[0]][move[1]] = symbol;

        return newState;
    }

    // Given a state, return true if some player has won in that state
    private static boolean checkWin(char[][] state) {
        // TODO
        // Hint: no need to check if player one has won and if player two has won in separate steps,
        // you can just check if the same symbol occurs three times in any row, col, or diagonal (except empty space symbol)
        // But either implementation is valid: do whatever makes most sense to you.

        boolean allCheck;
        boolean horCheck = false;
        boolean verCheck = false;
        boolean diagCheck = false;
        // Horizontals
        for (int i = 0; i < state.length; i++)
        {
            char what = state[i][0];
            int count = 0;
            for (int j = 0; j < state[0].length; j++) {
                if(state[i][j] == what && state[i][j] != emptySpaceSymbol){
                    count++;
                }
            }
            if(count == 3)
            {
                horCheck = true;
                break;
            }
        }
        // Verticals
        for (int i = 0; i < state.length; i++) {
            char what = state[0][i];
            int count = 0;
            for (int j = 0; j < state.length; j++) {
                if(state[j][i] == what && state[j][i] != emptySpaceSymbol){
                    count++;
                }
            }
            if(count == 3){
                verCheck = true;
                break;
            }
        }

        // Diagonals
        for (int i = 0; i < state.length; i++) {
            char what = state[i][i];
            char what2 = state[i][(state[0].length - 1) - i];
            int count = 0;
            for (int j = 0; j < state[0].length; j++) {
                if(state[j][j] == what && state[j][j] != emptySpaceSymbol){
                    count++;
                }
            }
            if(count == 3){
                diagCheck = true;
                break;
            } else {
                count = 0;
            }
            int count2 = 0;
            for (int j = state[0].length; j > 0; j--) {
                if(state[count2][j - 1] == what2 && state[j - 1][j - 1] != emptySpaceSymbol){
                    count++;
                    count2++;
                }
            }
            if(count == 3)
            {
                diagCheck = true;
                break;
            }

        }

        allCheck = (horCheck || verCheck || diagCheck);

        return allCheck;
    }

    // Given a state, simply checks whether all spaces are occupied. Does not care or check if a player has won.
    private static boolean checkDraw(char[][] state)
    {
        // TODO

        boolean openSpace = true;
        // Horizontals
        for (int i = 0; i < state.length; i++)
        {
            int count = 0;
            for (int j = 0; j < state[0].length; j++) {
                if(state[i][j]  == emptySpaceSymbol){
                    openSpace = false;
                    break;
                }
            }
        }

        return openSpace;
    }

    // Given a game state, return a new game state with move from the AI
    // It follows the algorithm in the PDF to ensure a tie (or win if possible)
    private static char[][] getCPUMove(char[][] gameState)
    {
        // TODO

        // Hint: you can call makeMove() and not end up returning the result, in order to "test" a move
        // and see what would happen. This is one reason why makeMove() does not modify the state argument

        // Determine all available spaces
          ArrayList<int []> space =  getValidMoves(gameState);

        // If there is a winning move available, make that move


        // If not, check if opponent has a winning move, and if so, make a move there

        // If not, move on center space if possible

        // If not, move on corner spaces if possible

        // Otherwise, move in any available spot

        return null;
    }

    // Given a game state, return an ArrayList of [row, column] positions that are unclaimed on the board
    private static ArrayList<int[]> getValidMoves(char[][] gameState)
    {
        // TODO

        ArrayList<int []> availableMoves =  new ArrayList<>();

        boolean openSpace = true;
        // Horizontals
        for (int i = 0; i < gameState.length; i++)
        {
            int count = 0;
            for (int j = 0; j < gameState[0].length; j++)
            {
                if(gameState[i][j]  == emptySpaceSymbol)
                {
                    int[] space = {i,j};
                    if (checkValidMove(space,gameState))
                    {
                        availableMoves.add(space);
                    }
                }
            }
        }
        return availableMoves;
    }

    // Given player names and the game history, display the past game as in the PDF sample code output
    private static void runGameHistory(String[] playerNames, ArrayList<char[][]> gameHistory)
    {
        // TODO
        // We have the names of the players in the format [playerOneName, playerTwoName]
        // Player one always gets 'X' while player two always gets 'O'
        // However, we do not know yet which player went first, but we'll need to know...
        // Hint for the above: which symbol appears after one turn is taken?
        char[][] firstGame = gameHistory.get(1);
        String firstPlayer = "";
        String SecondPlayer = "";


        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3 ; j++)
            {
                if (firstGame[i][j] != emptySpaceSymbol)
                {
                    if (firstGame[i][j] == playerOneSymbol)
                    {
                        firstPlayer = playerNames[0];
                        SecondPlayer = playerNames[1];
                    }
                    else
                    {
                        firstPlayer = playerNames[1];
                        SecondPlayer = playerNames[0];
                    }
                }

            }
        }
        // Hint: iterate over gameHistory using a loop

        String printName = firstPlayer;
        System.out.println(playerNames[0] + " (x) " + " vs " + playerNames[1] + " (O) ");
        for (int i = 0; i < gameHistory.size(); i++)
        {
            System.out.println(displayGameFromState(gameHistory.get(i)));
            System.out.println(printName + "'s turn");
            if (printName.equals(firstPlayer))
            {
                printName = SecondPlayer;
            }
            else
            {
                printName = firstPlayer;
            }
        }
    }
}
