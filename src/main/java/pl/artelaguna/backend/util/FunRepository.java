package pl.artelaguna.backend.util;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

@NoRepositoryBean
public interface FunRepository<T, ID extends Serializable> extends Repository<T, ID> {

    <S extends T> S save(S object);

    <S extends T> Collection<S> save(Iterable<S> objects);

    default <S extends T> Collection<S> save(S... objects) {
        return save(objects);
    }

    Optional<T> findOne(ID id);

    boolean exists(ID id);

    Collection<T> findAll();

    Collection<T> findAll(Iterable<ID> objects);

    long count();

    void delete(ID id);

    void delete(T object);

    void delete(Iterable<? extends T> objects);

    void deleteAll();
}
