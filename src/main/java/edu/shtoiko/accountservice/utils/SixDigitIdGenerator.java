package edu.shtoiko.accountservice.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Random;

public class SixDigitIdGenerator implements BeforeExecutionGenerator {

    private static final Random random = new Random();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj, Object event, EventType eventType) {
        int id;
        do {
            id = 100000 + random.nextInt(900000);
        } while (isIdExists(session, id));

        return id;
    }

    private boolean isIdExists(SharedSessionContractImplementor session, int id) {
        String sql = "SELECT COUNT(*) FROM " + session.getEntityPersister(null, null).getEntityName() + " WHERE id = :id";
        Long count = ((Number) session.createNativeQuery(sql)
                .setParameter("id", id)
                .getSingleResult()).longValue();
        return count > 0;
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EnumSet.of(EventType.INSERT);
    }
}