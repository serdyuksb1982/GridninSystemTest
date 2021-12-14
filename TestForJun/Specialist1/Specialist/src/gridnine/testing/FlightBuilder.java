package gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Factory class to get sample list of flights.
 */
abstract class FlightBuilder {
    static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
                //A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight departing in the past
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //A flight that departs before it arrives (Timur Moziev: Did you mean: departs after?)
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                //A flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //Another flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }

    /**
     * A bulk list of (realistic-ish) flights for filter performance testing
     *
     * @param flightsNumber Fixed number of flights to create
     * @return List with created flights
     */
    static List<Flight> createFlightsBulk(int flightsNumber) {
        Random random = new Random();
        List<Flight> flights = new ArrayList<>(flightsNumber);

        LocalDateTime time = LocalDateTime.now();

        for (int i = 0; i < flightsNumber; i++) {
            if (random.nextBoolean()) {
                time = time.plusHours(random.nextInt(24) + 1);
            }

            int segmentsNumber = random.nextInt(5) + 1;
            List<Segment> segments = new ArrayList<>(segmentsNumber);

            LocalDateTime flightTime = time;

            for (int seg = 0; seg < segmentsNumber; seg++) {
                int airborneHours = random.nextInt(10) + 1;
                segments.add(new Segment(flightTime, flightTime.plusHours(airborneHours)));
                flightTime = flightTime.plusHours(airborneHours + random.nextInt(18)); // ground time
            }
            flights.add(new Flight(segments));
        }

        return flights;
    }

    /**
     * @param dates Vararg that accepts dates. Shouldn't be empty or even number
     * @return Flight instance
     */
    static Flight createFlight(final LocalDateTime... dates) {
        if (dates.length == 0) {
            throw new IllegalArgumentException(
                    "flight should have at least two segments");
        } else if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}
