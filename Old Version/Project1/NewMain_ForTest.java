/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Project1;

import java.util.Scanner;

public class NewMain_ForTest 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        boolean isPlaying = true;

        System.out.println("=== 🔵 Welcome to Reversing Marbles ⚪ ===");

        while (isPlaying) 
        {
            int n = 0;
            
            while (true) 
            {
                System.out.print("Enter number of white/black marbles (n >= 2): ");
                String input = scanner.nextLine().trim();
                try 
                {
                    n = Integer.parseInt(input); 
                    if (n >= 2) 
                    {
                        break; 
                    }
                    System.out.println("Error: Please enter a number >= 2.");
                } 
                catch (NumberFormatException e) 
                {
                    System.out.println("Invalid input! Please enter a valid integer (e.g., 2, 3, 4).");
                }
            }
            
            Board board = new Board(n);
            int step = 1;
            boolean gameFinished = false; 

            System.out.println("\n--- Game Started! ---");
            
            while (!gameFinished) 
            {
                System.out.print("Step " + step + " >> ");
                board.printBoard();
                
                if (board.isSolved()) 
                {
                    System.out.println("\n🎉 Congratulations! You solved the puzzle! 🎉");
                    gameFinished = true;
                    break;
                }
                
                if (!board.hasValidMoves()) 
                {
                    System.out.println("\n******* Game Over! No more valid moves available. It's a dead end. *******");
                    gameFinished = true;
                    break;
                }
               
                System.out.print("Enter marble ID to move (or 'R' to restart, 'Q' to quit): ");
                String command = scanner.nextLine().trim();
                
                if (command.equalsIgnoreCase("Q")) 
                {
                    System.out.println("Thanks for playing! Goodbye.");
                    isPlaying = false; 
                    break;
                } 
                else if (command.equalsIgnoreCase("R")) 
                {
                    System.out.println("\n--- Restarting Game ---\n");
                    break;
                }
                
                if (board.canMove(command)) 
                {
                    board.move(command);
                    step++; 
                }
                else
                {
                    System.out.println("❌ Cannot move '" + command + "' or invalid ID. Please try again.");
                }
            }
            if (isPlaying && gameFinished) 
            {
                System.out.print("\nDo you want to play again? (Y/N): ");
                String playAgain = scanner.nextLine().trim();
                if (!playAgain.equalsIgnoreCase("Y")) 
                {
                    System.out.println("Thanks for playing! Goodbye.");
                    isPlaying = false; 
                } else 
                {
                    System.out.println("\n--- Starting New Game ---\n");
                }
            }
        }
        
        scanner.close(); 
    }
}