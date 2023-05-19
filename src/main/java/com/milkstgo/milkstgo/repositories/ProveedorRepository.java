package com.milkstgo.milkstgo.repositories;

import com.milkstgo.milkstgo.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, String>{

    @Query(value = "SELECT e.codigo FROM ProveedorEntity e")
    ArrayList<String> findAllCodigo();

    @Query(value = "SELECT e FROM ProveedorEntity e WHERE e.codigo = :codigo")
    ProveedorEntity findByCodigo(@Param("codigo") String codigo);
}
