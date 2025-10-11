// 代码生成时间: 2025-10-12 01:44:22
import io.micronaut.core.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class for sorting and filtering tables.
 */
public class TableSortFilter<T> {

    private final List<T> tableData;

    /**
     * Constructs a TableSortFilter with the provided table data.
     *
     * @param tableData The table data to sort and filter.
     */
    public TableSortFilter(List<T> tableData) {
        this.tableData = tableData;
    }

    /**
     * Sorts the table data based on the provided comparator.
     *
     * @param comparator The comparator to use for sorting.
     * @return The sorted table data.
     */
    public List<T> sort(Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        return tableData.stream()
                         .sorted(comparator)
                         .collect(Collectors.toList());
    }

    /**
     * Filters the table data based on the provided predicate.
     *
     * @param predicate The predicate to use for filtering.
     * @return The filtered table data.
     */
    public List<T> filter(@Nullable Predicate<T> predicate) {
        if (predicate == null) {
            return tableData;
        }
        return tableData.stream()
                         .filter(predicate)
                         .collect(Collectors.toList());
    }
}

/**
 * A functional interface for predicates used in filtering.
 */
@FunctionalInterface
interface Predicate<T> {
    boolean test(T t);
}