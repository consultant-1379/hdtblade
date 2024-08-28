package hdt.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hdt.dao.GenericDao;
import hdt.domain.NamedType;
import java.util.Iterator;
import javax.servlet.ServletException;

// FIXME: This could be made even more generic if the dependence on BasDaoJdbcImpl
// was removed. It's good enough for now.
public abstract class GenericDaoJdbcImpl<T extends NamedType, ID extends Serializable>
        extends BaseDaoJdbcImpl
        implements GenericDao<T, ID> {
   
    private Integer idCounter = 1;
    private final Map<ID, T> TYPE_MAP = new HashMap<ID, T>();
 
    public GenericDaoJdbcImpl() throws ServletException {
        super();
    }

    @Override
    public T findById(ID id) {
        return TYPE_MAP.get(id);
    }
   
    @Override
    public List<T> findAll() {
        return new ArrayList<T>(TYPE_MAP.values());
    }

    @Override public void insertAll(List<T> entityList) {
        Iterator<T> iter = entityList.iterator();
        while (iter.hasNext()) {
            insert(iter.next());
        }
    }

    @Override
    public T merge(T entity) {
        if (entity.getName() == null) {
            return insert(entity);
        } else {
            return replace(entity);
        }
    }

    private T replace(T entity) {
        return TYPE_MAP.put((ID) entity.getName(), entity);
    }

    @Override
    public T insert(T entity) {
        entity.setId(idCounter++);
        return TYPE_MAP.put((ID) entity.getName(), entity);
    }

    @Override
    public void remove(T entity) {
        TYPE_MAP.remove((ID) entity.getName());
    }

    @Override
    public void clear() {
        TYPE_MAP.clear();
    }
}
