package entity;

public class Customer extends User{

    private String address;


    public Customer(int id, String username, String password, String address) {
        super(id, username, password);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "address='" + address + '\'' +
                '}';
    }
}
