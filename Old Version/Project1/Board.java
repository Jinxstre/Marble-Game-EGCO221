package Project1;

import java.util.*;

public class Board 
{
    public  ArrayList<Marble> marbles;
    private int n;
    
    public Board(int n) 
    {
        this.n = n;
        initializeBoard();
    }
    
    private void initializeBoard() 
    {
        marbles = new ArrayList<>();
        for (int i = 0; i < n; i++) marbles.add(new Marble("w" + i, 'w'));
        marbles.add(new Marble("_", '_'));
        for (int i = 0; i < n; i++) marbles.add(new Marble("b" + i, 'b'));
    }
    
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
    
    public boolean canMove(String id) 
    {
        int idx = getIndexOf(id);
        if (idx == -1) return false; 

        Marble m = marbles.get(idx);
        int emptyIdx = getIndexOf("_");

        if (m.getType() == 'w') 
        {
            if (idx + 1 == emptyIdx) return true;
            if (idx + 2 == emptyIdx && marbles.get(idx + 1).getType() == 'b') return true;
        } 
        else if (m.getType() == 'b') 
        {
            // สีดำเดินซ้าย: เดินปกติ (ติดช่องว่าง) หรือ กระโดดข้ามสีขาว 1 ลูก
            if (idx - 1 == emptyIdx) return true;
            if (idx - 2 == emptyIdx && marbles.get(idx - 1).getType() == 'w') return true;
        }
        return false;
    }

    public void move(String id)
    {
        if (canMove(id)) 
        {
            int marbleIdx = getIndexOf(id);
            int emptyIdx = getIndexOf("_");
            Marble temp = marbles.get(marbleIdx);
            marbles.set(marbleIdx, marbles.get(emptyIdx));
            marbles.set(emptyIdx, temp);
        }
    }

    public void undoMove(String id)
    {
        int marbleIdx = getIndexOf(id);
        int emptyIdx = getIndexOf("_");
        Marble temp = marbles.get(marbleIdx);
        marbles.set(marbleIdx, marbles.get(emptyIdx));
        marbles.set(emptyIdx, temp);
    }
    
    public boolean isSolved() 
    {
        for (int i = 0; i < n; i++) 
        {
            if (marbles.get(i).getType() != 'b') return false; 
        }
        if (marbles.get(n).getType() != '_') return false;
        for (int i = n + 1; i < marbles.size(); i++)
        {
            if (marbles.get(i).getType() != 'w') return false;
        }
        return true;
    }
    
    public boolean hasValidMoves() 
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