package com.javarush.task.task33.task3308;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;
import java.util.List;

@XmlType(name = "shop")
@XmlRootElement
public class Shop {
    public Goods goods;
    public int count;
    public double profit;
    public String[] secretData;

    @XmlType(name = "goods")
    public static class Goods {
        public List<String> names;

    }

    @Override
    public String toString() {
        return "Shop { count = " + count + "\n" +
                "profit = " + profit + "\n" +
                "goods = " + goods.names + "\n" +
                "secretData = " + Arrays.toString(secretData);
    }
}
