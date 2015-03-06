package us.vanmaanen.yinztracker.RestAPI;

/**
 * Created by david on 2/25/15.
 */
public class BusRoute {
    String rt;
    String rtnm;
    String rtclr;
    String msg;
    public BusRoute(){
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public void setRtclr(String rtclr) {
        this.rtclr = rtclr;
    }

    public void setRtnm(String rtnm) {
        this.rtnm = rtnm;
    }
    public String getRt(){
        return this.rt;
    }

    @Override
    public String toString() {
        return rt+" : "+rtnm;
    }

    public String getRtclr() {
        return rtclr;
    }

    public String getRtnm() {
        return rtnm;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }
    public String getMsg(){
        return msg;
    }
}
