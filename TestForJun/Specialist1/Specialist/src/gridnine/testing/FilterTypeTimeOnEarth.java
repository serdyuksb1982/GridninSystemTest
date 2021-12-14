package gridnine.testing;


public abstract class FilterTypeTimeOnEarth implements FabricFilters {

    final long timeOnEarth;
    public abstract boolean filterRun(Flight flight);

    FilterTypeTimeOnEarth(long time) {
        this.timeOnEarth = time;
    }

    public Types getType() {
        return Types.TIME_ON_EARTH;
    }
}