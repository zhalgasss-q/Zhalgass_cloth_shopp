package model;

public abstract class ClothingItem implements Discountable {

    protected int id;
    protected String name;
    protected double price;

    public ClothingItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public abstract String getType();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public double applyDiscount(double percent) {
        return price - (price * percent / 100);
    }

    @Override
    public String toString() {
        return getType() +
                " | ID: " + id +
                " | Name: " + name +
                " | Price: " + price;
    }
}