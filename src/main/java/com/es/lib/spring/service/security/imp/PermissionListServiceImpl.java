package com.es.lib.spring.service.security.imp;

import com.es.lib.entity.iface.security.event.PermissionListInitEvent;
import com.es.lib.entity.iface.security.event.PermissionListPostProcessEvent;
import com.es.lib.entity.iface.security.model.Groups;
import com.es.lib.entity.iface.security.model.PermissionListBuilder;
import com.es.lib.spring.service.security.PermissionListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionListServiceImpl implements PermissionListService {

    private final ApplicationEventPublisher eventPublisher;

    private Groups groups;
    private Collection<String> allKeys;

    @PostConstruct
    public void postConstruct() {
        PermissionListBuilder builder = new PermissionListBuilder();
        // Fire collect all event
        eventPublisher.publishEvent(new PermissionListInitEvent(builder));
        log.trace("Init permission: " + builder);
        // Fire post process event (for exclude)
        eventPublisher.publishEvent(new PermissionListPostProcessEvent(builder));
        log.trace("Post process permission: " + builder);

        groups = builder.build();
        allKeys = groups.getKeys();

        log.trace("All available permissions: " + groups);
    }

    @Override
    public Groups getGroups() {
        if (groups == null) {
            return new Groups();
        }
        return new Groups(groups);
    }

    @Override
    public Collection<String> getAllKeys() {
        return allKeys;
    }

    @Override
    public boolean isAvailable(String key) {
        return getAllKeys().contains(key);
    }
}
