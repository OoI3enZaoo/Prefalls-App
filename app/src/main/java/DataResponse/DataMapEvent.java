package DataResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ben on 17/6/2560.
 */

public class DataMapEvent {
    String pid;
    Double lat;
    Double lng;
    String stab;
    String sym;
    String spd;
    String ts;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getStab() {
        return stab;
    }

    public void setStab(String stab) {
        this.stab = stab;
    }

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public String getSpd() {
        return spd;
    }

    public void setSpd(String spd) {
        this.spd = spd;
    }

    public String getTs() {
        return ts;
    }
    public void setTs(Long ts) {
        Date date = new Date(ts);
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String java_date = jdf.format(date);
        this.ts = java_date;
    }

}
