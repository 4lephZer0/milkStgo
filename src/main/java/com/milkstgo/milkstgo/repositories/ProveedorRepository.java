package com.milkstgo.milkstgo.repositories;


import com.milkstgo.milkstgo.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, String>{
}
