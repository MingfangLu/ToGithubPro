package com.xianjiu.www.togithubproject.activity.net;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-05-31.
 */
public class FastBillsBaseInfo {

    private int createTypeId = 1;//	;//开单的方式，1：快捷开单	number	@mock=1
    private String customerId;//客户ID	number	@mock=30
    private float orderCreditAmount = 0;//	欠款额	number	@mock=0
    private String orderDate;//	开单日期	string	@mock=2016-05-19
    private float  orderDiscountAmount;//	优惠
    private int    orderIsPayed;//	是否已经付款1、付款 0、未付款	number	@mock=1
    private float  orderPaidAmount;// 实付
    private float  orderPaidAmount1;// 实付
    private float  orderPaidAmount1_should;// 实付
    private String orderPayedDate;//支付时间	string	@mock=2016-05-20 22:19:10
    private String orderPayedTypeId1 = "1";//第一种支付方式ID	number	@mock=1
    private float orderShouldPayAmount;// 实付
    private int orderStatus;//	订单状态，1、进行中，2、已完成	number	@mock=2
    private float orderTotalAmount;//	应付
    private int orderTypeId = -1;//订单类型 接口查询
    private int               carMileage;//公里数
    private String            orderNotes;//开单备注
    private String            shopSalesManagerId;
    private ArrayList<String> edbOrderIds;//e代泊专用参数，预约订单Id Array	array<string>
    private String            shopCodeLM;// 联盟shopcode开单必填项	string
    private String            workOrderNo;// 订单号

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }


    public void setShopCodeLM(String shopCodeLM) {
        this.shopCodeLM = shopCodeLM;
    }

    public void setCreateTypeId(int createTypeId) {
        this.createTypeId = createTypeId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setOrderCreditAmount(float orderCreditAmount) {
        this.orderCreditAmount = orderCreditAmount;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderDiscountAmount(float orderDiscountAmount) {
        this.orderDiscountAmount = orderDiscountAmount;
    }

    public void setOrderIsPayed(int orderIsPayed) {
        this.orderIsPayed = orderIsPayed;
    }

    public void setOrderPaidAmount(float orderPaidAmount) {
        this.orderPaidAmount = orderPaidAmount;
    }

    public void setEdbOrderIds(ArrayList<String> edbOrderIds) {
        this.edbOrderIds = edbOrderIds;
    }

    public void setOrderPaidAmount1(float orderPaidAmount1) {
        this.orderPaidAmount1 = orderPaidAmount1;
    }

    public void setOrderPaidAmount1_should(float orderPaidAmount1_should) {
        this.orderPaidAmount1_should = orderPaidAmount1_should;
    }

    public void setOrderPayedDate(String orderPayedDate) {
        this.orderPayedDate = orderPayedDate;
    }

    public void setOrderPayedTypeId1(String orderPayedTypeId1) {
        this.orderPayedTypeId1 = orderPayedTypeId1;
    }

    public void setOrderShouldPayAmount(float orderShouldPayAmount) {
        this.orderShouldPayAmount = orderShouldPayAmount;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderTotalAmount(float orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public void setOrderTypeId(int orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public void setCarMileage(int carMileage) {
        this.carMileage = carMileage;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public void setShopSalesManagerId(String shopSalesManagerId) {
        this.shopSalesManagerId = shopSalesManagerId;
    }
}
