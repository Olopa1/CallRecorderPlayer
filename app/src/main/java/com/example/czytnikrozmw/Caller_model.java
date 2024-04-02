package com.example.czytnikrozmw;

import android.net.Uri;


public class Caller_model {
    String name;
    Uri call_uri;
    String day;
    String month;
    String year;
    String hour;
    String minute;
    String second;

    public Caller_model(String fileName, Uri call_uri) {
        this.call_uri = call_uri;
        String[] formated = separate(fileName);
        this.name = formated[0];
        this.year = formated[1];
        this.month = formated[2];
        this.day = formated[3];
        this.hour = formated[4];
        this.minute = formated[5];
        this.second = formated[6];
    }


    private String[] separate(String fullName){
        String[] separateString = new String[7];
        for(int i = 0;i < 7;i++){
            separateString[i] = "";
        }
        int stringPos;
        for(stringPos = 0;  ;stringPos++){
            if(fullName.charAt(stringPos) == ' '){
                break;
            }
        }
        stringPos++;
        for(stringPos = stringPos;  ;stringPos++){
            if(fullName.charAt(stringPos) == ' '){
                break;
            }
        }
        stringPos++;
        for(stringPos = stringPos;;stringPos++){
            if(fullName.charAt(stringPos) == '_'){
                break;
            }
            separateString[0] += fullName.charAt(stringPos);
        }
        stringPos++;
        //data
        for(int i = 0;i < 2;i++){
            separateString[1] += fullName.charAt(stringPos);
            stringPos++;
        }
        for(int i = 0;i < 2;i++){
            separateString[2] += fullName.charAt(stringPos);
            stringPos++;
        }
        for(int i = 0;i < 2;i++){
            separateString[3] += fullName.charAt(stringPos);
            stringPos++;
        }
        //godzina
        stringPos++;
        for(int i = 0;i < 2;i++){
            separateString[4] += fullName.charAt(stringPos);
            stringPos++;
        }
        for(int i = 0;i < 2;i++){
            separateString[5] += fullName.charAt(stringPos);
            stringPos++;
        }
        for(int i = 0;i < 2;i++){
            separateString[6] += fullName.charAt(stringPos);
            stringPos++;
        }
        return separateString;
    }

    public String getName() {
        return name;
    }

    public Uri getCall_uri() {
        return call_uri;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getSecond() {
        return second;
    }
}
