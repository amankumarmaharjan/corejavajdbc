
package org.example.repository.impl;


import org.example.config.ConnectionFactory;
import org.example.utitilty.Assert;
import org.example.utitilty.Id;
import org.example.utitilty.QueryUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;


public class SimpleJpaRepository<T, ID> implements CrudRepository<T, ID> {

    private final Class<T> type;

    public SimpleJpaRepository(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return this.type;
    }

    static Connection connection = ConnectionFactory.database.getConnectionInstance();


    private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";


    @Override
    public <S extends T> S save(S entity) {
        Assert.notNull(entity, "Entity must not be null.");
        String insertQuery = QueryUtils.getInsertQuery(entity);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Iterable<T> findAll() {
        String findAllQuery = QueryUtils.findAll(getType());
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(findAllQuery);) {
            List<T> list = new ArrayList<T>();
            while (rs.next()) {
                T t = getType().getDeclaredConstructor().newInstance();
                loadResultSetIntoObject(rs, t);
                list.add(t);
            }
            return list;
        } catch (SQLException | InstantiationException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        String findByIdQuery = QueryUtils.findById(getType(), id);
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(findByIdQuery);) {
            rs.next();
            T record = getType().getDeclaredConstructor().newInstance();
            loadResultSetIntoObject(rs, record);
            return Optional.ofNullable(record);
        } catch (SQLException | IllegalAccessException | NoSuchMethodException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(ID id) {
        String findByIdQuery = QueryUtils.existsById(getType(), id);
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(findByIdQuery);) {
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        String countQuery = QueryUtils.getCountQuery(getType());
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(countQuery);) {
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(ID id) {
        String deleteQuery = QueryUtils.getDeleteQuery(getType(), id);
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeUpdate(deleteQuery);) {

            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

    private void loadResultSetIntoObject(ResultSet rst, T object)
            throws IllegalArgumentException, IllegalAccessException, SQLException {
        Class<?> zclass = object.getClass();
        for (Field field : zclass.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = rst.getObject(field.getName());
            Class<?> type = field.getType();
            if (isPrimitive(type)) {
                Class<?> boxed = boxPrimitiveClass(type);
                value = boxed.cast(value);
            }
            field.set(object, value);
        }
    }

    public static Class<?> boxPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else {
            String string = "class '" + type.getName() + "' is not a primitive";
            throw new IllegalArgumentException(string);
        }
    }

    public static boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class || type == double.class || type == float.class
                || type == boolean.class || type == byte.class || type == char.class || type == short.class);
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        return null;
    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {

    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }

}
