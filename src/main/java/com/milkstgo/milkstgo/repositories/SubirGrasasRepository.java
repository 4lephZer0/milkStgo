package com.milkstgo.milkstgo.repositories;

import com.milkstgo.milkstgo.entities.SubirAcopioEntity;
import com.milkstgo.milkstgo.entities.SubirGrasasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SubirGrasasRepository extends JpaRepository<SubirGrasasEntity, String>{

    @Query(value = "SELECT vg FROM SubirGrasasEntity vg WHERE vg.proveedor = :proveedor")
    SubirGrasasEntity findByProveedor(@Param("proveedor") String proveedor);

    @Query(value = "SELECT a FROM SubirGrasasEntity a")
    ArrayList<SubirGrasasEntity> findAll();

    @Modifying
    @Query(value = "DELETE FROM SubirGrasasEntity")
    void deleteAll();

}