package com.irelint.ttt.util;

public class Constants {

	public static final String EXCHANGE_NAME = "ttt-exchange";
	
	public static final String EVENT_GOODSCREATED = "event.goodsCreated";
	public static final String EVENT_GOODSRATED = "event.goodsRated";
	public static final String EVENT_GOODSUPDATED = "event.goodsUpdated";
	public static final String EVENT_ORDERCANCELED = "event.orderCanceled";
	public static final String EVENT_ORDERCONFIRMED = "event.orderConfirmed";
	public static final String EVENT_ORDERCREATED = "event.orderCreated";
	public static final String EVENT_ORDERPAYED = "event.orderPayed";
	public static final String EVENT_ORDERRECEIVED = "event.orderReceived";
	public static final String EVENT_TOPAYORDER = "event.toPayOrder";
	public static final String EVENT_TOREFUNDORDER = "event.toRefundOrder";
	public static final String EVENT_USERCREATED = "event.userCreated";
	
	public static final String QUEUE_USERCREATED_ACCOUNT = "userCreated-account";
	public static final String QUEUE_TOPAYORDER_ACCOUNT = "toPayOrder-account";
	public static final String QUEUE_TOREFUNDORDER_ACCOUNT = "toRefundOrder-account";
	public static final String QUEUE_ORDERRECEIVED_ACCOUNT = "orderReceived-account";

	public static final String QUEUE_GOODSRATED_GOODS = "goodsRated-goods";

	public static final String QUEUE_ORDERCONFIRMED_ORDER = "orderConfirmed-order";
	public static final String QUEUE_ORDERPAYED_ORDER = "orderPayed-order";
	public static final String QUEUE_GOODSCREATED_ORDER = "goodsCreated-order";
	public static final String QUEUE_GOODSUPDATED_ORDER = "goodsUpdated-order";

	public static final String QUEUE_GOODSCREATED_INVENTORY = "goodsCreated-inventory";
	public static final String QUEUE_ORDERCREATED_INVENTORY = "orderCreated-inventory";
	public static final String QUEUE_ORDERCANCELED_INVENTORY = "orderCanceled-inventory";
}
