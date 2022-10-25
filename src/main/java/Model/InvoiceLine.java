/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Mahmoud
 */
public class InvoiceLine {
     // invoice line Data member
    private String itemName;
    private double itemPrice;
    private int count;
    private InvoiceHeader header;

    // empty constructor
    public InvoiceLine() {
    }
    // Constructor of invoice line contain required data
    public InvoiceLine(String itemName, double itemPrice, int count) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
    }

    public InvoiceLine(String itemName, double itemPrice, int count, InvoiceHeader header) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
        this.header = header;
    }

    // retrieve invoice master data
    public InvoiceHeader getHeader() {
        return header;
    }
    // retrieve Invoice number of each line
    public int getInvoiceNumberPerItem()
    {
        return getHeader().getInvoiceNum();
    }

    // Setter & Getter for private Data member
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public double getItemTotal()
    {
        return getItemPrice()*getCount();
    }

    @Override
    public String toString() {
        return "Invoice Number"+getInvoiceNumberPerItem()+"contain item : "+itemName+", Price"+itemPrice+", count"+count;
    }

     public String returnAsCSV() {
       
   return String.valueOf(header.getInvoiceNum())+","+itemName+","+String.valueOf(itemPrice)+","+String.valueOf(count);
    }
    
}
