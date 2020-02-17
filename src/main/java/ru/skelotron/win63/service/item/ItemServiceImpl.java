package ru.skelotron.win63.service.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.skelotron.win63.converer.ItemConverter;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.entity.PhotoEntity;
import ru.skelotron.win63.http_entities.GoodsEntry;
import ru.skelotron.win63.http_entities.Request;
import ru.skelotron.win63.http_entities.Response;
import ru.skelotron.win63.repository.ItemRepository;
import ru.skelotron.win63.repository.SettingsRepository;
import ru.skelotron.win63.service.response_reader.ResponseReader;
import ru.skelotron.win63.util.CollectionUtil;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    private final SettingsRepository settingsRepository;
    private final ResponseReader responseReader;
    private final ItemConverter itemConverter;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(SettingsRepository settingsRepository, @Qualifier("DummyResponseReader") ResponseReader responseReader, ItemConverter itemConverter, ItemRepository itemRepository) {
        this.settingsRepository = settingsRepository;
        this.responseReader = responseReader;
        this.itemConverter = itemConverter;
        this.itemRepository = itemRepository;
    }

    @Override
    public void load(CategoryEntity category) {
        int quantity = Integer.parseInt(settingsRepository.findByName("pageSize").getValue());
        int count = getCount(category);
        if (count > 0) {
            int batchesCount = (count / quantity) + (((count % quantity) == 0) ? 0 : 1);

            Collection<GoodsEntry> goods = new ArrayList<>();
            for (int batch = 0; batch < batchesCount; batchesCount++) {
                Response response = getPage(category, batch, quantity);
                System.out.println(response);
                goods.addAll(response.getGoods());
            }

            List<Item> items = convertToEntities(goods);
            mergeWithExisting(items);
        }
    }

    private void mergeWithExisting(Iterable<Item> items) {
        Collection<Item> mergedItems = new ArrayList<>();
        for (Item item : items) {
            Item existingItem = itemRepository.findByUrl(item.getUrl());

            if (existingItem != null) {
                merge(existingItem, item);
                mergedItems.add(existingItem);
            } else {
                mergedItems.add(item);
            }
        }
        itemRepository.saveAll(mergedItems);
    }

    private void merge(Item existingItem, Item item) {
        if ( !Objects.equals( existingItem.getAmount(), item.getAmount() ) ) {
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
                .quantity(1)
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
