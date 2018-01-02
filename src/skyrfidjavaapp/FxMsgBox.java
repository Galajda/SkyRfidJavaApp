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

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
//import javafx.event.Event;
import javafx.event.EventHandler;



/**
 *
 * @author MichalG
 */
public class FxMsgBox {
    private static final String BTN_OK_TXT = "OK";
    private static final String BTN_CANCEL_TXT = "Cancel";
    private Boolean confirmDialogResult;
    private Boolean loginSuccess;
    
    private static final String LOGIN_TITLE = "Login";
    private static final String LOGIN_LABEL = "Enter the password";
    
    public FxMsgBox() { 
        confirmDialogResult = false;
        loginSuccess = false;
    }
    
    /**
     * Displays a message box using FX tools
     * @param message The body of the message
     * @param title The title of the window
     */
    public static void show(String message, String title)
    {        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(300);
        stage.setMaxWidth(600);
        //set width according to content. 
        stage.getIcons().add(new Image("skyrfidjavaapp/images/attention.jpg"));
        
        Label msgArea = new Label(message); //how to wrap message?
        Button btnOk = new Button(FxMsgBox.BTN_OK_TXT);
        btnOk.setOnAction(e->stage.close());
        VBox pane = new VBox(40);
        pane.getChildren().addAll(msgArea, btnOk);
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.showAndWait();
    }
    public Boolean confirm(String message, String title) {
        Stage stage = new Stage();
//        Boolean response = false;
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(300);
        stage.setMaxWidth(900);
        //set width according to content. 
        stage.getIcons().add(new Image("skyrfidjavaapp/images/question.jpg"));
        
        Label msgArea = new Label(message); //how to wrap message?
        Button btnOk = new Button(FxMsgBox.BTN_OK_TXT);
//        btnOk.setOnAction(e -> getAnswer(e, stage));
//        MyClickHandler ch = new MyClickHandler();
        btnOk.setOnAction(e -> getAnswer(e, stage));
        Button btnCancel = new Button(FxMsgBox.BTN_CANCEL_TXT);
        btnCancel.setOnAction(e -> getAnswer(e, stage));
        VBox pane = new VBox(20);
        HBox btnPane = new HBox(10);
        btnPane.setAlignment(Pos.CENTER);
        btnPane.getChildren().addAll(btnOk, btnCancel);
        pane.getChildren().addAll(msgArea, btnPane);
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        System.out.println("about to wait");
        stage.showAndWait(); //thread waits here until button is clicked
        System.out.println("done waiting");
//        stage.close();
        return confirmDialogResult;
        
    }
    private void getAnswer(ActionEvent e, Stage s) {
//        System.out.println("ok/cancel clicked " + e.getSource());
        
        String name = "";
        if (e.getSource() instanceof Button) {
            Button b = (Button)e.getSource();
            name = b.getText();
//            System.out.println("button name " + name);
        }
        confirmDialogResult = name.equals(FxMsgBox.BTN_OK_TXT);
        s.close();
//        return (name.equals(FxMsgBox.BTN_OK_TXT));
    }
    
    //anonymous inner class event handler
    //new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        response = true; var must be final for reference from here
//                        
//                        
//                    }
    //if the class implements EventHandler, setOnAction must specify this. However, non-static
    //reference is not allowed in a static method.
//    @Override
//    public void handle(Event event) {
//        System.out.println("handling the click with fxmsgbox implementation");
//    }
    
    //the basic problem is that a click handler has a void return type. you cannot return a value
//    private static class MyClickHandler implements EventHandler {
//        @Override
//        public void handle(Event event) {
//            System.out.println("handling the click in inner class");
//        }        
//    }
    /**
     * experimental. use the style of the pwd field to indicate success
     * @param realPassword where should the app store this? hash in xml?
     * @return 
     */
    public Boolean login(String realPassword) {
        
        final Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(FxMsgBox.LOGIN_TITLE);
        stage.setMinWidth(300);
        stage.setMaxWidth(600);
        //set width according to content. 
        stage.getIcons().add(new Image("skyrfidjavaapp/images/attention.jpg"));
//        Boolean loginSuccess2 = false;
        final Label lblPassword = new Label(FxMsgBox.LOGIN_LABEL); //how to wrap message?
        final PasswordField pwdField = new PasswordField();
        Button btnOk = new Button(FxMsgBox.BTN_OK_TXT);
//        btnOk.setOnAction(e->validatePassword(e, pwdField.getText(), realPassword, stage));
        btnOk.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Boolean valid = (pwdField.getText() != null && 
                            realPassword != null  && pwdField.getText().equals(realPassword));
                    if (valid) {
                        pwdField.setStyle(AppConstants.STYLE_TEXT_FLD_OK);
                        //let user x out?
//                            stage.close();
                    }
                    else {
                        pwdField.setStyle(AppConstants.STYLE_TEXT_FLD_FAIL);
                    }
                }
            }
        );
        VBox pane = new VBox(30);
        pane.getChildren().addAll(lblPassword, pwdField, btnOk);
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.showAndWait();
        
        return pwdField.getStyle().equals(AppConstants.STYLE_TEXT_FLD_OK);
        //flip because app constant style is guaranteed to be non-null?
    }
    private void validatePassword(ActionEvent e, String testPwd, String realPwd, Stage s) {
        System.out.print("validating password " + testPwd + " against " + realPwd);
        loginSuccess = (testPwd != null && realPwd != null  && testPwd.equals(realPwd));
        System.out.println(" result " + loginSuccess);
        if (loginSuccess) {
            s.close();
        }
        else {
            
        }
        
        
    }
}
