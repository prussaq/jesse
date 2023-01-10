package net.prussaq.jesse.aspect;

import lombok.RequiredArgsConstructor;
import net.prussaq.jesse.service.DatabaseService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("!default")
@RequiredArgsConstructor
public class DatabaseModifiedAspect {

    private final DatabaseService databaseService;

    @Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository+.save(..))))")
    public void repositorySaveMethods() {}

    @Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository+.deleteById(..))))")
    public void repositoryDeleteByIdMethods() {}


    @After("repositorySaveMethods() && repositoryDeleteByIdMethods()")
    public void updateModCount() {
        databaseService.updateModCount();
    }
}
