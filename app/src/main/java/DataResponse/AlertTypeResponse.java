package DataResponse;

/**
 * Created by Ben on 12/6/2560.
 */

public class AlertTypeResponse {


    /**
     * alertType : 1
     * alertName : ดัชนีการเคลื่อนไหวต่ำกว่าเกณฑ์
     */

    private String alertType;
    private String alertName;

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }
}
