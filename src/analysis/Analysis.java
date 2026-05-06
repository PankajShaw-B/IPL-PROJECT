package analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public void extraRunsPerTeam(List<Match> matches, List<Delivery> deliveries) {
    
        Set<Integer> matchIdsIn2016 = new HashSet<>();
        for (Match m : matches) {
            if (m.season == 2016) {
                matchIdsIn2016.add(m.id);
            }
        }

        HashMap<String, Integer> extraRunsByTeam = new HashMap<>();
        for (Delivery d : deliveries) {
            if (matchIdsIn2016.contains(d.matchId)) {
                extraRunsByTeam.put(d.bowlingTeam,
                        extraRunsByTeam.getOrDefault(d.bowlingTeam, 0) + d.extraRuns);
            }
        }

        System.out.println("=== Extra Runs Per Team (Season 2016) ===");
        for (Map.Entry<String, Integer> entry : extraRunsByTeam.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " extra runs");
        }
    }

}
