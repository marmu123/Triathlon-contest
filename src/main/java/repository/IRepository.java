package repository;

public interface IRepository<ID, T> {
    int size();
    T save(T entity);
    void delete(ID id);
    void update(ID id, T entity);
    T findOne(ID id);
    Iterable<T> findAll();
}
