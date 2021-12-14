package gridnine.testing;

import java.time.LocalDateTime;


public abstract class FilterTypeFlightForPeriod implements FabricFilters {

    final LocalDateTime dateTime;
    public abstract boolean filterRun(Flight flight);

    FilterTypeFlightForPeriod(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    public Types getType() {
        return Types.FLIGHT_FOR_THE_PERIOD;
    }
}