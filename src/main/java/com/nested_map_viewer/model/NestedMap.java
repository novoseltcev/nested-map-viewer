package com.nested_map_viewer.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NestedMap<T> {
    final int depth = 8;
    Link<T> link = new Link<>();
    Map<T, Object> nodeMap;
    List<List<T>> keyTrace;

    public NestedMap(Map<T, Object> map) {
        nodeMap = map;
    }

    public List<T> getLink() {
        return link;
    }

    public void setLink(Link<T> link) {
        this.link = link;
        setKeyTraceFromDepth(link.size());
    }

    public int getDepth() {
        return depth;
    }

    public List<List<T>> getKeyTrace() {
        return keyTrace;
    }

    private Map<T, Object> setKeyTraceFromDepth(int index) {
        if (index < 0 || index >= depth)
            throw new IllegalArgumentException("index {" + index + "} must between 0 and " + depth);

        Map<T, Object> map;
        if (index == 0) {
            map = nodeMap;
            keyTrace = new LinkedList<>();
        } else {
            map = setKeyTraceFromDepth(index - 1);
            map = (Map<T, Object>) map.get(link.get(index - 1));
        }
        if (map != null)
            keyTrace.add(map.keySet().stream().toList());
        return map;
    }


}
