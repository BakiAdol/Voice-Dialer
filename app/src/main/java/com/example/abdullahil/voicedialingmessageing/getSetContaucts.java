/**
 *  Abdullhail Baki Adol
 */
package com.example.abdullahil.voicedialingmessageing;

/*-->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>>>>>>>>>>this class use for set all contaucts from phone book in List and then get>>>>>>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/public class getSetContaucts {//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private String PC_id;
    private String PC_name;
    private String PC_phone;

    public getSetContaucts(String PC_id, String PC_name, String PC_phone) {
        this.PC_id = PC_id;
        this.PC_name = PC_name;
        this.PC_phone = PC_phone;
    }

    public String getPC_id() {
        return PC_id;
    }

    public void setPC_id(String PC_id) {
        this.PC_id = PC_id;
    }

    public String getPC_name() {
        return PC_name;
    }

    public void setPC_name(String PC_name) {
        this.PC_name = PC_name;
    }

    public String getPC_phone() {
        return PC_phone;
    }

    public void setPC_phone(String PC_phone) {
        this.PC_phone = PC_phone;
    }
}
