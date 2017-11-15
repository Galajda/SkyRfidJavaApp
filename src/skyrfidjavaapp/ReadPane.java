/*
 * Copyright (C) 2017 Michal G. <Michal.G at cogitatummagnumtelae.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package skyrfidjavaapp;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

/**
 *
 * @author MichalG
 */
public class ReadPane 
{
//    private final AppState appState;
    
    private final VBox pane;
    private final Label lblWelcome;
//    private final Button btnOpenPort;
    private final Button btnReadData;
    private final Button btnClosePort;
    private final Label lblDecodedData;
    private final RfidNativeInterface rfidDll = RfidNativeInterface.INSTANCE;
    private final char READ_TAG_START_BLOCK = 0x1;
    private final char READ_TAG_BLOCK_LEN = 0x05;
    private int readerHdl;
    
    
    ReadPane() {
//        System.out.println("read pane constructor running");        
        pane = new VBox();
        pane.setMinWidth(300);
        lblWelcome = new Label("Welcome to the RFID reader.\nThe read mode is under construction.");
        pane.getChildren().add(lblWelcome);        
        
//        btnOpenPort = new Button("Open USB port");
//        btnOpenPort.setOnAction(e -> btnOpenPort_Click());
//        pane.getChildren().add(btnOpenPort);
        
        btnReadData = new Button("Read data");
        btnReadData.setOnAction(e -> btnReadData_Click());
        pane.getChildren().add(btnReadData);
        
        btnClosePort = new Button("Close USB port");
        btnClosePort.setOnAction(e -> btnClosePort_Click());
        pane.getChildren().add(btnClosePort);
        
        lblDecodedData = new Label("your number here");        
        lblDecodedData.setStyle("-fx-border-color: black; -fx-border-width: 2;"
                + "-fx-font-size:16px; -fx-alignment: center;" 
                + "-fx-min-width: 70; -fx-max-width:200; -fx-min-height: 30;");
        pane.getChildren().add(lblDecodedData);
        
        AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        System.out.println("read pane constructor sees multi read " + state.isMultiRead());
        System.out.println("read pane constructor sees anti theft action " + state.getAntiTheftAction());
        
        readerHdl = this.getDeviceHandle();
        System.out.println("initialized reader. handle = " + readerHdl);
        if (readerHdl<0) {
            lblDecodedData.setStyle("-fx-text-fill:#dc143c");
            lblDecodedData.setText("Error connecting to device. Try resetting or restarting.");
        }
//        System.out.println("read pane constructor finished.");
        
    }
    public VBox getPane()
    {
        return this.pane;
    }
    
//    private void btnOpenPort_Click() {
////        System.out.println("got click to open port");
//        try {
//            readerHdl = rfidDll.fw_init(AppConstants.USB_PORT, 0); //ignore baud rate
//            System.out.println("initialized reader. handle = " + readerHdl);
//        }
//        catch (Exception ex) {
//            System.out.println("cannot initialize reader. " + ex.getMessage());
//        }        
//    }
    
    private void btnReadData_Click() {
        System.out.println("got click to read data");
        int configStatus = rfidDll.fw_config_card(readerHdl, AppConstants.CARD_TYPE_ISO15693);        
        System.out.println("config card status " + (configStatus==0));
        
        char[] returnedUidLen = {0x0};
        System.out.println("returned len init hex val " + String.format("%04x", (int)returnedUidLen[0]) );
        char[] uidBuffer = new char[256];
//        int inventoryStatus = rfidDll.fw_inventory(readerHdl, AppConstants.READ_SINGLE_CARD,
//                READ_TAG_START_BLOCK, READ_TAG_BLOCK_LEN, returnedUidLen, uidBuffer);
        int inventoryStatus = rfidDll.fw_inventory(readerHdl, AppConstants.READ_SINGLE_CARD,
                (char)0x0, (char)0x0, returnedUidLen, uidBuffer);
        System.out.println("inventory status " + (inventoryStatus==0));
        System.out.println("\treturned uid length hex val" + String.format("%04x", (int)returnedUidLen[0]));
        System.out.println("\tuid buffer hex vals");
        System.out.print("\t");
        for (int i = 0; i <256; i++) {
            //stop at i = (int)returnedUidLen[0]?
            System.out.print("element" + i + ": " + String.format("%04x", (int)uidBuffer[i]) + ", ");
        }
        System.out.println();
                
        int selectStatus = rfidDll.fw_select_uid(readerHdl, (char)0x22, uidBuffer);
        System.out.println("select status " + (selectStatus==0));
        
        int resetReadyStatus = rfidDll.fw_reset_to_ready(readerHdl, (char)0x22, uidBuffer);
        System.out.println("reset to ready status " + (resetReadyStatus==0));
        
        
        char[] returnedReadLength = {0x0};
        System.out.println("returned read block len init hex val " + String.format("%04x", (int)returnedReadLength[0]) );
        char[] readDataBuffer = new char[256];
//        int securityInfoStatus = rfidDll.fw_get_securityinfo(readerHdl, (char)0x22, READ_TAG_START_BLOCK, 
//            READ_TAG_BLOCK_LEN, uidBuffer, returnedReadLength, readDataBuffer);
//        System.out.println("get security info status " + (securityInfoStatus==0));
//        System.out.println("\treturned read block length hex val " + String.format("%04x", (int)returnedReadLength[0]));
        //security info status does not appear to be necessary
        
        int readBlockStatus = rfidDll.fw_readblock(readerHdl, (char)0x22, READ_TAG_START_BLOCK, 
            READ_TAG_BLOCK_LEN, uidBuffer, returnedReadLength, readDataBuffer);
        System.out.println("read block status " + (readBlockStatus==0));
        //len of returned data is a better indicator than read block status
        System.out.println("\treturned read block length hex val " + String.format("%04x", (int)returnedReadLength[0]));
        System.out.println("\tread block buffer hex vals");
        System.out.print("\t");
//        int lastReadBlock = 
        for (int i = 0; i <=READ_TAG_BLOCK_LEN + 1; i++) {
            System.out.print("element" + i + ": " + String.format("%04x", (int)readDataBuffer[i]) + ", ");
        }
        System.out.println();
        String decodedData = TagEncoding.Decode(readDataBuffer, READ_TAG_BLOCK_LEN + 2);
        System.out.println("read pane got decoded data " + decodedData);
        lblDecodedData.setText(decodedData);
        
    }
    private void btnClosePort_Click() {
//        System.out.println("got click to close port");
        try {
            int closeSuccess = rfidDll.fw_exit(readerHdl);
            System.out.println("close reader sucess " + (closeSuccess==0));
        }
        catch (Exception ex) {
            System.out.println("cannot close reader. " + ex.getMessage());
        }
        
    }
    /**
     * The device may not be available immediately. Try for 2 seconds.
     * @return a positive integer if reader is found, -1 if fail
     */
    private int getDeviceHandle() {
        int h = -1;
        long startTime = System.currentTimeMillis();        
        do {
            h = rfidDll.fw_init(AppConstants.USB_PORT, 0); //ignore baud rate on USB port
        }
        while (((System.currentTimeMillis()-startTime)<2000) && (h < 0));        
        return h;        
    }
}
