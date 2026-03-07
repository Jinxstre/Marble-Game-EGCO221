/**
 * 6613123 Siwatida Jansawang
 * 6713219 Khetsophon Phokham
 * 6713228 Naphat Khwanchum
 * 6713237 Noppharut Ngamchokchai
 * 6713238 Nitit Wichaingernngam
 */

package Project1_6713238;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver 
{
    //Use record move during search
    private final ArrayDeque<String> prevMove;

    //Final Solution
    private List<String> solution;

    //Board
    private final Board tempboard;

    public int Trials = 0;

    public Solver(Board board)
    {
        this.tempboard      = board;
        this.prevMove       = new ArrayDeque<>();
        this.solution       = null;
    }

    public boolean solve()
    {
        boolean found = backtrack(false, false);

        if (found)
        {
            //Take order from prevMove
            solution = new ArrayList<>(prevMove);
            Collections.reverse(solution);

            //Get Board back to initial state
            for (String marbleId : prevMove) {
                tempboard.undoMove(marbleId);
            }
        }

        return found;
    }

    //
    //Find all possible solution
    //
    private boolean backtrack(boolean Lspace, boolean Rspace)
    {
        if (tempboard.isSolved()) return true;

        int emptyIdx = tempboard.getIndexOf("_");
        int lastIdx = tempboard.marbles.size() - 1; // ตำแหน่ง 2n
        
        boolean currL = Lspace || (emptyIdx == 0);
        boolean currR = Rspace || (emptyIdx == lastIdx);

        if (currL && currR) 
        {
            generateEndgameMoves();
            return true;
        }
        
        //Find All moveable marble
        List<String> Moveable = new ArrayList<>();
        for (Marble m : tempboard.marbles)
        {
            if (m.getType() != '_' && tempboard.canMove(m.getId()))
            {
                if (propermove(m.getId(), currL, currR)) 
                {
                    Moveable.add(m.getId());
                }
            }
        }

        if (Moveable.isEmpty())
        {
            System.out.println("Forward Step");
            System.out.print("Board : ");
            tempboard.printBoard();
            printStack();
            System.out.println();
            return false;
        }

        //Try each one
        for (String marbleId : Moveable)
        {
            tempboard.move(marbleId);
            prevMove.push(marbleId);

            Trials++;   
            
            //Show each step of forward and backtracking
            System.out.println("Forward Step");
            System.out.print("Board : ");
            tempboard.printBoard();
            printStack();
            System.out.println();

            if (backtrack(currL, currR)) return true;

            System.out.println("Backtracking Step");

            //Get last step out When Stuck
            prevMove.pop();

            //Undo last step
            tempboard.undoMove(marbleId);
            System.out.print("Board : ");
            tempboard.printBoard();
            printStack();
            System.out.println();
        }

        return false;
    }

    //
    //Used to print results of solving
    //
    public void printSolution()
    {
        System.out.printf("initial  >> ");
        tempboard.printBoard();
        int step = 1;
        for (String marbleId : solution)
        {
            tempboard.move(marbleId);
            System.out.printf("Auto %3d >> ",step);
            tempboard.printBoard();
            step++;
        }
        System.out.println("\nDone !!");
        System.out.printf("Trials : %,d\n",Trials);
        System.out.println("MarNum : " + (tempboard.marbles.size() - 1)/2);
        System.out.println("Steps  : " + (step-1));
    }

    //For showing ArrayDeque (Stack)
    private void printStack()
    {
        System.out.print("Stack : [");
        for (String s : prevMove)
        {
            System.out.print(s + " ");
        }
        System.out.println("]");
    }

    //
    //Find Move That Reasonable
    //
    private boolean propermove(String id, boolean Lspace, boolean Rspace)
    {
        if (Lspace && Rspace) return true;

        int idx = tempboard.getIndexOf(id);
        int emptyIdx = tempboard.getIndexOf("_");
        char type = tempboard.marbles.get(idx).getType();

        if (type == 'w') {
            if (emptyIdx + 1 < tempboard.marbles.size()) {
                char front = tempboard.marbles.get(emptyIdx + 1).getType();
                if (front == 'w') {
                    return false; 
                }
            }
        } else if (type == 'b') {
            if (emptyIdx - 1 >= 0) {
                char front = tempboard.marbles.get(emptyIdx - 1).getType();
                if (front == 'b') {
                    return false;
                }
            }
        }

        return true;
    }

    //
    //Create end game move by pattern
    //
    private void generateEndgameMoves() 
    {
        int emptyIdx = tempboard.getIndexOf("_");
        int lastIdx  = tempboard.marbles.size() - 1;

        if (emptyIdx == 0) 
        {
            while(!tempboard.isSolved())
            {
                moveAllOfType('b');
                moveAllOfType('w');
            }
        } 
        else if (emptyIdx == lastIdx) 
        {
            while(!tempboard.isSolved())
            {
                moveAllOfType('w');
                moveAllOfType('b');
            }
        }
    }

    //
    //Move marble with the type provided
    //
    private void moveAllOfType(char type) 
    {
        boolean moved = true;
        while (moved) 
        {
            moved = false;
            for (Marble m : tempboard.marbles) 
            {
                if (m.getType() == type && tempboard.canMove(m.getId())) 
                {
                    tempboard.move(m.getId());
                    prevMove.push(m.getId());
                    Trials++;

                    //Show forward step
                    System.out.println("Forward Step");
                    System.out.print("Board : ");
                    tempboard.printBoard();
                    printStack();
                    System.out.println();
                    
                    moved = true;
                    break;  // restart scan after each move
                }
            }
        }
    }
}
