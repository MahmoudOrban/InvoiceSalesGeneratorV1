/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mahmoud
 */
public class invoiceLineTable extends AbstractTableModel{
     private ArrayList <InvoiceLine> lines;
     private String[] columns ={"No.", "Item Nme","Item Price","Count", "Item Total"};

    public invoiceLineTable(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }

    public ArrayList<InvoiceLine> getProducts() {
        return lines;
    }
    @Override
    public int getRowCount() {
    return lines.size();
    }

    @Override
    public int getColumnCount() {
   return columns.length;
    }
@Override
    public String getColumnName(int column) {
    return columns[column];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
     InvoiceLine line = lines.get(rowIndex);
         return switch (columnIndex) {
             case 0 -> line.getHeader().getInvoiceNum();
             case 1 -> line.getItemName();
             case 2 -> line.getItemPrice();
             case 3 -> line.getCount();
             case 4 -> line.getItemTotal();
             default -> "";
         };
    
    
}
}