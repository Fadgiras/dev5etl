package com.fadgiras.dev5etl.controller;

import com.fadgiras.dev5etl.dto.ManufacturerProductDTO;
import com.fadgiras.dev5etl.model.PointVenteEntity;
import com.fadgiras.dev5etl.model.ProduitEntity;
import com.fadgiras.dev5etl.repository.ProduitRepository;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prod")
public class ProduitController {

    @Autowired
    ProduitRepository produitRepository;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping("/list")
    public List<ProduitEntity> getAllProduits() {
        return produitRepository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ProduitEntity addProduit(@RequestBody ProduitEntity produitEntity) {
        return produitRepository.save(produitEntity);
    }

    @RequestMapping(value = "/add/bulk", method = RequestMethod.POST)
    public List<ProduitEntity> addProduits(@RequestBody List<ProduitEntity> produitEntities) {
        for (ProduitEntity produitEntity : produitEntities) {
            produitRepository.save(produitEntity);
        }
        return produitRepository.findAll();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteProduit(ProduitEntity produitEntity) {
        produitRepository.delete(produitEntity);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ProduitEntity updateProduit(ProduitEntity produitEntity) {
        return produitRepository.save(produitEntity);
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public ProduitEntity findProduit(@PathVariable(value = "id") int id) {
        return produitRepository.findById(id).get();
    }

    @RequestMapping(value = "/count/cat/{id}", method = RequestMethod.GET)
    public List<ManufacturerProductDTO> countProduitByCat(@PathVariable(value = "id") int id) {
        return produitRepository.countProduitByCategorie(id);
    }


    //1.2
    @RequestMapping(value = "/avg/cat/{id}", method = RequestMethod.GET)
    public Long avgProdByCategorie(@PathVariable(value = "id") int id) {
        List list = produitRepository.countProduitByCategorie(id);
        if (list.size() == 0) return null;
        Long sum = 0L;
        for (Object dto : list) {
            ManufacturerProductDTO manufacturerProductDTO = (ManufacturerProductDTO) dto;
            sum += manufacturerProductDTO.getProductCount();
        }
        return sum / list.size();
    }

    @RequestMapping(value = "/debug/add", method = RequestMethod.POST)
    public ProduitEntity addProduitDebug(@RequestBody ProduitBuffer produitBuffer) throws ParseException {
        System.err.println(produitBuffer);
        return produitRepository.save(produitBuffer.BuffertoEntity(produitBuffer));
    }

    @RequestMapping(value = "/debug/add/bulk", method = RequestMethod.POST)
    public List<ProduitEntity> addProduitsDebug(@RequestBody List<ProduitBuffer> produitBuffers) throws ParseException {
        for (ProduitBuffer produitBuffer : produitBuffers) {
            produitRepository.save(produitBuffer.BuffertoEntity(produitBuffer));
        }
        return produitRepository.findAll();
    }

    @RequestMapping(value = "/debug/add/bulk/json", method = RequestMethod.POST)
    public List<ProduitEntity> addProduitsDebugJson() throws ParseException, IOException, URISyntaxException {
        File file = new File(getClass().getClassLoader().getResource("produits-tous.json").toURI());
        System.err.println(file.getAbsolutePath());
        List<ProduitBuffer> produitBuffers = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, ProduitBuffer.class));
        for (ProduitBuffer produitBuffer : produitBuffers) {
            System.err.println(produitBuffer);
            produitRepository.save(produitBuffer.BuffertoEntity(produitBuffer));
        }
        return produitRepository.findAll();
    }

    @RequestMapping(value = "/debug/json/point", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity reduce() throws IOException, URISyntaxException {
        File file = new File(getClass().getClassLoader().getResource("pointsDeVente-tous.json").toURI());
        List<PointVenteBuffer> pointBuffers = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, PointVenteBuffer.class));
        List<PointVenteBuffer> reduced = pointBuffers;

        Set<PointVenteBuffer> set = new LinkedHashSet<>();

        // Add the elements to set
        set.addAll(pointBuffers);

        // Clear the list
        pointBuffers.clear();

        // add the elements of set
        // with no duplicates to the list
        pointBuffers.addAll(set);

        System.err.println(pointBuffers.size());
        System.err.println(reduced.size());
        System.err.println(pointBuffers.get(0));
        // return the list
        return ResponseEntity.ok(pointBuffers);
    }

}

class ProduitBuffer {
    @JsonAlias("DATE")
    private String date;
    @JsonAlias("PRODID")
    private int prodid;
    @JsonAlias("CATID")
    private int catid;
    @JsonAlias("FABID")
    private int fabid;

    ProduitBuffer(String date, int prodid, int catid, int fabid) {
        this.date = date;
        this.prodid = prodid;
        this.catid = catid;
        this.fabid = fabid;
    }

    public String getDate() {
        return date;
    }

    public int getProdid() {
        return prodid;
    }

    public int getCatid() {
        return catid;
    }

    public int getFabid() {
        return fabid;
    }

    ProduitEntity BuffertoEntity(ProduitBuffer produitBuffer) throws ParseException {
        ProduitEntity produitEntity = new ProduitEntity();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        produitEntity.setDate(formatter.parse(produitBuffer.getDate()));
        produitEntity.setProdid(produitBuffer.getProdid());
        produitEntity.setCatid(produitBuffer.getCatid());
        produitEntity.setFabid(produitBuffer.getFabid());

        return produitEntity;
    }

    @Override
    public String toString() {
        return "ProduitBuffer{" +
                "date='" + date + '\'' +
                ", prodid=" + prodid +
                ", catid=" + catid +
                ", fabid=" + fabid +
                '}';
    }
}

class PointVenteBuffer {
    @JsonAlias("DATE")
    private String date;

    @JsonAlias("PRODID")
    private int prodid;

    @JsonAlias("CATID")
    private int catid;

    @JsonAlias("FABID")
    private int fabid;

    @JsonAlias("MAGID")
    private int magid;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getProdid() {
        return prodid;
    }

    public void setProdid(int prodid) {
        this.prodid = prodid;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getFabid() {
        return fabid;
    }

    public void setFabid(int fabid) {
        this.fabid = fabid;
    }

    public int getMagid() {
        return magid;
    }

    public void setMagid(int magid) {
        this.magid = magid;
    }

    @Override
    public String toString() {
        return "PointVenteBuffer{" +
                "date='" + date + '\'' +
                ", prodid=" + prodid +
                ", catid=" + catid +
                ", fabid=" + fabid +
                ", magid=" + magid +
                '}';
    }
}
