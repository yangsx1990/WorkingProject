package com.hiersun.oohdear.util;

import java.util.ArrayList;
import java.util.List;

import com.hiersun.oohdear.entity.OrderStatus;
import com.hiersun.oohdear.entity.OrderWorkflow;

/**
 * 状态轴
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
public class OrderStatusAxis {

	/**
	 * 管理系统默认状态轴
	 */
	private static final OrderStatus[] ORDER_AXIS = { OrderStatus.WAIT_PAY, OrderStatus.WAIT_CONFIRM,
			OrderStatus.DESIGNING, OrderStatus.WAIT_MAKE, OrderStatus.MAKING, OrderStatus.WAIT_SHIPPING,
			OrderStatus.DONE };

	/**
	 * 
	 * 获取状态轴
	 * @param h5 是否h5站点
	 * @param workflows 工作流
	 * @param orderStatus 订单状态
	 * @return
	 */
	public static List<String[]> get(boolean h5, List<OrderWorkflow> workflows, Integer currentOrderStatus) {
		//状态轴
		List<String[]> statusAxis = new ArrayList<String[]>();
		int size = workflows.size();
		if (!currentOrderStatus.equals(workflows.get(size - 1).getOrderStatus())) {
			statusAxis.add(new String[] { "数据问题 [ 订单当前状态和工作流不一致 ] ", "enabled" });
			return statusAxis;
		}
		int last = -1;
		for (int n = 0; n < size; n++) {
			Integer orderStatus = workflows.get(n).getOrderStatus();
			if (h5
					&& (OrderStatus.DESIGNING.getCode().equals(orderStatus) || OrderStatus.MAKING.getCode().equals(
							orderStatus))) {
				continue;
			}
			if (OrderStatus.SHIPPED.getCode().equals(orderStatus)) {
				continue;
			}
			//如果和上次状态相同则跳过
			if (last == orderStatus) {
				//如果是list最后一条记录和上一条重复，则把“已”改为“待”
				if (n == size - 1) {
					statusAxis.set(statusAxis.size() - 1, new String[] { OrderStatus.forOrderCode(orderStatus),
							"enabled", orderStatus.toString() });
				}
				continue;
			} else {
				//如果当前状态是31不可做，并且上一个状态是待确认，则替换上一个状态为不可做
				if (OrderStatus.REFUSE.getCode().equals(orderStatus) && OrderStatus.WAIT_CONFIRM.getCode().equals(last)) {
					//如果倒数第二个状态不是不可做
					if (statusAxis.size() >= 2
							&& !statusAxis.get(statusAxis.size() - 2)[2]
									.equals(OrderStatus.REFUSE.getCode().toString())) {
						statusAxis.set(statusAxis.size() - 1, new String[] { OrderStatus.forOrderCode(orderStatus),
								"enabled", orderStatus.toString() });
						last = orderStatus;
					} else {//如果倒数第二个状态也是不可做，那么移除最后一个状态轴里的状态
						statusAxis.remove(statusAxis.size() - 1);
						last = Integer.parseInt(statusAxis.get(statusAxis.size() - 1)[2]);
					}
					continue;
				} else if (OrderStatus.WAIT_REFUND.getCode().equals(orderStatus)//如果是待退款状态，并且前1个状态是待确认，则覆盖待确认状态为待付款
						&& OrderStatus.WAIT_CONFIRM.getCode().equals(last)) {
					statusAxis.set(statusAxis.size() - 1, new String[] { OrderStatus.forOrderCode(orderStatus),
						"enabled", orderStatus.toString() });
					continue;
				}else if(OrderStatus.CLOSED.getCode().equals(orderStatus) && OrderStatus.WAIT_PAY.getCode().equals(last)){//如果是已关闭状态，并且前1个是待付款状态，则状态轴为“待付款->已关闭”
					statusAxis.set(statusAxis.size() - 1, new String[] { OrderStatus.forOrderCode(last),
						"enabled", orderStatus.toString() });
				}
			}
			last = orderStatus;
			String[] e = new String[3];
			if (n == size - 1) {
				e = new String[] { OrderStatus.forOrderCode(orderStatus), "enabled", orderStatus.toString() };
			} else {
				//屏蔽特殊状态
				String name = OrderStatus.forOrderFinishedName(orderStatus);
				if (!h5 && OrderStatus.WAIT_MAKE.getCode().equals(orderStatus)) {
					name = OrderStatus.forOrderCode(orderStatus);
				}
				e = new String[] { name, "enabled", orderStatus.toString() };
			}
			statusAxis.add(e);
		}
		//计算状态轴没有启用的部分
		int length = statusAxis.size();
		if (OrderStatus.REFUSE.getCode().equals(currentOrderStatus)) {
			//重新计算状态轴
			statusAxis.clear();
			statusAxis.add(new String[] { OrderStatus.WAIT_PAY.getFinishedName(), "enabled",
					OrderStatus.WAIT_PAY.getCode().toString() });
			statusAxis.add(new String[] { OrderStatus.REFUSE.getFinishedName(), "enabled",
					OrderStatus.REFUSE.getCode().toString() });
			statusAxis.addAll(disabledAxis(OrderStatus.WAIT_PAY.getCode(), ORDER_AXIS));
		} else if (OrderStatus.WAIT_CONFIRM.getCode().equals(currentOrderStatus)
				&& OrderStatus.REFUSE.getName().equals(statusAxis.get(length - 2)[0])) {
			//重新计算状态轴
			statusAxis.clear();
			statusAxis.add(new String[] { OrderStatus.WAIT_PAY.getFinishedName(), "enabled",
					OrderStatus.WAIT_PAY.getCode().toString() });
			statusAxis.add(new String[] { OrderStatus.REFUSE.getFinishedName(), "enabled",
					OrderStatus.REFUSE.getCode().toString() });
			statusAxis.add(new String[] { OrderStatus.WAIT_CONFIRM.getName(), "enabled",
					OrderStatus.WAIT_CONFIRM.getCode().toString() });
			statusAxis.addAll(disabledAxis(currentOrderStatus, ORDER_AXIS));
		} else if (OrderStatus.WAIT_REFUND.getCode().equals(currentOrderStatus)) {
			//如果当前状态是待退款，并且状态轴中前1个状态是不可做
			if (OrderStatus.REFUSE.getName().equals(statusAxis.get(length - 2)[0])) {
				//重新计算状态轴
				statusAxis.clear();
				statusAxis.add(new String[] { OrderStatus.WAIT_PAY.getFinishedName(), "enabled",
						OrderStatus.WAIT_PAY.getCode().toString() });
				statusAxis.add(new String[] { OrderStatus.REFUSE.getFinishedName(), "enabled",
						OrderStatus.REFUSE.getCode().toString() });
				statusAxis.add(new String[] { OrderStatus.WAIT_REFUND.getName(), "enabled",
						OrderStatus.WAIT_REFUND.getCode().toString() });
			} else if (length > 3 && OrderStatus.REFUSE.getName().equals(statusAxis.get(length - 3)[0])) {
				//重新计算状态轴
				statusAxis.clear();
				statusAxis.add(new String[] { OrderStatus.WAIT_PAY.getFinishedName(), "enabled",
						OrderStatus.WAIT_PAY.getCode().toString() });
				statusAxis.add(new String[] { OrderStatus.REFUSE.getFinishedName(), "enabled",
						OrderStatus.REFUSE.getCode().toString() });
				statusAxis.add(new String[] { OrderStatus.WAIT_REFUND.getName(), "enabled",
						OrderStatus.WAIT_REFUND.getCode().toString() });
			}
			//直接添加51已退款
			String[] e = new String[] { OrderStatus.REFUNDED.getFinishedName(), "disabled",
					OrderStatus.REFUNDED.getCode().toString() };
			statusAxis.add(e);
		} else if (OrderStatus.REFUNDED.getCode().equals(currentOrderStatus)) {
			//不做任何处理
		} else {
			statusAxis.addAll(disabledAxis(currentOrderStatus, ORDER_AXIS));
		}
		//如果是h5的状态轴，则删除设计中和制作中的状态
		if (h5) {
			List<String[]> h5StatusAxis = new ArrayList<String[]>();
			statusAxis.forEach(action -> {
				if (!OrderStatus.DESIGNING.getName().equals(action[0])
						&& !OrderStatus.MAKING.getName().equals(action[0])) {
					h5StatusAxis.add(action);
				}
			});
			return h5StatusAxis;
		}

		return statusAxis;
	}

	/**
	 * 计算未激活的状态轴
	 * @param currentOrderStatus 当前订单状态
	 * @param orderStatusArray 默认的状态轴数组
	 * @return
	 */
	private static List<String[]> disabledAxis(Integer currentOrderStatus, OrderStatus[] orderStatusArray) {
		List<String[]> axis = new ArrayList<String[]>();
		if (OrderStatus.SHIPPED.getCode().equals(currentOrderStatus)) {
			axis.add(new String[] { OrderStatus.DONE.getName(), "disabled", OrderStatus.DONE.getCode().toString() });
		}
		int i = 0;
		//定位当前状态的位置
		for (; i < orderStatusArray.length; i++) {
			if (orderStatusArray[i].getCode().equals(currentOrderStatus)) {
				break;
			}
		}
		//获取当前状态后面的状态轴
		for (int j = i + 1; j < orderStatusArray.length; j++) {
			String[] e = new String[] { orderStatusArray[j].getName(), "disabled",
					orderStatusArray[j].getCode().toString() };
			axis.add(e);
		}
		return axis;
	}
}
