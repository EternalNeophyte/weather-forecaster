import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;

public class GeographicPoint {

    private BigDecimal latitude;
    private static final BigDecimal DEFAULT_LATITUDE = BigDecimal.valueOf(53.2001);
    private static final MathContext PRECISION = new MathContext(10);

    public GeographicPoint() {
        this(DEFAULT_LATITUDE);
    }

    public GeographicPoint(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getDeclinationAngle(LocalDate date) {
        return BigDecimal.valueOf(23.45).multiply(BigDecimal.valueOf(Math.sin(Math.toRadians(360.0 / 365.0 * (date.getDayOfYear() - 81)))));
    }

    public BigDecimal getDaylightDuration(LocalDate date) {
        return BigDecimal.valueOf(24).divide(BigDecimal.valueOf(Math.PI), PRECISION)
                .multiply(BigDecimal.valueOf(Math.acos(-Math.tan(Math.toRadians(latitude.doubleValue())) * Math.tan(Math.toRadians(getDeclinationAngle(date).doubleValue())))));
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
}
