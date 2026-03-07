/**
 * 6613123 Siwatida Jansawang
 * 6713219 Khetsophon Phokham
 * 6713228 Naphat Khwanchum
 * 6713237 Noppharut Ngamchokchai
 * 6713238 Nitit Wichaingernngam
 */

package Project1_6713238;

import java.util.ArrayList;

public class Board 
{
    public  ArrayList<Marble> marbles;
    private int n;
    
    public Board(int n) 
    {
        this.n = n;
        initializeBoard();
    }
    
    // สร้างขนาดของกระดานตามจำนวนที่ผู้เล่นต้องการ
    private void initializeBoard() 
    {
        marbles = new ArrayList<>();
        for (int i = 0; i < n; i++) marbles.add(new Marble("w" + i, 'w'));
        marbles.add(new Marble("_", '_'));
        for (int i = 0; i < n; i++) marbles.add(new Marble("b" + i, 'b'));
    }
    
    // หาตำแหน่งของ Marbles จาก ID ที่ผู้เล่นป้อน
    public int getIndexOf(String id) 
    {
        for (int i = 0; i < marbles.size(); i++)
        {
            if (marbles.get(i).getId().equals(id))
            {
                return i;
            }
        }
        return -1;
    }

    public boolean hasMarble(String id) //ตรวจสอบว่า marble id ที่ระบุมีอยู่ใน board หรือไม่โดยเรียกเมธอด getIndexOf() และตรวจสอบว่าค่าที่ได้ไม่เท่ากับ -1 ซึ่งหมายความว่า marble id นั้นมีอยู่ใน board
    {
        return getIndexOf(id) != -1;
    }

    public boolean canMove(String id) //ตรวจสอบว่า marble id ที่ระบุสามารถเคลื่อนที่ได้หรือไม่โดยเรียกเมธอด getIndexOf() เพื่อหาตำแหน่งของ marble id นั้นใน board และตรวจสอบเงื่อนไขการเคลื่อนที่ตามประเภทของ marble
    {
        int idx = getIndexOf(id); //เรียกเมธอด getIndexOf() เพื่อหาตำแหน่งของ marble id ที่ระบุใน board และเก็บไว้ในตัวแปร idx
        if (idx == -1) return false; //ถ้า idx เท่ากับ -1 หมายความว่า marble id ที่ระบุไม่มีอยู่ใน board ดังนั้นจึงไม่สามารถเคลื่อนที่ได้และคืนค่า false

        Marble m = marbles.get(idx); //ถ้า idx ไม่เท่ากับ -1 หมายความว่า marble id ที่ระบุมีอยู่ใน board ดังนั้นจึงเรียกเมธอด get() ของ ArrayList marbles เพื่อรับวัตถุ Marble ที่ตำแหน่ง idx และเก็บไว้ในตัวแปร m
        int emptyIdx = getIndexOf("_"); //เรียกเมธอด getIndexOf() เพื่อหาตำแหน่งของช่องว่าง "_" ใน board และเก็บไว้ในตัวแปร emptyIdx ซึ่งจะใช้ในการตรวจสอบเงื่อนไขการเคลื่อนที่ของ marble id ที่ระบุ

        if (m.getType() == 'w') // สีขาวเดินขวา: เดินปกติ (ติดช่องว่าง) หรือ กระโดดข้ามสีดำ 1 ลูก
        {
            if (idx + 1 == emptyIdx) return true; //ถ้า idx + 1 เท่ากับ emptyIdx หมายความว่า marble id ที่ระบุสามารถเดินขวาไปยังช่องว่างได้ตามกฎของเกม ดังนั้นจึงคืนค่า true
            if (idx + 2 == emptyIdx && marbles.get(idx + 1).getType() == 'b') return true; //ถ้า idx + 2 เท่ากับ emptyIdx และ marble ที่ตำแหน่ง idx + 1 มีประเภท 'b' หมายความว่า marble id ที่ระบุสามารถกระโดดข้ามสีดำ 1 ลูกไปยังช่องว่างได้ตามกฎของเกม ดังนั้นจึงคืนค่า true
        } 
        else if (m.getType() == 'b') // สีดำเดินซ้าย: เดินปกติ (ติดช่องว่าง) หรือ กระโดดข้ามสีขาว 1 ลูก
        {
            if (idx - 1 == emptyIdx) return true; //ถ้า idx - 1 เท่ากับ emptyIdx หมายความว่า marble id ที่ระบุสามารถเดินซ้ายไปยังช่องว่างได้ตามกฎของเกม ดังนั้นจึงคืนค่า true
            if (idx - 2 == emptyIdx && marbles.get(idx - 1).getType() == 'w') return true; //ถ้า idx - 2 เท่ากับ emptyIdx และ marble ที่ตำแหน่ง idx - 1 มีประเภท 'w' หมายความว่า marble id ที่ระบุสามารถกระโดดข้ามสีขาว 1 ลูกไปยังช่องว่างได้ตามกฎของเกม ดังนั้นจึงคืนค่า true
        }
        return false; //ถ้าเงื่อนไขการเคลื่อนที่ไม่ตรงกับกรณีใดๆ ที่กำหนดไว้สำหรับสีขาวและสีดำ หมายความว่า marble id ที่ระบุไม่สามารถเคลื่อนที่ได้ตามกฎของเกม ดังนั้นจึงคืนค่า false
    }

