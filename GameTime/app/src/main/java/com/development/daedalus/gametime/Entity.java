package com.development.daedalus.gametime;

/**
 * Created by AJR on 2015/02/15.
 */
public class Entity {
    private String Name;
    private String Location;
    private String Code;

    Entity(){

    }

    Entity(String name,String location, String code){
        SetName(name);
        SetLocation(location);
        SetCode(code);

    }

    public void SetName(String s){
        this.Name = s;
    }
    public void SetLocation(String s){
        this.Location = s;
    }
    public void SetCode(String s){
        this.Code = s;
    }

    public String GetName(){
        return this.Name;
    }
    public String GetLocation(){
        return this.Location;
    }
    public String GetCode(){
        return this.Code;
    }
}
