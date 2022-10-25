/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mahmoud-fawzy
 */
public class FileOperations {
    

   
    // Read method to read CSV File
   public  ArrayList<InvoiceHeader> readFile(Path headerPath, Path linePath ) throws IOException{
      ArrayList<InvoiceHeader> headers=new ArrayList<>();
      Date invoiceDate=null;
      String customerName;
      String row;
      BufferedReader csvRead=null;
       // reading Invoice Header
       try
        {
        csvRead = new BufferedReader(new FileReader(String.valueOf(headerPath)) );
       // loop for each row in csv File
       while((row=csvRead.readLine())!=null)
       {
           // intialize Array of strings for each row
           String[]dataStream=row.split(",");
        
           // Parse data stream and create new Invoice Header
           int invoiceNum=Integer.parseInt(dataStream[0]);
           try {
                invoiceDate=new SimpleDateFormat("dd/MM/yyyy").parse(dataStream[1]);
           } catch (ParseException ex) {
               Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
           }
          customerName=dataStream[2];
           headers.add(new InvoiceHeader(invoiceNum,invoiceDate,customerName));
       }
       
       }
       catch(FileNotFoundException e)
       {
       }
       finally{
       try{
           csvRead.close();
       }
       catch(IOException io)
       {
       }
       }
       
       try{
       csvRead=new BufferedReader(new  FileReader(String.valueOf(linePath)));
       // loop for each row in csv File
       while((row=csvRead.readLine())!=null)
       {
           // intialize Array of strings for each row
           String[]dataStream=row.split(",");
           int invoiceNum=Integer.parseInt(dataStream[0]);
           String itemName=dataStream[1];
           double itemPrice=Double.parseDouble(dataStream[2]);
           int itemCount=Integer.parseInt(dataStream[3]);
           InvoiceLine line=new InvoiceLine(itemName,itemPrice,itemCount,headers.get(invoiceNum-1));
           headers.get(invoiceNum-1).add(line);
       
       }
       
       }
       catch(IOException ie)
       {
           
       }
       finally{
           try{
       csvRead.close();
           }
           catch(IOException oo)
           {
           }
       }
       
    return headers;
   }
   public void writeFile(ArrayList<InvoiceHeader> invoices, Path headerPath, Path linesPath){
       try {
            FileWriter csvWriter = new FileWriter(String.valueOf(headerPath));
            for(InvoiceHeader header : invoices){
                csvWriter.append(header.returnAsCSV());
            }
            csvWriter.close();

            csvWriter = new FileWriter(String.valueOf(linesPath));
            for(InvoiceHeader header : invoices){
               for(InvoiceLine line : header.getInvoiceItems()){
                   csvWriter.append(line.returnAsCSV());
               }
            }
            csvWriter.close();
            
        } catch (IOException e) {}
   
       
   }
    
    public static void main(String[] args) {
        
    }

}