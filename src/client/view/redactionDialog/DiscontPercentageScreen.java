package client.view.redactionDialog;

import javafx.scene.control.Label;



public class DiscontPercentageScreen extends Label {


    private Integer persantage;


    public DiscontPercentageScreen()
    {
       persantage=new Integer(0);
       this.setText("                                                   " +
               "                                                                 " +
               "                                              Discont persentage "+persantage+"%" );
    }


    public void setPercentage(Integer percentage)
    {
        this.persantage=percentage;
        this.setText("                                                   " +
                "                                                                 " +
                "                                              Discont persentage "+persantage+"%" );



    }

}




