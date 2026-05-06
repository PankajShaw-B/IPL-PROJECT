package analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.*;


public class Analysis {

    
    public void matchesPlayedPerYear(List<Match> matches) {
        HashMap<Integer, Integer> countBySeason = new HashMap<>();

        for (Match m : matches) {
            countBySeason.put(m.season, countBySeason.getOrDefault(m.season, 0) + 1);
        }

        System.out.println("=== Matches Played Per Year ===");

        Map<Integer, Integer> sorted = new TreeMap<>(countBySeason);
        for (Map.Entry<Integer, Integer> entry : sorted.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " matches");
        }
    }

}
