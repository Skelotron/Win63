package ru.skelotron.win63.mvc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.mvc.model.FilterModel;
import ru.skelotron.win63.mvc.model.NotifiedModel;
import ru.skelotron.win63.entity.EmailNotified;
import ru.skelotron.win63.entity.Filter;
import ru.skelotron.win63.entity.Notified;
import ru.skelotron.win63.exception.EntityNotFoundException;
import ru.skelotron.win63.repository.NotifiedRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class NotifiedModelConverter implements ModelConverter<Notified, NotifiedModel> {
    private final FilterModelConverter filterModelConverter;
    private final NotifiedRepository notifiedRepository;

    @Autowired
    public NotifiedModelConverter(FilterModelConverter filterModelConverter, NotifiedRepository notifiedRepository) {
        this.filterModelConverter = filterModelConverter;
        this.notifiedRepository = notifiedRepository;
    }


    @Override
    public NotifiedModel convertToModel(Notified entity) {
        NotifiedModel notified = new NotifiedModel();
        notified.setId(entity.getId());
        notified.setRecipient(entity.getRecipient());
        if (entity instanceof EmailNotified) {
            notified.setSubject( ((EmailNotified) entity).getSubjectTemplate() );
            notified.setMessage( ((EmailNotified) entity).getTextTemplate() );
        }
        notified.setFilters(new ArrayList<>());
        for (Filter filter : entity.getFilters()) {
            notified.getFilters().add( filterModelConverter.convertToModel(filter) );
        }
        return notified;
    }

    @Override
    public Notified convertToEntity(NotifiedModel model) {
        EmailNotified notified;
        if (model.getId() != null) {
            notified = notifiedRepository.findById(model.getId()).orElseThrow(() -> new EntityNotFoundException(Notified.class, model.getId()));
        } else {
            notified = new EmailNotified();
        }
        notified.setEmail(model.getRecipient());
        notified.setSubjectTemplate(model.getSubject());
        notified.setTextTemplate(model.getMessage());

        Set<Filter> filters = new HashSet<>();
        for (FilterModel filterModel : model.getFilters()) {
            Filter filter = filterModelConverter.convertToEntity(filterModel);
            if (filter.getNotified() == null) {
                filter.setNotified(notified);
            }
            filters.add(filter);
        }
        notified.setFilters(filters);

        return notified;
    }
}
