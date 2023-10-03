package com.fadgiras.dev5etl.repository;

import com.fadgiras.dev5etl.model.PointVenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointVenteRepository extends JpaRepository<PointVenteEntity, Integer>{

}
