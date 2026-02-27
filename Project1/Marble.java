/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project1;

public class Marble 
{
    private String id;
    private char type; // 'w' = white , 'b' = black , '_' = empty space

    public Marble(String id, char type) // Constructor: Initializes a new object with specific state.
    {
        this.id = id;
        this.type = type;
    }

    public String getId() 
    {
        return id;
    }

    public char getType() 
    {
        return type;
    }
    
    @Override
    public String toString()
    {
        return id.equals("_") ? "_" : id; // when it is printed, which is very useful for debugging and UI.
    }
}