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
import javafx.event.ActionEvent;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author MichalG
 */
public class ReadPane 
{
    private final VBox pane;
    private final Label lblWelcome;
    private final Button btnReadMulti;  
    private final Label lblDecodedData;
    private final String LBL_STYLE_ERROR = "-fx-border-color: black; -fx-border-width: 2;"
                + "-fx-font-size:12px; -fx-alignment: center; -fx-text-fill: #dc143c;" 
                + "-fx-min-width: 70; -fx-max-width:200; -fx-min-height: 30;";            
    private final String LBL_STYLE_OK = "-fx-border-color: black; -fx-border-width: 2;"
                + "-fx-font-size:16px; -fx-alignment: center; -fx-text-fill: #000000;" 
                + "-fx-min-width: 70; -fx-max-width:200; -fx-min-height: 30;";
    private final Button btnStartReading;
    private final Button btnStopReading;
    private TagReader reader;
    private final int READ_FREQUENCY = 2012; //eventually get this from app state
//    private int portFailureCounter;
    private Timer tmr;
    private boolean runRabbitRun = true; //flag to stop the auto read timer
    //constructor
    ReadPane() {
//        System.out.println("read pane constructor running");        
        pane = new VBox();
        pane.setMinWidth(250);
        
        lblWelcome = new Label("Welcome to the RFID reader.\nThe read mode is under construction.");
        pane.getChildren().add(lblWelcome);        
        
        btnReadMulti = new Button("Read multiple");
        btnReadMulti.setOnAction(e -> btnReadMulti_Click(e));
        pane.getChildren().add(btnReadMulti);
        
        lblDecodedData = new Label("your number here");        
        lblDecodedData.setStyle(this.LBL_STYLE_OK);
        pane.getChildren().add(lblDecodedData);
        
        btnStartReading = new Button("Start auto reader");
        btnStartReading.setOnAction(e -> btnStartReading_Click(e));
        pane.getChildren().add(btnStartReading);
        
        btnStopReading = new Button("Stop the automatic reader");
        btnStopReading.setOnAction(e -> btnStopReading_Click(e));
        pane.getChildren().add(btnStopReading);
        
//        portFailureCounter = 0;
//        tmr = new Timer();
        
//        AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
//        System.out.println("read pane constructor sees multi read " + state.isMultiRead());
//        System.out.println("read pane constructor sees anti theft action " + state.getAntiTheftAction());
        
        
        
//        System.out.println("read pane constructor finished.");
        
    }
    public VBox getPane()
    {
        return this.pane;
    }
    
    private void btnReadMulti_Click(ActionEvent e) {
        FxMsgBox.show("multi read test uses common reader object\nerrors may occur if timer is running", "Warning: Test code");
        System.out.println("got click to read deck");       
        reader = new TagReader();

        String[] deckData = reader.readDeck();
        
        System.out.println("there are " + deckData.length + " cards in the deck");
//        switch (cardData) {
//            case DeviceErrorCodes.ERR_NO_HANDLE:                
//                lblDecodedData.setStyle(this.LBL_STYLE_ERROR);
//                lblDecodedData.setText("Error connecting to device.");
//                if (++portFailureCounter > 5) {
//                    lblDecodedData.setText("Multiple errors connecting to device. Check connections and restart.");
//                }
//                break;
//            case DeviceErrorCodes.ERR_MULTIPLE_CARDS:
//                lblDecodedData.setStyle(this.LBL_STYLE_ERROR);
//                lblDecodedData.setText("Multiple tags detected in single-card mode.");
//                break;
//            default:
//                lblDecodedData.setStyle(this.LBL_STYLE_OK);
//                lblDecodedData.setText(cardData);        
//                portFailureCounter = 0;
//        }
        reader.closePort();
//        reader = null;
    }
    
    private void btnStartReading_Click(ActionEvent e) {
        this.startTimer();
    }
    
    private void btnStopReading_Click(ActionEvent e) {
//        runRabbitRun = false; //better to change flag than to call stopTimer.
        this.stopTimer();
//        System.out.println("btn click to stop the auto reader.");
    }
    
    public void startTimer() {
        reader  = new TagReader();
        runRabbitRun = true;
        tmr = new Timer(); //instantiating tmr here rather than in constructor allows restarting
        tmr.schedule(new AutoRead(), 0, this.READ_FREQUENCY);
        //Fixed-delay execution is deliberately chosen over fixed-rate execution
        //so that tasks do not overlap. If this works as I expect, no adjustment will be 
        //needed for multiple cards. A possible complication is the runLater method within
        //the timer task. If this runs independently of the timer, then the display may be confused.
    }
    public void stopTimer() {
        System.out.println("stop timer fcn called");
        if (reader != null) {reader.closePort();}
        //cancel the current timer thread.
        if (tmr != null) {            
            tmr.cancel();
            tmr.purge();
        }
        //the flag is communicated to all timer threads, in case one has become detached
        //from the current timer
        runRabbitRun = false;                 
        
    }
    class AutoRead extends TimerTask {
        @Override
        public void run() {
            
            if (runRabbitRun) {                           
                //JavaWorld.com exploring JavaFX's Application class
                System.out.println("auto read task running at " + System.currentTimeMillis());
                System.out.println("timer thread " + Thread.currentThread());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
//                        lblDecodedData.setText("timer tick " + System.currentTimeMillis());  
                        String[] cardData = reader.readOneCard();     
//                        System.out.println("read one card " + cardData);
                        displayTags(cardData);                        
                    }
                });                
            }
            else { 
                this.cancel();
                //this.cancel() is preferable to tmr.cancel() 
                //in case of multiple timer threads, this.cancel() will stop each thread.
                //tmr.cancel() will stop only the most recent, the current instance of Timer.
                System.out.println("timer got flag to stop");
            }
        }   
    }
    private void displayTags(String[] deck) {
        for (String oneCard : deck) {
            switch (oneCard) {
                case DeviceErrorCodes.ERR_NO_HANDLE:                
                    lblDecodedData.setStyle(this.LBL_STYLE_ERROR);
                    lblDecodedData.setText("Error connecting to device. Please check connections and restart.");
                    this.stopTimer();
                    break;
                case DeviceErrorCodes.ERR_NO_CARD:
                    lblDecodedData.setStyle(this.LBL_STYLE_OK);
                    lblDecodedData.setText("no tag");
                    break;
                case DeviceErrorCodes.ERR_MULTIPLE_CARDS:
                    lblDecodedData.setStyle(this.LBL_STYLE_ERROR);
                    lblDecodedData.setText("Multiple tags detected in single-card mode.");
                    break;
                default:
                    lblDecodedData.setStyle(this.LBL_STYLE_OK);
                    lblDecodedData.setText(oneCard);
            }
            //display for 1 sec, then show next number.
        }
        
    }
}
