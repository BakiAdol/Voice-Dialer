/**
 *  Abdullhail Baki Adol
 */
package com.example.abdullahil.voicedialingmessageing;

import java.util.List;

public class sortingClass {
    List<getSetContaucts> data;

    public sortingClass(List<getSetContaucts> data) {
        this.data = data;
    }

    List<getSetContaucts> Sorting_Data(){

        for (int i=0;i<data.size()-1;i++)
        {
            int j=i+1;
            while (j>0 && data.get(j).getPC_name().compareTo(data.get(j-1).getPC_name())<0)
            {
                String name = data.get(j).getPC_name();
                String phone = data.get(j).getPC_phone();
                data.get(j).setPC_name(data.get(j-1).getPC_name());
                data.get(j).setPC_phone(data.get(j-1).getPC_phone());

                data.get(j-1).setPC_name(name);
                data.get(j-1).setPC_phone(phone);
                j--;
            }
        }

        return data;
    }
}
