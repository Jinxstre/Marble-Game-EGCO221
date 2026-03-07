/**
 * 6613123 Siwatida Jansawang
 * 6713219 Khetsophon Phokham
 * 6713228 Naphat Khwanchum
 * 6713237 Noppharut Ngamchokchai
 * 6713238 Nitit Wichaingernngam
 */

package Project1_6713238;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean isPlaying = true; //ค่า boolean สำหรับเช็คว่าเกมยังเล่นอยู่หรือไม่

        while (isPlaying) {

            int WhiteMarbles = 0; //ค่าเริ่มต้นของจำนวน White Marbles ที่จะรับจากผู้ใช้

            // ลูปรับค่าจำนวน marble
            while (true) {
                System.out.print("Enter number of white marbles = ");
                String input = sc.nextLine();

                try {
                    WhiteMarbles = Integer.parseInt(input);

                    if (WhiteMarbles <= 0) { //ถ้าผู้ใช้ป้อนจำนวนที่ไม่เป็นบวก ให้แจ้งและวนรับค่าใหม่
                        System.out.println("Number must be greater than 0");
                        continue;
                    }

                    break; // ออกจากลูปรับค่าเมื่อถูกต้อง

                } catch (NumberFormatException e) { //ถ้าผู้ใช้ป้อนค่าไม่ใช่ตัวเลข ให้แจ้งและวนรับค่าใหม่
                    System.out.println("Please enter numbers only");
                }
            }

            // เริ่มเกมหลังจากได้ค่าถูกต้องแล้ว
            System.out.println("white marbles move --> only, black marbles move <-- only");
            Board board = new Board(WhiteMarbles);
            System.out.print("Initial >> "); //พิมพ์ข้อความ "Initial >> " เพื่อแสดงสถานะเริ่มต้น
            board.printBoard();
            
            
            int step = 1; //ค่าเริ่มต้นของ step ที่จะใช้ในการนับจำนวนขั้นตอนการเล่นเกม
            boolean gameFinished = false; //ค่า boolean สำหรับเช็คว่าเกมจบหรือยัง
            boolean restartRequested = false; //ค่า boolean สำหรับเช็คว่าผู้ใช้ต้องการ Restart หรือไม่

            System.out.println("\n--- Game Started! ---");

            while (!gameFinished) { //ลูปหลักของเกมที่ทำงานจนกว่าเกมจะจบ

                System.out.printf("Step %3d >> ", step);
                System.out.print("Enter marble ID to move (or 'R' to reset, 'Q' to quit, 'A' to Auto): ");

                String command = sc.nextLine().trim().toLowerCase(); //รับคำ marble id หรือคำสั่งพิเศษจากผู้ใช้

                // กรณีต้องการออกจากเกม
                if (command.equalsIgnoreCase("Q")) {
                    System.out.println("Thanks for playing! Goodbye.");
                    isPlaying = false; //isPlaying = false เพื่อออกจากลูป while(isPlaying) และจบโปรแกรม
                    break;
                }

                // กรณีต้องการ Restart เกม
                if (command.equalsIgnoreCase("R")) {
                    restartRequested = true; //restartRequested = true เพื่อบอกว่าให้เริ่มเกมใหม่ในลูป while(isPlaying) ด้านบน
                    break;
                }

                // กรณีต้องการให้ระบบแก้โจทย์ให้
                if (command.equalsIgnoreCase("A")) {
                    Solver solver = new Solver(board); //สร้างอ็อบเจ็กต์ solver โดยส่ง board ปัจจุบันเข้าไปใน constructor
                    boolean found = solver.solve(); //เรียกเมธอด solve() ของ solver เพื่อหาวิธีแก้ปัญหาและเก็บผลลัพธ์ว่าเจอวิธีแก้หรือไม่ในตัวแปร found

                    if (found) { //ถ้าเจอวิธีแก้ปัญหา ให้เรียกเมธอด printSolution() เพื่อแสดงขั้นตอนการแก้ปัญหา
                        solver.printSolution();
                    } else { //ถ้าไม่เจอวิธีแก้ปัญหา ให้พิมพ์ข้อความแจ้งว่าไม่มีวิธีแก้ปัญหา
                        System.out.println("No solution !!");
                    }

                    gameFinished = true; //gameFinished = true เพื่อจบเกมหลังจากแสดงวิธีแก้ปัญหาแล้ว
                    break;
                }
                if (board.hasMarble(command)) { //ตรวจสอบว่า marble id ที่ผู้ใช้ป้อนมีอยู่ใน board หรือไม่โดยเรียกเมธอด hasMarble() ของ Board
                    if (board.canMove(command)) { //ถ้า marble id มีอยู่ใน board ให้ตรวจสอบว่า marble id นั้นสามารถเคลื่อนที่ได้หรือไม่โดยเรียกเมธอด canMove() ของ Board

                        String moveType = board.getMoveType(command); //เรียกเมธอด getMoveType() ของ board เพื่อรับข้อมูลประเภทของการเคลื่อนที่ (เช่น "Move right", "Move left", "Jump right", "Jump left") และเก็บไว้ในตัวแปร moveType
                        System.out.printf("%12s%s\n","", moveType);

                        board.move(command); //เรียกเมธอด move() ของ board เพื่อทำการเคลื่อนที่ของ marble ตาม marble id ที่ผู้ใช้ป้อน
                        System.out.printf("%12s", "");
                        board.printBoard();   // พิมพ์เฉพาะตอน move ได้
                        if (board.isSolved()) { //ตรวจสอบว่าเกมจบหรือยังโดยเรียกเมธอด isSolved() ของ board ถ้าเกมจบให้พิมพ์ข้อความแสดงความยินดีและตั้งค่า gameFinished = true เพื่อออกจากลูป while(!gameFinished)
                            System.out.println("\nCongratulations! You solved the puzzle!");
                            gameFinished = true;
                            break;
                        }
                        if (!board.hasValidMoves()) { //ตรวจสอบว่าเกมยังมี valid moves ให้เล่นอยู่หรือไม่โดยเรียกเมธอด hasValidMoves() ของ Board ถ้าไม่มี valid moves ให้พิมพ์ข้อความแจ้งว่าเกมจบเพราะไม่มี valid moves และตั้งค่า gameFinished = true เพื่อออกจากลูป while(!gameFinished)
                            System.out.println("\n******* Game Over! No more valid moves available. *******");
                            gameFinished = true;
                            break;
                        }
                    } else { //ถ้า marble id มีอยู่ใน board แต่ไม่สามารถเคลื่อนที่ได้ ให้พิมพ์ข้อความแจ้งว่าไม่สามารถเคลื่อนที่ได้โดยระบุ marble id ที่ผู้ใช้ป้อน
                        System.out.printf("%12s%s%s\n", "", "Cannot move ", command);
                    }

                    step++;   //เพิ่ม step ทุกครั้งที่ marble id ถูกต้อง

                } else { //ถ้า marble id ที่ผู้ใช้ป้อนไม่มีอยู่ใน board ให้พิมพ์ข้อความแจ้งว่า marble id ไม่ถูกต้อง
                    System.out.printf("%12s%s\n", "", "Invalid marble ID.");
                }
            }

            if (restartRequested) { //ถ้าผู้ใช้ต้องการ Restart ให้พิมพ์ข้อความแจ้งว่าเกมกำลังจะเริ่มใหม่และกลับไปเริ่มลูป while(isPlaying) ใหม่
                System.out.println("\n--- Restarting Game ---\n");
                continue;   // กลับไปเริ่ม while(isPlaying) ใหม่
            }

            //  ถามเล่นต่อไหม
            if (isPlaying) {
                while (true) 
                {
                    System.out.print("\nDo you want to play again? (Y/N): ");
                    String playAgain = sc.nextLine().trim().toUpperCase();

                    switch (playAgain) 
                    {
                        case "Y": //ถ้าผู้ใช้ตอบว่า Y ให้พิมพ์ข้อความแจ้งว่าเกมกำลังจะเริ่มใหม่และกลับไปเริ่มลูป while(isPlaying) ใหม่
                            System.out.println("\n--- Starting New Game ---\n");
                            break; //ออกจากลูปswitch case

                        case "N": //ถ้าผู้ใช้ตอบว่า N ให้พิมพ์ข้อความขอบคุณและตั้งค่า isPlaying = false เพื่อออกจากลูป while(isPlaying) และจบโปรแกรม
                            System.out.println("Thanks for playing! Goodbye.");
                            isPlaying = false;
                            break; //ออกจากลูปswitch case

                        default: //ถ้าผู้ใช้ตอบค่าอื่นที่ไม่ใช่ Y หรือ N ให้พิมพ์ข้อความแจ้งว่าให้ตอบ Y หรือ N เท่านั้นและวนถามใหม่
                            System.out.println("Please enter Y or N only.");
                            continue; // พิมผิดให้วนถามใหม่
                    }
                    break; // ออกจากลูปถ้าใส่ Y หรือ N 
                }
            }
        }
        sc.close();
    }
}
