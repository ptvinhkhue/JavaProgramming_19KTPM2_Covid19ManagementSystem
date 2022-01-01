/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Necessity;

/**
 *
 * @author Admin
 */
public class Necessity {
    public int NID;
    public String name;
    public int amount;
    public int price;

    public Necessity() {
        name = "";
    }
    
    public Necessity(int ID, String name, int amount, int price) {
        NID = ID;
        this.name = name;
        this.amount =  amount;
        this.price = price;
    }
}
