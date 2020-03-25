package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";
    //private String tmpLink = "http://javarush.ru/testdata/big28data.html?text=java+%s&page=%d";
    private int pageNumber = 0;

    protected Document getDocument(String searchString, int pageNumber) {
        String link = String.format(URL_FORMAT, searchString, pageNumber);
        Document doc = null;
        try {
            doc = Jsoup.connect(link).
                    userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)").
                    referrer("no-referrer-when-downgrade").
                    get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancyList = new ArrayList<>();
        Document doc;
        doc = getDocument(searchString, pageNumber);
        while (true) {
            Elements salaryElements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
            if (salaryElements.size() == 0) {
                pageNumber = 0;
                break;
            }
            for (Element element : salaryElements) {
                if (element != null) {
                    Vacancy vac = new Vacancy();
                    vac.setTitle(element.getElementsByAttributeValueContaining("data-qa", "title").text());
                    vac.setCity(element.getElementsByAttributeValueContaining("data-qa", "address").text());
                    vac.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vac.setSiteName(URL_FORMAT);
                    String urlPage = element.getElementsByAttributeValueContaining("data-qa", "title").attr("href");
                    vac.setUrl(urlPage);
                    String salary = element.getElementsByAttributeValueContaining("data-qa", "compensation").text();
                    vac.setSalary(salary.length() == 0 ? "" : salary);
                    vacancyList.add(vac);
                }
            }
            ++ pageNumber;
            //System.out.println(pageNumber);
            doc = getDocument(searchString, pageNumber);
        }
        return vacancyList;
    }
}
