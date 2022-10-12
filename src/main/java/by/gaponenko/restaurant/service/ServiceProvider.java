package by.gaponenko.restaurant.service;

import by.gaponenko.restaurant.service.impl.MenuServiceImpl;
import by.gaponenko.restaurant.service.impl.OrderServiceImpl;
import by.gaponenko.restaurant.service.impl.PaymentServiceImpl;
import by.gaponenko.restaurant.service.impl.UserServiceImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private UserService userService = new UserServiceImpl();
    private MenuService menuService = new MenuServiceImpl();
    private OrderService orderService = new OrderServiceImpl();
    private PaymentService paymentService = new PaymentServiceImpl();

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public MenuService getMenuService() {
        return menuService;
    }
    public PaymentService getPaymentService() {
        return paymentService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

}
