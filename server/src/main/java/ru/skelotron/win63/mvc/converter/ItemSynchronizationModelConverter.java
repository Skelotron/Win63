package ru.skelotron.win63.mvc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.mvc.model.ItemSynchronizationModel;
import ru.skelotron.win63.entity.ItemSynchronizationEntity;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.repository.ItemSynchronizationRepository;

@Component
public class ItemSynchronizationModelConverter implements ModelConverter<ItemSynchronizationEntity, ItemSynchronizationModel> {
    private final CategoryModelConverter categoryModelConverter;
    private final ItemSynchronizationRepository itemSynchronizationRepository;

    @Autowired
    public ItemSynchronizationModelConverter(CategoryModelConverter categoryModelConverter, ItemSynchronizationRepository itemSynchronizationRepository) {
        this.categoryModelConverter = categoryModelConverter;
        this.itemSynchronizationRepository = itemSynchronizationRepository;
    }

    @Override
    public ItemSynchronizationModel convertToModel(ItemSynchronizationEntity entity) {
        ItemSynchronizationModel model = new ItemSynchronizationModel();
        model.setId(entity.getId());
        model.setCategory( categoryModelConverter.convertToModel( entity.getCategory() ) );
        model.setManual( entity.getManual() );
        model.setNewItemsCount( entity.getNewEntitiesCount() );
        model.setSyncDate( entity.getSyncDate() );
        return model;
    }

    @Override
    public ItemSynchronizationEntity convertToEntity(ItemSynchronizationModel model) {
        ItemSynchronizationEntity entity;
        Long id = model.getId();
        if (id != null) {
            entity = itemSynchronizationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ItemSynchronizationEntity.class, id));
        } else {
            entity = new ItemSynchronizationEntity();
        }
        entity.setCategory( categoryModelConverter.convertToEntity(model.getCategory()) );
        entity.setManual( model.getManual() );
        entity.setNewEntitiesCount( model.getNewItemsCount() );
        entity.setSyncDate( model.getSyncDate() );
        return entity;
    }
}
