package Project1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int WhiteMarbles;

        while (true) {
            System.out.print("Enter number of white marbles = ");
            String input = sc.nextLine();

            try {
                WhiteMarbles = Integer.parseInt(input); // แปลง String เป็น int ถ้าไม่ใช่ตัวเลขจะerror

                if (WhiteMarbles <= 0) { //ตรวจสอบค่าที่รับมาเป็นบวกเท่านั้น
                    continue;//ถ้าค่าที่รับ<=0 ให้วนรับค่าใหม่
                }
                break; //ออกจากลูปเมื่อใส่ค่าถูก

            } catch (NumberFormatException e) {
                System.out.println("Please enter numbers only");
            }
        }
        System.out.println("white marbles move --> only, black marbles move <-- only");
        System.out.println("Initial >>");
    }
}