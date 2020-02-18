package ru.skelotron.win63.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.skelotron.win63.converer.CategoryConverter;
import ru.skelotron.win63.converer.ItemConverter;
import ru.skelotron.win63.http_entities.*;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.ItemRepository;
import ru.skelotron.win63.repository.SettingsRepository;
import ru.skelotron.win63.service.response_reader.ResponseReader;

@Service
public class LoadCategoryServiceImpl implements LoadCategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final SettingsRepository settingsRepository;
    private final ResponseReader responseReader;
    private final ItemConverter itemConverter;
    private final CategoryConverter categoryConverter;

    @Autowired
    public LoadCategoryServiceImpl(CategoryRepository categoryRepository, ItemRepository itemRepository,
                                   SettingsRepository settingsRepository, @Qualifier("DummyResponseReader") ResponseReader responseReader,
                                   ItemConverter itemConverter, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.settingsRepository = settingsRepository;
        this.responseReader = responseReader;
        this.itemConverter = itemConverter;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public void load() {
        int quantity = Integer.parseInt(settingsRepository.findByName("pageSize").getValue());
        Request request = Request.builder()
                .uri("/catalog/telefony/sotovye-telefony")
                .quantity(quantity)
                .s("new")
                .city(0)
                .category("143")
                .page(2)
                .build();
        Response response = responseReader.read(request);

        for (Category category : response.getCategories()) {
            categoryRepository.save( categoryConverter.convertToEntity(category) );
        }

        for (GoodsEntry goodsEntry : response.getGoods()) {
            itemRepository.save( itemConverter.convertToEntity(goodsEntry) );
        }
    }
}
