package org.example.utitilty;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryUtils {
    static Logger logger = Logger.getLogger(QueryUtils.class.getName());

    public static <S> String getInsertQuery(S entity) {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO");
        stringBuilder.append(" ")
                .append(entity.getClass().getSimpleName())
                .append(" ").append("(");
        Field[] fields = entity.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> stringBuilder.append("`").append(field.getName()).append("`,"));
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append(")")
                .append(" ")
                .append("VALUES")
                .append(" ")
                .append("(");

        Arrays.stream(fields).forEach(field -> {
            try {
                field.setAccessible(true);
                stringBuilder.append("\'")
                        .append(field.get(entity))
                        .append("\',");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append(");");

        logger.log(Level.INFO, "Query: " + stringBuilder);
        return stringBuilder.toString();
    }

    public static <S> String findAll(Class<S> s) {
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM ");
        stringBuilder.append(s.getSimpleName());
        stringBuilder.append(";");
        logger.log(Level.INFO, "Query: " + stringBuilder);
        return stringBuilder.toString();
    }

    public static <ID, S> String findById(Class<S> s, ID id) {
        Field field = Arrays.stream(s.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst().orElseThrow(() -> new RuntimeException("@Id not found"));

        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM ");
        stringBuilder.append(s.getSimpleName());
        stringBuilder.append(" ");
        stringBuilder.append("WHERE");
        stringBuilder.append(" ");
        stringBuilder.append(field.getName());
        stringBuilder.append("=");
        stringBuilder.append(id);
        stringBuilder.append(" ");
        stringBuilder.append("LIMIT 1");
        stringBuilder.append(";");
        logger.log(Level.INFO, "Query: " + stringBuilder);
        return stringBuilder.toString();
    }


    public static <ID, S> String existsById(Class<S> s, ID id) {
        Field field = Arrays.stream(s.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst().orElseThrow(() -> new RuntimeException("@Id not found"));
        StringBuilder stringBuilder = new StringBuilder("SELECT  1  FROM ");
        stringBuilder.append(s.getSimpleName());
        stringBuilder.append(" ");
        stringBuilder.append("WHERE");
        stringBuilder.append(" ");
        stringBuilder.append(field.getName());
        stringBuilder.append("=");
        stringBuilder.append(id);
        stringBuilder.append(";");
        logger.log(Level.INFO, "Query: " + stringBuilder);
        return stringBuilder.toString();
    }

    public static <T> String getCountQuery(Class<T> type) {
        StringBuilder stringBuilder = new StringBuilder("SELECT  count(*)  FROM ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(";");
        logger.log(Level.INFO, "Query: " + stringBuilder);
        return stringBuilder.toString();
    }

    public static <T, ID> String getDeleteQuery(Class<T> type, ID id) {
        Field field = Arrays.stream(type.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst().orElseThrow(() -> new RuntimeException("@Id not found"));
        StringBuilder stringBuilder = new StringBuilder("DELETE  FROM ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" ");
        stringBuilder.append("WHERE");
        stringBuilder.append(" ");
        stringBuilder.append(field.getName());
        stringBuilder.append("=");
        stringBuilder.append(id);
        stringBuilder.append(";");
        logger.log(Level.INFO, "Query: " + stringBuilder);
        return stringBuilder.toString();
    }
}