    /* Function สร้างมาเพื่อสลับตำแหน่ง marbles */
    public void move(String id) 
    {
        if (canMove(id)) // เช็คว่าก้อนหินสามารถ move ได้ไหม
        {
            int marbleIdx = getIndexOf(id);
            int emptyIdx = getIndexOf("_");
            Marble temp = marbles.get(marbleIdx);           //
            marbles.set(marbleIdx, marbles.get(emptyIdx));  // 3 บรรทัดนี้ทำหน้าที่สลับก้อนหินกันใน Arraylist
            marbles.set(emptyIdx, temp);                    //
        }
    }

    public String getMoveType(String id) { //ใช้เพื่อส่งข้อมูลประเภทของการเคลื่อนที่ (เช่น "Move right", "Move left", "Jump right", "Jump left")

        int idx = getIndexOf(id); //เรียกเมธอด getIndexOf() เพื่อหาตำแหน่งของ marble id ที่ระบุใน board และเก็บไว้ในตัวแปร idx
        int emptyIdx = getIndexOf("_"); //เรียกเมธอด getIndexOf() เพื่อหาตำแหน่งของช่องว่าง "_" ใน board และเก็บไว้ในตัวแปร emptyIdx ซึ่งจะใช้ในการคำนวณประเภทของการเคลื่อนที่ของ marble id ที่ระบุ

        int diff = emptyIdx - idx; //คำนวณความแตกต่างระหว่างตำแหน่งของช่องว่างและตำแหน่งของ marble id ที่ระบุโดยการลบ idx จาก emptyIdx และเก็บผลลัพธ์ไว้ในตัวแปร diff ซึ่งจะใช้ในการตรวจสอบประเภทของการเคลื่อนที่

        if (Math.abs(diff) == 1) { //ถ้าค่าสัมบูรณ์ของ diff เท่ากับ 1 หมายความว่า marble id ที่ระบุสามารถเดินไปยังช่องว่างได้ตามกฎของเกม ดังนั้นจึงตรวจสอบทิศทางของการเคลื่อนที่โดยดูว่า diff เป็นบวกหรือไม่ ถ้า diff เป็นบวกหมายความว่า marble id ที่ระบุเดินขวาไปยังช่องว่าง ดังนั้นจึงคืนค่า "Move right" ถ้า diff เป็นลบหมายความว่า marble id ที่ระบุเดินซ้ายไปยังช่องว่าง ดังนั้นจึงคืนค่า "Move left"
            return diff > 0 ? "Move right" : "Move left";
        }
        else if (Math.abs(diff) == 2) { //ถ้าค่าสัมบูรณ์ของ diff เท่ากับ 2 หมายความว่า marble id ที่ระบุสามารถกระโดดข้ามลูกอื่นไปยังช่องว่างได้ตามกฎของเกม ดังนั้นจึงตรวจสอบทิศทางของการเคลื่อนที่โดยดูว่า diff เป็นบวกหรือไม่ ถ้า diff เป็นบวกหมายความว่า marble id ที่ระบุกระโดดข้ามลูกอื่นไปทางขวาไปยังช่องว่าง ดังนั้นจึงคืนค่า "Jump right" ถ้า diff เป็นลบหมายความว่า marble id ที่ระบุกระโดดข้ามลูกอื่นไปทางซ้ายไปยังช่องว่าง ดังนั้นจึงคืนค่า "Jump left"
            return diff > 0 ? "Jump right" : "Jump left";
        }

        return ""; //ถ้าเงื่อนไขการเคลื่อนที่ไม่ตรงกับกรณีใดๆ ที่กำหนดไว้สำหรับการเดินและการกระโดด หมายความว่า marble id ที่ระบุไม่สามารถเคลื่อนที่ได้ตามกฎของเกม ดังนั้นจึงคืนค่าเป็นสตริงว่าง ""
    }

