/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package Controller;
import View.AddNewInvoice;
import View.AddNewInvoiceItem;
import View.SIG;
import Model.invoiceHeadeTable;
import Model.invoiceLineTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import Model.InvoiceLine;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import Model.InvoiceHeader;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Mahmoud
 */
public class Controller implements ActionListener, ListSelectionListener{
     private SIG frame;
    private AddNewInvoice newInvoiceDialoge ; 
    private AddNewInvoiceItem newItemDialog;

    public Controller(SIG frame){
        this.frame = frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String actioncommand = e.getActionCommand();
        System.out.println("Action "+actioncommand);
        switch(actioncommand){
            case "Load File" -> LoadFile();
            
            case "Save File" -> SaveFile();
            
            case "Create New Invoice" -> CreateNewInvoice();
            
            case "Delete Invoice" -> DeleteInvoice();
            
            case "Add New Item" -> Save();
            
            case "Delete Item" -> Cancel();
            case "Cancel Invoice Creation" -> CancelInvoiceCreation();
            case "Invoice Dialog Ok" -> InvoiceDialogOk();
                
                case "Item Dialog Ok" -> ItemDialogOk();
                
                case "Item Dialog Cancel" -> ItemDialogCancel();
          
    } 
}
    
@Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getHeaderTable().getSelectedRow();
        if (selectedIndex != -1){
        System.out.println("you have selected : "+selectedIndex);
        InvoiceHeader currentInvoice = frame.getInvoices().get(selectedIndex);
        frame.getInvoiceNumber().setText(""+currentInvoice.getInvoiceNum());
        frame.getInvoiceDate().setText(String.valueOf(currentInvoice.getInvoiceDate()) );
        frame.getCustomerName().setText(currentInvoice.getCustomerName());
        frame.getInvoiceTotal().setText(""+currentInvoice.getInvoiceTotal());
        invoiceLineTable itemsTableModel = new invoiceLineTable(currentInvoice.getInvoiceItems());
        frame.getLinesTable().setModel(itemsTableModel);
        itemsTableModel.fireTableDataChanged();
    }
    }
    private void LoadFile() {
      JFileChooser fc=new JFileChooser();
      try {
     int outcome= fc.showOpenDialog(frame);
     if(outcome==JFileChooser.APPROVE_OPTION){
         File billsFile = fc.getSelectedFile();
         Path billsPath= Paths.get(billsFile.getAbsolutePath());
        List<String> headerLines = Files.readAllLines(billsPath);
         System.out.println("invoices have brrn read");
         ArrayList<InvoiceHeader> invoiceArray = new ArrayList<> ();
         for (String headerLine: headerLines){
             try{
           String[] headerParts =  headerLine.split(",");
           int invoiceNum= Integer.parseInt( headerParts[0]);
           Date invoiceDate = new SimpleDateFormat("dd/MM/yyyy").parse(headerParts[1]);
           String customerName = headerParts[2];
           InvoiceHeader invoice=new InvoiceHeader(invoiceNum, invoiceDate, customerName);
           invoiceArray.add(invoice); 
         } catch( Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
         }
         }
         
         System.out.println("checkpoint");
        
         outcome = fc.showOpenDialog(frame);
         if(outcome == JFileChooser.APPROVE_OPTION){
             File lineFile = fc.getSelectedFile();
             Path linePath = Paths.get(lineFile.getAbsolutePath());
             List<String> lineLines = Files.readAllLines(linePath);
                      System.out.println("lines have brrn read");
                      for(String lineLine : lineLines){
                          try{
                          String lineParts[] =lineLine.split(",");
                          int invoiceNum = Integer.parseInt(lineParts[0]);
                          String itemName = lineParts[1];
                          double itemPrice = Double.parseDouble(lineParts[2]);
                          int count = Integer.parseInt(lineParts[3]);
                          InvoiceHeader inv = null;
                          for(InvoiceHeader invoice : invoiceArray){
                              if(invoice.getInvoiceNum() == invoiceNum){
                                  inv = invoice ;
                                  break;
                              }
                          }
                          InvoiceLine line=new InvoiceLine(itemName, itemPrice, count);
                          inv.getInvoiceItems().add(line);
                      }
                      catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                              }
         }
             System.out.println("check point");
         }
         frame.setInvoices(invoiceArray);
         invoiceHeadeTable invoicesTableModel= new invoiceHeadeTable(invoiceArray);
         frame.setHeaderTableModel(invoicesTableModel);
         frame.getHeaderTable().setModel(invoicesTableModel);
         frame.getHeaderTableModel().fireTableDataChanged();
     }
      } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void SaveFile() {
        ArrayList<InvoiceHeader> invoices =frame.getInvoices();
        String headers="";
        String lines ="";
        for (InvoiceHeader invoiceheader : invoices){
            String invCSV = invoiceheader.returnAsCSV();
            headers = headers + invCSV;
            headers += "\n";
            
            for(InvoiceLine line: invoiceheader.getInvoiceItems()){
                String lineCSV = line.returnAsCSV();
                lines += lineCSV;
                lines +="\n";
            }
    }
        System.out.println("checked line");
        try{
        JFileChooser fc = new JFileChooser();
        int result =    fc.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION){
            File headerFile = fc.getSelectedFile();
            FileWriter hfw = new FileWriter(headerFile);
            hfw.write(headers);
            hfw.flush();
            hfw.close();
            
            result = fc.showSaveDialog(frame);
            if(result == JFileChooser.APPROVE_OPTION){
                File lineFile = fc.getSelectedFile();
                FileWriter lfw = new FileWriter(lineFile);
            lfw.write(lines);
            lfw.flush();
            lfw.close();
         }
        }
        } catch (Exception ex){}
       
    }
    private void CreateNewInvoice() {
    newInvoiceDialoge = new AddNewInvoice(frame);
    newInvoiceDialoge.setVisible(true);
    
    }

    private void DeleteInvoice() {
        int selectedRow =  frame.getHeaderTable().getSelectedRow();
        if(selectedRow != -1){
         frame.getInvoices().remove(selectedRow);
         frame.getHeaderTableModel().fireTableDataChanged();
         
     }
    }

    private void Save() {
        newItemDialog = new AddNewInvoiceItem(frame);
        newItemDialog.setVisible(true);
    
    }

    private void Cancel() {
        int selectedRow =  frame.getLinesTable().getSelectedRow();
        if(selectedRow != -1){
         invoiceLineTable linesTableModel = (invoiceLineTable) frame.getLinesTable().getModel();
         linesTableModel.getProducts().remove(selectedRow);
         linesTableModel.fireTableDataChanged();
         frame.getHeaderTableModel().fireTableDataChanged();
        }
    }

    private void CancelInvoiceCreation() {
    newInvoiceDialoge.setVisible(false);
    newInvoiceDialoge.dispose();
    newInvoiceDialoge = null;
    }

    private void ItemDialogOk() {
        String item = newItemDialog.getItemNameField().getText();
        String countStr = newItemDialog.getItemCountField().getText();
        String priceStr = newItemDialog.getItemPriceField().getText();
        int count =Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedRow = frame.getHeaderTable().getSelectedRow();
        if(selectedRow != -1){
        InvoiceHeader header =frame.getInvoices().get( selectedRow );
        InvoiceLine lines =new InvoiceLine(item, price, count, header); 
        header.getInvoiceItems().add(lines);
        invoiceLineTable productTableModel =(invoiceLineTable) frame.getLinesTable().getModel();
        productTableModel.fireTableDataChanged();
        frame.getHeaderTableModel().fireTableDataChanged();
        }
        
        
        
    newItemDialog.setVisible(false);
        newItemDialog.dispose();
        newItemDialog = null;
    }
    
    private void InvoiceDialogOk() {
        String date = newInvoiceDialoge.getInvoiceDateField().getText();
        String customer =newInvoiceDialoge.getCustNameField().getText();
            int num = frame.getNextinvoiceNum();
            try {
            String[] dateParts = date.split("/");
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else{
        InvoiceHeader invoices  = new InvoiceHeader(num, new SimpleDateFormat("dd/MM/yyyy").parse(date), customer);
        frame.getInvoices().add(invoices);
        frame.getHeaderTableModel().fireTableDataChanged();
        newInvoiceDialoge.setVisible(false);
        newInvoiceDialoge.dispose();
        newInvoiceDialoge = null;
    }
            }
            } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void ItemDialogCancel() {
        newItemDialog.setVisible(false);
        newItemDialog.dispose();
        newItemDialog = null;
    
    }
    
}
