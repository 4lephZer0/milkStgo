package com.milkstgo.milkstgo.repositories;

import com.milkstgo.milkstgo.entities.PlanillaPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PlanillaPagoRepository extends JpaRepository<PlanillaPagoEntity, String> {

    @Query(value = "SELECT pl FROM PlanillaPagoEntity pl WHERE pl.codigoProv = :codigoProv")
    ArrayList<PlanillaPagoEntity> findByCodigo(@Param("codigoProv") String codigoProv);

    @Query(value = "SELECT pl FROM PlanillaPagoEntity pl")
    ArrayList<PlanillaPagoEntity> findAll();

}
