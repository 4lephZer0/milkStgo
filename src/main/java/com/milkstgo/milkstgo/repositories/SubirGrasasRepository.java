package com.milkstgo.milkstgo.repositories;

import com.milkstgo.milkstgo.entities.SubirGrasasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubirGrasasRepository extends JpaRepository<SubirGrasasEntity, String>{
}