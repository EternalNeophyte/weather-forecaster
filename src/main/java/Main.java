import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        try {
            String fs = System.getProperty("file.separator");
            Parser parser = new Parser("." + fs + "src" + fs + "main" + fs + "java" + fs +
                    "assets" + fs + "Weather Log 2019.txt");
            GeographicPoint pointOfSamara = new GeographicPoint();
            ArrayList<ClimaticDay> weatherLog = parser.getWeatherLogFromFile(pointOfSamara);
            LogicCore core = new LogicCore(weatherLog);
            parser.setFilePath("." + fs + "src" + fs + "main" + fs + "java" + fs +
                    "assets" + fs + "Coefficients.txt");
            parser.writeResultsOfML(core.getCoefficients());
            LocalDate date = LocalDate.of(2019, Month.NOVEMBER, 1);
            BigDecimal temperatue = core.predicateTemperature(new ClimaticDay(BigDecimal.valueOf(757), BigDecimal.valueOf(80), BigDecimal.valueOf(2.9),
                    pointOfSamara.getDaylightDuration(date)));
            System.out.println(String.format("Ожидаемая температура на %d %s %d: %.2f °C",
                    date.getDayOfMonth(), date.getMonth(), date.getYear(), temperatue));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}