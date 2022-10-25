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
public class invoiceHeadeTable  extends AbstractTableModel{
      private ArrayList<InvoiceHeader> header;
     private String[] colums ={"No.", "Date","Customer","Total"};
     public invoiceHeadeTable(ArrayList<InvoiceHeader> header)
     {
     this.header=header;
     }

    @Override
    public int getRowCount() {
    return header.size();
    }

    @Override
    public int getColumnCount() {
    return colums.length;
    }

    @Override
    public String getColumnName(int column) {
    return colums[column];
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
     InvoiceHeader invoice = header.get(rowIndex);
          return switch (columnIndex) {
              case 0 -> invoice.getInvoiceNum();
              case 1 -> invoice.getInvoiceDate();
              case 2 -> invoice.getCustomerName();
              case 3 -> invoice.getInvoiceTotal();
              default -> "";
          };
}
     
    
}
