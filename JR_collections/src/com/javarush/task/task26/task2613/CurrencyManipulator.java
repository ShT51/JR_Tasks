package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CurrencyManipulator {
    private String currencyCode;
    private TreeMap<Integer, Integer> denominations = new TreeMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        int totalCount;
        if (denominations.containsKey(denomination)) {
            totalCount = denominations.get(denomination) + count;
        } else {
            totalCount = count;
        }
        denominations.put(denomination, totalCount);
    }

    public int getTotalAmount() {
        int totalAmount = 0;

        for (Map.Entry<Integer, Integer> money : denominations.entrySet()) {
            totalAmount += money.getKey() * money.getValue();
        }
        return totalAmount;
    }

    public boolean hasMoney() {
        return getTotalAmount() > 0;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return expectedAmount <= getTotalAmount();
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        int sum = expectedAmount;

        TreeMap<Integer, Integer> temp = new TreeMap<>(Comparator.reverseOrder());
        TreeMap<Integer, Integer> result = new TreeMap<>(Comparator.reverseOrder());
        ArrayList<Integer> list = new ArrayList<>();

        // копируем всю мапу во временную, отсортированную от большого к малому
        temp.putAll(denominations);
        // копируем все значения банкнот в список
        temp.forEach((k, v) -> list.add(k));
        // сохраняем номер последней банкноты
        int lastNote = list.size() - 1;

        /* проходим по всем имеющимся банкнотам в двух циклах:
         * сначала пытаемся применить жадный алгоритм начиная со самой старшей банкноты, если не получается (sum > 0) -
         * повторяем со следующей по старшенству и т.д.
         * если и на последней итерации не получилось - кидаем исключение */
        for (int i = 0; i < list.size(); i++) {
            // проходим по всем доступным типам банкнот в этой итерации жадным алгоритмом
            for (int j = i; j < list.size(); j++) {
                int note = list.get(j);
                // кол-во берем из мапы temp
                int quantity = temp.get(note);
                // для каждой банкноты, начиная со старшей крутимся пока..
                while (true) {
                    /* если оставшаяся сумма < номинала или кол-во банктнот = 0
                     * записываем получившееся кол-во банкнот в temp
                     * выходим из петли, переходим во внутреннем цикле к следующей банкноте */
                    if (sum < note || quantity <= 0) {
                        temp.put(note, quantity);
                        break;
                    }
                    sum -= note;
                    quantity--;
                    // если такая банкнота уже есть в мапе result - увеличиваем ее кол-во на 1
                    // нет - просто добавляем 1 банкноту в мапу result
                    if (result.containsKey(note)) {
                        result.put(note, result.get(note) + 1);
                    } else {
                        result.put(note, 1);
                    }
                }
            }
            // если мы на последней итерации внешнего цикла, а сумма все еще > 0 - выходим из метода
            if (sum > 0 && i == lastNote) {
                throw new NotEnoughMoneyException();
                // если сумма = 0, записываем в denominations реальное кол-во банкнот - выходим из цикла
            } else if (sum == 0) {
                denominations.clear();
                denominations.putAll(temp);
                ConsoleHelper.writeMessage("Transaction was successful!");
                break;
                // если сумма > 0, итерируемся снова во внешнем цикле, начиная со следующей по старшенству банкноты
                // обнуляем промежуточные результаты
            } else {
                temp.clear();
                temp.putAll(denominations);
                result.clear();
                sum = expectedAmount;
            }
        }
        // выдаем CASH
        return result;
    }
}
