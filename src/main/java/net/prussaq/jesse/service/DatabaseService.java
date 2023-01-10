package net.prussaq.jesse.service;

import lombok.RequiredArgsConstructor;
import net.prussaq.jesse.dao.DatabaseDao;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Profile("!default")
@RequiredArgsConstructor
public class DatabaseService {

    private final DatabaseDao databaseDao;
    private final AtomicInteger modCount = new AtomicInteger();
    private int oldModCount;

    @Scheduled(cron = "0 0 12 * * ?")
    public synchronized void backup() {
        if (oldModCount != modCount.get()) {
            System.out.println("DB modified count = " + modCount.get());
            oldModCount = modCount.get();
            databaseDao.backup();
        }
    }

    public void updateModCount() {
        int i = modCount.incrementAndGet();
        System.out.println("Incrementing modCount = " + i);
    }
}
