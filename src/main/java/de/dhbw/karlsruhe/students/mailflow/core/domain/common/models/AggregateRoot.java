package de.dhbw.karlsruhe.students.mailflow.core.domain.common.models;

/**
 * An abstract class template for every AggregateRoot object that will be
 * created.
 * Aggregate Roots are special types of entities that serve as the entry points
 * to aggregates and encapsulate the
 * consistency rules and operations within the aggregate.
 *
 * @param <T> The type of the identifier for the aggregate root.
 */
public abstract class AggregateRoot<T> extends Entity<T> {
    /**
     * Creates a new aggregate root with the specified identifier.
     *
     * @param id The unique identifier for the aggregate root.
     * @throws IllegalArgumentException if the provided ID is null.
     */
    protected AggregateRoot(T id) {
        super(id);
    }
}
