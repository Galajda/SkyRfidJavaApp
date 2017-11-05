/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrfidjavaapp;


import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
//import jumf50.javacall;  //added the jar to Libraries folder

/**
 *
 * @author MichalG
 */
public class ReadPane 
{
    private final VBox pane;
    private final Label lblWelcome;
    private final Button btnOpenPort;
    private final Button btnReadData;
    private final Button btnClosePort;
//    private javacall reader;
    private RfidNativeInterface rfidDll = RfidNativeInterface.INSTANCE;
    private int readerHdl;
    private final char READ_SINGLE_CARD = 0x36;
    private final char CARD_TYPE_ISO15693 = 0x31;
    ReadPane()
    {
        System.out.println("constructor running");
        pane = new VBox();
        pane.setMinWidth(300);
        lblWelcome = new Label("Welcome to the RFID reader.\nThe read mode is under construction.");
        pane.getChildren().add(lblWelcome);
        
        btnOpenPort = new Button("Open USB port");
        btnOpenPort.setOnAction(e -> btnOpenPort_Click());
        pane.getChildren().add(btnOpenPort);
        
        btnReadData = new Button("Read data");
        btnReadData.setOnAction(e -> btnReadData_Click());
        pane.getChildren().add(btnReadData);
        
        btnClosePort = new Button("Close USB port");
        btnClosePort.setOnAction(e -> btnClosePort_Click());
        pane.getChildren().add(btnClosePort);
        
//        reader = new javacall();
        readerHdl = -1;
        System.out.println("constructor finished. reader initialized");
        
    }
    public VBox getPane()
    {
        return this.pane;
    }
    
    private void btnOpenPort_Click() {
        System.out.println("got click to open port");
        try {
            readerHdl = rfidDll.fw_init(100, 0); //100=USB
            System.out.println("initialized reader. handle = " + readerHdl);
        }
        catch (Exception ex) {
            System.out.println("cannot initialize reader. " + ex.getMessage());
        }        
    }
    
    private void btnReadData_Click() {
        System.out.println("got click to read data");
        int configStatus = rfidDll.fw_config_card(readerHdl, CARD_TYPE_ISO15693);
        System.out.println("config card status " + configStatus);
        
        char afi = 0x0;    
        char returnedLen = 0x0;
        char uidBuffer = 0x0;
//        int inventoryStatus = rfidDll.fw_inventory(readerHdl, READ_SINGLE_CARD, (char)0, 
//                    (char)0, returnedLen, uidBuffer);
        
//        char[] serNr = {56};
//        System.out.println("ser nr starting value " + serNr[0]);
//        
//        System.out.println("card ser no " + serNr[0]);
//        javacall.fw_read(readerHdl, s, serNr)
        byte[] uid = new byte[256];
//        javacall.fw_setcpu(readerHdl, s);
//        javacall.fw_select(readerHdl, readerHdl, shorts);
//        javacall.fw_read(readerHdl, s, serNr)
        
                
    }
    private void btnClosePort_Click() {
        System.out.println("got click to close port");
        try {
            int closeSuccess = rfidDll.fw_exit(readerHdl);
            System.out.println("close reader sucess " + (closeSuccess==0));
        }
        catch (Exception ex) {
            System.out.println("cannot close reader. " + ex.getMessage());
        }
        
    }
}