    public void undoMove(String id) //ใช้เพื่อย้อนกลับการเคลื่อนที่ของ marble id ที่ระบุโดยการสลับตำแหน่งของ marble id กับช่องว่าง "_" อีกครั้ง ซึ่งจะทำให้สถานะของ board กลับไปยังสถานะก่อนการเคลื่อนที่
    {
        int marbleIdx = getIndexOf(id);
        int emptyIdx = getIndexOf("_");
        Marble temp = marbles.get(marbleIdx);
        marbles.set(marbleIdx, marbles.get(emptyIdx));
        marbles.set(emptyIdx, temp);
    }
    
    public boolean isSolved() //ใช้เพื่อตรวจสอบว่าเกมจบหรือยังโดยการตรวจสอบว่าลูกแก้วสีดำทั้งหมดอยู่ทางซ้ายของช่องว่าง "_" และลูกแก้วสีขาวทั้งหมดอยู่ทางขวาของช่องว่าง "_" ถ้าเงื่อนไขนี้เป็นจริงหมายความว่าเกมจบแล้วและคืนค่า true ถ้าเงื่อนไขนี้ไม่เป็นจริงหมายความว่าเกมยังไม่จบและคืนค่า false
    {
        for (int i = 0; i < n; i++) 
        {
            if (marbles.get(i).getType() != 'b') return false;  // ครึ่งกระดานฝั่งซ้านต้องเป็น "b"
        }
        if (marbles.get(n).getType() != '_') return false;      // ตรงกลางกระดานต้องเป็น "_"
        for (int i = n + 1; i < marbles.size(); i++)
        {
            if (marbles.get(i).getType() != 'w') return false;  // ครึ่งกระดานฝั่งขวาต้องเป็น "w"
        }
        return true;
    }
    
    public boolean hasValidMoves() //ใช้เพื่อตรวจสอบว่ามีการเคลื่อนที่ที่ถูกต้องสำหรับ marble id ใดๆ ใน board หรือไม่โดยการวนลูปผ่าน marble ทั้งหมดใน board และเรียกเมธอด canMove() สำหรับแต่ละ marble id ที่ไม่ใช่ช่องว่าง "_" ถ้ามี marble id ใดๆ ที่สามารถเคลื่อนที่ได้ตามกฎของเกม หมายความว่าเกมยังไม่จบและคืนค่า true ถ้าไม่มี marble id ใดๆ ที่สามารถเคลื่อนที่ได้ หมายความว่าเกมจบแล้วและคืนค่า false
    {
        for (Marble m : marbles) 
        {
            if (m.getType() != '_' && canMove(m.getId()))
            {
                return true;
            }
        }
        return false;
    }

    public void printBoard() 
    {
        for (Marble m : marbles) 
        {
            System.out.print(m + " ");
        }
        System.out.println();
    }
}
