package dev.kirky.core.services.impl;

import org.osgi.service.component.annotations.Component;
import dev.kirky.core.services.ReadingTimeService;

@Component(service = ReadingTimeService.class)
public class ReadingTimeServiceImpl implements ReadingTimeService {

    private static final int WORDS_PER_MINUTE = 200;

    @Override
    public int minutes(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }
        int words = text.trim().split("\\s+").length;
        return Math.max(1, (int) Math.ceil((double) words / WORDS_PER_MINUTE));
    }
}