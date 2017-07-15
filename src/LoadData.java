
package src;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

public class LoadData {

    public LoadData() {
    }

    public void update(List<String> tickers) {
        //FIXME: update all tickers to current day
        for (String ticker : tickers) {
            load_csv("data/" + ticker + ".csv", ticker);
        }
    }

    public void update(String ticker) {
        //FIXME: update ticker to current day
        load_csv("data/" + ticker + ".csv", ticker);
    }

    public void load_csv(String path, String ticker) {
        Database db = new Database(); //connect to or create db
        try {

            //if data already loaded, return
            //DEBUG:
            //if (true) return;

            //create new table
            db.update("create table if not exists " + ticker + " ("
                    + "id integer primary key autoincrement, "
                    + "year integer, "
                    + "month integer, "
                    + "day integer, "
                    + "open real, "
                    + "high real, "
                    + "low real, "
                    + "close real, "
                    + "adjclose real, "
                    //+ "avg real, "
                    + "volume integer, "
                    //+ "ema12 real, "
                    //+ "ema24 real, "
                    //+ "macd real, "
                    //+ "macdpred real, "
                    + "unique (year, month, day)"
                    + ")");
            BufferedReader br = new BufferedReader(new FileReader(path));
            int linenr = 0;
            for (String line; (line = br.readLine()) != null; ) {
                if (linenr == 0) { ++linenr; continue; } //skip headers
                String[] info = line.split(",");
                if (info[1].equals("null")) {
                    ++linenr; continue; //unavailable day
                }
                String[] date = info[0].split("-");
                String placeholder = "-1.0";
                //insert day into db
                db.update("insert or ignore into " + ticker + "("
                        + "year, month, day, open, high, low, close, "
                        + "adjclose, "
                        //+ "avg, "
                        + "volume"
                        //+ ", ema12, ema24, macd, "
                        //+ "macdpred"
                        + ") values(" 
                        + date[0] + ", " 
                        + date[1] + ", " 
                        + date[2] + ", " 
                        + info[1] + ", " 
                        + info[2] + ", " 
                        + info[3] + ", " 
                        + info[4] + ", " 
                        + info[5] + ", " 
                        //+ placeholder + ", "
                        + info[6] //+ ", "
                        //+ placeholder + ", "
                        //+ placeholder + ", "
                        //+ placeholder + ", "
                        //+ placeholder 
                        + ")");
                ++linenr;
            }
            System.out.println("[+] loaded "+linenr+" rows from: " + path);
        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println("[-] file read failed: " + path);
        }
        db.disconnect();
    }

}

