package sg.edu.rp.desmond.fypaircontrollers;

/**
 * Created by 15017452 on 21/6/2017.
 */

public class TimeSlot {
    private String date;
    private String direction;
    private String flightNo;
    private String gateID;
    private String planeID;
    private String time;


    public TimeSlot(){

    }

    public TimeSlot(String date, String direction, String flightNo, String gateID, String planeID, String time) {
        this.date = date;
        this.direction = direction;
        this.flightNo = flightNo;
        this.gateID = gateID;
        this.planeID = planeID;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getPlaneID() {
        return planeID;
    }

    public void setPlaneID(String planeID) {
        this.planeID = planeID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGateID() {
        return gateID;
    }

    public void setGateID(String gateID) {
        this.gateID = gateID;
    }
}

