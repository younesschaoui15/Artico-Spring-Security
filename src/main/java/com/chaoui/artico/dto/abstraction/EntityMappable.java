package com.chaoui.artico.dto.abstraction;

public interface EntityMappable<E> {

    public void mapFromEntity(E entity);
    public E mapToEntity();

}
