package us.vanmaanen.yinztracker.RestAPI;

/**
 * Created by david on 2/25/15.
 */
public class Dirs {
    String dir;
    String msg;
    public Dirs(){}
    public void setDirection(String direction) {
        this.dir = direction;
    }

    public String getDirection() {
        return dir;
    }

    @Override
    public String toString() {
        return dir;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }
    public String getMsg(){
        return msg;
    }
}
