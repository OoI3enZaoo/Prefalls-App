package DataResponse;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Ben on 11/6/2560.
 */

public class AlertEvent {
    String pid;
    int type;
    String start;
    String end;
    String lat;
    String lng;
    String sym;
    String stab;
    Long ts;
    String spd;

    //object ที่รับค่าหลังจากถูกแปลงจาก xml
    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setStart(Long start) {
        Date date = new Date(start);
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String java_date = jdf.format(date);
        this.start = java_date;
    }
    public String getStart() {
        return start;
    }
    public void setEnd(Long end) {
        Date date = new Date(end);
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String java_date = jdf.format(date);
        this.end = java_date;
    }

    public String getEnd() {
        return end;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLat() {
        return lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLng() {
        return lng;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public String getSym() {
        return sym;
    }

    public void setStab(String stab) {
        this.stab = stab;
    }

    public String getStab() {
        return stab;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public Long getTs() {
        return ts;
    }

    public void setSpd(String spd) {
        this.spd = spd;
    }

    public String getSpd() {
        return spd;
    }
}
