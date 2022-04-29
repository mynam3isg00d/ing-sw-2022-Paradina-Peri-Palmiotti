package Model;

import java.util.Objects;

public class Assistant {
    //orderNumber goes from 1 to 10
    private int orderNumber;
    private int motherNumber;

    @Override
    public String toString() {
        return "Assistant{" +
                "orderNumber=" + orderNumber +
                ", motherNumber=" + motherNumber +
                '}';
    }

    public Assistant(int orderNumber) {
        this.orderNumber = orderNumber;
        motherNumber = ((orderNumber - 1) / 2) + 1;
    }

    public int getMotherNumber() {
        return motherNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }
}
