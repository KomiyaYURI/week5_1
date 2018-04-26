package practice3;

import java.math.BigDecimal;
import java.util.List;

public class PriceCaculator {

    private List<OrderLineItem> orderLineItemList;
    private List<BigDecimal> discounts;
    private BigDecimal tax;

    public PriceCaculator(List<OrderLineItem> orderLineItemList, List<BigDecimal> discounts, BigDecimal tax) {
        this.orderLineItemList = orderLineItemList;
        this.discounts = discounts;
        this.tax = tax;
    }

    private BigDecimal calcTotal()
    {
        BigDecimal subTotal = new BigDecimal(0);
        for (OrderLineItem lineItem : orderLineItemList) {
            subTotal = subTotal.add(lineItem.getPrice());
        }

        return subTotal;
    }

    private BigDecimal calcReduce()
    {
        BigDecimal subTotal = new BigDecimal(0);
        for (BigDecimal discount : discounts) {
            subTotal = subTotal.add(discount);
        }

        return subTotal;
    }

    private BigDecimal calcTax(BigDecimal subTotal)
    {
        return subTotal.multiply(tax);
    }

    public BigDecimal calculate() {
        BigDecimal total = calcTotal();

        BigDecimal reduce = calcReduce();
        total = total.subtract(reduce);

        BigDecimal tax = calcTax(total);
        total = total.add(tax);

        return total;
    }
}
