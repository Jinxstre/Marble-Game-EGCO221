package Project1;

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
        visited.add(getBoardState());
        boolean found = backtrack();

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
    private boolean backtrack()
    {
        if (tempboard.isSolved()) return true;

        //Find All moveable marble
        List<String> Moveable = new ArrayList<>();
        for (Marble m : tempboard.marbles)
        {
            if (m.getType() != '_' && tempboard.canMove(m.getId()))
            {
                Moveable.add(m.getId());
            }
        }

        //Try each one
        for (String marbleId : Moveable)
        {
            tempboard.move(marbleId);
            String state = getBoardState();

            //Compare from previous to make sure there is no repeat
            if (!visited.contains(state))
            {
                visited.add(state);
                prevMove.push(marbleId);

                if (backtrack()) return true;

                //Stuck. get last step out
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
        if (solution == null)
        {
            System.out.println("No solution !!");
            return;
        }

        int step = 1;
        for (String marbleId : solution)
        {
            tempboard.move(marbleId);
            System.out.printf("Auto %3d >> ",step);
            tempboard.printBoard();
            step++;
        }
        System.out.println("\nDone !!");
    }

    //
    //Make board to string. For easier comparing
    //
    private String getBoardState()
    {
        StringBuilder sb = new StringBuilder();
        for (Marble m : tempboard.marbles)
        {
            sb.append(m.getId());
        }
        return sb.toString();
    }

}