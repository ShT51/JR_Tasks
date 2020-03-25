package com.javarush.task.task28.task2810.view;

import com.google.common.io.Files;
import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class HtmlView implements View {
    private Controller controller;
    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().
            replaceAll("\\.", "/") + "/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        String file = getUpdatedFileContent(vacancies);
        updateFile(file);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Kiev");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancyList) {
        Document doc;
        Element vacancyTemplate;
        Element originTemplate;

        try {
            doc = getDocument();
            originTemplate = doc.getElementsByClass("template").first();
            vacancyTemplate = originTemplate.clone();
            vacancyTemplate.removeAttr("style");
            vacancyTemplate.removeClass("template");
            //doc.select("tr[class=vacancy]").remove().not("tr[class=vacancy template");
            doc.select("tr[class=vacancy]").remove();

            for (Vacancy vac : vacancyList) {
                Element localVacancyTemplate = vacancyTemplate.clone();
                localVacancyTemplate.getElementsByClass("city").first().text(vac.getCity());
                localVacancyTemplate.getElementsByClass("companyName").first().text(vac.getCompanyName());
                localVacancyTemplate.getElementsByClass("salary").first().text(vac.getSalary());
                Element link = localVacancyTemplate.getElementsByTag("a").first();
                link.text(vac.getTitle());
                link.attr("href", vac.getUrl());

                originTemplate.before(localVacancyTemplate.outerHtml());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("");
            return "Some exception occurred";
        }
        return doc.html();
    }

    private void updateFile(String file) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] array = file.getBytes();
            fos.write(array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Document getDocument() throws IOException {

        return Jsoup.parse(new File(filePath), "UTF-8");
    }

}
