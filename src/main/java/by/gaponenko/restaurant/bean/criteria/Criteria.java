package by.gaponenko.restaurant.bean.criteria;

import java.util.HashMap;
import java.util.Map;

/**
 * The ${@code Criteria} class provides the ability to search by criteria
 */
public class Criteria {
    private String groupSearchName;
    private Map<String, Object> criteria = new HashMap<String, Object>();

    public Criteria(){}

    public Criteria(String groupSearchName) {
        this.groupSearchName = groupSearchName;
    }

    public String getGroupSearchName() {
        return groupSearchName;
    }

    public void add(String searchCriteria, Object value) {
        criteria.put(searchCriteria, value);
    }

    public void remove(String searchCriteria) {
        criteria.remove(searchCriteria);
    }

    public Map<String, Object> getCriteria() {
        return criteria;
    }
}
