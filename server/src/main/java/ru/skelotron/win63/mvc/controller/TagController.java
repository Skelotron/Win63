package ru.skelotron.win63.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skelotron.win63.common.Tag;
import ru.skelotron.win63.mvc.model.AvailableTags;

import java.util.Arrays;

@RestController
@RequestMapping("tag")
public class TagController {

    @GetMapping("available/{type}")
    public ResponseEntity<AvailableTags> getAvailableTags(@PathVariable TagType type) {
        switch (type) {
            case MESSAGE:
            case SUBJECT:
                return ResponseEntity.ok(new AvailableTags(Arrays.asList(Tag.ITEM_COST.getName(), Tag.ITEM_TITLE.getName(), Tag.ITEM_DESCRIPTION.getName(), Tag.ITEM_URL.getName())));
            default:
                return ResponseEntity.ok(new AvailableTags());
        }
    }

    enum TagType {
        MESSAGE,
        SUBJECT
    }
}
