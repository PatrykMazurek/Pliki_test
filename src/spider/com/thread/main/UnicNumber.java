package spider.com.thread.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UnicNumber {


    private List<Integer> unicNumber;

    public UnicNumber() {
        unicNumber = new ArrayList<>();
    }

    public void setNumber(int number){
        this.unicNumber.add(number);
    }

    public boolean checkNumber(int number){
        if(this.unicNumber.contains(number)){
            return true;
        }
        return false;
    }

    public void showDuplicate(){
        List<Integer> duplicate = this.unicNumber.stream()
                .filter(n -> Collections.frequency(this.unicNumber, n) >1 )
                .collect(Collectors.toList());

        for (int nr : duplicate){
            System.out.println(nr);
        }
    }

}
