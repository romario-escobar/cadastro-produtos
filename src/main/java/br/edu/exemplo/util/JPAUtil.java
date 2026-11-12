package br.edu.exemplo.util;

import jakarta.persistence.*;

public final class JPAUtil {

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("produtosPU");

    private JPAUtil() {}

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

    public static void fechar() {
        if (FACTORY.isOpen()) FACTORY.close();
    }
}