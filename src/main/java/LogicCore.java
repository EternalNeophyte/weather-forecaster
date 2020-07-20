import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class LogicCore {

    private ArrayList<BigDecimal> columnOfPressures, columnOfHumidities,
            columnOfWindSpeeds, columnOfDaylightDurations, columnOfTemperatures;
    private BigDecimal averageTemperature, qOfPressure, qOfHumidity, qOfWindSpeed, qOfDaylightDuration;
    private final static MathContext PRECISION = new MathContext(10);

    public LogicCore(ArrayList<ClimaticDay> weatherLog) {
        splitLogToColumns(weatherLog);
        averageTemperature = getAverageValue(columnOfTemperatures);
        qOfPressure = getQ(columnOfPressures, getAverageValue(columnOfPressures));
        qOfHumidity = getQ(columnOfHumidities, getAverageValue(columnOfHumidities));
        qOfWindSpeed = getQ(columnOfWindSpeeds, getAverageValue(columnOfWindSpeeds));
        qOfDaylightDuration = getQ(columnOfDaylightDurations, getAverageValue(columnOfDaylightDurations));
    }

    private void splitLogToColumns(ArrayList<ClimaticDay> weatherLog) {
        ArrayList<BigDecimal> columnOfPressures = new ArrayList<>();
        ArrayList<BigDecimal> columnOfHumidities = new ArrayList<>();
        ArrayList<BigDecimal> columnOfWindSpeeds = new ArrayList<>();
        ArrayList<BigDecimal> columnOfDaylightDurations = new ArrayList<>();
        ArrayList<BigDecimal> columnOfTemperatures = new ArrayList<>();
        for(ClimaticDay day : weatherLog) {
            columnOfPressures.add(day.getPressure());
            columnOfHumidities.add(day.getHumidity());
            columnOfWindSpeeds.add(day.getWindSpeed());
            columnOfDaylightDurations.add(day.getDaylightDuration());
            columnOfTemperatures.add(day.getTemperature());
        }
        this.columnOfPressures = columnOfPressures;
        this.columnOfHumidities = columnOfHumidities;
        this.columnOfWindSpeeds = columnOfWindSpeeds;
        this.columnOfDaylightDurations = columnOfDaylightDurations;
        this.columnOfTemperatures = columnOfTemperatures;

    }

    private BigDecimal getAverageValue(ArrayList<BigDecimal> columnOfCurrentValues) {
        BigDecimal sum = BigDecimal.ZERO;
        for(BigDecimal value : columnOfCurrentValues) {
            sum = sum.add(value);
        }
        return sum.divide(BigDecimal.valueOf(columnOfCurrentValues.size()), PRECISION);
    }

    private BigDecimal getQ(ArrayList<BigDecimal> columnOfCurrentValues, BigDecimal averageValue) {
        BigDecimal sumOfMuliplies = BigDecimal.ZERO, sumOfSquares = BigDecimal.ZERO;
        for(int i = 0; i < columnOfCurrentValues.size(); i++) {
            sumOfMuliplies = sumOfMuliplies.add(columnOfCurrentValues.get(i).subtract(averageValue)
                    .multiply(columnOfTemperatures.get(i).subtract(averageTemperature)));
            sumOfSquares = sumOfSquares.add(columnOfCurrentValues.get(i).subtract(averageValue).pow(2));
        }
        return sumOfMuliplies.divide(sumOfSquares, PRECISION);
    }

    private BigDecimal getIndependentQ() {
        return averageTemperature.subtract(qOfPressure.multiply(getAverageValue(columnOfPressures)))
                .subtract(qOfHumidity.multiply(getAverageValue(columnOfHumidities)))
                .subtract(qOfWindSpeed.multiply(getAverageValue(columnOfWindSpeeds)))
                .subtract(qOfDaylightDuration.multiply(getAverageValue(columnOfDaylightDurations)));
    }

    public BigDecimal predicateTemperature(ClimaticDay day) {
        return getIndependentQ().add(qOfPressure.multiply(day.getPressure())).add(qOfHumidity.multiply(day.getHumidity()))
                .add(qOfWindSpeed.multiply(day.getWindSpeed())).add(qOfDaylightDuration.multiply(day.getDaylightDuration()));
    }

    public BigDecimal getError(ClimaticDay day) {
        return predicateTemperature(day).subtract(day.getTemperature());
    }

    public String getCoefficients() {
        return "qOfPressure: " + qOfPressure + "\nqOfHumidity: " + qOfHumidity +
                "\nqOfWindSpeed: " + qOfWindSpeed + "\nqOfDaylightDuration: " + qOfDaylightDuration;
    }

}
