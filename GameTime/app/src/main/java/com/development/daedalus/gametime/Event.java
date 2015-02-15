package com.development.daedalus.gametime;

import java.util.Date;

/**
 * Created by AJR on 2015/02/15.
 */
public class Event {
    private long StartMillis;
    private long EndMillis;
    private Date StartDate;
    private Date EndDate;

    private String TeamXCode;

    private String TeamYCode;

    private String Location;

    Event(){

    }
    Event(long startMillis,long endMillis,String teamXCode, String teamYCode, String location){
        SetStartMillis(startMillis);
        SetEndMillis(endMillis);
        SetTeamXCode(teamXCode);
        SetTeamYCode(teamYCode);
        SetLocation(location);
    }

    public void SetStartMillis(long l){
        this.StartMillis = l;
    }
    public void SetEndMillis(long l){
        this.EndMillis = l;
    }

    public void SetTeamXCode(String s){
        this.TeamXCode = s;
    }

    public void SetTeamYCode(String s){
        this.TeamYCode = s;
    }

    public void SetLocation(String s){
        this.Location = s;
    }

    public long GetStartMillis(){
        return this.StartMillis;
    }

    public long GetEndMillis(){
        return this.EndMillis;
    }
    public String GetTeamXCode(){
        return this.TeamXCode;
    }
    public String GetTeamYCode(){
        return this.TeamYCode;
    }
    public String GetLocation(){
        return this.Location;
    }

}
