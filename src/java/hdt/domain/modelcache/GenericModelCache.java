/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hdt.domain.modelcache;

import java.util.HashMap;
import java.util.Map;

/**
 * This class caches model information needed to render the view that is NOT already
 * contained in the web form. Useful to carry data across different controllers.
 * 
 * @param <T> The key type.
 * @param <X> The model object type to be cached.
 * 
 * @author escralp
 */
public abstract class GenericModelCache<T, X> {

    private Map<T, X> cachedModel;

    /**
     * Constructor
     */
    public GenericModelCache() {
        this.cachedModel = new HashMap<T, X>();
    }

    /**
     * Getter function for cached model.
     * 
     * @return The cached model.
     */
    public Map<T, X> getCachedModel() {
        return cachedModel;
    }

    /**
     * Setter function for cached model. This puts a model into the cache.
     * 
     * @param key
     * @param x
     */
    public void put(T key, X object) {
        this.cachedModel.put(key, object);
    }
}
