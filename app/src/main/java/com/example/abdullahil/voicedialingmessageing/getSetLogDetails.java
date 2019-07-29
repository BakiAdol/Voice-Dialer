/**
 *  Abdullhail Baki Adol
 */
package com.example.abdullahil.voicedialingmessageing;

public class getSetLogDetails {
    String name,number,type,date;

    public getSetLogDetails(String name, String number, String type, String date) {
        this.name = name;
        this.number = number;
        this.type = type;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
