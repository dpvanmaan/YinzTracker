package us.vanmaanen.yinztracker.RestAPI;

/**
 * Created by david on 2/25/15.
 */
public class Pred {
    String typ; //Type of prediction A for arrival D for departure
    String stpnm; //Stop Name
    String stpid; //Stop ID
    String rt; //Route name
    String rtdir; //Route Direction
    String prdtm; //Date and Time of bus Arrival
    String prdctdn; //Minutes to bus Arrival
    String msg;
    public Pred(){}

    public void setStpid(String stpid) {
        this.stpid = stpid;
    }

    public void setStpnm(String stpnm) {
        this.stpnm = stpnm;
    }

    public void setPrdctdn(String prdctdn) {
        this.prdctdn = prdctdn;
    }

    public void setPrdtm(String prdtm) {
        this.prdtm = prdtm;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public void setRtdir(String rtdir) {
        this.rtdir = rtdir;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getStpnm() {
        return stpnm;
    }

    public String getStpid() {
        return stpid;
    }

    public String getPrdctdn() {
        return prdctdn;
    }

    public String getPrdtm() {
        return prdtm;
    }

    public String getRtdir() {
        return rtdir;
    }

    public String getRt() {
        return rt;
    }

    public String getTyp() {
        return typ;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }
    public String getMsg(){
        return msg;
    }

}
