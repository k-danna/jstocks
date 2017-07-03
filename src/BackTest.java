
package src;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;

class BackTest {
    
    static Monitor monitor = new Monitor();

    public BackTest() {
    }

    public static void test(User user, List<String> tickers) {
        //get the data
        List<List<Day>> data = load_data(tickers);
        
        //test
        for (List<Day> stock : data) {
            //start with fresh user
            //User temp = new User(user.name, ...);
            for (Day day : stock) {
                monitor.feed(user, day);
            }
        }
    }

    public static List<List<Day>> load_data(List<String> tickers) {
        String path;
        List<List<Day>> data = new ArrayList<List<Day>>();
        for (String ticker : tickers) {
            path = "data/" + ticker + ".csv";
            data.add(load_csv(path, ticker));
        }
        return data;
    }

    public static List<Day> load_csv(String path, String ticker) {
        List<Day> data = new ArrayList<Day>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            int linenr = 0;
            for (String line; (line = br.readLine()) != null; ) {
                if (linenr == 0) { ++linenr; continue; } //skip headers
                String[] info = line.split(",");
                if (info[1].equals("null")) {
                    ++linenr; continue; //unavailable day
                }
                String[] date = info[0].split("-");
                data.add(new Day(ticker, Integer.parseInt(date[0]), 
                        Integer.parseInt(date[1]), 
                        Integer.parseInt(date[2]),
                        Double.parseDouble(info[1]), 
                        Double.parseDouble(info[2]), 
                        Double.parseDouble(info[3]), 
                        Double.parseDouble(info[4]), 
                        Double.parseDouble(info[5]), 
                        Integer.parseInt(info[6])));
                ++linenr;
            }
            System.out.println("[+] loaded data from: " + path);
        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println("[-] file read failed: " + path);
        }

        return data;
    }

}

