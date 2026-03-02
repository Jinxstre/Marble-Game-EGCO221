package Project1_alt;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {
    private final ArrayDeque<String> prevMove; // เก็บประวัติการเดินหมาก
    private final Set<String> visited;         // เก็บสถานะที่เคยผ่านมาแล้ว
    private List<String> solution;             // ลำดับคำตอบสุดท้าย
    private final Board tempboard;             // ตัวแปรอ้างอิงกระดาน

    public int Trials = 0; // นับจำนวนครั้งที่พยายาม

    public Solver(Board board) {
        this.tempboard = board;
        this.prevMove = new ArrayDeque<>();
        this.visited = new HashSet<>();
        this.solution = null;
    }

    public boolean solve() {
        // เริ่มต้นส่งค่าสถานะการแตะขอบ (Lspace, Rspace) เป็น false
        boolean found = backtrack(false, false);

        if (found) {
            solution = new ArrayList<>(prevMove);
            Collections.reverse(solution);

            // คืนค่ากระดานกลับสู่สถานะเริ่มต้น
            for (String marbleId : prevMove) {
                tempboard.undoMove(marbleId);
            }
        }
        return found;
    }

    private boolean backtrack(boolean lTouched, boolean rTouched) {
        if (tempboard.isSolved()) return true; //

        int emptyIdx = tempboard.getIndexOf("_");
        int lastIdx = tempboard.marbles.size() - 1; // ตำแหน่ง 2n

        // อัปเดตสถานะ "Ready to Solve" เมื่อช่องว่างไปแตะขอบตามที่คุณสังเกต
        boolean currentL = lTouched || (emptyIdx == 0);
        boolean currentR = rTouched || (emptyIdx == lastIdx);

        List<String> moveable = new ArrayList<>();
        for (Marble m : tempboard.marbles) {
            // เช็คว่าหินตัวนี้ขยับได้ตามกฎ และผ่านกฎ Pruning (WW/BB) หรือไม่
            if (m.getType() != '_' && tempboard.canMove(m.getId())) {
                if (isSafeMove(m.getId(), currentL, currentR)) {
                    moveable.add(m.getId());
                }
            }
        }

        for (String marbleId : moveable) {
            tempboard.move(marbleId);
            String state = getBoardState();
            
            Trials++; // นับจำนวนครั้ง

            if (!visited.contains(state)) {
                visited.add(state);
                prevMove.push(marbleId);

                // ส่งค่าสถานะ L/R ต่อไปในชั้นถัดไป
                if (backtrack(currentL, currentR)) return true;

                prevMove.pop();
            }
            tempboard.undoMove(marbleId);
        }

        return false;
    }

    // กฎ Pruning ที่คุณค้นพบ: ห้ามหินพวกเดียวกันติดกันจนกว่าจะถึงช่วงจบเกม
    private boolean isSafeMove(String id, boolean L, boolean R) {
        if (L || R) return true; // ถ้าแตะขอบแล้ว ให้ยกเลิกกฎห้ามติดกัน

        int idx = tempboard.getIndexOf(id);
        int emptyIdx = tempboard.getIndexOf("_");
        char type = tempboard.marbles.get(idx).getType();
        int lastIdx = tempboard.marbles.size() - 1;

        if (type == 'w') {
            if (emptyIdx + 1 <= lastIdx) {
                // ห้ามขาวขยับไปจอดติดขาว
                if (tempboard.marbles.get(emptyIdx + 1).getType() == 'w') return false;
            }
        } else if (type == 'b') {
            if (emptyIdx - 1 >= 0) {
                // ห้ามดำขยับไปจอดติดดำ
                if (tempboard.marbles.get(emptyIdx - 1).getType() == 'b') return false;
            }
        }
        return true;
    }

    // ปรับปรุงการเก็บสถานะให้ประหยัด Memory สำหรับ n=100
    private String getBoardState() {
        StringBuilder sb = new StringBuilder();
        for (Marble m : tempboard.marbles) {
            sb.append(m.getType()); // เก็บแค่ประเภท 'w', 'b', '_'
        }
        return sb.toString();
    }

    public void printSolution() {
        if (solution == null) {
            System.out.println("No solution !!");
            return;
        }

        System.out.print("initial  >> ");
        tempboard.printBoard();
        int step = 1;
        for (String marbleId : solution) {
            tempboard.move(marbleId);
            System.out.printf("Move >> %5s   Auto %3d >> ", marbleId, step);
            tempboard.printBoard();
            step++;
        }
        System.out.println("\nDone !!");
        System.out.println("Total Trials: " + Trials);
        System.out.println("Total Steps: " + solution.size());
    }
}