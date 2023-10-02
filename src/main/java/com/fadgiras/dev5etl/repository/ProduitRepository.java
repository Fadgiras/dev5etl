package com.fadgiras.dev5etl.repository;

import com.fadgiras.dev5etl.model.ProduitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<ProduitEntity, Integer> {

}
