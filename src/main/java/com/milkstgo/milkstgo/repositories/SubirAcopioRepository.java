package com.milkstgo.milkstgo.repositories;

import com.milkstgo.milkstgo.entities.SubirAcopioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubirAcopioRepository extends JpaRepository<SubirAcopioEntity, String> {

    @Query(value = "SELECT a FROM SubirAcopioEntity a WHERE a.proveedor = :proveedor")
    List<SubirAcopioEntity> findByProveedor(@Param("proveedor") String proveedor);

    @Query(value = "SELECT a FROM SubirAcopioEntity a")
    List<SubirAcopioEntity> findAll();

    @Modifying
    @Query(value = "DELETE FROM SubirAcopioEntity")
    void deleteAll();
}