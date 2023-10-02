package com.fadgiras.dev5etl.controller;

import com.fadgiras.dev5etl.model.ProduitEntity;
import com.fadgiras.dev5etl.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prod")
public class ProduitController {

    @Autowired
    ProduitRepository produitRepository;

    @RequestMapping("/list")
    public List<ProduitEntity> getAllProduits() {
        return produitRepository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ProduitEntity addProduit(ProduitEntity produitEntity) {
        return produitRepository.save(produitEntity);
    }

    @RequestMapping(value = "/add/bulk", method = RequestMethod.POST)
    public List<ProduitEntity> addProduits(List<ProduitEntity> produitEntities) {
        return produitRepository.saveAll(produitEntities);
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

}
