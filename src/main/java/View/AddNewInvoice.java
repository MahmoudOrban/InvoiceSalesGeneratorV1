/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author Mahmoud
 */
public class AddNewInvoice extends JDialog {
    private JTextField custNameField;
    private JTextField invoiceDateField;
    private JLabel custNameLbl;
    private JLabel invoiceDateLbl;
    private JButton okBtn;
    private JButton cancelBtn;

    public AddNewInvoice(SIG frame) {
        custNameLbl = new JLabel("Customer Name:");
        custNameField = new JTextField(20);
        invoiceDateLbl = new JLabel("Invoice Date:");
        invoiceDateField = new JTextField(20);
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("Invoice Dialog Ok");
        cancelBtn.setActionCommand("Cancel Invoice Creation");
        
        okBtn.addActionListener(frame.getController());
        cancelBtn.addActionListener(frame.getController());
        setLayout(new GridLayout(3, 2));
        
        add(invoiceDateLbl);
        add(invoiceDateField);
        add(custNameLbl);
        add(custNameField);
        add(okBtn);
        add(cancelBtn);
        
        pack();
        
    }

    public JTextField getCustNameField() {
        return custNameField;
    }

    public JTextField getInvoiceDateField() {
        return invoiceDateField;
    }
}
