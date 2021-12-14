package gridnine.testing;


public abstract class FilterTypeArrivalBeforeDepartureDate implements FabricFilters {

    public abstract boolean filterRun(Flight flight);


    public Types getType() {
        return Types.ARRIVAL_BEFORE_DEPARTURE_DATE;
    }
}