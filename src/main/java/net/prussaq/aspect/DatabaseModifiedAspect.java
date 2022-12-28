package net.prussaq.aspect;

import lombok.RequiredArgsConstructor;
import net.prussaq.service.DatabaseService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
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
