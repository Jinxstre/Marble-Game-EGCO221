package Project1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean isPlaying = true;
        
        while (isPlaying) {
            
            
            int WhiteMarbles = 0;

            // 🔹 ลูปรับค่าจำนวนลูกแก้ว
            while (true) {
                System.out.print("Enter number of white marbles = ");
                String input = sc.nextLine();

                try {
                    WhiteMarbles = Integer.parseInt(input);

                    if (WhiteMarbles <= 0) {
                        System.out.println("Number must be greater than 0");
                        continue;
                    }

                    break; // ออกจากลูปรับค่าเมื่อถูกต้อง

                } catch (NumberFormatException e) {
                    System.out.println("Please enter numbers only");
                }
            }

            // เริ่มเกมหลังจากได้ค่าถูกต้องแล้ว
            System.out.println("white marbles move --> only, black marbles move <-- only");
            Board board = new Board(WhiteMarbles); 
            System.out.print("Initial >> ");
            board.printBoard();
            
            int step = 1;
            boolean gameFinished = false;

            System.out.println("\n--- Game Started! ---");

            while (!gameFinished) {
                System.out.printf("Step %3d >> ", step);
                System.out.print("Enter marble ID to move (or 'R' to restart, 'Q' to quit, 'A' to Auto): ");
                String command = sc.nextLine().trim();

                if (command.equalsIgnoreCase("Q")) {
                    System.out.println("Thanks for playing! Goodbye.");
                    isPlaying = false;
                    break;
                } 
                else if (command.equalsIgnoreCase("R")) {
                    System.out.println("\n--- Restarting Game ---\n");
                    
                    break;
                }
                
                if (!board.hasValidMoves()) {
                    System.out.println("\n******* Game Over! No more valid moves available. *******");
                    gameFinished = true;
                    break;
                }

                else if (command.equalsIgnoreCase("A")) {
                    Solver solver = new Solver(board);
                    boolean found = solver.solve();
                    if (found)
                    {
                        solver.printSolution();
                    }
                    else
                    {
                        System.out.println("No solution !!");
                    }
                    break;
                }

                if (board.hasMarble(command)) 
                {
                    if (board.canMove(command)) {

                        String moveType = board.getMoveType(command);
                        System.out.printf("%-12s%s","", moveType);

                        board.move(command);
                        System.out.printf("\n%-12s", "");
                        board.printBoard(); //พิมพ์หลัง Move/Jump เท่านั้น

                        if (board.isSolved()) {
                            System.out.println("\nCongratulations! You solved the puzzle!");
                            gameFinished = true;
                            break;
                        } 
                    } else {
                        System.out.printf("%-12s%s %s\n","","Cannot move ",command);
                    }
                    step++; // เพิ่ม step ทันทีเมื่อ id ถูกต้อง
                } 
                else 
                {
                    System.out.printf("%-12s%s","","Invalid marble ID.");
                }
            }

            //  ถามเล่นต่อไหม

                while (true) 
                {

                    
                        System.out.print("\nDo you want to play again? (Y/N): ");
                    
                        String playAgain = sc.nextLine().trim().toUpperCase();
                    

                    switch (playAgain) 
                    {
                        case "Y":
                            System.out.println("\n--- Starting New Game ---\n");
                            break; //ออกจากลูปswitch case

                        case "N":
                            System.out.println("Thanks for playing! Goodbye.");
                            isPlaying = false;
                            break; //ออกจากลูปswitch case

                        default:
                            System.out.println("Please enter Y or N only.");
                            continue; // พิมผิดให้วนถามใหม่
                    }

                    break; // ออกจากลูปถ้าใส่ Y หรือ N 
                }

        }

        sc.close();
    }
}