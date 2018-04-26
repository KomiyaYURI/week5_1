package practice2;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {
    private BigDecimal tax;

    public Receipt() {
        tax = new BigDecimal(0.1);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal calculateSubtotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = new BigDecimal(0);
        for (Product product : products) {
            OrderItem item = findOrderItemByProduct(items, product);
            BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(item.getCount()));
            subTotal = subTotal.add(itemTotal);
        }
        return subTotal;
    }

    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
        OrderItem curItem = null;
        for (OrderItem item : items) {
            if (item.getCode() == product.getCode()) {
                curItem = item;
                break;
            }
        }
        return curItem;
    }

    private BigDecimal calculateReducedPrice(List<Product> products, List<OrderItem> items) {
        BigDecimal reducedPrice = new BigDecimal(0);

        for (Product product : products) {
            OrderItem curItem = findOrderItemByProduct(items, product);

            reducedPrice = reducedPrice.add(product.getPrice()
                    .multiply(product.getDiscountRate())
                    .multiply(new BigDecimal(curItem.getCount())));
        }

        return reducedPrice;
    }

    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = calculateSubtotal(products, items);

        BigDecimal reducedPrice = calculateReducedPrice(products, items);
        subTotal = subTotal.subtract(reducedPrice);

        BigDecimal taxTotal = subTotal.multiply(tax);
        BigDecimal grandTotal = subTotal.add(taxTotal);

        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
