import eceptions.*;
import entity.*;
import repository.MyConnection;
import service.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    private static CustomerService customerService;
    private static AdminService adminService;
    private static ProductService productService;
    private static CategoryService categoryService;
    private static OrderService orderService;
    private static ShoppingCardService shoppingCardService;

    public static void main(String[] args) {
        customerService=new CustomerService();
        adminService = new AdminService();
        productService  = new ProductService();
        categoryService = new CategoryService();
        orderService = new OrderService();
        shoppingCardService = new ShoppingCardService();



        String loginMenu = " 1 : CUSTOMER LOGIN \n 2 : ADMIN LOGIN \n 3 : CUSTOMER SIGN UP \n 4 : EXIT \n ENTER NUMBER :";
        Boolean state = true;

            while (state) {
                try {
                    System.out.println("------------------------------------------------------");
                    System.out.print(loginMenu);
                    int n = getNumber();
                    switch (n) {
                        case 1:
                            System.out.print(" ENTER YOUR USERNAME : ");
                            String userName = scanner.nextLine();
                            System.out.print(" ENTER YOUR PASSWORD : ");
                            String passWord = scanner.nextLine();
                            Customer customer = customerService.login(userName, passWord);
                            if(customer == null){
                                throw new NotFoundException("wrong username password");
                            }
                            customerPage(customer);
                            break;
                        case 2:
                            System.out.print(" ENTER YOUR USERNAME : ");
                            String user = scanner.nextLine();
                            System.out.print(" ENTER YOUR PASSWORD : ");
                            String pass = scanner.nextLine();
                             Admin admin= adminService.login(user, pass);
                            if(admin == null){
                                throw new NotFoundException("wrong username password");
                            }
                            adminPage(admin);
                            break;
                        case 3:
                            System.out.print(" ENTER YOUR USERNAME : ");
                            String u = scanner.nextLine();
                            System.out.print(" ENTER YOUR PASSWORD : ");
                            String p = scanner.nextLine();
                            System.out.print(" ENTER YOUR ADDRESS : ");
                            String address = scanner.nextLine();
                            customerService.save(new Customer(0, u, p, address));
                            break;
                        case 4:
                            System.out.println(" BAY ...");
                            state = false;
                            break;
                    }
                } catch (InvalidValueEntered e) {
                    System.out.println(e.getMessage());
                } catch (ConnectionException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidNationalCodeException e) {
                    System.out.println(e.getMessage());
                } catch (NotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (SaveException e) {
                    System.out.println(e.getMessage());
                }
            }
        try {
            MyConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getNumber(){
        int number;
        try {
            number = scanner.nextInt();
        }catch (Exception e){
            throw new InvalidValueEntered("please enter number not alphabet ");
        }
        scanner.nextLine();
        return number;
    }

    private static void customerPage(Customer customer){
        boolean state = true;
        String menu = " 1 : SEE ALL PRODUCTS \n 2 : SEARCH PRODUCT BY CATEGORY \n 3 : ADD TO SHOPPING CARD \n 4 : SEE MY ORDERS \n 5 : PAYMENT \n 6 : EXIT ";
        while (state){
            System.out.println(menu);
            int n = getNumber();
            switch (n){
                case 1 :
                    productService.findAll();
                    break;
                case 2 :
                    categoryService.findAll();
                    System.out.print(" ENTER CATEGORY ID :");
                    int id = getNumber();
                    productService.findByCategory(id);
                case 3 :
                    System.out.print("enter your product id :");
                    int productId = getNumber();
                    ShoppingCard shoppingCard = new ShoppingCard(0,new Date(Calendar.getInstance().getTime().getTime()),false);
                    int shoppingCardId = shoppingCardService.save(shoppingCard);
                    shoppingCard.setId(shoppingCardId);
                    orderService.save(new Order(0,productService.findById(productId),customer,shoppingCard));
                    break;
                case 4 :
                    orderService.findAll();
                    break;
                case 5 :
                    orderService.findAll();
                    System.out.print("enter your order id to pay :");
                    int orderId = getNumber();
                    Order order = orderService.findById(orderId);
                    order.getShoppingCard().setPayed(true);
                    shoppingCardService.update(order.getShoppingCard());
                    break;
                case 6 :
                    state = false;
                    break;
                default:
                    System.out.println("enter valid number");
                    break;
            }

        }
    }
    private static void adminPage(Admin admin) {
        boolean state = true;
        String menu = " 1 : ADD PRODUCT \n 2 : ADD CATEGORY \n 3 : ADD NEW ADMIN \n 4 : DELETE PRODUCT \n 5 : SEARCH PRODUCT WITH CATEGORY \n 6 : EXIT ";
        while (state) {
            System.out.println(menu);
            int n = getNumber();
            switch (n) {
                case 3:
                    System.out.println(" enter user name : ");
                    String username = scanner.nextLine();
                    System.out.println(" enter password  : ");
                    String password = scanner.nextLine();
                    System.out.println(" enter nationalCode : ");
                    String nationalCode = scanner.nextLine();
                    adminService.save(new Admin(0,username,password,nationalCode));
                    break;
                case 2:
                    categoryService.findAll();
                    System.out.println("enter parent category id :");
                    int id = getNumber();
                    System.out.println(" enter title : ");
                    String title = scanner.nextLine();
                    System.out.println(" enter description : ");
                    String description = scanner.nextLine();
                    categoryService.save(new Category(0,title,description,categoryService.findById(id)));
                case 1:
                    categoryService.findAll();
                    System.out.println("enter parent category id :");
                    int categoryId = getNumber();
                    System.out.println(" enter product name : ");
                    String name = scanner.nextLine();
                    System.out.println(" enter description : ");
                    String pDescription = scanner.nextLine();
                    System.out.println(" enter product quantity : ");
                    int qty = getNumber();
                    System.out.println(" enter product price : ");
                    int price = getNumber();
                    productService.save(new Product(0,name,pDescription,categoryService.findById(categoryId),qty,price));
                    break;
                case 4:
                    productService.findAll();
                    System.out.println(" enter product id : ");
                    int productId = getNumber();
                    productService.delete(productId);
                    break;
                case 5:
                    categoryService.findAll();
                    System.out.print(" ENTER CATEGORY ID :");
                    int cId = getNumber();
                    productService.findByCategory(cId);
                    break;
                case 6:
                    state = false;
                    break;
                default:
                    System.out.println("enter valid number");
                    break;
            }
        }
    }
}
