package ru.skelotron.win63.service.item;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skelotron.win63.converter.ItemConverter;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.entity.PhotoEntity;
import ru.skelotron.win63.http_entities.Category;
import ru.skelotron.win63.http_entities.GoodsEntry;
import ru.skelotron.win63.http_entities.Request;
import ru.skelotron.win63.http_entities.Response;
import ru.skelotron.win63.model.ItemsChangeData;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.ItemRepository;
import ru.skelotron.win63.repository.SettingsRepository;
import ru.skelotron.win63.service.response_reader.ResponseReader;
import ru.skelotron.win63.service.settings.SettingsService;
import ru.skelotron.win63.util.CollectionUtil;

import java.util.*;

@Service
@Log
public class ItemServiceImpl implements ItemService {

    private final SettingsRepository settingsRepository;
    private final ResponseReader responseReader;
    private final ItemConverter itemConverter;
    private final ItemRepository itemRepository;
    private final SettingsService settingsService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ItemServiceImpl(SettingsRepository settingsRepository, ResponseReader responseReader, ItemConverter itemConverter, ItemRepository itemRepository, SettingsService settingsService, CategoryRepository categoryRepository) {
        this.settingsRepository = settingsRepository;
        this.responseReader = responseReader;
        this.itemConverter = itemConverter;
        this.itemRepository = itemRepository;
        this.settingsService = settingsService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ItemsChangeData load(CategoryEntity category) {
        int quantity = Integer.parseInt(settingsRepository.findByName("pageSize").getValue());
        int count = getCount(category);
        log.info("Found " + count + " items of category " + category.getName());
        if (count > 0) {
            int batchesCount = (count / quantity) + (((count % quantity) == 0) ? 0 : 1);
            log.info("Start load " + batchesCount + " pages of items with category " + category.getName());

            Collection<GoodsEntry> goods = new ArrayList<>();
            Collection<Category> categories = new ArrayList<>();
            for (int batch = 0; batch < batchesCount; batch++) {
                Response response = getPage(category, batch + 1, quantity);
                categories.addAll(response.getCategories());
                goods.addAll(response.getGoods());
            }

            String baseUri = settingsService.getBaseUri();
            for (Category categoryRecord : categories) {
                String externalId = categoryRecord.getId();
                if (!StringUtils.isBlank(externalId)) {
                    String url = baseUri + categoryRecord.getUrl();
                    if (!url.endsWith("/")) {
                        url += "/";
                    }
                    CategoryEntity categoryEntity = categoryRepository.findByUrl(url);
                    if (categoryEntity != null) {
                        if (StringUtils.isBlank(categoryEntity.getExternalId())) {
                            categoryEntity.setExternalId(externalId);
                            categoryRepository.save(categoryEntity);
                        }
                    }
                }
            }

            List<Item> items = convertToEntities(goods);
            List<Item> newItems = mergeWithExisting(items);
            return new ItemsChangeData(newItems);
        }
        return new ItemsChangeData();
    }

    private List<Item> mergeWithExisting(Iterable<Item> items) {
        Collection<Item> mergedItems = new ArrayList<>();
        List<Item> newItems = new ArrayList<>();
        for (Item item : items) {
            Item existingItem = itemRepository.findByUrl(item.getUrl());

            if (existingItem != null) {
                merge(existingItem, item);
                mergedItems.add(existingItem);
            } else {
                mergedItems.add(item);
                newItems.add(item);
            }
        }
        itemRepository.saveAll(mergedItems);
        return newItems;
    }

    private void merge(Item existingItem, Item item) {
        if ( !( existingItem.getAmount() != null && existingItem.getAmount().compareTo(item.getAmount()) == 0 ) ) {
            existingItem.setAmount( item.getAmount() );
        }

        if ( !Objects.equals( existingItem.getTitle(), item.getTitle() ) ) {
            existingItem.setTitle( item.getTitle() );
        }

        Map<String, PhotoEntity> existingPhotoMap = CollectionUtil.toMap(PhotoEntity::getUrl, existingItem.getPhotos());

        Set<PhotoEntity> photos = new HashSet<>();
        for (PhotoEntity photo : item.getPhotos()) {
            PhotoEntity existingPhoto = existingPhotoMap.get(photo.getUrl());
            if ( existingPhoto != null ) {
                existingPhoto.setContent( photo.getContent() );
                existingPhoto.setDescription( photo.getDescription() );
                photos.add(existingPhoto);
            } else {
                photos.add(photo);
            }
        }
        existingItem.setPhotos(photos);
    }

    private List<Item> convertToEntities(Iterable<GoodsEntry> goods) {
        List<Item> items = new ArrayList<>();
        for (GoodsEntry goodsEntry : goods) {
            items.add( itemConverter.convertToEntity(goodsEntry) );
        }
        return items;
    }

    private int getCount(CategoryEntity category) {
        Request request = Request.builder()
                .uri(category.getUrl())
                .quantity(20)
                .s("new")
                .city(0)
                .category(category.getExternalId())
                .page(1)
                .build();
        Response response = responseReader.read(request);

        return response.getCount();
    }

    private Response getPage(CategoryEntity category, int page, int pageSize) {
        Request request = Request.builder()
                .uri(category.getUrl())
                .quantity(pageSize)
                .s("new")
                .city(0)
                .category(category.getExternalId())
                .page(page)
                .build();
        return responseReader.read(request);
    }
}
