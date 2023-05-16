package com.milkstgo.milkstgo.repositories;

import com.milkstgo.milkstgo.entities.SubirAcopioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubirAcopioRepository extends JpaRepository<SubirAcopioEntity, String>{
}
