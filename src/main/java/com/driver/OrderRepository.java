package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String, Order> orders = new HashMap<>();
    HashMap<String, DeliveryPartner> deliveryPartners = new HashMap<>();
    HashMap<String, List<String>> partnerOrdersList = new HashMap<>();
    HashMap<String, List<Order>> partnerObj = new HashMap<>();



    //Add order
    public void addOrderInDB(Order order){
        orders.put(order.getId(), order);
    }

    //Add delivery partner
    public void addDeliveryPartnerInDB(String partnerId){
        deliveryPartners.put(partnerId, new DeliveryPartner(partnerId));
    }

    //Add Order Delivery partner pair
    public void addOrderPartnerPairInDB(String orderId, String partnerId){
        boolean isOrderExist = orders.containsKey(orderId);
        boolean isPartnerExist = partnerOrdersList.containsKey(partnerId);

        DeliveryPartner deliveryPartner = deliveryPartners.get(partnerId);

        //Increment no of orders for that Delivery Partner
        Integer noOfOrders = deliveryPartner.getNumberOfOrders() + 1;
        deliveryPartner.setNumberOfOrders(noOfOrders);

        Order order = null;
        List<String> orderLists = new ArrayList<>();
        List<Order> orderObjList = new ArrayList<>();


        if(isOrderExist){
            order  = orders.get(orderId);
            orders.remove(orderId);
        }

        if(isPartnerExist){
            if(partnerOrdersList.get(partnerId).isEmpty()){
                partnerOrdersList.put(partnerId,orderLists);
                partnerObj.put(partnerId,orderObjList);
            }
            orderLists = partnerOrdersList.get(partnerId);
            orderObjList = partnerObj.get(partnerId);


            orderLists.add(order.getId());
            orderObjList.add(order);
        }

        partnerOrdersList.put(partnerId, orderLists);
    }

    //Get order by ID
    public Order getOrderByIdFromDB(String orderId){
        if(orders.containsKey(orderId)){
            return orders.get(orderId);
        }else{
            for(String partnerId: partnerObj.keySet()){
                List<Order> pOrders = partnerObj.get(partnerId);
                if(!pOrders.isEmpty()){
                    for(Order partnerOrder : pOrders){
                        if(partnerOrder.equals(orderId)) return partnerOrder;
                    }
                }
            }
        }
        return null;
    }


    //Get partner by ID
    public DeliveryPartner getPartnerByIdFromDB(String partnerId){
        return deliveryPartners.get(partnerId);
    }

    //Get order's count by partnerID
    public Integer getOrderCountByPartnerIdFromDB(String partnerId){
       List<String> ordersList= partnerOrdersList.get(partnerId);
       return ordersList != null ? ordersList.size() : 0;
    }

    //Get List of all orders by PartnerID
    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> ordersList= partnerOrdersList.get(partnerId);
        return ordersList;
    }

    //Get List of all orders
    public List<String> getOrdersFromDB(){
        List<String> allOrders = new ArrayList<>();
        for(String orderId : orders.keySet()){
            allOrders.add(orderId);
        }

        for(String orderId : partnerOrdersList.keySet()){
            allOrders.add(orderId);
        }

        return allOrders;
    }


    //Get count of unassigned orders
    public Integer getCountOfUnassignedOrders(){
//        Integer count = 0;
//        List<String> listOfAssignedOrders = new ArrayList<>();
//        for(String partnerId: partnerOrdersList.keySet()){
//            List<String> temp = partnerOrdersList.get(partnerId);
//            if(!temp.isEmpty()){
//                for(String id: temp){
//                    listOfAssignedOrders.add(id);
//                }
//            }
//        }
//
//        for(String orderId: orders.keySet()){
//            if(!listOfAssignedOrders.contains(orderId)) count++;
//        }

        return orders.size();
    }

    //Count of orders which are left undelivered
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        Integer count = 0;
        List<String> orderList = partnerOrdersList.get(partnerId);

        for(String id: orderList){
            int actualDeliverTime = orders.get(id).getDeliveryTime();
            if(actualDeliverTime > Integer.parseInt(time)) count++;
        }

        return count;
    }

    //get the time by which last delivery made
    public String getLastDeliveryTimeByPartnerId(String partnerId){

        List<String> orderList = partnerOrdersList.get(partnerId);
        int maxTime = 0;
        String time = "";

        for(String id: orderList){
            int deliveryTime = orders.get(id).getDeliveryTime();
            maxTime = Math.max(maxTime,deliveryTime);
        }

        int hr = maxTime / 60;
        int min = maxTime % 60;

        time = Integer.toString(hr) + ":" + Integer.toString(min);

        return time;
    }

    //Delete partner
    public void deletePartnerById(String partnerId){

        List<Order> orderObj = partnerObj.get(partnerId);
        deliveryPartners.remove(partnerId);

        for(Order pOrder : orderObj){
            orders.put(pOrder.getId(),pOrder);
        }

        partnerObj.remove(partnerId);
        partnerOrdersList.remove(partnerId);

    }

    //Delete order
    public void deleteOrderById(String orderId){
        if(orders.containsKey(orderId))
             orders.remove(orderId);

        for(String partnerId : partnerOrdersList.keySet()){
            List<String> pOrderList = partnerOrdersList.get(partnerId);
            List<Order> pOrderObj = partnerObj.get(partnerId);

            if(!pOrderList.isEmpty()){
                if(pOrderList.contains(orderId)){
                    pOrderList.remove(orderId);
                    pOrderObj.remove(orderId);
                    partnerOrdersList.put(partnerId,pOrderList);
                    partnerObj.put(partnerId,pOrderObj);
                    deliveryPartners.get(partnerId).setNumberOfOrders(deliveryPartners.get(partnerId).getNumberOfOrders()-1);
                }
            }
        }
    }
}
