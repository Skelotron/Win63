package ru.skelotron.win63.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.service.settings.SettingsService;

@Component
public class HttpCategoryReader implements CategoryReader {

    private final SettingsService settingsService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public HttpCategoryReader(SettingsService settingsService, CategoryRepository categoryRepository) {
        this.settingsService = settingsService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void read() {
        String url = settingsService.getCatalogUrl();

        RestTemplate restTemplate = new RestTemplate();
        String htmlContent = restTemplate.getForObject(url, String.class);
        if (htmlContent != null) {
            Document document = Jsoup.parse(htmlContent);
            Elements categories = document.select("div[am-category]");
            for (Element category : categories) {
                Elements categoryElements = category.getAllElements();
                String categoryExternalId = categoryElements.select("div[am-category-icon]").attr("am-category-icon");
                Elements categoryElement = categoryElements.select("a[am-category-title]");
                String categoryUrl = categoryElement.get(0).attr("href");
                String categoryTitle = categoryElement.select("span[am-title]").get(0).text();

                CategoryEntity categoryEntity = new CategoryEntity(categoryTitle, categoryUrl, categoryExternalId);

                Elements subCategories = categoryElements.select("a[am-category-subtitle]");
                for (Element subcategory : subCategories) {
                    Elements subcategoryElements = subcategory.getAllElements();
                    String subcategoryUrl = subcategory.attr("href");
                    String subcategoryTitle = subcategoryElements.select("span[am-title]").get(0).text();

                    CategoryEntity subcategoryEntity = new CategoryEntity( subcategoryTitle, subcategoryUrl );
                    subcategoryEntity.setParentCategory(categoryEntity);
                    categoryEntity.getSubCategories().add( subcategoryEntity );
                }

                categoryRepository.save(categoryEntity);
            }
        }

    }
}
