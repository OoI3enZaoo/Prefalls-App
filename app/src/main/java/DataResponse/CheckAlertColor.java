package DataResponse;

/**
 * Created by Ben on 15/6/2560.
 */

public class CheckAlertColor {
    private  static String color = "#bae1e1";



    public static String CheckAlertColor(int type){
        if(type == 3 || type == 4){color = "#FFCC00";}else if(type == 7 || type == 8 || type == 9){color = "#B33A3A";}
        return color;
    }
}
