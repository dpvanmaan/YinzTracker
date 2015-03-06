package us.vanmaanen.yinztracker.RestAPI;

/**
 * Created by david on 2/25/15.
 */
public class Stops {
    String stpid;
    String stpnm;
    float lat;
    float lon;
    String msg;
    public Stops(){}

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public void setStpid(String stpid) {
        this.stpid = stpid;
    }

    public void setStpnm(String stpnm) {
        this.stpnm = stpnm;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getStpid() {
        return stpid;
    }
    public int getStopId(){
        return Integer.parseInt(this.stpid);
    }

    public String getStpnm() {
        return stpnm;
    }

    @Override
    public String toString() {
        return stpnm;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }
    public String getMsg(){
        return msg;
    }

}
