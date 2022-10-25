/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mahmoud
 */
public class InvoiceHeader {
    // invoice Data member
    private int invoiceNum;
    private Date invoiceDate;
    private  String customerName;
    private ArrayList<InvoiceLine>invoiceItems;
    private double invoiceTotal;

    // empty Constructor
    public InvoiceHeader() {
    }
    // Invoice Header constructor
    public InvoiceHeader(int invoiceNum, Date invoiceDate, String customerName) {
        this.invoiceNum = invoiceNum;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
    }

// setter & getter for Invoice header data member
    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    // Get Items method to retrieve list of invoice line items
    public ArrayList<InvoiceLine> getInvoiceItems()
    {

            if (invoiceItems == null) {
              invoiceItems=new ArrayList<>();
            }
            return invoiceItems;


    }
    public double getInvoiceTotal()
    {
        invoiceTotal=0;
        for (InvoiceLine invoiceItem :getInvoiceItems()) {
            invoiceTotal+=invoiceItem.getItemTotal();

        }
        return invoiceTotal;
    }
    public void add (InvoiceLine line)
    {
    this.invoiceItems.add(line);
    }
 public String returnAsCSV() {
       
   return String.valueOf(invoiceNum)+","+String.valueOf(invoiceDate)+","+customerName;
    }
    @Override
    public String toString() {
        return "Invoice Number "+ invoiceNum+", Invoiced at date "+invoiceDate+", for Customer Name:  "+customerName +", with total "+getInvoiceTotal();
    }

   
}
