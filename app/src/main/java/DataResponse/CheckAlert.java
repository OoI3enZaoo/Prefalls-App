package DataResponse;

import Activity.R;

/**
 * Created by Ben on 15/6/2560.
 */

public class CheckAlert {
    private  static String color = "#CCFFBB";

    public static String CheckAlertColor(int type){
        if(type == 3 || type == 8){color = "#FFCC00";}
        else if(type == 7 || type == 4 || type == 9){color = "#B33A3A";}
        else if(type == 5 || type == 6){ color = "#FFCC00";}
        else if(type == 2){ color = "#FFCC00";}
        return color;
    }
    public static int CheckAlertSound(int type){
        int alert;
        if(type == 7 || type == 4 || type == 9){
            alert = R.raw.danger_sound;
        }else{
            alert = R.raw.warning_sound;
        }
        return alert;
    }
}
