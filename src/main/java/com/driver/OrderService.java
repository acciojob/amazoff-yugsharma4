package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    //Add order
    public void addOrderInDB(Order order){
        orderRepository.addOrderInDB(order);
    }

    //Add delivery partner
    public void addDeliveryPartnerInDB(String partnerId){
        orderRepository.addDeliveryPartnerInDB(partnerId);
    }

    //Add order partner pair
    public void addOrderPartnerPairInDB(String orderId, String partnerId){
        orderRepository.addOrderPartnerPairInDB(orderId,partnerId);
    }

    //Get order by ID
    public Order getOrderByIdFromDB(String orderId){
        return orderRepository.getOrderByIdFromDB(orderId);
    }

    //Get partner by ID
    public DeliveryPartner getPartnerByIdFromDB(String partnerId){
        return orderRepository.getPartnerByIdFromDB(partnerId);
    }

    //Get Order Count by partner by partnerID
    public Integer getOrderCountByPartnerIdFromDB(String partnerId){
        return orderRepository.getOrderCountByPartnerIdFromDB(partnerId);
    }


    //Get List of all orders by PartnerID
    public List<String> getOrdersByPartnerId(String partnerId){
        return orderRepository.getOrdersByPartnerId(partnerId);
    }


    //Get List of all orders
    public List<String> getOrdersFromDB(){
       return orderRepository.getOrdersFromDB();
    }

    //count of unassigned orders
    public Integer getCountOfUnassignedOrders(){
       return orderRepository.getCountOfUnassignedOrders();
    }

    //Count of orders which are left undelivered
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    //get the time by which last delivery made
    public String getLastDeliveryTimeByPartnerId(String partnerId){
       return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }
    //Delete partner by Id
    public void deletePartnerById(String partnerId){

      orderRepository.deletePartnerById(partnerId);
    }

    //Delete order
    public void deleteOrderById(String orderId){
       orderRepository.deleteOrderById(orderId);
    }
}
