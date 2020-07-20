import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class Parser {

    private String filePath;

    public Parser(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<ClimaticDay> getWeatherLogFromFile(GeographicPoint point) throws IOException {
        ArrayList<ClimaticDay> weatherLog = new ArrayList<>();
        String[] tokens;
        LocalDate currentDate = LocalDate.now();
        for(String line : Files.readAllLines(Path.of(filePath))) {
            line = line.replaceAll("\\uFEFF", "");
            line = line.replaceAll("−", "-");
            tokens = line.split("[\\s \t%°C]+");
            if(tokens.length == 2) {
                currentDate = LocalDate.of(2019, getMonth(tokens[1]), Integer.parseInt(tokens[0]));
            }
            else if(tokens.length >= 5) {
                weatherLog.add(new ClimaticDay(new BigDecimal(tokens[3]), new BigDecimal(tokens[2]), new BigDecimal(tokens[4]),
                        new BigDecimal(tokens[1]), point.getDaylightDuration(currentDate)));
            }
        }
        return weatherLog;
    }

    public void writeResultsOfML(String coefficients) throws IOException {
            Files.writeString(Path.of(filePath), coefficients);
    }

    private Month getMonth(String value) {
        switch (value) {
            case "января":
                return Month.JANUARY;
            case "февраля":
                return Month.FEBRUARY;
            case "марта":
                return Month.MARCH;
            case "апреля":
                return Month.APRIL;
            case "мая":
                return Month.MAY;
            case "июня":
                return Month.JUNE;
            case "июля":
                return Month.JULY;
            case "августа":
                return Month.AUGUST;
            case "сентября":
                return Month.SEPTEMBER;
            case "октября":
                return Month.OCTOBER;
            case "ноября":
                return Month.NOVEMBER;
            case "декабря":
                return Month.DECEMBER;
        }
        return Month.JANUARY;
    }
}
