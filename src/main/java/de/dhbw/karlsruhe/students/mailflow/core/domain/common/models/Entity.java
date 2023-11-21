package de.dhbw.karlsruhe.students.mailflow.core.domain.common.models;

/**
 * An abstract class template for every Entity object that will be created
 * 
 * Entities are objects with distinct identities. Two objects are considered
 * equal if their identifier is equal.
 * 
 * @param <T> The type of the identifier for the entity.
 */
public abstract class Entity<T> {
    private T id;

    /**
     * Creates a new entity with the specified identifier.
     *
     * @param id The unique identifier for the entity.
     * @throws IllegalArgumentException if the provided ID is null.
     */
    protected Entity(T id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        this.id = id;
    }

    /**
     * Gets the unique identifier of the entity.
     *
     * @return The entity's unique identifier.
     */
    public T getId() {
        return id;
    }

    /**
     * Computes the hash code of the entity only using its unique identifier.
     *
     * @return The hash code for the entity.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Compares this entity to another object for equality. Entities are considered
     * equal if their IDs are equal.
     *
     * @param obj The object to compare with this entity.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Entity<?> other = (Entity<?>) obj;
        return id.equals(other.id);
    }

    @Override
    public String toString() {
        return "Entity [id=" + id + "]";
    }
}