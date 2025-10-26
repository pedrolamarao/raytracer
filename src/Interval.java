value record Interval(
        double min, double max
) {
    Interval() {
        this(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
    }

    double size() {
        return max - min;
    }

    boolean contains(double x) {
        return min <= x && x <= max;
    }

    boolean surrounds(double x) {
        return min < x && x < max;
    }

    static final Interval EMPTY = new Interval(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
    static final Interval UNIVERSE = new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
}