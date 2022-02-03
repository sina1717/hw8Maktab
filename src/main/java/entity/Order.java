package entity;

public class Order {

    private int id;
    private Product product;
    private Customer customer;
    private ShoppingCard shoppingCard;

    public Order(int id, Product product, Customer customer, ShoppingCard shoppingCard) {
        this.id = id;
        this.product = product;
        this.customer = customer;
        this.shoppingCard = shoppingCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ShoppingCard getShoppingCard() {
        return shoppingCard;
    }

    public void setShoppingCard(ShoppingCard shoppingCard) {
        this.shoppingCard = shoppingCard;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", product=" + product +
                ", customer=" + customer +
                ", shoppingCard=" + shoppingCard +
                '}';
    }
}
