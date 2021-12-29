package com.nested_map_viewer.model;

import java.io.StreamCorruptedException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represent a nested map with the selection of elements in the chain
 * @author Novoseltcev Stanislav
 * @version 1.0
 */
public class NestedMap<T> {
    final int depth = 8;
    Chain<T> chain = new Chain<>();
    Map<T, Object> nodeMap;
    List<List<T>> keyTrace;

    public NestedMap(Map<T, Object> map) {
        nodeMap = map;
    }

    public List<T> getChain() {
        return chain;
    }

    public void setChain(Chain<T> chain) throws StreamCorruptedException {
        this.chain = chain;
        try {
            setKeyTraceFromDepth(chain.size());
        } catch (NullPointerException e) {
            throw new StreamCorruptedException();
        }
    }

    public int getDepth() {
        return depth;
    }

    public List<List<T>> getKeyTrace() {
        return keyTrace;
    }

    /**
     * Recursive function that sets the selection along the length of the chain
     */
    private Map<T, Object> setKeyTraceFromDepth(int index) {
        if (index < 0 || index >= depth)
            throw new IllegalArgumentException("index {" + index + "} must between 0 and " + depth);

        Map<T, Object> map;
        if (index == 0) {
            map = nodeMap;
            keyTrace = new LinkedList<>();
        } else {
            map = setKeyTraceFromDepth(index - 1);
            map = (Map<T, Object>) map.get(chain.get(index - 1));
        }
        if (map != null)
            keyTrace.add(map.keySet().stream().toList());
        return map;
    }
}
