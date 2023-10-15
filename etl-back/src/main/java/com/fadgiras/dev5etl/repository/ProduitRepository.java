package com.fadgiras.dev5etl.repository;

import com.fadgiras.dev5etl.dto.ManufacturerProductDTO;
import com.fadgiras.dev5etl.model.ProduitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<ProduitEntity, Integer> {

    @Query(value = "SELECT NEW com.fadgiras.dev5etl.dto.ManufacturerProductDTO(p.fabid, COUNT(DISTINCT p.prodid)) FROM ProduitEntity p WHERE p.catid = ?1 GROUP BY p.fabid")
    List<ManufacturerProductDTO> countProduitByCategorie(Integer catid);

}
