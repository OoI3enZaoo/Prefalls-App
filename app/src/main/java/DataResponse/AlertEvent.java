package DataResponse;

/**
 * Created by Ben on 11/6/2560.
 */

public class AlertEvent {
    String pid;
    int type;
    Long start;
    Long end;
    String lat;
    String lng;

//object ที่รับค่าหลังจากถูกแปลงจาก xml
    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getPid(){
        return pid;
    }
    public void setType(int type) {
        this.type = type;
    }
    public Integer getType(){
        return type;
    }

    public void setStart(Long start) {
        this.start = start;
    }
    public Long getStart(){
        return start;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
    public Long getEnd(){
        return end;
    }
    public void setLat(String lat){
        this.lat = lat;
    }
    public String getLat(){
        return lat;
    }
    public void setLng(String lng){
        this.lng = lng;
    }
    public String getLng(){
        return lng;
    }
}
