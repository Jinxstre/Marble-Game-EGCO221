package Project1_alt;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver 
{
    //Use record move during search
    private final ArrayDeque<String> prevMove;

    //Saved format that have been seen
    private final Set<String> visited;

    //Final Solution
    private List<String> solution;

    //Board
    private final Board tempboard;

    public int Trials = 0;

    public Solver(Board board)
    {
        this.tempboard      = board;
        this.prevMove       = new ArrayDeque<>();
        this.visited        = new HashSet<>();
        this.solution       = null;
    }

    public boolean solve()
    {
        //Saved init state
        //visited.add(getBoardState());
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

        //Try each one
        for (String marbleId : Moveable)
        {
            tempboard.move(marbleId);
            String state = getBoardState();

            if (Trials%1000000==0)
            {
                System.out.println("Trials : " + Trials/1000000 + " M"); 
            }
            Trials++;   

            //Compare from previous to make sure there is no repeat
            if (!visited.contains(state))
            {
                //visited.add(state);
                prevMove.push(marbleId);

                if (backtrack(currL, currR)) return true;

                //Get last step out When Stuck
                prevMove.pop();
            }

            //Undo last step
            tempboard.undoMove(marbleId);
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
        System.out.printf("Trials : %,d",Trials);
        System.out.println("MarNum : " + (tempboard.marbles.size() - 1)/2);
    }

    //
    //Make board to string. For easier comparing
    //
    private String getBoardState()
    {
        StringBuilder sb = new StringBuilder();
        for (Marble m : tempboard.marbles)
        {
            sb.append(m.getType());
        }
        return sb.toString();
    }

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
}