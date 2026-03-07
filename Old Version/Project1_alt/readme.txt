Board.java
 - ขอเปลี่ยน marbles จาก private เป็น public เพื่อความง่าย
 - เพิ่ม undoMove สลับตำแหน่งโดยไม่เช็ค canmove

Main.java
 - เพิ่มตัวเลือก Auto

 restart ควรวนเล่นใหม่
 หลังใส่จำนวนลูกแก้ว ต้องแสดงโจทย์ที่ Initial

Main.java (2/3/2026)
 - ปัจจุบัน Initial แสดงโจทย์แล้ว
 - Step ขึ้นทุกครั้งที่ input marble id ถูก ทั้งเมื่อขยับได้และไม่ได้ 
   แต่ไม่ขึ้นเมื่อ input marble id ไม่ตรงกับ marble ใดๆเลย
 - Output Move left/right, Jump left/right ทุกครั้งที่ลูกแก้วขยับแล้ว
 - Output board เฉพาะตอน Initial และตอนเคลื่อน Marble ได้
 - Output จัด format แล้ว

ฺBoard.java (2/3/2026)
 - เพิ่ม Method hasMarble ไว้สำหรับเช็คว่ามี marble id อยู่ใน Board หรือไม่
 - เพิ่ม Method getMoveType ไว้สำหรับส่งคืนประเภท Move เพื่อ Output

Solver.java (2/3/2026)
 - Output จัด format แล้ว