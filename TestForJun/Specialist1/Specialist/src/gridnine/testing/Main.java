package gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.System.out;
import static java.time.temporal.ChronoUnit.*;

class Main {
    private static final int TIME_PERIOD = 7200;

    public static void main(String[] args) {

        List<Flight> flightList = FlightBuilder.createFlights();
        FilterBuilder filterBuilder = new FilterBuilder();
        int i = Math.toIntExact(flightList.size());
        out.printf("Общее колличество вылетов: %d\n", i);
        flightList.forEach(System.out::println);

        out.println("============================");

        out.println("1.\tвылет до текущего момента времени".concat(System.lineSeparator()) );

        filterBuilder.choice(new FilterTypeFlightForPeriod(LocalDateTime.now()) {
            @Override
            public boolean filterRun(Flight f) {
                return f.getSegments()
                        .parallelStream()
                        .noneMatch(s -> s.getDepartureDate().isBefore(dateTime));
            }});
        List<Flight> flightList1 = filterBuilder.filter(flightList);

        flightList1.forEach(out::println);

        out.println("============================");

        out.println("2.\tимеются сегменты с датой прилёта раньше даты вылета:".concat(System.lineSeparator()));

        filterBuilder.choice(new FilterTypeArrivalBeforeDepartureDate() {
            @Override
            public boolean filterRun(Flight f) {
                return f.getSegments()
                        .parallelStream()
                        .noneMatch(s -> s.getDepartureDate().isAfter(s.getArrivalDate()));
            }});
        List<Flight> flightList2 = filterBuilder.filter(flightList);

        flightList2.forEach(out::println);

        out.println("============================");

        out.println("3.\tобщее время, проведённое на земле превышает два часа (время на земле — это интервал между прилётом одного сегмента и вылетом следующего за ним):"
                .concat(System.lineSeparator()));

        filterBuilder.choice(new FilterTypeTimeOnEarth(TIME_PERIOD) {
            long time;
            List<Segment> s;
            @Override
            public boolean filterRun(Flight f) {
                s = f.getSegments();
                if (s.size() >= 2) {
                    IntStream.range(1, s.size()).parallel().forEach(i -> {
                        LocalDateTime arrivalDate = s.get(i - 1).getArrivalDate();
                        LocalDateTime departureDate = s.get(i).getDepartureDate();
                        time += arrivalDate.until(departureDate, SECONDS);});}
                return time <= timeOnEarth;}});
        List<Flight> flightList3 = filterBuilder.filter(flightList);

        flightList3.forEach(out::println);

    }

}