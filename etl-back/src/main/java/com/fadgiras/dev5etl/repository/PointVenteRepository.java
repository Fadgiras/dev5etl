package com.fadgiras.dev5etl.repository;

import com.fadgiras.dev5etl.dto.*;
import com.fadgiras.dev5etl.model.PointVenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface PointVenteRepository extends JpaRepository<PointVenteEntity, Integer>{

    @Query(value = "SELECT pv FROM PointVenteEntity pv WHERE pv.fabid = ?1")
    List<PointVenteEntity> findByFabid(Integer fabid);

    @Query(value = "SELECT pv FROM PointVenteEntity pv WHERE pv.catid = ?1")
    List<PointVenteEntity> findByCatid(Integer fabid);

    @Query(value = "SELECT pv FROM PointVenteEntity pv WHERE pv.magid = ?1")
    List<PointVenteEntity> findByMagid(Integer fabid);

    @Query(value = "SELECT pv FROM PointVenteEntity pv WHERE pv.date >= ?1 AND pv.date <= ?2")
    List<PointVenteEntity> findBetweenDate(Date date1, Date date2);

    @Query(value = "SELECT NEW com.fadgiras.dev5etl.dto.ManufacturerProductDTO(p.fabid, COUNT(DISTINCT p.prodid)) FROM PointVenteEntity p WHERE p.catid = ?1 GROUP BY p.fabid")
    List<ManufacturerProductDTO> countProduitByCategorieManufacturer(Integer catid);

    @Query(value = "SELECT COUNT(p.prodid) FROM PointVenteEntity p WHERE p.catid = ?1 AND p.date >= ?2 AND p.date <= ?3")
    Integer countProduitByCategorieDate(Integer catid, Date date1, Date date2);

    @Query(value = " SELECT MAX(p.date) FROM ProduitEntity p")
    Date getLastDateEntry();

    @Query("SELECT NEW com.fadgiras.dev5etl.dto.MagasinProductDTO(p.magid, COUNT(p.prodid)) " +
            "FROM PointVenteEntity p " +
            "WHERE p.date BETWEEN :startDate AND :endDate " +
            "GROUP BY p.magid " +
            "ORDER BY COUNT(p.prodid) DESC")
    List<MagasinProductDTO> findTopMagasinsByProducts(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT COUNT(DISTINCT p.fabid) " +
            "FROM PointVenteEntity p " +
            "WHERE p.catid = :categoryId")
    long countDistinctActorsForCategory(@Param("categoryId") int categoryId);

    @Query("SELECT NEW com.fadgiras.dev5etl.dto.MagasinMarketProdDTO(p.magid, COUNT(p.prodid)) " +
            "FROM PointVenteEntity p " +
            "WHERE p.catid = :categoryId " +
            "AND p.date BETWEEN :startDate AND :endDate " +
            "GROUP BY p.magid " +
            "ORDER BY COUNT( p.prodid) DESC")
    List<MagasinMarketProdDTO> findTop10MagasinMarketShareForCategory(@Param("categoryId") int categoryId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT p.magid " +
            "FROM PointVenteEntity p " +
            "WHERE p.catid = :categoryId " +
            "AND p.date BETWEEN :startDate AND :endDate " +
            "GROUP BY p.magid " +
            "ORDER BY COUNT( p.prodid) DESC")
    ArrayList<Integer> findTop10MagasinIdForCategory(@Param("categoryId") int categoryId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT COUNT( p.prodid) " +
            "FROM PointVenteEntity p " +
            "WHERE p.catid = :categoryId " +
            "AND p.date BETWEEN :startDate AND :endDate " +
            "GROUP BY p.magid " +
            "ORDER BY COUNT( p.prodid) DESC")
    List<Integer> findTop10MagasinProdForCategory(@Param("categoryId") int categoryId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    @Query("SELECT COUNT(p.prodid) " +
            "FROM PointVenteEntity p " +
            "WHERE p.catid = :categoryId AND p.fabid = :fabId AND p.magid IN :magasinIds")
    List<Integer> productsFromManufacturerInTopMagasins(@Param("fabId") int fabId, @Param("categoryId") int categoryId, @Param("magasinIds") List<Integer> magasinIds);

    @Query("SELECT COUNT(p.prodid) " +
            "FROM PointVenteEntity p " +
            "WHERE p.catid = :categoryId AND p.magid IN :magasinIds")
    List<Integer> productsInTopMagasins(@Param("categoryId") int categoryId, @Param("magasinIds") List<Integer> magasinIds);

    @Query("SELECT COUNT(DISTINCT p.prodid) " +
            "FROM PointVenteEntity p " +
            "WHERE p.catid = :categoryId")
    long countDistinctProductsInCategory(@Param("categoryId") int categoryId);

    @Query("SELECT NEW com.fadgiras.dev5etl.dto.FabricantHealthDTO(p.magid, FUNCTION('DATE_TRUNC', 'month', p.date), COUNT(p.prodid)) " +
            "FROM PointVenteEntity p " +
            "WHERE p.catid = :categoryId AND p.fabid = :fabId AND p.date BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('DATE_TRUNC', 'month', p.date), p.magid " +
            "ORDER BY COUNT(p.prodid) DESC, FUNCTION('DATE_TRUNC', 'month', p.date) DESC")
    List<FabricantHealthDTO> findFabricantHealth(@Param("fabId") int fabId, @Param("categoryId") int categoryId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    //TODO : Query to get total of products sold by month for a category to make the market share in the column chart
    @Query("SELECT NEW com.fadgiras.dev5etl.dto.FabProdsDateDTO(COUNT(p.prodid), p.date) " +
            "FROM PointVenteEntity p " +
            "WHERE p.catid = :categoryId " +
//            "AND p.date BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('MONTH', p.date), p.date " +
            "ORDER BY p.date DESC")
    List<FabProdsDateDTO> countProductsInCategoryMonth(@Param("categoryId") int categoryId);


    @Query("SELECT NEW com.fadgiras.dev5etl.dto.FabProdsDateDTO(COUNT(p.prodid), p.date) " +
            "FROM PointVenteEntity p " +
            "WHERE p.catid = :categoryId " +
            "AND p.fabid = :fabId " +
//            "AND p.date BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('MONTH', p.date), p.date " +
            "ORDER BY p.date DESC")
    List<FabProdsDateDTO> countProductsInCategoryMonthByFab(@Param("categoryId") int categoryId, @Param("fabId") int fabid);
    //TODO : Query to get each share of market for a manufacturer by month for each magasin in top 10

    @Query("SELECT NEW com.fadgiras.dev5etl.dto.MagasinMarketProdDTO(p.magid, COUNT(p.prodid)) " +
            "FROM PointVenteEntity p " +
            "WHERE p.catid = :categoryId " +
            "AND p.fabid = :fabId " +
            "AND p.magid IN :magIds " +
            "AND p.date BETWEEN :startDate AND :endDate "+
            "GROUP BY p.magid")
    List<MagasinMarketProdDTO> getProductsCountByMagasinForCategoryAndFabricant(@Param("categoryId") int categoryId, @Param("fabId") int fabId, @Param("startDate") Date startDate, @Param("endDate") Date endDate , @Param("magIds") List<Integer> magIds);

    @Query("SELECT NEW com.fadgiras.dev5etl.dto.MagasinMarketProdDTO(p.magid, COUNT(p.prodid)) " +
            "FROM PointVenteEntity p " +
            "WHERE p.catid = :categoryId " +
            "AND p.magid IN :magIds " +
            "AND p.date BETWEEN :startDate AND :endDate "+
            "GROUP BY p.magid")
    List<MagasinMarketProdDTO> getProductsCountByMagasinForCategory(@Param("categoryId") int categoryId, @Param("startDate") Date startDate, @Param("endDate") Date endDate , @Param("magIds") List<Integer> magIds);



}
