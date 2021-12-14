package gridnine.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.time.chrono.JapaneseEra.values;


class FilterBuilder {
    private FabricFilters[] filters;

    private final List<Flight> flightList = new ArrayList<>();

    FilterBuilder() {
        dump();
        filters = new FabricFilters[values().length];
    }



    void add(FabricFilters... filters) {
        Arrays.stream(filters).forEach(filter -> this.filters[filter.getType().ordinal()] = filter);
    }


    void choice(FabricFilters... filters) {
        dump();
        add(filters);
    }


    void dump() {
        filters = new FabricFilters[values().length];
    }


    List<Flight> filter(List<Flight> flights) {

        flights.forEach(flight -> {
            boolean b = Arrays.stream(filters).filter(Objects::nonNull).allMatch(filter -> filter.filterRun(flight));
            if (b) {flightList.add(flight);}});
        return flightList;
    }
}