package DataResponse;

/**
 * Created by Ben on 12/6/2560.
 */

public class LatLongResponse {

    private String lat;
    private String lng;
    private String tstamp;

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
    public String getTstamp(){
        return tstamp;
    }
    public void setTstamp(String tstamp){
        this.tstamp = tstamp;
    }
}
