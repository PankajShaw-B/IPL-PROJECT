package loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Delivery;
import model.Match;


public class CsvLoader {

    public static List<Match> loadMatches(String filePath) throws IOException {
        List<Match> matches = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        reader.readLine();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] columns = splitCsvLine(line);
            Match m = parseMatch(columns);
            matches.add(m);
        }

        reader.close();
        return matches;
    }

    public static List<Delivery> loadDeliveries(String filePath) throws IOException {
        List<Delivery> deliveries = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        reader.readLine();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] columns = splitCsvLine(line);
            Delivery d = parseDelivery(columns);
            deliveries.add(d);
        }

        reader.close();
        return deliveries;
    }

    private static Match parseMatch(String[] col) {
        Match m = new Match();
        m.id            = toInt(col[0]);
        m.season        = toInt(col[1]);
        m.city          = col[2];
        m.date          = col[3];
        m.team1         = col[4];
        m.team2         = col[5];
        m.tossWinner    = col[6];
        m.tossDecision  = col[7];
        m.result        = col[8];
        m.dlApplied     = col[9];
        m.winner        = col[10];
        m.winByRuns     = toInt(col[11]);
        m.winByWickets  = toInt(col[12]);
        m.playerOfMatch = col[13];
        m.venue         = col[14];
        m.umpire1       = col[15];
        m.umpire2       = col[16];
        return m;
    }

    private static Delivery parseDelivery(String[] col) {
        Delivery d = new Delivery();
        d.matchId         = toInt(col[0]);
        d.inning          = toInt(col[1]);
        d.battingTeam     = col[2];
        d.bowlingTeam     = col[3];
        d.over            = toInt(col[4]);
        d.ball            = toInt(col[5]);
        d.batsman         = col[6];
        d.nonStriker      = col[7];
        d.bowler          = col[8];
        d.isSuperOver     = toInt(col[9]);
        d.wideRuns        = toInt(col[10]);
        d.byeRuns         = toInt(col[11]);
        d.legByeRuns      = toInt(col[12]);
        d.noBallRuns      = toInt(col[13]);
        d.penaltyRuns     = toInt(col[14]);
        d.batsmanRuns     = toInt(col[15]);
        d.extraRuns       = toInt(col[16]);
        d.totalRuns       = toInt(col[17]);
        d.playerDismissed = col[18];
        d.dismissalKind   = col[19];
        d.fielder         = col[20];
        return d;
    }

    private static String[] splitCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                fields.add(currentField.toString().trim());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }

        fields.add(currentField.toString().trim());

        return fields.toArray(new String[0]);
    }

    private static int toInt(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
