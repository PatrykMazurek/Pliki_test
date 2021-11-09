package spider.com.thread.main;

import java.util.ArrayList;
import java.util.List;

public class UnicNumber {


    private List<Integer> unicNumber;

    public UnicNumber() {
        unicNumber = new ArrayList<>();
    }

    public void setNumber(int number){
        this.unicNumber.add(number);
    }

    public boolean checkNumber(int number){
        return this.unicNumber.contains(number);
    }

}
