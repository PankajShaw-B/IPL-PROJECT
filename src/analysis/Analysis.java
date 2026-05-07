package analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public void matchesWonPerTeam(List<Match> matches) {
        HashMap<String, Integer> winsByTeam = new HashMap<>();

        for (Match m : matches) {
            if (m.winner != null && !m.winner.isEmpty()) {
                winsByTeam.put(m.winner, winsByTeam.getOrDefault(m.winner, 0) + 1);
            }
        }

        System.out.println("=== Matches Won Per Team ===");
        for (Map.Entry<String, Integer> entry : winsByTeam.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " wins");
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

    public void top10bowlerEconomy(List<Match> matches, List<Delivery> deliveries) {
        Set<Integer> matchIdsIn2015 = new HashSet<>();
        for (Match m : matches) {
            if (m.season == 2015) {
                matchIdsIn2015.add(m.id);
            }
        }

        
        HashMap<String, Integer> runsByBowler  = new HashMap<>();
        HashMap<String, Integer> ballsByBowler = new HashMap<>();

        for (Delivery d : deliveries) {
            if (matchIdsIn2015.contains(d.matchId)) {
                runsByBowler.put(d.bowler,
                        runsByBowler.getOrDefault(d.bowler, 0) + d.totalRuns);

                if (d.wideRuns == 0) {
                    ballsByBowler.put(d.bowler,
                            ballsByBowler.getOrDefault(d.bowler, 0) + 1);
                }
            }
        }
        HashMap<String, Double> economyByBowler = new HashMap<>();
        for (String bowler : runsByBowler.keySet()) {
            int runs  = runsByBowler.get(bowler);
            int balls = ballsByBowler.getOrDefault(bowler, 0);
            if (balls > 0) {
                double economy = (runs * 6.0) / balls;
                economyByBowler.put(bowler, economy);
            }
        }
        List<Map.Entry<String, Double>> sortedList = new ArrayList<>(economyByBowler.entrySet());
        Collections.sort(sortedList, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
                return Double.compare(a.getValue(), b.getValue());
            }
        });

        System.out.println("=== Top 10 Bowlers by Economy (Season 2015) ===");
        int count = 0;
        for (Map.Entry<String, Double> entry : sortedList) {
            System.out.printf("%-25s -> %.2f%n", entry.getKey(), entry.getValue());
            count++;
            if (count == 10) break;
        }
    }

    public void totalSixesPerTeam(List<Delivery> deliveries) {
        HashMap<String, Integer> sixesByTeam = new HashMap<>();

        for (Delivery d : deliveries) {
            if (d.batsmanRuns == 6) {
                sixesByTeam.put(d.battingTeam,
                    sixesByTeam.getOrDefault(d.battingTeam, 0) + 1);
            }
        }

        System.out.println("=== Total Sixes Per Team ===");
        for (Map.Entry<String, Integer> entry : sixesByTeam.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public void topBatsmanStrikeRate(List<Delivery> deliveries) {

        HashMap<String, Integer> runs = new HashMap<>();
        HashMap<String, Integer> balls = new HashMap<>();

        for (Delivery d : deliveries) {
            runs.put(d.batsman,
                runs.getOrDefault(d.batsman, 0) + d.batsmanRuns);

            if (d.wideRuns == 0) {
                balls.put(d.batsman,
                    balls.getOrDefault(d.batsman, 0) + 1);
            }
        }

        HashMap<String, Double> strikeRate = new HashMap<>();

        for (String batsman : runs.keySet()) {
            int b = balls.getOrDefault(batsman, 0);
            if (b >= 100) {
                double sr = (runs.get(batsman) * 100.0) / b;
                strikeRate.put(batsman, sr);
            }
        }

        List<Map.Entry<String, Double>> list = new ArrayList<>(strikeRate.entrySet());
        Collections.sort(list, (a, b) -> Double.compare(b.getValue(), a.getValue()));

        System.out.println("=== Top 10 Batsmen by Strike Rate ===");
        int count = 0;
        for (Map.Entry<String, Double> e : list) {
            System.out.printf("%-25s -> %.2f%n", e.getKey(), e.getValue());
            if (++count == 10) break;
        }
    }

    public void highestStrikeRateAgainstRCB(List<Match> matches,List<Delivery> deleveries){

        HashMap<Integer, String> matchVenue = new HashMap<>();

        for(Match m : matches){
            if(m.season == 2016 && (m.team1.equals("Royal Challengers Bangalore") || m.team2.equals("Royal Challengers Bangalore"))){
                    matchVenue.put(m.id, m.venue);
            }
        }

        HashMap<String, HashMap<String, Integer>> runsMap = new HashMap<>();

        HashMap<String, HashMap<String, Integer>> ballsMap = new HashMap<>();

        for(Delivery d : deleveries){
            if(matchVenue.containsKey(d.matchId)){
                if(d.bowlingTeam.equals("Royal Challengers Bangalore")){
                    String venue = matchVenue.get(d.matchId);

                    runsMap.putIfAbsent(venue, new HashMap<>());

                    ballsMap.putIfAbsent(venue, new HashMap<>());

                    HashMap<String, Integer> run = runsMap.get(venue);
                    HashMap<String, Integer>  ball = ballsMap.get(venue);

                    run.put(d.batsman, run.getOrDefault(d.batsman, 0) + d.batsmanRuns);

                    if(d.wideRuns == 0){
                        ball.put(d.batsman, ball.getOrDefault(d.batsman, 0) + 1);
                    }
                }
            }
        }

        System.out.println("Highest Strike Rate Against RCB By Venue (2016): ");

        for(String venue : runsMap.keySet()){
        
            HashMap<String, Integer> run = runsMap.get(venue);
            HashMap<String, Integer> ball = ballsMap.get(venue);

            String bestPlayer = "";
            double bestSR = 0;


            for(String batsman : run.keySet()){

                int runs = run.get(batsman);
                int balls = ball.getOrDefault(batsman, 0);

                if(balls > 0){
                    double sr = (runs * 100.0) / balls;

                    if(sr > bestSR){
                        bestSR = sr;
                        bestPlayer = batsman;
                    }
                }
            }

            System.out.printf("%s -> %s -> %.2f SR%n", venue, bestPlayer, bestSR);
        }
    }

}


