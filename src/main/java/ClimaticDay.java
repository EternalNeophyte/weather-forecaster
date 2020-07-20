import java.math.BigDecimal;
import java.time.LocalDate;

public class ClimaticDay {

    private BigDecimal pressure, humidity, windSpeed, temperature, daylightDuration;
    private static final BigDecimal NORMAL_PRESSURE = BigDecimal.valueOf(760);
    private static final BigDecimal NORMAL_HUMIDITY = BigDecimal.valueOf(50);
    private static final BigDecimal DEFAULT_WIND_SPEED = BigDecimal.ZERO;
    private static final BigDecimal DEFAULT_TEMPERATURE = BigDecimal.ZERO;

    public ClimaticDay(BigDecimal daylightDuration) {
        this(NORMAL_PRESSURE, NORMAL_HUMIDITY, DEFAULT_WIND_SPEED, daylightDuration);
    }

    public ClimaticDay(BigDecimal pressure, BigDecimal humidity, BigDecimal windSpeed, BigDecimal daylightDuration) {
        this(pressure, humidity, windSpeed, DEFAULT_TEMPERATURE, daylightDuration);
    }

    public ClimaticDay(BigDecimal pressure, BigDecimal humidity, BigDecimal windSpeed,
                       BigDecimal temperature, BigDecimal daylightDuration) {
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.temperature = temperature;
        this.daylightDuration = daylightDuration;
    }

    public BigDecimal getPressure() {
        return pressure;
    }

    public void setPressure(BigDecimal pressure) {
        this.pressure = pressure;
    }

    public BigDecimal getHumidity() {
        return humidity;
    }

    public void setHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    public BigDecimal getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(BigDecimal windSpeed) {
        this.windSpeed = windSpeed;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getDaylightDuration() {
        return daylightDuration;
    }

    public void setDaylightDuration(BigDecimal daylightDuration) {
        this.daylightDuration = daylightDuration;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\n=== Климатический день ===");
        builder.append("\nДавление: ").append(pressure).append(" мм рт. ст.\nВлажность воздуха: ").append(humidity);
        builder.append("%\nСкорость ветра: ").append(windSpeed).append(" м/с\nТемпература: ").append(temperature);
        builder.append(" °C\nДлительность светового дня: ").append(daylightDuration).append(" ч\n");
        return builder.toString();
    }
}
