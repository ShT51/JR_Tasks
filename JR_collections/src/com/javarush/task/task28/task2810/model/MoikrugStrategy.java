package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoikrugStrategy implements Strategy {
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";
    private int pageNumber = 0;

    protected Document getDocument(String searchString, int pageNumber) {
        String link = String.format(URL_FORMAT, searchString, pageNumber);
        Document doc = null;
        try {
            // парсим нужный нам сайт, достаем из него Document
            doc = Jsoup.connect(link).
                    userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)").
                    referrer("no-referrer-when-downgrade").
                    get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * основной метод, который парсит все страницы сайта и сохраняет их в виде объекта класса Vacancy
     * @param searchString - передаем в него нужный нам город (или любой другой параметр)
     * @return - возвращаем список всех найденых вакансий в виде объектов класса Vacancy
     */
    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancyList = new ArrayList<>();
        Document doc;
        doc = getDocument(searchString, pageNumber);
        // в цикле проходим по всем страницам (начинаем с 0) и находим нужные нам элементы с описание вакансий
        while (true) {
            Elements salaryElements = doc.getElementsByClass("job");
            //если такие элемены заканчиваются - выходим из цикла.
            if (salaryElements.size() == 0) {
                pageNumber = 0;
                break;
            }
            for (Element element : salaryElements) {
                if (element != null) {
                    // заполняем поля объекта vacancy распарсивая элементы
                    Vacancy vac = new Vacancy();
                    vac.setTitle(element.getElementsByClass("title").first().text());
                    String cityName = element.getElementsByAttributeValueContaining("class", "location").text();
                    vac.setCity(cityName.length() == 0 ? "" : cityName);
                    vac.setCompanyName(element.getElementsByAttributeValue("class", "company_name").text());
                    vac.setSiteName(URL_FORMAT);
                    String urlPage = "https://moikrug.ru" + element.getElementsByTag("a").attr("href");
                    vac.setUrl(urlPage);
                    String salary = element.getElementsByAttributeValueContaining("class", "count").text();
                    vac.setSalary(salary.length() == 0 ? "" : salary);
                    vacancyList.add(vac);
                }
            }
            // закончили с одной страницей - переходим к следующй и так далее
            ++ pageNumber;
            //System.out.println(pageNumber);
            doc = getDocument(searchString, pageNumber);
        }
        return vacancyList;
    }
}
