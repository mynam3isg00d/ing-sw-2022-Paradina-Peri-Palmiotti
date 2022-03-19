package Model;

public class Assistant {
    //orderNumber goes from 1 to 10
    private int orderNumber;
    private int motherNumber;

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
