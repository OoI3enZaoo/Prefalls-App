package DataResponse;

/**
 * Created by Ben on 12/6/2560.
 */

public class LatLongResponse {

    /**
     * lat : 14.08125802
     * lng : 100.60435793
     * tstamp : 2017-06-06 11:21:32.0
     * stab : 1.28306
     * sym : 0.550791
     * spd : 1.0918516368492555
     */

    private String lat;
    private String lng;
    private String tstamp;
    private String stab;
    private String sym;
    private String spd;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getTstamp() {
        return tstamp;
    }

    public void setTstamp(String tstamp) {
        this.tstamp = tstamp;
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
}
