package net.prussaq.service;

import lombok.RequiredArgsConstructor;
import net.prussaq.dao.DatabaseDao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
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
