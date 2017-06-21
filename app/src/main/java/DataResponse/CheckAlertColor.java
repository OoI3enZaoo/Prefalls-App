package DataResponse;

/**
 * Created by Ben on 15/6/2560.
 */

public class CheckAlertColor {
    private  static String color = "#CCFFBB";



    public static String CheckAlertColor(int type){
        if(type == 3 || type == 8){color = "#FFCC00";}else if(type == 7 || type == 4 || type == 9){color = "#B33A3A";}
        return color;
    }
}
